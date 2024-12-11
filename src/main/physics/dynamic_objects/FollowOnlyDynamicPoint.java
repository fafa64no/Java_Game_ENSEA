package main.physics.dynamic_objects;

import main.utils.vectors.Vec2;

public class FollowOnlyDynamicPoint extends DynamicPoint{
    public FollowOnlyDynamicPoint(Vec2 initialPosition, double initialRotation) {
        super(0, 0, initialPosition, new Vec2(), initialRotation);
    }

    @Override
    protected void convertInputToVelocity() {

    }

    @Override
    protected void convertInputToRotation() {

    }
}
