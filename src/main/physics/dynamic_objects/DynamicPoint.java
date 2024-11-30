package main.physics.dynamic_objects;

import main.game.effects.Effect;
import main.physics.PhysicEngine;
import main.physics.colliders.Collider;
import main.utils.vectors.Vec2;

public abstract class DynamicPoint {
    protected final Collider mainCollider;

    protected Vec2 position;
    protected Vec2 currentVelocity;
    protected Vec2 currentInput;
    protected double rotation;

    protected final double velocityModifier;

    public DynamicPoint(
            Collider mainCollider,
            double velocityModifier,
            Vec2 initialPosition,
            Vec2 initialVelocity,
            double initialRotation
    ) {
        this.mainCollider = mainCollider;
        this.velocityModifier = velocityModifier;
        this.position = initialPosition;
        this.currentVelocity = initialVelocity;
        this.rotation = initialRotation;

        PhysicEngine.addDynamicPoint(this);
    }

    public void addInput(Vec2 input) {
        currentInput = Vec2.add(currentInput, input);
    }

    public void updatePhysics(){
        convertInputToVelocity();
        currentInput = new Vec2();
    }

    public void applyCurrentVelocity() {
        if (mainCollider.isBlockedAlongAxis().x)
            position.x += currentVelocity.x * mainCollider.getEncounteredInverseFriction();
        if (mainCollider.isBlockedAlongAxis().y)
            position.y += currentVelocity.y * mainCollider.getEncounteredInverseFriction();
        mainCollider.updateCollider();
    }

    public Vec2 getPosition() {
        return position;
    }

    public Vec2 getCurrentVelocity() {
        return currentVelocity;
    }

    public void applyEffect(Effect effect, double modifier) {

    }

    protected abstract void convertInputToVelocity();
}
