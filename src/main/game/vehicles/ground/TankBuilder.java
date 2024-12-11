package main.game.vehicles.ground;

import main.game.level.target.Target;
import main.game.vehicles.turret.BasicTurret;
import main.physics.dynamic_objects.TankControlDynamicPoint;
import main.rendering.layers.RenderingLayer;
import main.rendering.sprites.AnimatedSprite;
import main.utils.data.datagen.TextureGen;
import main.utils.vectors.Vec2;

public class TankBuilder {
    public static TankControlDynamicPoint genTestTank(Vec2 position) {
        TankControlDynamicPoint tank = new TankControlDynamicPoint(
                8,
                0.15,
                position,
                new Vec2(),
                0
        );
        BasicTurret turret = new BasicTurret(
                0.05,
                new Vec2(),
                0,
                null
        );
        Target target = new Target(
                100
        );

        tank
            .setTarget(target)
            .addSprite(new AnimatedSprite(
                RenderingLayer.RENDERING_LAYER_GROUND_BOTTOM,
                    target,
                    100,
                    TextureGen.getPanzerIV_deadTexture().toArray(),
                    TextureGen.getPanzerIV_texture().toArray(),
                    TextureGen.getPanzerIV_texture().toArray(),
                    TextureGen.getPanzerIV_texture().toArray(),
                    TextureGen.getPanzerIV_texture().toArray(),
                    TextureGen.getPanzerIV_texture().toArray(),
                    TextureGen.getPanzerIV_texture().toArray(),
                    TextureGen.getPanzerIV_texture().toArray(),
                    TextureGen.getPanzerIV_texture().toArray()
            ))
            .addChild(turret)
            .addSprite(new AnimatedSprite(
                    RenderingLayer.RENDERING_LAYER_GROUND_BOTTOM,
                    target,
                    100,
                    TextureGen.getPanzerIV_turret_deadTexture().toArray(),
                    TextureGen.getPanzerIV_turret_texture().toArray(),
                    TextureGen.getPanzerIV_turret_texture().toArray(),
                    TextureGen.getPanzerIV_turret_texture().toArray(),
                    TextureGen.getPanzerIV_turret_texture().toArray(),
                    TextureGen.getPanzerIV_turret_texture().toArray(),
                    TextureGen.getPanzerIV_turret_texture().toArray(),
                    TextureGen.getPanzerIV_turret_texture().toArray(),
                    TextureGen.getPanzerIV_turret_texture().toArray()
            ));
        return tank;
    }
}
