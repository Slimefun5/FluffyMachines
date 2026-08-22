package io.ncbpfluffybear.fluffymachines.machines;

import io.github.thebusybiscuit.slimefun5.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun5.implementation.items.electric.machines.accelerators.AbstractGrowthAccelerator;
import io.github.thebusybiscuit.slimefun5.implementation.items.electric.machines.accelerators.CropGrowthAccelerator;
import io.github.thebusybiscuit.slimefun5.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import io.github.thebusybiscuit.slimefun5.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import io.ncbpfluffybear.fluffymachines.utils.CompatUtils;
import io.ncbpfluffybear.fluffymachines.utils.MaterialCompat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@link WaterSprinkler} speeds up the growth of nearby crops
 * when water is under the machine
 * Essentially a modified {@link CropGrowthAccelerator}
 *
 * @author FluffyBear
 */
public class WaterSprinkler extends AbstractGrowthAccelerator {

    public final ItemSetting<Double> successChance = new ItemSetting<>(this, "success-chance", 0.5);
    public static final int ENERGY_CONSUMPTION = 16;
    public static final int CAPACITY = 128;
    private static final int RADIUS = 2;
    private static final int PROGRESS_SLOT = 4;
    private static final ItemStack noWaterItem = CustomItemStack.create(MaterialCompat.safe(XMaterial.BUCKET),
        "&cNo water found",
        "",
        "&cPlease place water under the sprinkler!"
    );
    private static final ItemStack waterFoundItem = CustomItemStack.create(MaterialCompat.safe(XMaterial.WATER_BUCKET),
        "&bWater detected"
    );
    private final ItemSetting<Boolean> particles = new ItemSetting<>(this, "particles", true);

    public WaterSprinkler(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        createPreset(this, "&bWater Sprinkler", ChatColor.AQUA,
            blockMenuPreset -> {
                for (int i = 0; i < 9; i++)
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());

                blockMenuPreset.addItem(PROGRESS_SLOT, noWaterItem);
            });

        addItemSetting(successChance, particles);
    }

    public int getEnergyConsumption() {
        return ENERGY_CONSUMPTION;
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    public int getRadius() {
        return RADIUS;
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    protected void tick(@Nonnull Block b) {
        if (this.isDisabled()) {
            return;
        }

        final BlockMenu inv = BlockStorage.getInventory(b);
        boolean open = inv.hasViewer();

        if (b.getRelative(BlockFace.DOWN).getType() == MaterialCompat.safe(XMaterial.WATER)) {
            if (open) {
                inv.replaceExistingItem(PROGRESS_SLOT, waterFoundItem);
            }
        } else {
            if (open) {
                inv.replaceExistingItem(PROGRESS_SLOT, noWaterItem);
            }
            return;
        }

        // Crop growth here relies on BlockData (1.13+); skip gracefully on legacy versions.
        if (!CompatUtils.isBlockDataAvailable()) {
            return;
        }

        if (getCharge(b.getLocation()) >= getEnergyConsumption()) {
            for (int x = -getRadius(); x <= getRadius(); x++) {
                for (int z = -getRadius(); z <= getRadius(); z++) {
                    final Block block = b.getRelative(x, 0, z);

                    if (particles.getValue()) {
                        block.getWorld().spawnParticle(Particle.WATER_SPLASH, block.getLocation().add(0.5D, 0.5D,
                            0.5D), 4, 0.1F, 0.1F, 0.1F);
                    }

                    if (CompatUtils.isAgeable(block)) {
                        grow(block);
                        removeCharge(b.getLocation(), getEnergyConsumption());
                    }
                }
            }
        }
    }

    private void grow(@Nonnull Block crop) {

        final double random = ThreadLocalRandom.current().nextDouble();
        if (successChance.getValue() >= random) {
            if (crop.getType() == MaterialCompat.safe(XMaterial.SUGAR_CANE)) {
                for (int i = 1; i < 3; i++) {
                    final Block above = crop.getRelative(BlockFace.UP, i);
                    if (CompatUtils.isAir(above.getType())) {
                        above.setType(MaterialCompat.safe(XMaterial.SUGAR_CANE));
                        break;
                    } else if (above.getType() != MaterialCompat.safe(XMaterial.SUGAR_CANE)) {
                        return;
                    }
                }
            } else {
                int age = CompatUtils.getAge(crop);
                if (age >= 0 && age < CompatUtils.getMaximumAge(crop)) {

                    CompatUtils.setAge(crop, age + 1);

                    crop.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, crop.getLocation().add(0.5D, 0.5D, 0.5D),
                        4, 0.1F, 0.1F, 0.1F);
                }
            }
        }
    }

}

