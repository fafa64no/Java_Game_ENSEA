package main.game.projectiles;

import main.physics.ColliderType;
import main.physics.CollisionLayer;
import main.rendering.vfx.VfxType;
import main.utils.data.DataGen;

import java.awt.image.BufferedImage;

public class AerialMachineGunBullet extends BasicProjectileHandler {
    private static AerialMachineGunBullet instance = null;

    public AerialMachineGunBullet(){
        super(
                new BufferedImage[]{DataGen.getMachineGunBulletShellTexture()},
                600,
                20,
                1,
                CollisionLayer.COLLISION_LAYER_ENNEMY_PROJECTILES,
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
}
