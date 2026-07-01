package io.ncbpfluffybear.fluffymachines;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun5.libraries.dough.items.CustomItemStack;
import io.ncbpfluffybear.fluffymachines.items.Barrel;
import io.ncbpfluffybear.fluffymachines.items.EnderChestExtractionNode;
import io.ncbpfluffybear.fluffymachines.items.EnderChestInsertionNode;
import io.ncbpfluffybear.fluffymachines.items.FireproofRune;
import io.ncbpfluffybear.fluffymachines.items.HelicopterHat;
import io.ncbpfluffybear.fluffymachines.items.MiniBarrel;
import io.ncbpfluffybear.fluffymachines.items.tools.ACBUpgradeCard;
import io.ncbpfluffybear.fluffymachines.items.tools.CargoManipulator;
import io.ncbpfluffybear.fluffymachines.items.tools.Dolly;
import io.ncbpfluffybear.fluffymachines.items.tools.FluffyWrench;
import io.ncbpfluffybear.fluffymachines.items.tools.Paxel;
import io.ncbpfluffybear.fluffymachines.items.tools.PortableCharger;
import io.ncbpfluffybear.fluffymachines.items.tools.Scythe;
import io.ncbpfluffybear.fluffymachines.items.tools.UpgradedExplosivePickaxe;
import io.ncbpfluffybear.fluffymachines.items.tools.UpgradedExplosiveShovel;
import io.ncbpfluffybear.fluffymachines.items.tools.UpgradedLumberAxe;
import io.ncbpfluffybear.fluffymachines.items.tools.WarpPadConfigurator;
import io.ncbpfluffybear.fluffymachines.items.tools.WateringCan;
import io.ncbpfluffybear.fluffymachines.machines.AdvancedAutoDisenchanter;
import io.ncbpfluffybear.fluffymachines.machines.AdvancedChargingBench;
import io.ncbpfluffybear.fluffymachines.machines.AlternateElevatorPlate;
import io.ncbpfluffybear.fluffymachines.machines.AutoAncientAltar;
import io.ncbpfluffybear.fluffymachines.machines.AutoArmorForge;
import io.ncbpfluffybear.fluffymachines.machines.AutoCraftingTable;
import io.ncbpfluffybear.fluffymachines.machines.AutoEnhancedCraftingTable;
import io.ncbpfluffybear.fluffymachines.machines.AutoMagicWorkbench;
import io.ncbpfluffybear.fluffymachines.machines.AutoTableSaw;
import io.ncbpfluffybear.fluffymachines.machines.BackpackLoader;
import io.ncbpfluffybear.fluffymachines.machines.BackpackUnloader;
import io.ncbpfluffybear.fluffymachines.machines.ElectricDustFabricator;
import io.ncbpfluffybear.fluffymachines.machines.ElectricDustRecycler;
import io.ncbpfluffybear.fluffymachines.machines.SmartFactory;
import io.ncbpfluffybear.fluffymachines.machines.WarpPad;
import io.ncbpfluffybear.fluffymachines.machines.WaterSprinkler;
import io.ncbpfluffybear.fluffymachines.multiblocks.CrankGenerator;
import io.ncbpfluffybear.fluffymachines.multiblocks.ExpDispenser;
import io.ncbpfluffybear.fluffymachines.multiblocks.Foundry;
import io.ncbpfluffybear.fluffymachines.multiblocks.components.GeneratorCore;
import io.ncbpfluffybear.fluffymachines.multiblocks.components.SuperheatedFurnace;
import io.ncbpfluffybear.fluffymachines.utils.FluffyItems;
import io.ncbpfluffybear.fluffymachines.utils.MaterialCompat;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import javax.annotation.Nonnull;
import org.bukkit.Material;
import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public final class FluffyItemSetup {

    private static final SlimefunItemStack advancedCircuitBoard = SlimefunItems.ADVANCED_CIRCUIT_BOARD;
    private static final ItemStack orangeGlass = new ItemStack(MaterialCompat.safe(XMaterial.ORANGE_STAINED_GLASS));
    private static final ItemStack brownGlass = new ItemStack(MaterialCompat.safe(XMaterial.BROWN_STAINED_GLASS));

    // ItemGroups
    private static final NestedItemGroup fluffymachines = new NestedItemGroup(
            new NamespacedKey("fluffymachines","fluffymachines"),
            CustomItemStack.create(MaterialCompat.safe(XMaterial.SMOKER), "&6Fluffy Machines")
    );

    private static final ItemGroup generators = new SubItemGroup(
            new NamespacedKey("fluffymachines","generators"), fluffymachines,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.BLAST_FURNACE), "&aGenerators"), 1
    );

    private static final ItemGroup machines = new SubItemGroup(
            new NamespacedKey("fluffymachines","machines"), fluffymachines,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.SMOKER), "&9Machines"), 2
    );

    private static final ItemGroup tools = new SubItemGroup(
            new NamespacedKey("fluffymachines","tools"), fluffymachines,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.IRON_PICKAXE), "&bTools"), 3
    );

    private static final ItemGroup multiblocks = new SubItemGroup(
            new NamespacedKey("fluffymachines","multiblocks"), fluffymachines,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.BRICKS), "&cMultiblocks"), 4
    );

    private static final ItemGroup fluffybarrels = new SubItemGroup(
            new NamespacedKey("fluffymachines","barrels"), fluffymachines,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.BARREL), "&6Fluffy Barrels"), 5
    );

    private static final ItemGroup portableChargers = new SubItemGroup(
            new NamespacedKey("fluffymachines","portable_chargers"), fluffymachines,
            CustomItemStack.create(FluffyItems.CARBONADO_PORTABLE_CHARGER.item(), "&ePortable Chargers"), 6
    );

    private static final ItemGroup wrenches = new SubItemGroup(
            new NamespacedKey("fluffymachines","wrenches"), fluffymachines,
            CustomItemStack.create(FluffyItems.CARBONADO_FLUFFY_WRENCH.item(), "&7Wrenches"), 7
    );

    private static final ItemGroup cargo = new SubItemGroup(
            new NamespacedKey("fluffymachines","cargo"), fluffymachines,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.CHEST), "&3Cargo"), 8
    );

    private static final ItemGroup misc = new SubItemGroup(
            new NamespacedKey("fluffymachines","misc"), fluffymachines,
            CustomItemStack.create(MaterialCompat.safe(XMaterial.HOPPER), "&8Misc"), 9
    );

    private FluffyItemSetup() {
    }

    public static void setupBarrels(@Nonnull FluffyMachines plugin) {

        new MiniBarrel(fluffybarrels, FluffyItems.MINI_FLUFFY_BARREL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.OAK_SLAB)), new ItemStack(MaterialCompat.safe(XMaterial.BARREL)), new ItemStack(MaterialCompat.safe(XMaterial.OAK_SLAB)),
                new ItemStack(MaterialCompat.safe(XMaterial.OAK_SLAB)), new ItemStack(MaterialCompat.safe(XMaterial.BARREL)), new ItemStack(MaterialCompat.safe(XMaterial.OAK_SLAB)),
                SlimefunItems.STEEL_PLATE.item(), SlimefunItems.STEEL_PLATE.item(), SlimefunItems.STEEL_PLATE.item()
        }).register(plugin);

        ItemStack previousBarrel = new ItemStack(MaterialCompat.safe(XMaterial.BARREL));

        for (Barrel.BarrelType barrelType : Barrel.BarrelType.values()) {

            SlimefunItemStack barrelStack = new SlimefunItemStack(barrelType.getKey(),
                    barrelType.getType(),
                    barrelType.getDisplayName(),
                    "",
                    "&7Stores a large amount of an item",
                    "",
                    "&bCapacity: &e" + Barrel.getDisplayCapacity(barrelType) + " Items"
            );

            new Barrel(fluffybarrels, barrelStack, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                    barrelType.getBorder(), previousBarrel, barrelType.getBorder(),
                    barrelType.getBorder(), previousBarrel, barrelType.getBorder(),
                    barrelType.getBorder(), barrelType.getReinforcement(), barrelType.getBorder()
            }, barrelType.getDefaultSize()).register(plugin);

            previousBarrel = barrelStack.item().clone();

        }
    }

    public static void setup(@Nonnull FluffyMachines plugin) {

        fluffymachines.setTheme("machines");

        // Chargers
        new PortableCharger(portableChargers, FluffyItems.SMALL_PORTABLE_CHARGER,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.COPPER_WIRE.item(), SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.SMALL_CAPACITOR.item(), SlimefunItems.STEEL_INGOT.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.BRICK)), SlimefunItems.STEEL_PLATE.item(), new ItemStack(MaterialCompat.safe(XMaterial.BRICK))},
                PortableCharger.Type.SMALL.chargeCapacity, PortableCharger.Type.SMALL.chargeSpeed
        ).register(plugin);

        new PortableCharger(portableChargers, FluffyItems.MEDIUM_PORTABLE_CHARGER,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.COPPER_WIRE.item(), SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.MEDIUM_CAPACITOR.item(), SlimefunItems.STEEL_INGOT.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), SlimefunItems.STEEL_PLATE.item(), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT))},
                PortableCharger.Type.MEDIUM.chargeCapacity, PortableCharger.Type.MEDIUM.chargeSpeed
        ).register(plugin);

        new PortableCharger(portableChargers, FluffyItems.BIG_PORTABLE_CHARGER,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.COPPER_WIRE.item(), SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.BIG_CAPACITOR.item(), SlimefunItems.STEEL_INGOT.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.GOLD_INGOT)), SlimefunItems.STEEL_PLATE.item(), new ItemStack(MaterialCompat.safe(XMaterial.GOLD_INGOT))},
                PortableCharger.Type.BIG.chargeCapacity, PortableCharger.Type.BIG.chargeSpeed
        ).register(plugin);

        new PortableCharger(portableChargers, FluffyItems.LARGE_PORTABLE_CHARGER,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.COPPER_WIRE.item(), SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.LARGE_CAPACITOR.item(), SlimefunItems.STEEL_INGOT.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.NETHER_BRICK)), SlimefunItems.STEEL_PLATE.item(), new ItemStack(MaterialCompat.safe(XMaterial.NETHER_BRICK))},
                PortableCharger.Type.LARGE.chargeCapacity, PortableCharger.Type.LARGE.chargeSpeed
        ).register(plugin);

        new PortableCharger(portableChargers, FluffyItems.CARBONADO_PORTABLE_CHARGER,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.COPPER_WIRE.item(), SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.CARBONADO_EDGED_CAPACITOR.item(), SlimefunItems.STEEL_INGOT.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.NETHERITE_INGOT)), SlimefunItems.STEEL_PLATE.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.NETHERITE_INGOT))},
                PortableCharger.Type.CARBONADO.chargeCapacity, PortableCharger.Type.CARBONADO.chargeSpeed
        ).register(plugin);

        // Multiblocks
        new CrankGenerator(generators, FluffyItems.CRANK_GENERATOR).register(plugin);
        new Foundry(multiblocks, FluffyItems.FOUNDRY).register(plugin);
        new ExpDispenser(multiblocks, FluffyItems.EXP_DISPENSER, new ItemStack[]{
                null, null, null,
                null, new ItemStack(MaterialCompat.safe(XMaterial.GRINDSTONE)), null,
                null, new ItemStack(MaterialCompat.safe(XMaterial.DISPENSER)), null
        }).register(plugin);

        // Tools
        new WateringCan(tools, FluffyItems.WATERING_CAN,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), null, new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)),
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.BUCKET)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)),
                null, new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), null
        }).register(plugin);

        new Scythe(tools, FluffyItems.SCYTHE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)),
                null, new ItemStack(MaterialCompat.safe(XMaterial.IRON_HOE)), null,
                null, new ItemStack(MaterialCompat.safe(XMaterial.STICK)), null
        }).register(plugin);

        new FluffyWrench(wrenches, FluffyItems.FLUFFY_WRENCH,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.COPPER_INGOT.item(), null, SlimefunItems.COPPER_INGOT.item(),
                SlimefunItems.COPPER_INGOT.item(), SlimefunItems.COPPER_INGOT.item(), SlimefunItems.COPPER_INGOT.item(),
                null, SlimefunItems.COPPER_INGOT.item(), null
        }, FluffyWrench.Wrench.DEFAULT).register(plugin);

        new FluffyWrench(wrenches, FluffyItems.REINFORCED_FLUFFY_WRENCH,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.REINFORCED_ALLOY_INGOT.item(), null, SlimefunItems.REINFORCED_ALLOY_INGOT.item(),
                SlimefunItems.REINFORCED_ALLOY_INGOT.item(), FluffyItems.FLUFFY_WRENCH.item(), SlimefunItems.REINFORCED_ALLOY_INGOT.item(),
                null, SlimefunItems.SYNTHETIC_DIAMOND.item(), null
        }, FluffyWrench.Wrench.REINFORCED).register(plugin);

        new FluffyWrench(wrenches, FluffyItems.CARBONADO_FLUFFY_WRENCH,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.CARBONADO.item(), null, SlimefunItems.CARBONADO.item(),
                SlimefunItems.CARBONADO.item(), FluffyItems.REINFORCED_FLUFFY_WRENCH.item(), SlimefunItems.CARBONADO.item(),
                null, SlimefunItems.CARBONADO_EDGED_CAPACITOR.item(), null
        }, FluffyWrench.Wrench.CARBONADO).register(plugin);

        new UpgradedLumberAxe(tools, FluffyItems.UPGRADED_LUMBER_AXE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, new ItemStack(MaterialCompat.safe(XMaterial.DIAMOND)), new ItemStack(MaterialCompat.safe(XMaterial.DIAMOND)),
                null, SlimefunItems.LUMBER_AXE.item(), null,
                null, new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)), null
        }).register(plugin);

        new UpgradedExplosivePickaxe(tools, FluffyItems.UPGRADED_EXPLOSIVE_PICKAXE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SYNTHETIC_EMERALD.item(), SlimefunItems.SYNTHETIC_EMERALD.item(), SlimefunItems.SYNTHETIC_EMERALD.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.TNT)), SlimefunItems.EXPLOSIVE_PICKAXE.item(), new ItemStack(MaterialCompat.safe(XMaterial.TNT)),
                null, new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)), null
        }).register(plugin);

        new UpgradedExplosiveShovel(tools, FluffyItems.UPGRADED_EXPLOSIVE_SHOVEL,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SYNTHETIC_EMERALD.item(), SlimefunItems.SYNTHETIC_EMERALD.item(), SlimefunItems.SYNTHETIC_EMERALD.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.TNT)), SlimefunItems.EXPLOSIVE_SHOVEL.item(), new ItemStack(MaterialCompat.safe(XMaterial.TNT)),
                null, new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)), null
        }).register(plugin);

        new Paxel(tools, FluffyItems.PAXEL,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SYNTHETIC_EMERALD.item(), new ItemStack(MaterialCompat.safe(XMaterial.DIAMOND_PICKAXE)), SlimefunItems.SYNTHETIC_EMERALD.item(),
                SlimefunItems.REINFORCED_ALLOY_INGOT.item(), new ItemStack(MaterialCompat.safe(XMaterial.DIAMOND_AXE)), SlimefunItems.REINFORCED_ALLOY_INGOT.item(),
                SlimefunItems.SYNTHETIC_DIAMOND.item(), new ItemStack(MaterialCompat.safe(XMaterial.DIAMOND_SHOVEL)), SlimefunItems.SYNTHETIC_DIAMOND.item(),
        }).register(plugin);

        // Machines
        new WaterSprinkler(machines, FluffyItems.WATER_SPRINKER,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), SlimefunItems.ELECTRIC_MOTOR.item(), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)),
                new ItemStack(MaterialCompat.safe(XMaterial.BUCKET)), new ItemStack(MaterialCompat.safe(XMaterial.DISPENSER)), new ItemStack(MaterialCompat.safe(XMaterial.BUCKET)),
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), SlimefunItems.SMALL_CAPACITOR.item(), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT))
        }).register(plugin);

        new AutoCraftingTable(machines, FluffyItems.AUTO_CRAFTING_TABLE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.CRAFTING_TABLE)), SlimefunItems.BASIC_CIRCUIT_BOARD.item(), new ItemStack(MaterialCompat.safe(XMaterial.CRAFTING_TABLE)),
                SlimefunItems.CARGO_MOTOR.item(), SlimefunItems.BLISTERING_INGOT_3.item(), SlimefunItems.CARGO_MOTOR.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.CRAFTING_TABLE)), SlimefunItems.ELECTRIC_MOTOR.item(), new ItemStack(MaterialCompat.safe(XMaterial.CRAFTING_TABLE))
        }).register(plugin);

        new AutoAncientAltar(machines, FluffyItems.AUTO_ANCIENT_ALTAR,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.ANCIENT_PEDESTAL.item(), SlimefunItems.MEDIUM_CAPACITOR.item(), SlimefunItems.ANCIENT_PEDESTAL.item(),
                SlimefunItems.ANCIENT_PEDESTAL.item(), SlimefunItems.ANCIENT_ALTAR.item(), SlimefunItems.ANCIENT_PEDESTAL.item(),
                SlimefunItems.ANCIENT_PEDESTAL.item(), SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.ANCIENT_PEDESTAL.item()
        }).register(plugin);

        new AutoEnhancedCraftingTable(machines, FluffyItems.AUTO_ENHANCED_CRAFTING_TABLE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.CRAFTING_TABLE)), advancedCircuitBoard.item(), new ItemStack(MaterialCompat.safe(XMaterial.CRAFTING_TABLE)),
                SlimefunItems.CARGO_MOTOR.item(), SlimefunItems.BLISTERING_INGOT_3.item(), SlimefunItems.CARGO_MOTOR.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.CRAFTING_TABLE)), SlimefunItems.ELECTRIC_MOTOR.item(), new ItemStack(MaterialCompat.safe(XMaterial.CRAFTING_TABLE))
        }).register(plugin);

        new AutoTableSaw(machines, FluffyItems.AUTO_TABLE_SAW,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                advancedCircuitBoard.item(), SlimefunItems.MEDIUM_CAPACITOR.item(), advancedCircuitBoard.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.SMOOTH_STONE_SLAB)), new ItemStack(MaterialCompat.safe(XMaterial.STONECUTTER)),
                new ItemStack(MaterialCompat.safe(XMaterial.SMOOTH_STONE_SLAB)),
                SlimefunItems.ELECTRIC_MOTOR.item(), new ItemStack(MaterialCompat.safe(XMaterial.IRON_BLOCK)), SlimefunItems.ELECTRIC_MOTOR.item()
        }).register(plugin);

        new AutoMagicWorkbench(machines, FluffyItems.AUTO_MAGIC_WORKBENCH,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.BOOKSHELF)), advancedCircuitBoard.item(), new ItemStack(MaterialCompat.safe(XMaterial.BOOKSHELF)),
                new ItemStack(MaterialCompat.safe(XMaterial.BOOKSHELF)), new ItemStack(MaterialCompat.safe(XMaterial.CRAFTING_TABLE)),
                new ItemStack(MaterialCompat.safe(XMaterial.DISPENSER)),
                new ItemStack(MaterialCompat.safe(XMaterial.BOOKSHELF)), FluffyItems.AUTO_CRAFTING_TABLE.item(), new ItemStack(MaterialCompat.safe(XMaterial.BOOKSHELF))
        }).register(plugin);

        new AutoArmorForge(machines, FluffyItems.AUTO_ARMOR_FORGE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.ANVIL)), new ItemStack(MaterialCompat.safe(XMaterial.ANVIL)), new ItemStack(MaterialCompat.safe(XMaterial.ANVIL)),
                advancedCircuitBoard.item(), new ItemStack(MaterialCompat.safe(XMaterial.DISPENSER)), advancedCircuitBoard.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.ANVIL)), FluffyItems.AUTO_CRAFTING_TABLE.item(), new ItemStack(MaterialCompat.safe(XMaterial.ANVIL))
        }).register(plugin);

        new AdvancedAutoDisenchanter(machines, FluffyItems.ADVANCED_AUTO_DISENCHANTER,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.REDSTONE_ALLOY.item(), SlimefunItems.AUTO_ANVIL_2.item(), SlimefunItems.REDSTONE_ALLOY.item(),
                SlimefunItems.BLISTERING_INGOT_3.item(), SlimefunItems.AUTO_DISENCHANTER.item(), SlimefunItems.BLISTERING_INGOT_3.item(),
                SlimefunItems.WITHER_PROOF_OBSIDIAN.item(), SlimefunItems.WITHER_PROOF_OBSIDIAN.item(),
                SlimefunItems.WITHER_PROOF_OBSIDIAN.item()
        }).register(plugin);

        new BackpackLoader(machines, FluffyItems.BACKPACK_LOADER,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                orangeGlass, orangeGlass, orangeGlass,
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.HOPPER)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.BIG_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).register(plugin);

        new BackpackUnloader(machines, FluffyItems.BACKPACK_UNLOADER,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                brownGlass, brownGlass, brownGlass,
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.DISPENSER)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.BIG_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).register(plugin);

        new GeneratorCore(generators, FluffyItems.GENERATOR_CORE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)),
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), SlimefunItems.ELECTRO_MAGNET.item(), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)),
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), advancedCircuitBoard.item(), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT))
        }).register(plugin);

        new SuperheatedFurnace(multiblocks, FluffyItems.SUPERHEATED_FURNACE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)), new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)), new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)),
                new ItemStack(MaterialCompat.safe(XMaterial.LAVA_BUCKET)), new ItemStack(MaterialCompat.safe(XMaterial.BLAST_FURNACE)),
                new ItemStack(MaterialCompat.safe(XMaterial.LAVA_BUCKET)),
                new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)), new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)), new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN))
        }).register(plugin);

        // Misc
        new HelicopterHat(misc, FluffyItems.HELICOPTER_HAT,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)),
                advancedCircuitBoard.item(), new ItemStack(MaterialCompat.safe(XMaterial.LEATHER_HELMET)), advancedCircuitBoard.item(),
                SlimefunItems.COMPRESSED_CARBON.item(), SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.COMPRESSED_CARBON.item()
        }).register(plugin);

        new FireproofRune(misc, FluffyItems.FIREPROOF_RUNE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.SYNTHETIC_EMERALD.item(), new ItemStack(MaterialCompat.safe(XMaterial.NETHERITE_INGOT)), SlimefunItems.SYNTHETIC_EMERALD.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)), SlimefunItems.FIRE_RUNE.item(), new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)),
                SlimefunItems.SYNTHETIC_EMERALD.item(), new ItemStack(MaterialCompat.safe(XMaterial.OBSIDIAN)), SlimefunItems.SYNTHETIC_EMERALD.item()
        }).register(plugin);

        new EnderChestInsertionNode(cargo, FluffyItems.ENDER_CHEST_INSERTION_NODE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.ENDER_LUMP_2.item(), SlimefunItems.BASIC_CIRCUIT_BOARD.item(), SlimefunItems.ENDER_LUMP_2.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.DISPENSER)), new ItemStack(MaterialCompat.safe(XMaterial.ENDER_PEARL)), new ItemStack(MaterialCompat.safe(XMaterial.HOPPER)),
                SlimefunItems.ENDER_LUMP_2.item(), SlimefunItems.BASIC_CIRCUIT_BOARD.item(), SlimefunItems.ENDER_LUMP_2.item()
        }).register(plugin);

        new EnderChestExtractionNode(cargo, FluffyItems.ENDER_CHEST_EXTRACTION_NODE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.ENDER_LUMP_2.item(), SlimefunItems.BASIC_CIRCUIT_BOARD.item(), SlimefunItems.ENDER_LUMP_2.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.HOPPER)), new ItemStack(MaterialCompat.safe(XMaterial.ENDER_PEARL)), new ItemStack(MaterialCompat.safe(XMaterial.DISPENSER)),
                SlimefunItems.ENDER_LUMP_2.item(), SlimefunItems.BASIC_CIRCUIT_BOARD.item(), SlimefunItems.ENDER_LUMP_2.item()
        }).register(plugin);

        new Dolly(misc, FluffyItems.DOLLY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.LEATHER)), new ItemStack(MaterialCompat.safe(XMaterial.LEATHER)), new ItemStack(MaterialCompat.safe(XMaterial.LEATHER)),
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.MINECART)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)),
                new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT)), new ItemStack(MaterialCompat.safe(XMaterial.IRON_INGOT))
        }).register(plugin);

        new SlimefunItem(misc, FluffyItems.ANCIENT_BOOK,
                RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.BOOK)), SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE.item(), new ItemStack(MaterialCompat.safe(XMaterial.BOOK)),
                SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE.item(), SlimefunItems.ENCHANTMENT_RUNE.item(),
                SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.BOOK)), SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE.item(), new ItemStack(MaterialCompat.safe(XMaterial.BOOK))
        }).register(plugin);

        new WarpPad(misc, FluffyItems.WARP_PAD,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)), new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)), new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)),
                new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)), SlimefunItems.GPS_TELEPORTER_PYLON.item(), new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)),
                new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)), new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)), new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE))
        }).register(plugin);

        new WarpPadConfigurator(misc, FluffyItems.WARP_PAD_CONFIGURATOR,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)), null,
                null, SlimefunItems.MAGNESIUM_INGOT.item(), null,
                null, SlimefunItems.MAGNESIUM_INGOT.item(), null
        }).register(plugin);

        new ElectricDustFabricator(machines, FluffyItems.ELECTRIC_DUST_FABRICATOR,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.ELECTRIC_ORE_GRINDER_2.item(), SlimefunItems.ELECTRIC_ORE_GRINDER_2.item(),
                SlimefunItems.ELECTRIC_ORE_GRINDER_2.item(),
                SlimefunItems.ELECTRIC_GOLD_PAN_3.item(), SlimefunItems.BLISTERING_INGOT_3.item(), SlimefunItems.ELECTRIC_GOLD_PAN_3.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.ELECTRIC_DUST_WASHER_3.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).register(plugin);

        new ElectricDustRecycler(machines, FluffyItems.ELECTRIC_DUST_RECYCLER,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.LAVA_BUCKET)), new ItemStack(MaterialCompat.safe(XMaterial.PISTON)), new ItemStack(MaterialCompat.safe(XMaterial.LAVA_BUCKET)),
                new ItemStack(MaterialCompat.safe(XMaterial.LAVA_BUCKET)), SlimefunItems.ELECTRIFIED_CRUCIBLE_3.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.LAVA_BUCKET)),
                SlimefunItems.ELECTRIC_MOTOR.item(), new ItemStack(MaterialCompat.safe(XMaterial.PISTON)), SlimefunItems.ELECTRIC_MOTOR.item()
        }).register(plugin);

        new AlternateElevatorPlate(misc, FluffyItems.ALTERNATE_ELEVATOR_PLATE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(MaterialCompat.safe(XMaterial.STONE_PRESSURE_PLATE)), new ItemStack(MaterialCompat.safe(XMaterial.STONE_PRESSURE_PLATE)), new ItemStack(MaterialCompat.safe(XMaterial.STONE_PRESSURE_PLATE)),
                new ItemStack(MaterialCompat.safe(XMaterial.PISTON)), SlimefunItems.ELECTRIC_MOTOR.item(), new ItemStack(MaterialCompat.safe(XMaterial.PISTON)),
                SlimefunItems.ALUMINUM_BRONZE_INGOT.item(), SlimefunItems.ALUMINUM_BRONZE_INGOT.item(),
                SlimefunItems.ALUMINUM_BRONZE_INGOT.item()},
                new org.bukkit.inventory.ItemStack(FluffyItems.ALTERNATE_ELEVATOR_PLATE.item().getType(), 2)
        ).register(plugin);

        new AdvancedChargingBench(machines, FluffyItems.ADVANCED_CHARGING_BENCH,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.CORINTHIAN_BRONZE_INGOT.item(), advancedCircuitBoard.item(), SlimefunItems.CORINTHIAN_BRONZE_INGOT.item(),
                advancedCircuitBoard.item(), SlimefunItems.CHARGING_BENCH.item(), advancedCircuitBoard.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.SMALL_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).register(plugin);

        new ACBUpgradeCard(machines, FluffyItems.ACB_UPGRADE_CARD,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.CORINTHIAN_BRONZE_INGOT.item(), advancedCircuitBoard.item(), SlimefunItems.CORINTHIAN_BRONZE_INGOT.item(),
                advancedCircuitBoard.item(), SlimefunItems.ELECTRIC_MOTOR.item(), advancedCircuitBoard.item(),
                SlimefunItems.GOLD_24K.item(), SlimefunItems.SMALL_CAPACITOR.item(), SlimefunItems.GOLD_24K.item()
        }).register(plugin);

        new CargoManipulator(cargo, FluffyItems.CARGO_MANIPULATOR,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.CARGO_MOTOR.item(), new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)), SlimefunItems.ELECTRIC_MOTOR.item(),
                new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)), new ItemStack(MaterialCompat.safe(XMaterial.COMPASS)), new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)),
                SlimefunItems.ELECTRIC_MOTOR.item(), new ItemStack(MaterialCompat.safe(XMaterial.ENDER_EYE)), SlimefunItems.CARGO_MOTOR.item()
        }).register(plugin);

        new SmartFactory(machines, FluffyItems.SMART_FACTORY,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.CARGO_MOTOR.item(), SlimefunItems.ELECTRIC_SMELTERY_2.item(), SlimefunItems.CARGO_MOTOR.item(),
                SlimefunItems.ENHANCED_AUTO_CRAFTER.item(), SlimefunItems.CARBON_PRESS_3.item(), SlimefunItems.VANILLA_AUTO_CRAFTER.item(),
                SlimefunItems.CRAFTING_MOTOR.item(), SlimefunItems.ELECTRIC_INGOT_FACTORY_3.item(), SlimefunItems.CRAFTING_MOTOR.item()
        }).register(plugin);
    }

}

