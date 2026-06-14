package io.ncbpfluffybear.fluffymachines.items.tools;

import io.github.thebusybiscuit.slimefun5.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun5.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun5.core.handlers.ToolUseHandler;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun5.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.libraries.dough.blocks.Vein;
import io.github.thebusybiscuit.slimefun5.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import io.ncbpfluffybear.fluffymachines.utils.CompatUtils;
import io.ncbpfluffybear.fluffymachines.utils.MaterialCompat;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class Scythe extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

    private static final int MAX_BROKEN = 5;

    public Scythe(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        super.preRegister();

        addItemHandler(onBlockBreak());
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    public ToolUseHandler onBlockBreak() {
        return (e, tool, fortune, drops) -> {

            if (e instanceof AlternateBreakEvent) {
                return;
            }

            if (CompatUtils.isBlockDataAvailable()
                && CompatUtils.isAgeable(e.getBlock())
                && CompatUtils.getAge(e.getBlock())
                == CompatUtils.getMaximumAge(e.getBlock())) {
                List<Block> crops = Vein.find(e.getBlock(), MAX_BROKEN, b -> CompatUtils.isTagged("CROPS", b.getType()));

                crops.remove(e.getBlock());

                boolean creative = e.getPlayer().getGameMode() == GameMode.CREATIVE;

                for (Block b : crops) {
                    if (Slimefun.getProtectionManager().hasPermission(e.getPlayer(), b, Interaction.BREAK_BLOCK)) {
                        AlternateBreakEvent breakEvent = new AlternateBreakEvent(b, e.getPlayer());
                        Bukkit.getPluginManager().callEvent(breakEvent);
                        if (creative) {
                            b.setType(MaterialCompat.safe(XMaterial.AIR));
                        } else {
                            b.breakNaturally(tool);
                        }
                    }
                }
            }
        };
    }
}

