package main.game.projectiles;

import main.physics.ColliderType;
import main.rendering.vfx.VfxType;
import main.utils.data.DataGen;

public class AerialMachineGunBullet extends BasicProjectileHandler {
    private static AerialMachineGunBullet instance = null;

    public AerialMachineGunBullet(){
        super(
                DataGen.getMachineGunBulletTexture().toArray(),
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
}
