package main.game.projectiles;

import main.physics.ColliderType;
import main.physics.CollisionLayer;
import main.rendering.vfx.VfxType;
import main.utils.data.DataGen;

import java.awt.image.BufferedImage;

public class MachineGunBullet extends BasicProjectileHandler {
    private static MachineGunBullet instance = null;

    public MachineGunBullet(){
        super(
                new BufferedImage[]{DataGen.getMachineGunBulletShellTexture()},
                700,
                15,
                0.5,
                CollisionLayer.COLLISION_LAYER_ENNEMY_PROJECTILES,
                VfxType.VFX_PIERCING_METAL,
                ColliderType.NONE_DAMAGE_DEALER,
                0,
                1
        );
        if(instance==null)instance=this;
    }

    public static MachineGunBullet getInstance(){
        if(instance!=null)return instance;
        return new MachineGunBullet();
    }
}
