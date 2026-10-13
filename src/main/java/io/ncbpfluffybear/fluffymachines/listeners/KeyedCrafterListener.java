package io.ncbpfluffybear.fluffymachines.listeners;

import io.github.thebusybiscuit.slimefun5.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems;
import io.ncbpfluffybear.fluffymachines.machines.AutoCraftingTable;
import io.ncbpfluffybear.fluffymachines.machines.SmartFactory;
import io.ncbpfluffybear.fluffymachines.utils.Messages;
import io.ncbpfluffybear.fluffymachines.utils.Utils;
import java.util.Optional;
import javax.annotation.Nullable;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import io.ncbpfluffybear.fluffymachines.utils.MaterialCompat;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import io.ncbpfluffybear.fluffymachines.utils.HandCompat;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.utils.SlimefunUtils;

public class KeyedCrafterListener implements Listener {

    public KeyedCrafterListener() {
    }

    @EventHandler
    private void onSmartFactoryInteract(PlayerRightClickEvent e) {
        Optional<Block> clickedBlock = e.getClickedBlock();

        if (HandCompat.isMainHand(e) && e.useBlock() != Event.Result.DENY && clickedBlock.isPresent() && e.getPlayer().isSneaking()) {
            Optional<SlimefunItem> slimefunBlock = e.getSlimefunBlock();

            if (!slimefunBlock.isPresent()) {
                return;
            }

            SlimefunItem sfBlock = slimefunBlock.get();
            ItemStack item = e.getItem();
            Player p = e.getPlayer();
            SlimefunItem key = SlimefunItem.getByItem(item);
            Block b = clickedBlock.get();

            // Handle SmartFactory recipe setting
            if (sfBlock instanceof SmartFactory) {

                if (isCargoNode(key)) {
                    return;
                }
                e.cancel();

                if (key == null) {
                    Messages.send(p, ChatColor.RED, "keyed-crafter.no-vanilla-items", "You can not use vanilla items with this machine!");
                    return;
                }

                if (SmartFactory.getAcceptedItems().stream().anyMatch(i -> SlimefunUtils.isItemSimilar(i.item(), key.getItem(), false))) {

                    BlockStorage.addBlockInfo(b, "recipe", key.getId());
                    BlockStorage.getInventory(b).replaceExistingItem(SmartFactory.RECIPE_SLOT,
                            SmartFactory.getDisplayItem(key, ((RecipeDisplayItem) sfBlock).getDisplayRecipes())
                    );
                    Messages.send(p, ChatColor.GREEN, "keyed-crafter.recipe-set", "Target recipe set to %item%",
                            line -> line.replace("%item%", Messages.itemName(p, key)));
                } else {
                    Messages.send(p, ChatColor.RED, "keyed-crafter.unsupported", "This item is not supported!");
                }

            } else if (sfBlock instanceof AutoCraftingTable) {

                if (isCargoNode(key)) {
                    return;
                }
                e.cancel();

                if (item.getType() == MaterialCompat.safe(XMaterial.AIR)) {
                    Messages.send(p, ChatColor.RED, "keyed-crafter.right-click-hint", "Right click the machine with an item to set the vanilla recipe");
                    return;
                }

                BlockStorage.getInventory(b).replaceExistingItem(AutoCraftingTable.KEY_SLOT,
                        AutoCraftingTable.createKeyItem(item.getType())
                );

                Messages.send(p, ChatColor.GREEN, "keyed-crafter.recipe-set", "Target recipe set to %item%",
                        line -> line.replace("%item%", Messages.itemName(p, item)));
            }
        }
    }

    private boolean isCargoNode(@Nullable SlimefunItem recipe) {
        return recipe != null && (SlimefunUtils.isItemSimilar(recipe.getItem(), SlimefunItems.CARGO_INPUT_NODE.item(), false)
                || SlimefunUtils.isItemSimilar(recipe.getItem(), SlimefunItems.CARGO_OUTPUT_NODE.item(), false)
                || SlimefunUtils.isItemSimilar(recipe.getItem(), SlimefunItems.CARGO_OUTPUT_NODE_2.item(), false));
    }
}

