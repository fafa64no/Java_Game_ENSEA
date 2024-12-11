package main.physics.dynamic_objects;

import main.utils.vectors.Vec2;

public class TankControlDynamicPoint extends DynamicPoint {
    public TankControlDynamicPoint(
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
        currentVelocity.x = - currentInput.y * velocityModifier * Math.sin(rotation);
        currentVelocity.y = + currentInput.y * velocityModifier * Math.cos(rotation);
    }

    @Override
    protected void convertInputToRotation() {
        currentRotationSpeed = currentInput.x * rotationModifier;
    }
}
