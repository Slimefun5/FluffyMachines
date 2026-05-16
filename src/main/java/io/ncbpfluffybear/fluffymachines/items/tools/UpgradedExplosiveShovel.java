package io.ncbpfluffybear.fluffymachines.items.tools;

import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun5.implementation.items.tools.ExplosivePickaxe;
import io.github.thebusybiscuit.slimefun5.utils.tags.SlimefunTag;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.libraries.dough.protection.Interaction;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The {@link UpgradedExplosiveShovel} works similar to the
 * {@link io.github.thebusybiscuit.slimefun5.implementation.items.tools.ExplosivePickaxe}.
 * However it can only break blocks that a shovel can break.
 *
 * @author Linox, NCBPFluffyBear
 * @see ExplosivePickaxe
 * @see UpgradedExplosiveTool
 */
public class UpgradedExplosiveShovel extends UpgradedExplosiveTool {

    public UpgradedExplosiveShovel(ItemGroup category, SlimefunItemStack item, RecipeType recipeType,
                                   ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected boolean canBreak(Player p, Block b) {
        return SlimefunTag.EXPLOSIVE_SHOVEL_BLOCKS.isTagged(b.getType())
            && Slimefun.getProtectionManager().hasPermission(p, b.getLocation(), Interaction.BREAK_BLOCK);
    }

}

