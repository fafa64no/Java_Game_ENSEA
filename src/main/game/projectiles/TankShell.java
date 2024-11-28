package main.game.projectiles;

import main.physics.CollisionLayers;
import main.utils.data.DataGen;

import java.awt.image.BufferedImage;

public class TankShell extends BasicProjectileHandler {
    private static TankShell instance=null;

    public TankShell(){
        super(new BufferedImage[]{DataGen.getTankShellTexture()},800,15,40,CollisionLayers.COLLISION_LAYER_ALLY_PROJECTILES);
        if(instance==null)instance=this;
    }

    public static TankShell getInstance(){
        if(instance!=null)return instance;
        return new TankShell();
    }
}
