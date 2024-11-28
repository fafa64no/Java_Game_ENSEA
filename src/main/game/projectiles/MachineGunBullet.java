package main.game.projectiles;

import main.physics.CollisionLayers;
import main.utils.data.DataGen;

import java.awt.image.BufferedImage;

public class MachineGunBullet extends BasicProjectileHandler {
    private static MachineGunBullet instance = null;

    public MachineGunBullet(){
        super(new BufferedImage[]{DataGen.getMachineGunBulletShellTexture()},700,15,1,CollisionLayers.COLLISION_LAYER_ENNEMY_PROJECTILES);
        if(instance==null)instance=this;
    }

    public static MachineGunBullet getInstance(){
        if(instance!=null)return instance;
        return new MachineGunBullet();
    }
}
