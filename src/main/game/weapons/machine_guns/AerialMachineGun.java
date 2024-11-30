package main.game.weapons.machine_guns;

import main.game.projectiles.AerialMachineGunBullet;
import main.game.weapons.BasicWeapon;
import main.physics.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.Vec2;

public class AerialMachineGun extends BasicWeapon {
    public AerialMachineGun(
            double spread,
            DynamicPoint parent,
            boolean isAlly,
            Vec2 initialOffset
    ) {
        super(
            AerialMachineGunBullet.getInstance(),
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
