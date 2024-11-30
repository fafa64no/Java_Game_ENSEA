package main.physics.dynamic_objects;

import main.physics.colliders.Collider;
import main.utils.vectors.Vec2;

public class DroneControlDynamicPoint extends DynamicPoint {
    public DroneControlDynamicPoint(
            Collider mainCollider,
            double velocityModifier,
            double rotationModifier,
            Vec2 initialPosition,
            Vec2 initialVelocity,
            double initialRotation
    ) {
        super(mainCollider, velocityModifier, rotationModifier, initialPosition, initialVelocity, initialRotation);
    }

    @Override
    protected void convertInputToVelocity() {
        currentVelocity.x = velocityModifier * (currentInput.z * Math.cos(rotation) - currentInput.y * Math.sin(rotation));
        currentVelocity.y = velocityModifier * (currentInput.z * Math.sin(rotation) + currentInput.y * Math.cos(rotation));
    }

    @Override
    protected void convertInputToRotation() {
        currentRotationSpeed = currentInput.x * rotationModifier;
    }
}
