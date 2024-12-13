package main.game.level.weapons.tank_guns;

import main.game.level.weapons.projectiles.TankShell;
import main.game.level.weapons.BasicWeapon;
import main.physics.layers.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.layers.RenderingLayer;
import main.utils.vectors.Vec2;

public class TankGun extends BasicWeapon {
    public TankGun(
            double spread,
            DynamicPoint parent,
            boolean isAlly,
            Vec2 initialOffset,
            RenderingLayer renderingLayer
    ) {
        super(
            TankShell.getInstance(),
            30,
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
