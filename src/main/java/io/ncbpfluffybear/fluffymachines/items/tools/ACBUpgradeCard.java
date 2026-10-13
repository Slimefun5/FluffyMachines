package io.ncbpfluffybear.fluffymachines.items.tools;

import io.github.thebusybiscuit.slimefun5.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun5.implementation.items.SimpleSlimefunItem;
import io.ncbpfluffybear.fluffymachines.utils.FluffyItems;
import io.ncbpfluffybear.fluffymachines.utils.Messages;
import io.ncbpfluffybear.fluffymachines.utils.Utils;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.ItemHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import io.ncbpfluffybear.fluffymachines.utils.HandCompat;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ACBUpgradeCard extends SimpleSlimefunItem<ItemHandler> {

    private static final int MAX_TIER = 100;

    public ACBUpgradeCard(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
        setGuideType("resources");
    }

    @Nonnull
    @Override
    public ItemHandler getItemHandler() {
        return (ItemUseHandler) e -> {
            // Prevent offhand right clicks
            if (!HandCompat.isMainHand(e)) {
                return;
            }

            // Block exists
            Optional<Block> optB = e.getClickedBlock();

            if (!optB.isPresent()) {
                return;
            }

            // Prevent menu opening and interactions
            e.cancel();

            Block b = optB.get();
            SlimefunItem sfItem = BlockStorage.check(b);
            Player p = e.getPlayer();
            ItemStack card = p.getInventory().getItemInHand();

            // Make sure the block is an ACB
            if (sfItem == null || sfItem != FluffyItems.ADVANCED_CHARGING_BENCH.getItem()) {
                Messages.send(e.getPlayer(), ChatColor.RED, "charging-bench.wrong-target", "You can only use this card on an %item%",
                        line -> line.replace("%item%", Messages.itemName(e.getPlayer(), FluffyItems.ADVANCED_CHARGING_BENCH)));
                return;
            }

            // Increment the tier by 1
            int tier = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "tier"));
            if (tier == MAX_TIER) {
                Messages.send(e.getPlayer(), ChatColor.RED, "charging-bench.maxed", "This %item% is maxed (Tier %max%)",
                        line -> line.replace("%item%", Messages.itemName(e.getPlayer(), FluffyItems.ADVANCED_CHARGING_BENCH))
                                .replace("%max%", String.valueOf(MAX_TIER)));
                return;
            }
            int upgradedTier = tier + 1;
            BlockStorage.addBlockInfo(b.getLocation(), "tier", String.valueOf(upgradedTier));

            // Remove a card
            card.setAmount(card.getAmount() - 1);

            Messages.send(e.getPlayer(), ChatColor.GREEN, "charging-bench.upgraded", "This %item% has been upgraded! %tier%",
                    line -> line.replace("%item%", Messages.itemName(e.getPlayer(), FluffyItems.ADVANCED_CHARGING_BENCH))
                            .replace("%tier%", ChatColor.YELLOW + Messages.get(e.getPlayer(), "charging-bench.tier", "Tier: %tier%")
                                    .replace("%tier%", String.valueOf(upgradedTier))));
        };
    }
}

