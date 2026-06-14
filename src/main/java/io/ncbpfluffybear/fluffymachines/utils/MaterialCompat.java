package io.ncbpfluffybear.fluffymachines.utils;

import javax.annotation.Nonnull;

import org.bukkit.Material;

import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

/**
 * Resolves {@link XMaterial} constants to a {@link Material} that exists on the running server.
 * Keeps FluffyMachines loadable on legacy versions (e.g. 1.8) where modern constants like
 * {@code LIME_STAINED_GLASS_PANE} or {@code NETHERITE_INGOT} are absent.
 */
public final class MaterialCompat {

    private MaterialCompat() {}

    @Nonnull
    public static Material safe(@Nonnull XMaterial material) {
        Material resolved = material.parseMaterial();
        return resolved != null ? resolved : Material.STONE;
    }
}
