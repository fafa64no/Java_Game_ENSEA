package main.game.vehicles.ground;

import main.game.vehicles.turret.BasicTurret;
import main.physics.colliders.Collider;
import main.physics.dynamic_objects.TankControlDynamicPoint;
import main.utils.vectors.Vec2;

public class SingleTurretTank extends TankControlDynamicPoint {
    protected final BasicTurret turret;

    public SingleTurretTank(
            Collider mainCollider,
            double velocityModifier,
            double rotationModifier,
            Vec2 initialPosition
    ) {
        super(
                mainCollider,
                velocityModifier,
                rotationModifier,
                initialPosition,
                new Vec2(),
                0
        );

        turret = new BasicTurret(

        );
    }
}
