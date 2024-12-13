package main.game.level.weapons.artillery_guns;

import main.game.level.weapons.projectiles.ArtilleryShell;
import main.game.level.weapons.BasicWeapon;
import main.physics.layers.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.layers.RenderingLayer;
import main.utils.vectors.Vec2;

public class ArtilleryGun extends BasicWeapon {
    public ArtilleryGun(
            double spread,
            DynamicPoint parent,
            boolean isAlly,
            Vec2 initialOffset,
            RenderingLayer renderingLayer
    ) {
        super(
                ArtilleryShell.getInstance(),
                40,
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
