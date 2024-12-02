package main.rendering.vfx;

import main.utils.containers.SizedTextureArray;
import main.utils.data.DataGen;

public enum VfxType {
    VFX_NONE,
    VFX_ELECTRICITY,
    VFX_PIERCING_METAL,
    VFX_EXPLOSION;

    public static SizedTextureArray getCorrespondingTexture(VfxType vfxType) {
        return switch (vfxType) {
            case VFX_ELECTRICITY -> DataGen.getElectricVfxTextures();
            case VFX_PIERCING_METAL -> DataGen.getPiercingMetalVfxTextures();
            case VFX_EXPLOSION -> DataGen.getExplosionVfxTextures();

            default -> {
                System.out.println("Unknown VFX type requested.");
                yield null;
            }
        };
    }
}
