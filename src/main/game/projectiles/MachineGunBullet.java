package main.game.projectiles;

import main.physics.ColliderType;
import main.rendering.vfx.VfxType;
import main.utils.data.DataGen;

public class MachineGunBullet extends BasicProjectileHandler {
    private static MachineGunBullet instance = null;

    public MachineGunBullet(){
        super(
                DataGen.getMachineGunBulletTexture().toArray(),
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
}
