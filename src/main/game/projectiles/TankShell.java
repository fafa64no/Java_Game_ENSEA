package main.game.projectiles;

import main.physics.CollisionLayers;
import main.utils.data.DataGen;

public class TankShell extends BasicProjectile {
    private static TankShell instance=null;

    public TankShell(){
        super(DataGen.getTankShellTexture(),500,15,40,CollisionLayers.COLLISION_LAYER_ALLY_PROJECTILES);
        if(instance==null)instance=this;
    }

    public static TankShell getInstance(){
        if(instance!=null)return instance;
        return new TankShell();
    }
}
