package main.game.projectiles;

import main.physics.ColliderType;
import main.rendering.vfx.VfxType;
import main.utils.data.DataGen;

public class ArtilleryShell extends BasicProjectileHandler {
    private static ArtilleryShell instance = null;

    public ArtilleryShell(){
        super(
                DataGen.getArtilleryShellTextures(),
                4000,
                5,
                100,
                VfxType.VFX_PIERCING_METAL,
                ColliderType.AERIAL_DAMAGE_DEALER,
                10,
                10
        );
        if(instance==null)instance=this;
    }

    public static ArtilleryShell getInstance(){
        if(instance!=null)return instance;
        return new ArtilleryShell();
    }
}
