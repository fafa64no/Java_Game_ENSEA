package main.game.weapons.tank_guns;

import main.game.projectiles.TankShell;
import main.game.weapons.BasicWeapon;
import main.physics.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.Vec2;

public class TankGun extends BasicWeapon {
    public TankGun(
            double spread,
            DynamicPoint parent,
            boolean isAlly,
            Vec2 initialOffset
    ) {
        super(
            TankShell.getInstance(),
            30,
            spread,
            parent,
            (isAlly) ? CollisionLayer.COLLISION_LAYER_ALLY_PROJECTILES
                    : CollisionLayer.COLLISION_LAYER_ENEMY_PROJECTILES,
            initialOffset,
            0
        );
    }
}
