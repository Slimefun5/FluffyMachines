package io.ncbpfluffybear.fluffymachines;

import io.github.thebusybiscuit.slimefun5.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun5.core.guide.wiki.WikiText;
import io.github.thebusybiscuit.slimefun5.core.guide.wiki.WikiTopic;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import io.github.thebusybiscuit.slimefun5.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun5.libraries.dough.config.Config;
import io.ncbpfluffybear.fluffymachines.listeners.KeyedCrafterListener;
import io.ncbpfluffybear.fluffymachines.utils.Constants;
import io.ncbpfluffybear.fluffymachines.utils.Events;
import io.ncbpfluffybear.fluffymachines.utils.McMMOEvents;
import io.ncbpfluffybear.fluffymachines.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
<<<<<<< HEAD
=======
import io.ncbpfluffybear.fluffymachines.utils.CompatUtils;
>>>>>>> origin/experimental
import dev.walshy.sfmetrics.MetricsModule;
import org.bukkit.util.RayTraceResult;

public class FluffyMachines extends JavaPlugin implements SlimefunAddon {

    private static FluffyMachines instance;
    // RecipeChoice (1.13+) is held as Object so the type is never referenced in bytecode; on legacy
    // versions Bukkit exposes no choice-based recipes, so these maps simply stay empty.
    public static final HashMap<ItemStack, List<Pair<ItemStack, List<Object>>>> shapedVanillaRecipes = new HashMap<>();
    public static final HashMap<ItemStack, List<Pair<ItemStack, List<Object>>>> shapelessVanillaRecipes =
            new HashMap<>();

    @Override
    public void onEnable() {
        MetricsModule.setup(this, 8927);

        try {
            instance = this;
            // Read something from your config.yml
            Config cfg = new Config(this);

            // Register ACT Recipes
            Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
            while (recipeIterator.hasNext()) {
                Recipe r = recipeIterator.next();

                if (r instanceof ShapedRecipe) {
                    ShapedRecipe sr = (ShapedRecipe) r;
                    List<Object> rc = new ArrayList<>();
                    ItemStack key = new ItemStack(sr.getResult().getType(), 1);

                    // Convert the recipe to a list (RecipeChoice access is reflective for 1.8 safety)
                    for (Map.Entry<Character, Object> choice : CompatUtils.getChoiceMap(sr).entrySet()) {
                        if (choice.getValue() != null) {
                            rc.add(choice.getValue());
                        }
                    }

                    if (!shapedVanillaRecipes.containsKey(key)) {
                        shapedVanillaRecipes.put(key,
                                new ArrayList<>(Collections.singletonList(new Pair<>(sr.getResult(), rc))));
                    } else {
                        shapedVanillaRecipes.get(key).add(new Pair<>(sr.getResult(), rc));
                    }

                } else if (r instanceof ShapelessRecipe) {
                    ShapelessRecipe slr = (ShapelessRecipe) r;
                    ItemStack key = new ItemStack(slr.getResult().getType(), 1);
                    List<Object> rc = CompatUtils.getShapelessChoiceList(slr);

                    // Key has a list of recipe options
                    if (!shapelessVanillaRecipes.containsKey(key)) {
                        shapelessVanillaRecipes.put(key,
                                new ArrayList<>(Collections.singletonList(new Pair<>(slr.getResult(), rc))));
                    } else {
                        shapelessVanillaRecipes.get(key).add(new Pair<>(slr.getResult(), rc));
                    }
                }
            }

            // Register McMMO Events
            if (getServer().getPluginManager().isPluginEnabled("McMMO")) {
                getLogger().log(Level.INFO, "McMMO found!");
                getServer().getPluginManager().registerEvents(new McMMOEvents(), this);
            }

            // Registering Items
            FluffyItemSetup.setup(this);
            FluffyItemSetup.setupBarrels(this);

            // Contribute this addon's per-language item translations (languages/<lang>/items.yml).
            Slimefun.getItemTranslationService().registerTranslations(this);
            registerWiki();

            // Register Events Class
            getServer().getPluginManager().registerEvents(new Events(), this);
            getServer().getPluginManager().registerEvents(new KeyedCrafterListener(), this);

<<<<<<< HEAD
            final         } catch (Exception e) {
=======
            } catch (Exception e) {
>>>>>>> origin/experimental
            getLogger().log(Level.SEVERE, "An error occurred while enabling FluffyMachines", e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    private void registerWiki() {
        WikiText wiki = Slimefun.getWikiText();

        // Bucket this addon's items by their ItemGroup, preserving registration order.
        Map<ItemGroup, List<String>> byGroup = new LinkedHashMap<>();
        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            try {
                if (item.getAddon() != this) {
                    continue;
                }

                byGroup.computeIfAbsent(item.getItemGroup(), key -> new ArrayList<>()).add(item.getId());

                List<String> itemPage = describeItem(item.getId());
                if (itemPage != null) {
                    wiki.set(item.getId(), itemPage);
                }
            } catch (Exception | LinkageError ignored) {
                // Skip items whose group/id cannot be resolved on this platform.
            }
        }

        // One topic per category, dynamically derived from the buckets above.
        for (Map.Entry<ItemGroup, List<String>> entry : byGroup.entrySet()) {
            ItemGroup group = entry.getKey();
            String groupKey = group.getKey().getKey();
            String topicId = "addon_fluffymachines_" + groupKey;

            wiki.registerTopic(new WikiTopic(
                    topicId, categoryDisplayName(groupKey), categoryIcon(groupKey), categoryTagline(groupKey)));
            wiki.setMechanic(topicId, describeCategory(groupKey));
            wiki.setTopicItems(topicId, entry.getValue());
        }
    }

    private String categoryDisplayName(String groupKey) {
        switch (groupKey) {
            case "generators": return "Fluffy Machines: Generators";
            case "machines": return "Fluffy Machines: Machines";
            case "tools": return "Fluffy Machines: Tools";
            case "multiblocks": return "Fluffy Machines: Multiblocks";
            case "barrels": return "Fluffy Machines: Barrels";
            case "portable_chargers": return "Fluffy Machines: Portable Chargers";
            case "wrenches": return "Fluffy Machines: Wrenches";
            case "cargo": return "Fluffy Machines: Cargo Utilities";
            case "misc": return "Fluffy Machines: Miscellaneous";
            default: return "Fluffy Machines";
        }
    }

    private XMaterial categoryIcon(String groupKey) {
        switch (groupKey) {
            case "generators": return XMaterial.BLAST_FURNACE;
            case "machines": return XMaterial.SMOKER;
            case "tools": return XMaterial.IRON_PICKAXE;
            case "multiblocks": return XMaterial.BRICKS;
            case "barrels": return XMaterial.BARREL;
            case "portable_chargers": return XMaterial.GOLD_INGOT;
            case "wrenches": return XMaterial.IRON_INGOT;
            case "cargo": return XMaterial.CHEST;
            case "misc": return XMaterial.HOPPER;
            default: return XMaterial.SMOKER;
        }
    }

    private String categoryTagline(String groupKey) {
        switch (groupKey) {
            case "generators": return "&7Hand-cranked & multiblock power";
            case "machines": return "&7Automatic crafting & processing";
            case "tools": return "&7Area-effect picks, hoes & gadgets";
            case "multiblocks": return "&7Built-in-the-world structures";
            case "barrels": return "&7Massive single-item storage";
            case "portable_chargers": return "&7Charge gear on the move";
            case "wrenches": return "&7Quickly dismantle Slimefun blocks";
            case "cargo": return "&7Copy & tune cargo networks";
            case "misc": return "&7Handy odds and ends";
            default: return "&7Quality-of-life additions";
        }
    }

    private List<String> describeCategory(String groupKey) {
        switch (groupKey) {
            case "generators":
                return java.util.Arrays.asList(
                        "&6Fluffy Generators", "",
                        "&7Power sources that feed your energy network.",
                        "&7The &7Crank Generator &7lets you produce energy by",
                        "&7hand - right-click its lever to generate power",
                        "&7with no fuel required, perfect for early game.", "",
                        "&7Generators are assembled around a &7Generator Core",
                        "&7multiblock component.", "",
                        "&7Click an item below for its recipe.");
            case "machines":
                return java.util.Arrays.asList(
                        "&9Fluffy Machines", "",
                        "&7Electric machines that automate the tedious.",
                        "&7The &6Auto &7crafters mirror Slimefun's manual",
                        "&7workbenches - Enhanced, Magic, Table Saw, Armor",
                        "&7Forge, Ancient Altar - but run on energy.", "",
                        "&7The &cSmart Factory &7and &6Electric Dust Fabricator",
                        "&7chain whole production lines into one block.",
                        "&7Sprinklers, backpack loaders and disenchanters",
                        "&7round out the automation toolkit.", "",
                        "&7Click an item below for its recipe.");
            case "tools":
                return java.util.Arrays.asList(
                        "&bFluffy Tools", "",
                        "&7Tools that turn chores into single clicks.",
                        "&7The &bPaxel &7combines pickaxe, axe and shovel.",
                        "&7Explosive tools clear &75x5 &7areas, the",
                        "&6Lumber Axe &7fells entire trees, and the",
                        "&eScythe &7harvests crops in bulk.", "",
                        "&7The &bDolly &7picks up filled chests, and the",
                        "&bWatering Can &7speeds up crop growth.", "",
                        "&7Click an item below for its recipe.");
            case "multiblocks":
                return java.util.Arrays.asList(
                        "&cFluffy Multiblocks", "",
                        "&7Structures you build block-by-block in the",
                        "&7world, then interact with directly.", "",
                        "&7The &cFoundry &7melts and stores enormous",
                        "&7quantities of dust and ingots. The &aExp",
                        "&aDispenser &7banks experience, and the",
                        "&7Crank Generator powers early machines.", "",
                        "&7Click an item below for its recipe.");
            case "barrels":
                return java.util.Arrays.asList(
                        "&6Fluffy Barrels", "",
                        "&7Bulk storage for a single item type, holding",
                        "&7far more than a chest ever could.", "",
                        "&7Tiers are crafted by surrounding the previous",
                        "&7barrel with stronger reinforcement plates.",
                        "&7Each barrel shows a live hologram of its",
                        "&7contents and current count.", "",
                        "&7The &eMini Fluffy Barrel &7offers an adjustable",
                        "&7capacity for smaller needs.", "",
                        "&7Click an item below for its recipe.");
            case "portable_chargers":
                return java.util.Arrays.asList(
                        "&ePortable Chargers", "",
                        "&7Handheld batteries that charge your rechargeable",
                        "&7Slimefun items wherever you are.", "",
                        "&7Larger tiers store more energy and charge",
                        "&7faster - from the &eSmall &7charger up to the",
                        "&2Large &7and &6Carbonado &7models.", "",
                        "&7Hold one in your inventory and powered gear",
                        "&7tops up automatically over time.", "",
                        "&7Click an item below for its recipe.");
            case "wrenches":
                return java.util.Arrays.asList(
                        "&7Fluffy Wrenches", "",
                        "&7A faster way to dismantle Slimefun cargo nodes",
                        "&7and electricity components.", "",
                        "&7Left or right click a compatible block to break",
                        "&7it instantly and reclaim the item.", "",
                        "&7Higher tiers (Reinforced, Carbonado) are more",
                        "&7durable and built from tougher materials.", "",
                        "&7Click an item below for its recipe.");
            case "cargo":
                return java.util.Arrays.asList(
                        "&3Cargo Utilities", "",
                        "&7Tools for tuning Slimefun cargo networks.", "",
                        "&7The &9Cargo Manipulator &7copies a node's filter",
                        "&7and settings, then pastes them onto another",
                        "&7node - no more re-typing item filters by hand.", "",
                        "&7Sneak and right-click to wipe a node clean.", "",
                        "&7Click an item below for its recipe.");
            case "misc":
                return java.util.Arrays.asList(
                        "&8Fluffy Miscellaneous", "",
                        "&7Handy items that don't fit anywhere else.", "",
                        "&7The &cFireproof Rune &7protects dropped items",
                        "&7from lava and fire. The &1Helicopter Hat &7lets",
                        "&7you glide, and Warp Pads teleport you across",
                        "&7linked destinations.", "",
                        "&7Elevator plates move you between floors.", "",
                        "&7Click an item below for its recipe.");
            default:
                return java.util.Arrays.asList(
                        "&6Fluffy Machines", "",
                        "&7Quality-of-life additions from FluffyMachines.", "",
                        "&7Click an item below for its recipe.");
        }
    }

    private List<String> describeItem(String id) {
        switch (id) {
            // --- Machines ---
            case "AUTO_CRAFTING_TABLE":
                return java.util.Arrays.asList(
                        "&7Automatically crafts &fvanilla &7recipes.",
                        "&7Set the target recipe, then feed it ingredients",
                        "&7and it churns out the result while powered.");
            case "AUTO_ENHANCED_CRAFTING_TABLE":
                return java.util.Arrays.asList(
                        "&7Automates the &eEnhanced Crafting Table.",
                        "&7Supply the Slimefun recipe's ingredients and it",
                        "&7crafts the result automatically using energy.");
            case "AUTO_MAGIC_WORKBENCH":
                return java.util.Arrays.asList(
                        "&7Automates the &6Magic Workbench.",
                        "&7Feeds ingredients in and produces magic-recipe",
                        "&7results without manual clicking.");
            case "AUTO_TABLE_SAW":
                return java.util.Arrays.asList(
                        "&7Automates the &6Table Saw.",
                        "&7Turns logs into planks in bulk while powered.");
            case "AUTO_ARMOR_FORGE":
                return java.util.Arrays.asList(
                        "&7Automates the &7Armor Forge.",
                        "&7Crafts Armor Forge recipes automatically using",
                        "&7supplied ingredients and energy.");
            case "AUTO_ANCIENT_ALTAR":
                return java.util.Arrays.asList(
                        "&7Automatically crafts &5Ancient Altar &7recipes.",
                        "&7No pedestals or rituals needed - just supply the",
                        "&7ingredients and let it run.");
            case "ADVANCED_AUTO_DISENCHANTER":
                return java.util.Arrays.asList(
                        "&7Removes a single enchantment from an item,",
                        "&7letting you keep the rest.",
                        "&7Requires an &6Ancient Book &7to operate.");
            case "ADVANCED_CHARGING_BENCH":
                return java.util.Arrays.asList(
                        "&7Charges rechargeable items placed inside.",
                        "&7Can be upgraded with an &6ACB Upgrade Card &7to",
                        "&7raise its speed, capacity and consumption.");
            case "ELECTRIC_DUST_FABRICATOR":
                return java.util.Arrays.asList(
                        "&7An all-in-one machine that grinds, pans and",
                        "&7washes ore into clean dust in one block.",
                        "&7Replaces a whole chain of processing machines.");
            case "ELECTRIC_DUST_RECYCLER":
                return java.util.Arrays.asList(
                        "&7Recycles dust back into sifted ore,",
                        "&7reversing ore processing when you overshoot.");
            case "SMART_FACTORY":
                return java.util.Arrays.asList(
                        "&7An all-in-one factory that crafts finished",
                        "&7resources directly from raw materials,",
                        "&7collapsing long production lines into one block.");
            case "WATER_SPRINKLER":
                return java.util.Arrays.asList(
                        "&bWaters and grows nearby crops automatically",
                        "&7while supplied with energy.");
            case "BACKPACK_LOADER":
                return java.util.Arrays.asList(
                        "&7Fills backpacks with items from an attached",
                        "&7inventory, ready for cargo automation.");
            case "BACKPACK_UNLOADER":
                return java.util.Arrays.asList(
                        "&7Empties the contents of backpacks into an",
                        "&7attached inventory automatically.");
            case "ALTERNATE_ELEVATOR_PLATE":
                return java.util.Arrays.asList(
                        "&7Place one on each floor to teleport between",
                        "&7them. Right-click to name a floor.",
                        "&7Uses a chest GUI instead of a book menu.");

            // --- Generators / Multiblocks ---
            case "CRANK_GENERATOR":
                return java.util.Arrays.asList(
                        "&7Right-click the lever to generate power by hand.",
                        "&7A fuel-free early-game energy source.");
            case "GENERATOR_CORE":
                return java.util.Arrays.asList(
                        "&7Multiblock component used at the heart of",
                        "&7Fluffy generators.");
            case "FOUNDRY":
                return java.util.Arrays.asList(
                        "&eMelts and stores dusts and ingots in bulk.",
                        "&7Holds up to 138,240 dust - about 40 double",
                        "&7chests worth. Built with a Super Heated Furnace.");
            case "SUPERHEATED_FURNACE":
                return java.util.Arrays.asList(
                        "&7Multiblock component of the &cFoundry.",
                        "&cMust be used as part of the Foundry structure.");
            case "EXP_DISPENSER":
                return java.util.Arrays.asList(
                        "&7Right-click to collect all experience from the",
                        "&7exp bottles in the dispenser and the barrel",
                        "&7it is facing. A compact XP bank.");

            // --- Tools ---
            case "PAXEL":
                return java.util.Arrays.asList(
                        "&7A pickaxe, axe and shovel combined into one",
                        "&7tool, so you only carry one item for digging.");
            case "SCYTHE":
                return java.util.Arrays.asList(
                        "&7Breaks up to 5 crops at once,",
                        "&7making harvesting fields far faster.");
            case "UPGRADED_LUMBER_AXE":
                return java.util.Arrays.asList(
                        "&7Chops down an entire tree in one swing.",
                        "&7Has a 2-block reach and works on diagonals too.");
            case "UPGRADED_EXPLOSIVE_SHOVEL":
                return java.util.Arrays.asList(
                        "&7Breaks all shovelable blocks in a 5x5 radius,",
                        "&7great for clearing dirt, sand and gravel fast.");
            case "DOLLY":
                return java.util.Arrays.asList(
                        "&7Right-click a chest to pick it up with its",
                        "&7contents intact, then place it elsewhere.");
            case "WATERING_CAN":
                return java.util.Arrays.asList(
                        "&fWaters and speeds up plant growth.",
                        "&7Right-click water to fill it, then right-click",
                        "&7a plant to grow it (right-click a player to slow them).");

            // --- Wrenches ---
            case "FLUFFY_WRENCH":
            case "REINFORCED_FLUFFY_WRENCH":
            case "CARBONADO_FLUFFY_WRENCH":
                return java.util.Arrays.asList(
                        "&7Quickly removes Slimefun cargo nodes and",
                        "&7electricity components.",
                        "&7Left or right click a compatible block to break it.");

            // --- Cargo ---
            case "CARGO_MANIPULATOR":
                return java.util.Arrays.asList(
                        "&eRight-click &7a cargo node to copy its settings.",
                        "&eLeft-click &7another node to paste them.",
                        "&7Sneak + right-click to clear a node.");

            // --- Misc ---
            case "FIREPROOF_RUNE":
                return java.util.Arrays.asList(
                        "&7Drop this rune onto a dropped item to make",
                        "&7that item &cfireproof&7, protecting it from lava.");
            case "HELICOPTER_HAT":
                return java.util.Arrays.asList(
                        "&7A helmet that lets you glide through the air.",
                        "&eSneak &7to use it.");
            case "WARP_PAD":
                return java.util.Arrays.asList(
                        "&eCrouch &7on this block to teleport to its linked",
                        "&7destination pad.",
                        "&7Use a Warp Pad Configurator to link two pads.");
            case "WARP_PAD_CONFIGURATOR":
                return java.util.Arrays.asList(
                        "&eSneak + right-click &7a Warp Pad to set the",
                        "&7destination, or right-click to set the origin.");
            case "ANCIENT_BOOK":
                return java.util.Arrays.asList(
                        "&7A book of concentrated power used by the",
                        "&cAdvanced Auto Disenchanter &7to function.");
            case "ACB_UPGRADE_CARD":
                return java.util.Arrays.asList(
                        "&eRight-click &7an &cAdvanced Charging Bench &7to",
                        "&7boost its charge speed, capacity and consumption.");
            case "MINI_FLUFFY_BARREL":
                return java.util.Arrays.asList(
                        "&7Stores a large amount of a single item with an",
                        "&7adjustable capacity for smaller storage needs.");

            // --- Portable Chargers ---
            case "SMALL_PORTABLE_CHARGER":
            case "MEDIUM_PORTABLE_CHARGER":
            case "BIG_PORTABLE_CHARGER":
            case "LARGE_PORTABLE_CHARGER":
                return java.util.Arrays.asList(
                        "&7A handheld charger that stores power and tops",
                        "&7up rechargeable items in your inventory.",
                        "&7Higher tiers hold more energy and charge faster.");

            default:
                // No authored page - the built-in fallback will describe it.
                return null;
        }
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, String[] args) {

        if (args.length == 0) {
            Utils.send(sender, "&cInvalid command");
            return true;
        }

        if (!(sender instanceof Player)) {
            Utils.send(sender, "&cThere are no console commands available");
            return true;
        }

        Player p = (Player) sender;

        switch (args[0].toUpperCase()) {
            case "META":
                Utils.send(p, String.valueOf(p.getInventory().getItemInHand().getItemMeta()));
                return true;
            case "RAWMETA":
                p.sendMessage(String.valueOf(p.getInventory().getItemInHand().getItemMeta()).replace("\u00a7", "&"));
                return true;
            case "VERSION":
            case "V":
                Utils.send(p, "&eThe current version is " + this.getPluginVersion());
                return true;
        }

        if (p.hasPermission("fluffymachines.admin")) {
            switch (args[0].toUpperCase()) {
                case "ADDINFO":

                    if (args.length != 3) {
                        Utils.send(p, "&cPlease specify the key and the data");

                    } else {
                        RayTraceResult rayResult = p.rayTraceBlocks(5d);
                        if (rayResult != null && rayResult.getHitBlock() != null
                                && BlockStorage.hasBlockInfo(rayResult.getHitBlock())) {

                            BlockStorage.addBlockInfo(rayResult.getHitBlock(), args[1], args[2]);
                            Utils.send(p, "&aInfo has been added.");

                        } else {
                            Utils.send(p, "&cYou must be looking at a Slimefun block");
                        }
                    }
                    return true;
                case "SAVEPLAYERS":
                    saveAllPlayers();
                    return true;
            }
        }

        Utils.send(p, "&cCommand not found");

        return false;
    }

    private void saveAllPlayers() {
        Iterator<PlayerProfile> iterator = PlayerProfile.iterator();
        int players = 0;

        while (iterator.hasNext()) {
            PlayerProfile profile = iterator.next();

            profile.save();
            players++;
        }

        if (players > 0) {
            getLogger().log(Level.INFO, "Auto-saved all player data for {0} player(s)!", players);
        }
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Slimefun5/FluffyMachines/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public static FluffyMachines getInstance() {
        return instance;
    }

}

