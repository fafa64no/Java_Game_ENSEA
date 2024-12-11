package main.game.vehicles.turret;

import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.Vec2;

public class BasicTurret extends DynamicPoint {
    protected final double rotationOffset;
    protected final Vec2 rotationRange;

    public BasicTurret(
            double rotationModifier,
            Vec2 initialPosition,
            double initialRotation
    ) {
        super(
                0,
                rotationModifier,
                initialPosition,
                new Vec2(),
                initialRotation
        );

        this.rotationOffset = initialRotation;
        this.rotationRange = null;
    }

    public BasicTurret(
            double rotationModifier,
            Vec2 initialPosition,
            double initialRotation,
            Vec2 rotationRange
    ) {
        super(
                0,
                rotationModifier,
                initialPosition,
                new Vec2(),
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
        currentRotationSpeed = rotationModifier * (targetRotation - rotation);
    }

    @Override
    protected void convertInputToVelocity() {

    }
}
