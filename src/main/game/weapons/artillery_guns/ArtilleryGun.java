package main.game.weapons.artillery_guns;

import main.game.projectiles.ArtilleryShell;
import main.game.weapons.BasicWeapon;
import main.physics.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.Vec2;

public class ArtilleryGun extends BasicWeapon {
    public ArtilleryGun(
            double spread,
            DynamicPoint parent,
            boolean isAlly,
            Vec2 initialOffset
    ) {
        super(
                ArtilleryShell.getInstance(),
                40,
                spread,
                parent,
                (isAlly) ? CollisionLayer.COLLISION_LAYER_ALLY_PROJECTILES
                        : CollisionLayer.COLLISION_LAYER_ENEMY_PROJECTILES,
                initialOffset,
                0
        );
    }
}
