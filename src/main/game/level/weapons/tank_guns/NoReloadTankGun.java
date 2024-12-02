package main.game.level.weapons.tank_guns;

import main.game.level.weapons.projectiles.TankShell;
import main.game.level.weapons.BasicWeapon;
import main.physics.layers.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.Vec2;

public class NoReloadTankGun extends BasicWeapon {
    public NoReloadTankGun(
            double spread,
            DynamicPoint parent,
            boolean isAlly,
            Vec2 initialOffset
    ) {
        super(
            TankShell.getInstance(),
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
