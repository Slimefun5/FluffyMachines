package io.ncbpfluffybear.fluffymachines.items.tools;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.attributes.HologramOwner;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun5.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun5.utils.SlimefunUtils;
import io.ncbpfluffybear.fluffymachines.FluffyMachines;
import io.ncbpfluffybear.fluffymachines.utils.FluffyItems;
import io.ncbpfluffybear.fluffymachines.utils.Utils;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import io.ncbpfluffybear.fluffymachines.compat.Pdc;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import io.ncbpfluffybear.fluffymachines.utils.HandCompat;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class WarpPadConfigurator extends SlimefunItem implements HologramOwner, Listener {

    private static final String xCoord = "fluffymachines:xcoordinate";
    private static final String yCoord = "fluffymachines:ycoordinate";
    private static final String zCoord = "fluffymachines:zcoordinate";
    private static final String world = "fluffymachines:world";

    private static final int LORE_COORDINATE_INDEX = 4;
    private final ItemSetting<Integer> MAX_DISTANCE = new ItemSetting<>(this, "max-distance", 100);

    public WarpPadConfigurator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        Bukkit.getPluginManager().registerEvents(this, FluffyMachines.getInstance());

        addItemSetting(MAX_DISTANCE);

    }

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {

        if (e.getClickedBlock() == null || !HandCompat.isMainHand(e)) {
            return;
        }

        Block b = e.getClickedBlock();
        Player p = e.getPlayer();

        if (BlockStorage.hasBlockInfo(b) && BlockStorage.check(b) == FluffyItems.WARP_PAD.getItem()
            && Slimefun.getProtectionManager().hasPermission(p, b.getLocation(), Interaction.PLACE_BLOCK)) {
            if (SlimefunUtils.isItemSimilar(p.getInventory().getItemInHand(), FluffyItems.WARP_PAD_CONFIGURATOR.item(),
                false)) {

                ItemStack item = p.getInventory().getItemInHand();
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();

                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    // Destination
                    if (p.isSneaking()) {
                        Pdc.setString(meta, world, b.getWorld().getName());

                        Pdc.setInt(meta, xCoord, b.getX());
                        Pdc.setInt(meta, yCoord, b.getY());
                        Pdc.setInt(meta, zCoord, b.getZ());
                        lore.set(LORE_COORDINATE_INDEX, ChatColor.translateAlternateColorCodes(
                            '&', "&eLinked Coordinates: &7" + b.getX() + ", " + b.getY() + ", " + b.getZ()));

                        meta.setLore(lore);
                        item.setItemMeta(meta);

                        updateHologram(b, "&a&lDestination");
                        BlockStorage.addBlockInfo(b, "type", "destination");
                        Utils.send(p, "&3This pad has been marked as a &aDestination &3and bound to your configurator");

                    // Origin
                    } else if (b.getWorld().getName().equals(Pdc.getString(meta, world, ""))) {
                        int x = Pdc.getInt(meta, xCoord, 0);
                        int y = Pdc.getInt(meta, yCoord, 0);
                        int z = Pdc.getInt(meta, zCoord, 0);

                        if (Math.abs(x - b.getX()) > MAX_DISTANCE.getValue()
                            || Math.abs(z - b.getZ()) > MAX_DISTANCE.getValue()) {

                            Utils.send(p, "&cYou can not link blocks more than "
                                + MAX_DISTANCE.getValue() + " blocks apart!");

                            return;
                        }

                        registerOrigin(b, x, y, z);

                        Utils.send(p, "&3This pad has been marked as an &aOrigin &3and your configurator's settings " +
                            "have been pasted onto this pad");

                    } else {

                        Utils.send(p, "&cSneak and right click on a Warp Pad to set the destination, then right click" +
                            " " + "another Warp Pad tp set the origin!");
                    }

                }

            } else {
                Utils.send(p, "&cConfigure this Warp Pad using a Warp Pad Configurator");
            }
        }
    }

    private void registerOrigin(Block b, int x, int y, int z) {
        BlockStorage.addBlockInfo(b, "type", "origin");

        BlockStorage.addBlockInfo(b, "x", String.valueOf(x));
        BlockStorage.addBlockInfo(b, "y", String.valueOf(y));
        BlockStorage.addBlockInfo(b, "z", String.valueOf(z));

        updateHologram(b, "&a&lOrigin");
    }
}

