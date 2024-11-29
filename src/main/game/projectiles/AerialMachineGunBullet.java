package main.game.projectiles;

import main.physics.CollisionLayers;
import main.rendering.vfx.VfxType;
import main.utils.data.DataGen;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public class AerialMachineGunBullet extends BasicProjectileHandler {
    private static AerialMachineGunBullet instance = null;

    public AerialMachineGunBullet(){
        super(
                new BufferedImage[]{DataGen.getMachineGunBulletShellTexture()},
                800,
                20,
                1,
                CollisionLayers.COLLISION_LAYER_ENNEMY_PROJECTILES,
                VfxType.VFX_PIERCING_METAL
        );
        if(instance==null)instance=this;
    }

    public static AerialMachineGunBullet getInstance(){
        if(instance!=null)return instance;
        return new AerialMachineGunBullet();
    }

    @Override
    public void fireInDirection(Vec2 initialPosition, double rotation) {
        projectiles[projectilePointer]=new Projectile(
                projectileLifeSpan,
                10,
                initialPosition,
                projectileSpeed,
                rotation,
                projectilePointer,
                this,
                collisionLayer,
                modifier,
                textures.length,
                10,
                vfxType
        );
        projectilePointer=(projectilePointer+1)%projectiles.length;
    }
}
