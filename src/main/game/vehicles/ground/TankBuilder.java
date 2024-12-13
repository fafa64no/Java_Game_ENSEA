package main.game.vehicles.ground;

import main.game.level.target.Target;
import main.game.level.weapons.BasicWeapon;
import main.game.level.weapons.machine_guns.MachineGun;
import main.game.level.weapons.tank_guns.TankGun;
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
    public static TankControlDynamicPoint genTestTank(Vec2 position, boolean isAlly) {
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
        BasicTurret turret2 = new BasicTurret(
                0.04,
                new Vec2(10,10),
                0,
                null
        );
        Target target = new Target(
                100
        );
        BasicWeapon mainGun = new TankGun(
                0,
                turret,
                isAlly,
                new Vec2(),
                RenderingLayer.RENDERING_LAYER_GROUND_MIDDLE
        );
        BasicWeapon secondaryGun = new MachineGun(
                0,
                turret,
                isAlly,
                new Vec2(),
                RenderingLayer.RENDERING_LAYER_GROUND_MIDDLE
        );
        BasicWeapon mainGun2 = new TankGun(
                0,
                turret2,
                isAlly,
                new Vec2(),
                RenderingLayer.RENDERING_LAYER_GROUND_MIDDLE
        );

        target
                .addPrimaryWeapon(mainGun)
                .addSecondaryWeapon(secondaryGun)
                .addPrimaryWeapon(mainGun2);

        int hitBoxSize = 15;

        tank
            .setMainCollider(new BoxCollider(
                    new Vec4(-hitBoxSize, hitBoxSize,-hitBoxSize, hitBoxSize),
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
                    TextureGen.getPanzerIV_texture().toArray()
            ))
            .addChild(turret)
            .addSprite(new AnimatedSprite(
                    RenderingLayer.RENDERING_LAYER_GROUND_BOTTOM,
                    target,
                    100,
                    TextureGen.getPanzerIV_turret_deadTexture().toArray(),
                    TextureGen.getPanzerIV_turret_texture().toArray()
            ))
            .setTarget(target);
        tank
            .addChild(turret2)
            .addSprite(new AnimatedSprite(
                    RenderingLayer.RENDERING_LAYER_GROUND_BOTTOM,
                    target,
                    100,
                    TextureGen.getPanzerIV_turret_deadTexture().toArray(),
                    TextureGen.getPanzerIV_turret_texture().toArray()
            ))
            .setTarget(target);
        return tank;
    }
}
