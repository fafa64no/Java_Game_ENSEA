package main.game.projectiles;

import main.physics.ColliderType;
import main.rendering.vfx.VfxType;
import main.utils.data.DataGen;

public class TankShell extends BasicProjectileHandler {
    private static TankShell instance=null;

    public TankShell(){
        super(
                DataGen.getTankShellTexture().toArray(),
                800,
                15,
                40,
                VfxType.VFX_PIERCING_METAL,
                ColliderType.POINT_DAMAGE_DEALER,
                0,
                2
        );
        if(instance==null)instance=this;
    }

    public static TankShell getInstance(){
        if(instance!=null)return instance;
        return new TankShell();
    }
}
