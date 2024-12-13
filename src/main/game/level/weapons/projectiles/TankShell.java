package main.game.level.weapons.projectiles;

import main.physics.ColliderType;
import main.rendering.layers.RenderingLayer;
import main.rendering.sprites.Sprite;
import main.rendering.vfx.VfxType;
import main.utils.data.datagen.TextureGen;

public class TankShell extends BasicProjectileHandler {
    private static TankShell instance=null;

    public TankShell(){
        super(
                TextureGen.getTankShellTexture().toArray(),
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

    @Override
    protected Sprite getProjectileSprite(RenderingLayer renderingLayer) {
        return new Sprite(TextureGen.getTankShellTexture(), renderingLayer);
    }
}
