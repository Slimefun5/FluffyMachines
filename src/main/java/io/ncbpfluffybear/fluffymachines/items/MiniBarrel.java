package io.ncbpfluffybear.fluffymachines.items;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun5.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun5.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun5.utils.NumberUtils;
import io.ncbpfluffybear.fluffymachines.utils.Messages;
import io.ncbpfluffybear.fluffymachines.utils.Utils;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import io.ncbpfluffybear.fluffymachines.utils.MaterialCompat;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class MiniBarrel extends Barrel {
    private static final int MAX_STORAGE = 172800;

    public MiniBarrel(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe, MAX_STORAGE);
        addItemHandler(onPlace());
    }

    private BlockPlaceHandler onPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                BlockStorage.addBlockInfo(e.getBlock(), "max-size", String.valueOf(barrelCapacity.getValue()));
            }
        };
    }

    @Override
    protected void buildMenu(BlockMenu menu, Block b) {
        super.buildMenu(menu, b);
        menu.replaceExistingItem(13, CustomItemStack.create(MaterialCompat.safe(XMaterial.YELLOW_STAINED_GLASS_PANE),
                "&eChange barrel size", "&7> Click to change max size", "&eCurrent size: " + getCapacity(b),
                "&eSize limit: " + barrelCapacity.getValue()
        ));
        menu.addMenuClickHandler(13, (p, slot, item, action) -> {
            p.closeInventory();
            Messages.send(p, ChatColor.YELLOW, "mini-barrel.enter-size", "Type the new size of the barrel. Max size: %max%",
                    line -> line.replace("%max%", String.valueOf(barrelCapacity.getValue())));
            ChatUtils.awaitInput(p, message -> {
                String cleanMsg = message.replaceAll("[^0-9]", "");
                int renameSize = NumberUtils.getInt(cleanMsg, 0);

                if (renameSize == 0 || renameSize > barrelCapacity.getValue()) {
                    Messages.send(p, ChatColor.RED, "mini-barrel.out-of-range", "The new size must be between 0 and %max%",
                            line -> line.replace("%max%", String.valueOf(barrelCapacity.getValue())));
                    return;
                }

                if (renameSize < getStored(b)) {
                    Messages.send(p, ChatColor.RED, "mini-barrel.remove-items", "Remove items from the barrel before changing it to this size!");
                    return;
                }

                BlockStorage.addBlockInfo(b, "max-size", String.valueOf(renameSize));
                menu.replaceExistingItem(13, CustomItemStack.create(MaterialCompat.safe(XMaterial.YELLOW_STAINED_GLASS_PANE),
                        "&eChange barrel size", "&7> Click to change max size", "&eCurrent size: " + renameSize,
                        "&eSize limit: " + barrelCapacity.getValue()
                ));
                Messages.send(p, ChatColor.GREEN, "mini-barrel.size-changed", "Max size has been changed to %size%",
                        line -> line.replace("%size%", String.valueOf(renameSize)));
                updateMenu(b, menu, true, renameSize);
            });
            return false;
        });
    }

    @Override
    public int getCapacity(Block b) {
        String capacity = BlockStorage.getLocationInfo(b.getLocation(), "max-size");
        if (capacity == null) {
            BlockStorage.addBlockInfo(b, "max-size", String.valueOf(barrelCapacity.getValue()));
            return barrelCapacity.getValue();
        }
        return Integer.parseInt(capacity);
    }

    public static int getDisplayCapacity() {
        int capacity = Slimefun.getItemCfg().getInt("MINI_FLUFFY_BARREL.capacity");
        if (capacity == 0) {
            capacity = MAX_STORAGE;
        }

        return capacity;
    }
}

