package io.ncbpfluffybear.fluffymachines.machines;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.ncbpfluffybear.fluffymachines.objects.AutoCrafter;
import org.bukkit.Material;
import io.ncbpfluffybear.fluffymachines.utils.MaterialCompat;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;

public class AutoEnhancedCraftingTable extends AutoCrafter {
    public AutoEnhancedCraftingTable(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe, "&6Auto Enhanced Crafting Table",
                MaterialCompat.safe(XMaterial.CRAFTING_TABLE), "&6Enhanced Crafting Table", RecipeType.ENHANCED_CRAFTING_TABLE
        );
    }
}

