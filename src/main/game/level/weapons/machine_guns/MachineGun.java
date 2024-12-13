package main.game.level.weapons.machine_guns;

import main.game.level.weapons.projectiles.MachineGunBullet;
import main.game.level.weapons.BasicWeapon;
import main.physics.layers.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.layers.RenderingLayer;
import main.utils.vectors.Vec2;

public class MachineGun extends BasicWeapon {
    public MachineGun(
            double spread,
            DynamicPoint parent,
            boolean isAlly,
            Vec2 initialOffset,
            RenderingLayer renderingLayer
    ) {
        super(
            MachineGunBullet.getInstance(),
            0,
            spread,
            parent,
            (isAlly) ? CollisionLayer.COLLISION_LAYER_ALLY_PROJECTILES
                    : CollisionLayer.COLLISION_LAYER_ENEMY_PROJECTILES,
            initialOffset,
            0,
            renderingLayer
        );
    }
}
