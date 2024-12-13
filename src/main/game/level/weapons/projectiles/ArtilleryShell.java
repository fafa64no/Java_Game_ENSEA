package main.game.level.weapons.projectiles;

import main.physics.ColliderType;
import main.rendering.layers.RenderingLayer;
import main.rendering.sprites.Sprite;
import main.rendering.vfx.VfxType;
import main.utils.data.datagen.TextureGen;

public class ArtilleryShell extends BasicProjectileHandler {
    private static ArtilleryShell instance = null;

    public ArtilleryShell(){
        super(
                TextureGen.getArtilleryShellTextures(),
                4000,
                5,
                100,
                VfxType.VFX_PIERCING_METAL,
                ColliderType.AERIAL_DAMAGE_DEALER,
                10,
                10
        );
        if(instance==null)instance=this;
    }

    public static ArtilleryShell getInstance(){
        if(instance!=null)return instance;
        return new ArtilleryShell();
    }

    @Override
    protected Sprite getProjectileSprite(RenderingLayer renderingLayer) {
        return new Sprite(TextureGen.getMachineGunBulletTexture(), renderingLayer);
    }
}
