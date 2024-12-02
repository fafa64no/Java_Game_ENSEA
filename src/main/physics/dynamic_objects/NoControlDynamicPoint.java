package main.physics.dynamic_objects;

import main.physics.colliders.Collider;
import main.utils.vectors.Vec2;

public class NoControlDynamicPoint extends DynamicPoint{
    public NoControlDynamicPoint(
            Collider mainCollider,
            Vec2 initialPosition,
            Vec2 initialVelocity,
            double initialRotation
    ) {
        super(
                mainCollider,
                0,
                0,
                initialPosition,
                initialVelocity,
                initialRotation
        );
    }

    @Override
    protected void convertInputToVelocity() {
        currentVelocity = initialVelocity;
    }

    @Override
    protected void convertInputToRotation() {

    }
}
