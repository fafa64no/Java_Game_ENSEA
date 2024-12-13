package main.game.level.weapons.projectiles;

import main.physics.ColliderType;
import main.rendering.layers.RenderingLayer;
import main.rendering.sprites.Sprite;
import main.rendering.vfx.VfxType;
import main.utils.data.datagen.TextureGen;

public class AerialMachineGunBullet extends BasicProjectileHandler {
    private static AerialMachineGunBullet instance = null;

    public AerialMachineGunBullet(){
        super(
                TextureGen.getMachineGunBulletTexture().toArray(),
                600,
                20,
                1,
                VfxType.VFX_PIERCING_METAL,
                ColliderType.AERIAL_DAMAGE_DEALER,
                0,
                1
        );
        if(instance==null)instance=this;
    }

    public static AerialMachineGunBullet getInstance(){
        if(instance!=null)return instance;
        return new AerialMachineGunBullet();
    }

    @Override
    protected Sprite getProjectileSprite(RenderingLayer renderingLayer) {
        return new Sprite(TextureGen.getMachineGunBulletTexture(), renderingLayer);
    }
}
