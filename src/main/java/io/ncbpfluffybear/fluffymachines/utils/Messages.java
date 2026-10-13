package io.ncbpfluffybear.fluffymachines.utils;

import java.util.function.UnaryOperator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun5.libraries.commons.lang.WordUtils;
import io.ncbpfluffybear.fluffymachines.FluffyMachines;

/**
 * Resolves this addon's chat text for one viewer, from {@code languages/<lang>/messages.yml}.
 *
 * @implNote The {@link ChatColor} deliberately stays at the call site rather than moving into the
 *           translated value: a translator never edits a colour code they cannot see the effect of.
 *           The addon's {@code [FluffyMachines]} tag is applied here rather than left to
 *           {@link Utils#send}, so a migrated call keeps the prefix every other message has.
 */
public final class Messages {

    private static final String PREFIX = Utils.color("&7[&6FluffyMachines&7] &r");

    private Messages() {}

    @Nonnull
    public static String get(@Nullable Player player, @Nonnull String key, @Nonnull String english) {
        return Slimefun.getAddonMessageService().getMessage(player, FluffyMachines.getInstance(), key, english);
    }

    @Nonnull
    public static String get(@Nullable Player player, @Nonnull String key, @Nonnull String english,
                             @Nonnull UnaryOperator<String> placeholders) {
        return placeholders.apply(get(player, key, english));
    }

    public static void send(@Nonnull CommandSender recipient, @Nonnull ChatColor colour, @Nonnull String key, @Nonnull String english) {
        send(recipient, colour, key, english, UnaryOperator.identity());
    }

    public static void send(@Nonnull CommandSender recipient, @Nonnull ChatColor colour, @Nonnull String key,
                            @Nonnull String english, @Nonnull UnaryOperator<String> placeholders) {
        Slimefun.getAddonMessageService().sendMessage(recipient, FluffyMachines.getInstance(), key, english,
                line -> PREFIX + colour + placeholders.apply(line));
    }

    /**
     * A {@link SlimefunItem}'s display name in the viewer's own language.
     */
    @Nonnull
    public static String itemName(@Nullable Player viewer, @Nullable SlimefunItem item) {
        if (item == null) {
            return "";
        }
        return viewer != null ? Slimefun.getItemTranslationService().getName(viewer, item) : item.getItemName();
    }

    /**
     * The same for one of this addon's declared item stacks.
     *
     * @implNote {@link SlimefunItemStack} is a wrapper in this fork rather than an
     *           {@link ItemStack} subclass, so it needs an overload of its own.
     */
    @Nonnull
    public static String itemName(@Nullable Player viewer, @Nonnull SlimefunItemStack stack) {
        return itemName(viewer, SlimefunItem.getByItem(stack));
    }

    /**
     * The same for a bare {@link ItemStack}, which may be a vanilla one.
     *
     * @implNote A vanilla stack falls back to its humanised material name, which is the fork's
     *           existing behaviour for vanilla items everywhere and not this addon's to change.
     */
    @Nonnull
    public static String itemName(@Nullable Player viewer, @Nullable ItemStack stack) {
        if (stack == null) {
            return "";
        }
        SlimefunItem item = SlimefunItem.getByItem(stack);
        if (item != null) {
            return itemName(viewer, item);
        }
        return WordUtils.capitalizeFully(stack.getType().name().replace("_", " "));
    }

    /**
     * The grey {@code (UUID: ...)} an ownership message ends with.
     *
     * @implNote A key of its own rather than a literal at the call site, so the acronym stays
     *           inside something a translator can see and the highlight survives.
     */
    @Nonnull
    public static String uuidTag(@Nullable Player viewer, @Nonnull String id) {
        return ChatColor.GRAY + get(viewer, "ender-chest-node.uuid", "(UUID: %id%)").replace("%id%", id);
    }
}
