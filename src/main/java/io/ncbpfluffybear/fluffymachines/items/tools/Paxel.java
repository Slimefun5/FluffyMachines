package io.ncbpfluffybear.fluffymachines.items.tools;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun5.utils.tags.SlimefunTag;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import io.ncbpfluffybear.fluffymachines.FluffyMachines;
import io.ncbpfluffybear.fluffymachines.utils.FluffyItems;
import io.ncbpfluffybear.fluffymachines.utils.CompatUtils;
import io.ncbpfluffybear.fluffymachines.utils.MaterialCompat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Paxel extends SlimefunItem implements Listener, NotPlaceable {

    public final Set<Material> axeBlocks = Stream.of(
            CompatUtils.tagValues("LOGS"),
            CompatUtils.tagValues("PLANKS"),
            CompatUtils.tagValues("WOODEN_STAIRS"),
            CompatUtils.tagValues("SIGNS"),
            CompatUtils.tagValues("WOODEN_FENCES"),
            CompatUtils.tagValues("FENCE_GATES"),
            CompatUtils.tagValues("WOODEN_TRAPDOORS"),
            CompatUtils.tagValues("WOODEN_PRESSURE_PLATES"),
            CompatUtils.tagValues("WOODEN_DOORS"),
            CompatUtils.tagValues("WOODEN_SLABS"),
            CompatUtils.tagValues("WOODEN_BUTTONS"),
            CompatUtils.tagValues("BANNERS"),
            CompatUtils.tagValues("LEAVES"),
            new HashSet<>(Arrays.asList(MaterialCompat.safe(XMaterial.CHEST), MaterialCompat.safe(XMaterial.TRAPPED_CHEST), MaterialCompat.safe(XMaterial.CRAFTING_TABLE), MaterialCompat.safe(XMaterial.SMITHING_TABLE),
                    MaterialCompat.safe(XMaterial.LOOM), MaterialCompat.safe(XMaterial.CARTOGRAPHY_TABLE), MaterialCompat.safe(XMaterial.FLETCHING_TABLE), MaterialCompat.safe(XMaterial.BARREL), MaterialCompat.safe(XMaterial.JUKEBOX),
                    MaterialCompat.safe(XMaterial.CAMPFIRE), MaterialCompat.safe(XMaterial.BOOKSHELF), MaterialCompat.safe(XMaterial.JACK_O_LANTERN), MaterialCompat.safe(XMaterial.CARVED_PUMPKIN),
                    MaterialCompat.safe(XMaterial.PUMPKIN), MaterialCompat.safe(XMaterial.MELON), MaterialCompat.safe(XMaterial.COMPOSTER), MaterialCompat.safe(XMaterial.BEEHIVE), MaterialCompat.safe(XMaterial.BEE_NEST),
                    MaterialCompat.safe(XMaterial.NOTE_BLOCK), MaterialCompat.safe(XMaterial.LADDER), MaterialCompat.safe(XMaterial.COCOA_BEANS), MaterialCompat.safe(XMaterial.DAYLIGHT_DETECTOR), MaterialCompat.safe(XMaterial.MUSHROOM_STEM),
                    MaterialCompat.safe(XMaterial.BROWN_MUSHROOM_BLOCK), MaterialCompat.safe(XMaterial.RED_MUSHROOM_BLOCK), MaterialCompat.safe(XMaterial.BAMBOO), MaterialCompat.safe(XMaterial.VINE), MaterialCompat.safe(XMaterial.LECTERN)))
    ).flatMap(Set::stream).collect(Collectors.toSet());

    public Paxel(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        Bukkit.getPluginManager().registerEvents(this, FluffyMachines.getInstance());
    }

    @EventHandler(ignoreCancelled = true)
    private void onMine(BlockDamageEvent e) {
        Player p = e.getPlayer();
        SlimefunItem sfItem = SlimefunItem.getByItem(p.getInventory().getItemInHand());

        if (sfItem != null && sfItem == FluffyItems.PAXEL.getItem()) {
            boolean netherite = false;
            Block b = e.getBlock();
            ItemStack item = p.getInventory().getItemInHand();

            Material blockType = b.getType();

            if (item.getType() == MaterialCompat.safe(XMaterial.NETHERITE_PICKAXE)
                    || item.getType() == MaterialCompat.safe(XMaterial.NETHERITE_AXE)
                    || item.getType() == MaterialCompat.safe(XMaterial.NETHERITE_SHOVEL)
            ) {
                netherite = true;
            }

            if (SlimefunTag.EXPLOSIVE_SHOVEL_BLOCKS.isTagged(blockType)) {
                if (netherite) {
                    item.setType(MaterialCompat.safe(XMaterial.NETHERITE_SHOVEL));
                } else {
                    item.setType(MaterialCompat.safe(XMaterial.DIAMOND_SHOVEL));
                }
            } else if (axeBlocks.contains(blockType)) {
                if (netherite) {
                    item.setType(MaterialCompat.safe(XMaterial.NETHERITE_AXE));
                } else {
                    item.setType(MaterialCompat.safe(XMaterial.DIAMOND_AXE));
                }
            } else {
                if (netherite) {
                    item.setType(MaterialCompat.safe(XMaterial.NETHERITE_PICKAXE));
                } else {
                    item.setType(MaterialCompat.safe(XMaterial.DIAMOND_PICKAXE));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onEntityHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getDamager();
        ItemStack item = p.getInventory().getItemInHand();
        SlimefunItem sfItem = SlimefunItem.getByItem(item);

        if (sfItem instanceof Paxel) {

            boolean netherite = item.getType() == MaterialCompat.safe(XMaterial.NETHERITE_PICKAXE)
                    || item.getType() == MaterialCompat.safe(XMaterial.NETHERITE_AXE)
                    || item.getType() == MaterialCompat.safe(XMaterial.NETHERITE_SHOVEL);

            if (netherite) {
                item.setType(MaterialCompat.safe(XMaterial.NETHERITE_AXE));
            } else {
                item.setType(MaterialCompat.safe(XMaterial.DIAMOND_AXE));
            }
        }

    }
}

