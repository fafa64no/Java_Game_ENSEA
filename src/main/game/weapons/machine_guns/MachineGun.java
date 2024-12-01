package main.game.weapons.machine_guns;

import main.game.projectiles.MachineGunBullet;
import main.game.weapons.BasicWeapon;
import main.physics.layers.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.Vec2;

public class MachineGun extends BasicWeapon {
    public MachineGun(
            double spread,
            DynamicPoint parent,
            boolean isAlly,
            Vec2 initialOffset
    ) {
        super(
            MachineGunBullet.getInstance(),
            0,
            spread,
            parent,
            (isAlly) ? CollisionLayer.COLLISION_LAYER_ALLY_PROJECTILES
                    : CollisionLayer.COLLISION_LAYER_ENEMY_PROJECTILES,
            initialOffset,
            0
        );
    }
}
