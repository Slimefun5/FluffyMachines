package io.ncbpfluffybear.fluffymachines.utils;

import dev.j3fftw.extrautils.utils.LoreBuilderDynamic;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun5.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun5.utils.LoreBuilder;
import io.github.thebusybiscuit.slimefun5.utils.itemstack.ColoredFireworkStar;
import io.ncbpfluffybear.fluffymachines.items.FireproofRune;
import io.ncbpfluffybear.fluffymachines.items.MiniBarrel;
import io.ncbpfluffybear.fluffymachines.items.tools.FluffyWrench;
import io.ncbpfluffybear.fluffymachines.items.tools.PortableCharger;
import io.ncbpfluffybear.fluffymachines.machines.AdvancedAutoDisenchanter;
import io.ncbpfluffybear.fluffymachines.machines.AdvancedChargingBench;
import io.ncbpfluffybear.fluffymachines.machines.AutoAncientAltar;
import io.ncbpfluffybear.fluffymachines.machines.AutoCraftingTable;
import io.ncbpfluffybear.fluffymachines.machines.AutoTableSaw;
import io.ncbpfluffybear.fluffymachines.machines.BackpackLoader;
import io.ncbpfluffybear.fluffymachines.machines.BackpackUnloader;
import io.ncbpfluffybear.fluffymachines.machines.ElectricDustFabricator;
import io.ncbpfluffybear.fluffymachines.machines.ElectricDustRecycler;
import io.ncbpfluffybear.fluffymachines.machines.SmartFactory;
import io.ncbpfluffybear.fluffymachines.machines.WaterSprinkler;
import io.ncbpfluffybear.fluffymachines.multiblocks.CrankGenerator;
import io.ncbpfluffybear.fluffymachines.objects.AutoCrafter;
import org.bukkit.Color;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import org.bukkit.Material;
import io.ncbpfluffybear.fluffymachines.utils.MaterialCompat;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Specifies all plugin items
 */
public class FluffyItems {

    private FluffyItems() {
    }

    // Barrels
    public static final SlimefunItemStack MINI_FLUFFY_BARREL = new SlimefunItemStack("MINI_FLUFFY_BARREL", MaterialCompat.safe(XMaterial.COMPOSTER));

    // Portable Chargers
    public static final SlimefunItemStack SMALL_PORTABLE_CHARGER = new SlimefunItemStack("SMALL_PORTABLE_CHARGER", MaterialCompat.safe(XMaterial.BRICK));

    public static final SlimefunItemStack MEDIUM_PORTABLE_CHARGER = new SlimefunItemStack("MEDIUM_PORTABLE_CHARGER", MaterialCompat.safe(XMaterial.IRON_INGOT));

    public static final SlimefunItemStack BIG_PORTABLE_CHARGER = new SlimefunItemStack("BIG_PORTABLE_CHARGER", MaterialCompat.safe(XMaterial.GOLD_INGOT));

    public static final SlimefunItemStack LARGE_PORTABLE_CHARGER = new SlimefunItemStack("LARGE_PORTABLE_CHARGER", MaterialCompat.safe(XMaterial.NETHER_BRICK));

    public static final SlimefunItemStack CARBONADO_PORTABLE_CHARGER = new SlimefunItemStack("CARBONADO_PORTABLE_CHARGER", MaterialCompat.safe(XMaterial.NETHERITE_INGOT));

    // Items
    public static final SlimefunItemStack ANCIENT_BOOK = new SlimefunItemStack("ANCIENT_BOOK", MaterialCompat.safe(XMaterial.BOOK));
    public static final SlimefunItemStack HELICOPTER_HAT = new SlimefunItemStack("HELICOPTER_HAT", MaterialCompat.safe(XMaterial.LEATHER_HELMET), Color.AQUA);
    public static final SlimefunItemStack WATERING_CAN = new SlimefunItemStack("WATERING_CAN", "6484da45301625dee79ae29ff513efa583f1ed838033f20db80963cedf8aeb0e");
    public static final SlimefunItemStack ENDER_CHEST_EXTRACTION_NODE = new SlimefunItemStack("ENDER_CHEST_EXTRACTION_NODE", "e707c7f6c3a056a377d4120028405fdd09acfcd5ae804bfde0f653be866afe39");
    public static final SlimefunItemStack ENDER_CHEST_INSERTION_NODE = new SlimefunItemStack("ENDER_CHEST_INSERTION_NODE", "7e5dc50c0186d53381d9430a2eff4c38f816b8791890c7471ffdb65ba202bc5");
    // Machines
    public static final SlimefunItemStack AUTO_CRAFTING_TABLE = new SlimefunItemStack("AUTO_CRAFTING_TABLE", MaterialCompat.safe(XMaterial.CRAFTING_TABLE));
    public static final SlimefunItemStack AUTO_ANCIENT_ALTAR = new SlimefunItemStack("AUTO_ANCIENT_ALTAR", MaterialCompat.safe(XMaterial.ENCHANTING_TABLE));
    public static final SlimefunItemStack AUTO_TABLE_SAW = new SlimefunItemStack("AUTO_TABLE_SAW", MaterialCompat.safe(XMaterial.STONECUTTER));
    public static final SlimefunItemStack WATER_SPRINKER = new SlimefunItemStack("WATER_SPRINKLER", "d6b13d69d1929dcf8edf99f3901415217c6a567d3a6ead12f75a4de3ed835e85");
    public static final SlimefunItemStack GENERATOR_CORE = new SlimefunItemStack("GENERATOR_CORE", MaterialCompat.safe(XMaterial.BLAST_FURNACE));
    public static final SlimefunItemStack CRANK_GENERATOR = new SlimefunItemStack("CRANK_GENERATOR", MaterialCompat.safe(XMaterial.BLAST_FURNACE));

    public static final SlimefunItemStack FOUNDRY = new SlimefunItemStack("FOUNDRY", MaterialCompat.safe(XMaterial.BLAST_FURNACE));

    public static final SlimefunItemStack BACKPACK_UNLOADER = new SlimefunItemStack("BACKPACK_UNLOADER", MaterialCompat.safe(XMaterial.BROWN_STAINED_GLASS));
    public static final SlimefunItemStack BACKPACK_LOADER = new SlimefunItemStack("BACKPACK_LOADER", MaterialCompat.safe(XMaterial.ORANGE_STAINED_GLASS));
    public static final SlimefunItemStack UPGRADED_EXPLOSIVE_PICKAXE = new SlimefunItemStack("UPGRADED_EXPLOSIVE_PICKAXE", MaterialCompat.safe(XMaterial.DIAMOND_PICKAXE));
    public static final SlimefunItemStack UPGRADED_EXPLOSIVE_SHOVEL = new SlimefunItemStack("UPGRADED_EXPLOSIVE_SHOVEL", MaterialCompat.safe(XMaterial.DIAMOND_SHOVEL));
    public static final SlimefunItemStack FIREPROOF_RUNE = new SlimefunItemStack(
            "FIREPROOF_RUNE",
            ColoredFireworkStar.create(Color.fromRGB(255, 165, 0)));
    public static final SlimefunItemStack SUPERHEATED_FURNACE = new SlimefunItemStack("SUPERHEATED_FURNACE", MaterialCompat.safe(XMaterial.BLAST_FURNACE));
    public static final SlimefunItemStack AUTO_ENHANCED_CRAFTING_TABLE = new SlimefunItemStack("AUTO_ENHANCED_CRAFTING_TABLE", MaterialCompat.safe(XMaterial.CRAFTING_TABLE));
    public static final SlimefunItemStack AUTO_MAGIC_WORKBENCH = new SlimefunItemStack("AUTO_MAGIC_WORKBENCH", MaterialCompat.safe(XMaterial.BOOKSHELF));
    public static final SlimefunItemStack AUTO_ARMOR_FORGE = new SlimefunItemStack("AUTO_ARMOR_FORGE", MaterialCompat.safe(XMaterial.SMITHING_TABLE));
    public static final SlimefunItemStack ADVANCED_AUTO_DISENCHANTER = new SlimefunItemStack("ADVANCED_AUTO_DISENCHANTER", MaterialCompat.safe(XMaterial.ENCHANTING_TABLE));
    public static final SlimefunItemStack SCYTHE = new SlimefunItemStack("SCYTHE", MaterialCompat.safe(XMaterial.IRON_HOE));
    public static final SlimefunItemStack UPGRADED_LUMBER_AXE = new SlimefunItemStack("UPGRADED_LUMBER_AXE", MaterialCompat.safe(XMaterial.DIAMOND_AXE));
    public static final SlimefunItemStack DOLLY = new SlimefunItemStack("DOLLY", MaterialCompat.safe(XMaterial.MINECART));

    public static final SlimefunItemStack WARP_PAD = new SlimefunItemStack("WARP_PAD", MaterialCompat.safe(XMaterial.SMOKER));

    public static final SlimefunItemStack WARP_PAD_CONFIGURATOR = new SlimefunItemStack("WARP_PAD_CONFIGURATOR", MaterialCompat.safe(XMaterial.BLAZE_ROD));

    public static final SlimefunItemStack ELECTRIC_DUST_FABRICATOR = new SlimefunItemStack("ELECTRIC_DUST_FABRICATOR", MaterialCompat.safe(XMaterial.BLAST_FURNACE));

    public static final SlimefunItemStack ELECTRIC_DUST_RECYCLER = new SlimefunItemStack("ELECTRIC_DUST_RECYCLER", MaterialCompat.safe(XMaterial.IRON_BLOCK));

    public static final SlimefunItemStack ALTERNATE_ELEVATOR_PLATE = new SlimefunItemStack("ALTERNATE_ELEVATOR_PLATE", MaterialCompat.safe(XMaterial.POLISHED_BLACKSTONE_PRESSURE_PLATE));

    public static final SlimefunItemStack FLUFFY_WRENCH = new SlimefunItemStack("FLUFFY_WRENCH", FluffyWrench.Wrench.DEFAULT.getMaterial());

    public static final SlimefunItemStack REINFORCED_FLUFFY_WRENCH =
            new SlimefunItemStack("REINFORCED_FLUFFY_WRENCH", FluffyWrench.Wrench.REINFORCED.getMaterial());

    public static final SlimefunItemStack CARBONADO_FLUFFY_WRENCH =
            new SlimefunItemStack("CARBONADO_FLUFFY_WRENCH", FluffyWrench.Wrench.CARBONADO.getMaterial());

    public static final SlimefunItemStack PAXEL = new SlimefunItemStack("PAXEL", MaterialCompat.safe(XMaterial.DIAMOND_PICKAXE));

    public static final SlimefunItemStack ADVANCED_CHARGING_BENCH = new SlimefunItemStack("ADVANCED_CHARGING_BENCH", MaterialCompat.safe(XMaterial.SMITHING_TABLE));

    public static final SlimefunItemStack ACB_UPGRADE_CARD = new SlimefunItemStack("ACB_UPGRADE_CARD", MaterialCompat.safe(XMaterial.PAPER));

    public static final SlimefunItemStack CARGO_MANIPULATOR = new SlimefunItemStack("CARGO_MANIPULATOR", MaterialCompat.safe(XMaterial.SEA_PICKLE));

    public static final SlimefunItemStack EXP_DISPENSER = new SlimefunItemStack("EXP_DISPENSER", MaterialCompat.safe(XMaterial.DISPENSER));

    public static final SlimefunItemStack SMART_FACTORY = new SlimefunItemStack("SMART_FACTORY", MaterialCompat.safe(XMaterial.SMOKER));

    static {
        FireproofRune.setFireproof(FIREPROOF_RUNE.item());
        addGlow(SMALL_PORTABLE_CHARGER.item());
        addGlow(MEDIUM_PORTABLE_CHARGER.item());
        addGlow(BIG_PORTABLE_CHARGER.item());
        addGlow(LARGE_PORTABLE_CHARGER.item());
        addGlow(CARBONADO_PORTABLE_CHARGER.item());
    }

    private static void addGlow(ItemStack item) {
        // BINDING_CURSE is 1.11+; resolve by name and fall back to a legacy enchant for the (hidden) glow.
        Enchantment glow = Enchantment.getByName("BINDING_CURSE");
        if (glow == null) {
            glow = Enchantment.DURABILITY;
        }
        item.addUnsafeEnchantment(glow, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }
}

