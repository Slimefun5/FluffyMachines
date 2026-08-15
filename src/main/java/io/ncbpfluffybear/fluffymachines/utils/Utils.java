package io.ncbpfluffybear.fluffymachines.utils;

import io.github.thebusybiscuit.slimefun5.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun5.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun5.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun5.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun5.utils.ChestMenuUtils;
import io.ncbpfluffybear.fluffymachines.FluffyMachines;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import io.ncbpfluffybear.fluffymachines.compat.Pdc;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

public final class Utils {

    private static final String fluffykey = "fluffymachines:fluffykey";
    private static final String nonClickable = "fluffymachines:nonclickable";

    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    private Utils() {
    }

    public static String color(String str) {
        if (str == null) {
            return null;
        }

        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void send(CommandSender p, String message) {
        p.sendMessage(color("&7[&6FluffyMachines&7] &r" + message));
    }

    public static String multiBlockWarning() {
        return "&cThis is a Multiblock machine!";
    }

    // TODO: Deprecate custom model data method of detecting non interactables
    public static ItemStack buildNonInteractable(Material material, @Nullable String name, @Nullable String... lore) {
        ItemStack nonClickableItem = new ItemStack(material);
        ItemMeta NCMeta = nonClickableItem.getItemMeta();
        if (name != null) {
            NCMeta.setDisplayName(ChatColors.color(name));
        } else {
            NCMeta.setDisplayName(" ");
        }

        if (lore.length > 0) {
            List<String> lines = new ArrayList<>();

            for (String line : lore) {
                lines.add(ChatColor.translateAlternateColorCodes('&', line));
            }
            NCMeta.setLore(lines);
        }

        Pdc.setInt(NCMeta, nonClickable, 1);
        nonClickableItem.setItemMeta(NCMeta);
        return nonClickableItem;
    }

    // TODO: Deprecate custom model data method of detecting non interactables
    public static boolean checkNonInteractable(ItemStack item) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }

        return (Pdc.getInt(item.getItemMeta(), nonClickable, 0) == 1);
    }

    public static void createBorder(ChestMenu menu, ItemStack backgroundItem, int[] slots) {
        for (int slot : slots) {
            menu.addItem(slot, backgroundItem, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    public static BlockBreakHandler getDefaultBreakHandler(int[] inputs, int[] outputs) {
        return new SimpleBlockBreakHandler() {

            @Override
            public void onBlockBreak(@Nonnull Block b) {
                BlockMenu inv = BlockStorage.getInventory(b);

                if (inv != null) {
                    inv.dropItems(b.getLocation(), inputs);
                    inv.dropItems(b.getLocation(), outputs);
                }
            }

        };
    }

    public static void giveOrDropItem(Player p, ItemStack toGive) {
        for (ItemStack leftover : p.getInventory().addItem(toGive).values()) {
            p.getWorld().dropItemNaturally(p.getLocation(), leftover);
        }
    }

    /**
     * @implNote Slimefun core no longer bakes lore onto fresh item templates (name/lore are resolved
     *           per viewer at render time), so {@link ItemMeta#getLore()} returns null instead of an
     *           empty list until something explicitly adds a line.
     */
    @Nonnull
    public static List<String> loreOrEmpty(@Nullable ItemMeta meta) {
        return meta != null && meta.hasLore() ? meta.getLore() : Collections.emptyList();
    }

    /**
     * A mutable lore list to append to: the item's current lore, or a fresh empty list if it has none.
     *
     * @implNote See {@link #loreOrEmpty(ItemMeta)}; unlike that method this returns a modifiable list
     *           since callers here are about to add lines to it.
     */
    @Nonnull
    public static List<String> mutableLoreOf(@Nullable ItemMeta meta) {
        List<String> lore = meta != null ? meta.getLore() : null;
        return lore != null ? lore : new ArrayList<>();
    }

    /**
     * Sets a single lore line at {@code index} and persists it onto {@code meta}, padding with blank
     * lines first if the item has fewer lines than {@code index}.
     *
     * @implNote Since core no longer bakes a lore skeleton onto fresh item templates, {@code index} may
     *           not exist yet where an addon's item used to always ship with it pre-filled.
     */
    public static void setLoreLine(@Nonnull ItemMeta meta, int index, @Nonnull String line) {
        List<String> lore = mutableLoreOf(meta);

        while (lore.size() <= index) {
            lore.add("");
        }

        lore.set(index, line);
        meta.setLore(lore);
    }

    public static String getViewableName(ItemStack item) {
        if (item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        } else {
            return WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
        }
    }

    public static String toRoman(int number) {
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
    }

    public static ItemStack keyItem(ItemStack item) {
        ItemStack clone = item.clone();
        ItemMeta meta = clone.getItemMeta();
        Pdc.setInt(meta, fluffykey, 1);
        clone.setItemMeta(meta);
        return clone;
    }

    public static ItemStack unKeyItem(ItemStack item) {
        ItemStack clone = item.clone();
        ItemMeta meta = clone.getItemMeta();
        Pdc.remove(meta, fluffykey);
        clone.setItemMeta(meta);
        return clone;
    }

    public static boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
        return (p.hasPermission("slimefun.inventory.bypass")
                || Slimefun.getProtectionManager().hasPermission(
                p, b.getLocation(), Interaction.INTERACT_BLOCK));
    }

    // Don't use Slimefun's runsync
    public static BukkitTask runSync(Runnable r) {
        return FluffyMachines.getInstance() != null && FluffyMachines.getInstance().isEnabled() ?
                Bukkit.getScheduler().runTask(FluffyMachines.getInstance(), r) : null;
    }

    public static BukkitTask runSync(Runnable r, long delay) {
        return FluffyMachines.getInstance() != null && FluffyMachines.getInstance().isEnabled() ?
                Bukkit.getScheduler().runTaskLater(FluffyMachines.getInstance(), r, delay) : null;
    }
}


