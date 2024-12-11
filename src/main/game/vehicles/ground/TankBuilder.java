package main.game.vehicles.ground;

import main.game.level.target.Target;
import main.game.vehicles.turret.BasicTurret;
import main.physics.ColliderType;
import main.physics.colliders.BoxCollider;
import main.physics.dynamic_objects.TankControlDynamicPoint;
import main.physics.layers.CollisionLayer;
import main.rendering.layers.RenderingLayer;
import main.rendering.sprites.AnimatedSprite;
import main.utils.data.datagen.TextureGen;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

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
                0.08,
                new Vec2(),
                0,
                null
        );
        Target target = new Target(
                100
        );

        int boxSize = 15;

        tank
            .setMainCollider(new BoxCollider(
                    new Vec4(-boxSize,boxSize,-boxSize,boxSize),
                    false,
                    0.5,
                    0,
                    ColliderType.SOLID_INERT,
                    CollisionLayer.COLLISION_LAYER_ALLIES,
                    new Vec2(),
                    null
            ))
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
            ))
            .setTarget(target);
        return tank;
    }
}
