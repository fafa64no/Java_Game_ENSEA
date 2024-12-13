package main.game.level.weapons.projectiles;

import main.physics.ColliderType;
import main.rendering.layers.RenderingLayer;
import main.rendering.sprites.Sprite;
import main.rendering.vfx.VfxType;
import main.utils.data.datagen.TextureGen;

public class MachineGunBullet extends BasicProjectileHandler {
    private static MachineGunBullet instance = null;

    public MachineGunBullet(){
        super(
                TextureGen.getMachineGunBulletTexture().toArray(),
                600,
                20,
                1,
                VfxType.VFX_PIERCING_METAL,
                ColliderType.POINT_DAMAGE_DEALER,
                0,
                1
        );
        if(instance==null)instance=this;
    }

    public static MachineGunBullet getInstance(){
        if(instance!=null)return instance;
        return new MachineGunBullet();
    }

    @Override
    protected Sprite getProjectileSprite(RenderingLayer renderingLayer) {
        return new Sprite(TextureGen.getMachineGunBulletTexture(), renderingLayer);
    }
}
