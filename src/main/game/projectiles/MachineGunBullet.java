package main.game.projectiles;

import main.physics.CollisionLayers;
import main.utils.data.DataGen;

public class MachineGunBullet extends BasicProjectile {
    private static MachineGunBullet instance = null;

    public MachineGunBullet(){
        super(DataGen.getMachineGunBulletShellTexture(),500,15,1,CollisionLayers.COLLISION_LAYER_ENNEMY_PROJECTILES);
        if(instance==null)instance=this;
    }

    public static MachineGunBullet getInstance(){
        if(instance!=null)return instance;
        return new MachineGunBullet();
    }
}
