package io.ncbpfluffybear.fluffymachines.utils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;
import io.github.thebusybiscuit.slimefun5.utils.compatibility.BukkitKeys;

/**
 * Java-8 universal port: reflective guards around Bukkit API members that only exist on MC 1.13+/1.14+
 * ({@code RecipeChoice}, {@code Material.isAir()}, {@code Tag}, {@code BlockData},
 * {@code ItemMeta.hasCustomModelData()}, the persistent-data container). On servers without them
 * (1.8&ndash;1.12) these helpers degrade gracefully instead of throwing
 * {@code NoSuchMethodError}/{@code NoClassDefFoundError}.
 */
public final class CompatUtils {

    private static final boolean RECIPE_CHOICE_AVAILABLE = classExists("org.bukkit.inventory.RecipeChoice");
    private static final boolean BLOCK_DATA_AVAILABLE = classExists("org.bukkit.block.data.BlockData");
    private static final boolean TAG_AVAILABLE = classExists("org.bukkit.Tag");

    private static final Method GET_CHOICE_MAP = method(ShapedRecipe.class, "getChoiceMap");
    private static final Method GET_CHOICE_LIST = method(ShapelessRecipe.class, "getChoiceList");
    private static final Method IS_AIR = method(Material.class, "isAir");
    private static final Method HAS_CUSTOM_MODEL_DATA = method(ItemMeta.class, "hasCustomModelData");

    private CompatUtils() {}

    public static boolean isRecipeChoiceAvailable() {
        return RECIPE_CHOICE_AVAILABLE;
    }

    public static boolean isBlockDataAvailable() {
        return BLOCK_DATA_AVAILABLE;
    }

    public static boolean isTagAvailable() {
        return TAG_AVAILABLE;
    }

    /**
     * Returns the {@code getChoiceMap()} entries of a shaped recipe as {@code Character -> RecipeChoice}
     * (typed as {@link Object} so {@code RecipeChoice} is never referenced in bytecode), or an empty map
     * on versions without {@code RecipeChoice}.
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static Map<Character, Object> getChoiceMap(@Nonnull ShapedRecipe recipe) {
        if (!RECIPE_CHOICE_AVAILABLE || GET_CHOICE_MAP == null) {
            return Collections.emptyMap();
        }

        try {
            Object result = GET_CHOICE_MAP.invoke(recipe);
            return result != null ? (Map<Character, Object>) result : Collections.emptyMap();
        } catch (ReflectiveOperationException e) {
            return Collections.emptyMap();
        }
    }

    /**
     * Returns the {@code getChoiceList()} of a shapeless recipe (elements typed as {@link Object}), or an
     * empty list on versions without {@code RecipeChoice}.
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static List<Object> getShapelessChoiceList(@Nonnull ShapelessRecipe recipe) {
        if (!RECIPE_CHOICE_AVAILABLE || GET_CHOICE_LIST == null) {
            return new java.util.ArrayList<>();
        }

        try {
            Object result = GET_CHOICE_LIST.invoke(recipe);
            return result != null ? new java.util.ArrayList<>((List<Object>) result) : new java.util.ArrayList<>();
        } catch (ReflectiveOperationException e) {
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Reflectively invokes {@code RecipeChoice#test(ItemStack)}. Returns {@code false} when the type is
     * unavailable.
     */
    public static boolean testChoice(@Nullable Object recipeChoice, @Nullable ItemStack item) {
        if (recipeChoice == null) {
            return false;
        }

        try {
            Method test = recipeChoice.getClass().getMethod("test", ItemStack.class);
            Object result = test.invoke(recipeChoice, item);
            return Boolean.TRUE.equals(result);
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public static boolean isAir(@Nonnull Material material) {
        if (IS_AIR != null) {
            try {
                return Boolean.TRUE.equals(IS_AIR.invoke(material));
            } catch (ReflectiveOperationException ignored) {
                // fall through to name-based check
            }
        }

        String name = material.name();
        return name.equals("AIR") || name.equals("CAVE_AIR") || name.equals("VOID_AIR");
    }

    public static boolean hasCustomModelData(@Nullable ItemMeta meta) {
        if (meta == null || HAS_CUSTOM_MODEL_DATA == null) {
            return false;
        }

        try {
            return Boolean.TRUE.equals(HAS_CUSTOM_MODEL_DATA.invoke(meta));
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    /**
     * Resolves the {@link Material}s of a {@code Tag} (by registry key, e.g. {@code "logs"}) reflectively.
     * Returns an empty set on versions without {@code Tag}.
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static Set<Material> tagValues(@Nonnull String tagKey) {
        if (!TAG_AVAILABLE) {
            return Collections.emptySet();
        }

        try {
            Class<?> tagClass = Class.forName("org.bukkit.Tag");
            Object tag = tagClass.getField(tagKey.toUpperCase()).get(null);
            Object values = tag.getClass().getMethod("getValues").invoke(tag);
            return new HashSet<>((java.util.Collection<Material>) values);
        } catch (ReflectiveOperationException e) {
            return Collections.emptySet();
        }
    }

    public static boolean isTagged(@Nonnull String tagKey, @Nonnull Material material) {
        return tagValues(tagKey).contains(material);
    }

    /**
     * Returns the facing {@link BlockFace} of a directional block, using the 1.13+ {@code BlockData}
     * API when present and falling back to the legacy {@code MaterialData} {@code Directional} on 1.8.
     * Returns {@link BlockFace#SELF} when the block is not directional.
     */
    @Nonnull
    public static BlockFace getFacing(@Nonnull Block block) {
        if (BLOCK_DATA_AVAILABLE) {
            try {
                Object data = Block.class.getMethod("getBlockData").invoke(block);
                Class<?> directional = Class.forName("org.bukkit.block.data.Directional");
                if (directional.isInstance(data)) {
                    Object facing = directional.getMethod("getFacing").invoke(data);
                    return (BlockFace) facing;
                }
            } catch (ReflectiveOperationException ignored) {
                // fall through to legacy path
            }
            return BlockFace.SELF;
        }

        try {
            Object state = block.getState();
            Object materialData = state.getClass().getMethod("getData").invoke(state);
            Class<?> legacyDirectional = Class.forName("org.bukkit.material.Directional");
            if (legacyDirectional.isInstance(materialData)) {
                Object facing = legacyDirectional.getMethod("getFacing").invoke(materialData);
                return (BlockFace) facing;
            }
        } catch (ReflectiveOperationException ignored) {
            // not directional on this version
        }
        return BlockFace.SELF;
    }

    /**
     * Reflectively reads {@code block.getBlockData()} (1.13+). Returns {@code null} on legacy versions or
     * when the data is unavailable. The result is typed as {@link Object} so {@code BlockData} never enters
     * the bytecode of callers.
     */
    @Nullable
    private static Object blockData(@Nonnull Block block) {
        if (!BLOCK_DATA_AVAILABLE) {
            return null;
        }

        try {
            return Block.class.getMethod("getBlockData").invoke(block);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    private static void applyBlockData(@Nonnull Block block, @Nonnull Object data) {
        try {
            Class<?> blockDataClass = Class.forName("org.bukkit.block.data.BlockData");
            Block.class.getMethod("setBlockData", blockDataClass).invoke(block, data);
        } catch (ReflectiveOperationException ignored) {
            // not supported on this version
        }
    }

    private static boolean isInstanceOf(@Nullable Object data, @Nonnull String className) {
        if (data == null) {
            return false;
        }

        try {
            return Class.forName(className).isInstance(data);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    // --- Ageable crops (1.13+) routed through reflection ---

    /**
     * Returns {@code true} when the block's data is an {@code org.bukkit.block.data.Ageable}.
     */
    public static boolean isAgeable(@Nonnull Block block) {
        return isInstanceOf(blockData(block), "org.bukkit.block.data.Ageable");
    }

    /**
     * Returns the current age of an {@code Ageable} block, or {@code -1} when it is not ageable.
     */
    public static int getAge(@Nonnull Block block) {
        Object data = blockData(block);
        if (!isInstanceOf(data, "org.bukkit.block.data.Ageable")) {
            return -1;
        }

        try {
            Object age = data.getClass().getMethod("getAge").invoke(data);
            return age instanceof Integer ? (Integer) age : -1;
        } catch (ReflectiveOperationException e) {
            return -1;
        }
    }

    /**
     * Returns the maximum age of an {@code Ageable} block, or {@code -1} when it is not ageable.
     */
    public static int getMaximumAge(@Nonnull Block block) {
        Object data = blockData(block);
        if (!isInstanceOf(data, "org.bukkit.block.data.Ageable")) {
            return -1;
        }

        try {
            Object max = data.getClass().getMethod("getMaximumAge").invoke(data);
            return max instanceof Integer ? (Integer) max : -1;
        } catch (ReflectiveOperationException e) {
            return -1;
        }
    }

    /**
     * Sets the age of an {@code Ageable} block and writes it back. No-op when the block is not ageable.
     */
    public static void setAge(@Nonnull Block block, int age) {
        Object data = blockData(block);
        if (!isInstanceOf(data, "org.bukkit.block.data.Ageable")) {
            return;
        }

        try {
            data.getClass().getMethod("setAge", int.class).invoke(data, age);
            applyBlockData(block, data);
        } catch (ReflectiveOperationException ignored) {
            // not supported on this version
        }
    }

    // --- Orientable logs (1.13+) routed through reflection ---

    /**
     * Returns the {@link Axis} of an {@code Orientable} block, or {@code null} when it is not orientable.
     */
    @Nullable
    public static Axis getOrientableAxis(@Nonnull Block block) {
        Object data = blockData(block);
        if (!isInstanceOf(data, "org.bukkit.block.data.Orientable")) {
            return null;
        }

        try {
            Object axis = data.getClass().getMethod("getAxis").invoke(data);
            return axis instanceof Axis ? (Axis) axis : null;
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    /**
     * Sets the {@link Axis} of an {@code Orientable} block and writes it back. No-op when not orientable.
     */
    public static void setOrientableAxis(@Nonnull Block block, @Nonnull Axis axis) {
        Object data = blockData(block);
        if (!isInstanceOf(data, "org.bukkit.block.data.Orientable")) {
            return;
        }

        try {
            data.getClass().getMethod("setAxis", Axis.class).invoke(data, axis);
            applyBlockData(block, data);
        } catch (ReflectiveOperationException ignored) {
            // not supported on this version
        }
    }

    // --- Directional / Chest block data (1.13+) routed through reflection ---

    /**
     * Sets the facing {@link BlockFace} of a {@code Directional} block and writes it back. No-op when the
     * block is not directional.
     */
    public static void setDirectionalFacing(@Nonnull Block block, @Nonnull BlockFace face) {
        Object data = blockData(block);
        if (!isInstanceOf(data, "org.bukkit.block.data.Directional")) {
            return;
        }

        try {
            data.getClass().getMethod("setFacing", BlockFace.class).invoke(data, face);
            applyBlockData(block, data);
        } catch (ReflectiveOperationException ignored) {
            // not supported on this version
        }
    }

    /**
     * Sets the {@code Chest.Type} (by enum name, e.g. {@code "LEFT"}, {@code "RIGHT"}, {@code "SINGLE"}) of a
     * chest block and writes it back. No-op when the block is not a {@code Chest} block data.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void setChestType(@Nonnull Block block, @Nonnull String typeName) {
        Object data = blockData(block);
        if (!isInstanceOf(data, "org.bukkit.block.data.type.Chest")) {
            return;
        }

        try {
            Class<?> chestClass = Class.forName("org.bukkit.block.data.type.Chest");
            Class<?> typeEnum = Class.forName("org.bukkit.block.data.type.Chest$Type");
            Object type = Enum.valueOf((Class<? extends Enum>) typeEnum, typeName);
            chestClass.getMethod("setType", typeEnum).invoke(data, type);
            applyBlockData(block, data);
        } catch (ReflectiveOperationException | IllegalArgumentException ignored) {
            // not supported on this version
        }
    }

    // --- Persistent data container (1.14+) routed through reflection on the real NamespacedKey ---

    public static void setPdcByte(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key, byte value) {
        pdcSet(meta, key, "BYTE", value);
    }

    public static void setPdcInt(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key, int value) {
        pdcSet(meta, key, "INTEGER", value);
    }

    public static void setPdcString(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key, @Nonnull String value) {
        pdcSet(meta, key, "STRING", value);
    }

    public static boolean hasPdc(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key, @Nonnull String typeName) {
        Object container = container(meta);
        Object bukkitKey = BukkitKeys.toBukkit(key);
        Object type = dataType(typeName);

        if (container == null || bukkitKey == null || type == null) {
            return false;
        }

        try {
            Class<?> pdcClass = container.getClass();
            Class<?> keyClass = Class.forName("org.bukkit.NamespacedKey");
            Class<?> typeClass = Class.forName("org.bukkit.persistence.PersistentDataType");
            Method has = findMethod(pdcClass, "has", keyClass, typeClass);
            return Boolean.TRUE.equals(has.invoke(container, bukkitKey, type));
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public static int getPdcInt(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key, int defaultValue) {
        Object value = pdcGet(meta, key, "INTEGER", defaultValue);
        return value instanceof Integer ? (Integer) value : defaultValue;
    }

    public static byte getPdcByte(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key, byte defaultValue) {
        Object value = pdcGet(meta, key, "BYTE", defaultValue);
        return value instanceof Byte ? (Byte) value : defaultValue;
    }

    @Nullable
    public static String getPdcString(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key) {
        Object value = pdcGet(meta, key, "STRING", null);
        return value instanceof String ? (String) value : null;
    }

    public static void removePdc(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key) {
        Object container = container(meta);
        Object bukkitKey = BukkitKeys.toBukkit(key);

        if (container == null || bukkitKey == null) {
            return;
        }

        try {
            Class<?> keyClass = Class.forName("org.bukkit.NamespacedKey");
            Method remove = findMethod(container.getClass(), "remove", keyClass);
            remove.invoke(container, bukkitKey);
        } catch (ReflectiveOperationException ignored) {
            // not supported on this version
        }
    }

    private static void pdcSet(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key, @Nonnull String typeName, @Nonnull Object value) {
        Object container = container(meta);
        Object bukkitKey = BukkitKeys.toBukkit(key);
        Object type = dataType(typeName);

        if (container == null || bukkitKey == null || type == null) {
            return;
        }

        try {
            Class<?> keyClass = Class.forName("org.bukkit.NamespacedKey");
            Class<?> typeClass = Class.forName("org.bukkit.persistence.PersistentDataType");
            Method set = findMethod(container.getClass(), "set", keyClass, typeClass, Object.class);
            set.invoke(container, bukkitKey, type, value);
        } catch (ReflectiveOperationException ignored) {
            // not supported on this version
        }
    }

    @Nullable
    private static Object pdcGet(@Nonnull ItemMeta meta, @Nonnull NamespacedKey key, @Nonnull String typeName, @Nullable Object defaultValue) {
        Object container = container(meta);
        Object bukkitKey = BukkitKeys.toBukkit(key);
        Object type = dataType(typeName);

        if (container == null || bukkitKey == null || type == null) {
            return defaultValue;
        }

        try {
            Class<?> keyClass = Class.forName("org.bukkit.NamespacedKey");
            Class<?> typeClass = Class.forName("org.bukkit.persistence.PersistentDataType");
            Method get = findMethod(container.getClass(), "get", keyClass, typeClass);
            Object result = get.invoke(container, bukkitKey, type);
            return result != null ? result : defaultValue;
        } catch (ReflectiveOperationException e) {
            return defaultValue;
        }
    }

    @Nullable
    private static Object container(@Nonnull ItemMeta meta) {
        try {
            return ItemMeta.class.getMethod("getPersistentDataContainer").invoke(meta);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    @Nullable
    private static Object dataType(@Nonnull String typeName) {
        try {
            return Class.forName("org.bukkit.persistence.PersistentDataType").getField(typeName).get(null);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    @Nullable
    private static Method findMethod(@Nonnull Class<?> owner, @Nonnull String name, @Nonnull Class<?>... params) {
        try {
            return owner.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            for (Method m : owner.getMethods()) {
                if (m.getName().equals(name) && m.getParameterCount() == params.length) {
                    return m;
                }
            }
            return null;
        }
    }

    private static boolean classExists(@Nonnull String name) {
        try {
            Class.forName(name);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    @Nullable
    private static Method method(@Nonnull Class<?> owner, @Nonnull String name, @Nonnull Class<?>... params) {
        try {
            return owner.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
