package main.game.vehicles.turret;

import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.Vec2;

public class BasicTurret extends DynamicPoint {
    protected final double rotationOffset;
    protected final Vec2 rotationRange;

    public BasicTurret(
            double rotationModifier,
            Vec2 initialPosition,
            Vec2 initialVelocity,
            double initialRotation,
            Vec2 rotationRange
    ) {
        super(
                null,
                0,
                rotationModifier,
                initialPosition,
                initialVelocity,
                initialRotation
        );

        this.rotationOffset = initialRotation;
        this.rotationRange = rotationRange;
    }

    protected double getTargetRotation(Vec2 targetPosition) {
        double targetRotation = new Vec2(
                targetPosition.x - this.position.x,
                targetPosition.y - this.position.y
        ).getAngle() + rotationOffset + 5 * Math.PI / 2;

        if (rotationRange == null) {
            return targetRotation % (2 * Math.PI);
        }

        double minAngle = rotationRange.x - rotationOffset + 5 * Math.PI / 2;
        double maxAngle = rotationRange.y - rotationOffset + 5 * Math.PI / 2;

        if (parent != null) {
            minAngle += parent.getRotation();
            maxAngle += parent.getRotation();
        }

        targetRotation = Math.clamp(targetRotation, minAngle, maxAngle);

        return targetRotation % (2 * Math.PI);
    }

    @Override
    protected void convertInputToRotation() {
        Vec2 targetPosition = new Vec2(
                currentInput.x,
                currentInput.y
        );
        double targetRotation = getTargetRotation(targetPosition);
        currentRotationSpeed = targetRotation;
    }

    @Override
    protected void convertInputToVelocity() {

    }
}
