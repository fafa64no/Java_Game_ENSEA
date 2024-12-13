package main.physics.dynamic_objects;

import main.utils.vectors.Vec2;

public class PlaneControlDynamicPoint extends DynamicPoint {
    public PlaneControlDynamicPoint(
            double velocityModifier,
            double rotationModifier,
            Vec2 initialPosition,
            Vec2 initialVelocity,
            double initialRotation
    ) {
        super(velocityModifier, rotationModifier, initialPosition, initialVelocity, initialRotation);
    }

    @Override
    protected void convertInputToVelocity() {
        currentVelocity.x = - velocityModifier * Math.sin(rotation);
        currentVelocity.y = + velocityModifier * Math.cos(rotation);
    }

    @Override
    protected void convertInputToRotation() {
        currentRotationSpeed = currentInput.x * rotationModifier;
    }
}