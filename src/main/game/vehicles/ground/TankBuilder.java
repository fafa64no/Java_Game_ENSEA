package main.game.vehicles.ground;

import main.game.vehicles.turret.BasicTurret;
import main.physics.dynamic_objects.TankControlDynamicPoint;
import main.rendering.sprites.AnimatedSprite;
import main.utils.vectors.Vec2;

public class TankBuilder {
    public TankControlDynamicPoint genTestTank(Vec2 position) {
        TankControlDynamicPoint tank = new TankControlDynamicPoint(
                1,
                0.5,
                position,
                new Vec2(),
                0
        );
        BasicTurret turret = new BasicTurret(
                0.5,
                new Vec2(),
                0,
                null
        );
        return tank;
    }
}
