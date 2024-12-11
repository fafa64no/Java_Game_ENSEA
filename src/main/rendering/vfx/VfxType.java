package main.rendering.vfx;

import main.utils.containers.SizedTextureArray;
import main.utils.data.datagen.TextureGen;

public enum VfxType {
    VFX_NONE,
    VFX_ELECTRICITY,
    VFX_PIERCING_METAL,
    VFX_EXPLOSION;

    public static SizedTextureArray getCorrespondingTexture(VfxType vfxType) {
        return switch (vfxType) {
            case VFX_ELECTRICITY -> TextureGen.getElectricVfxTextures();
            case VFX_PIERCING_METAL -> TextureGen.getPiercingMetalVfxTextures();
            case VFX_EXPLOSION -> TextureGen.getExplosionVfxTextures();

            default -> {
                System.out.println("Unknown VFX type requested.");
                yield null;
            }
        };
    }
}
