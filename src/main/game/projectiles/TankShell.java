package main.game.projectiles;

import main.physics.CollisionLayers;
import main.rendering.vfx.VfxType;
import main.utils.data.DataGen;

import java.awt.image.BufferedImage;

public class TankShell extends BasicProjectileHandler {
    private static TankShell instance=null;

    public TankShell(){
        super(
                new BufferedImage[]{DataGen.getTankShellTexture()},
                800,
                15,
                40,
                CollisionLayers.COLLISION_LAYER_ALLY_PROJECTILES,
                VfxType.VFX_PIERCING_METAL
        );
        if(instance==null)instance=this;
    }

    public static TankShell getInstance(){
        if(instance!=null)return instance;
        return new TankShell();
    }
}
