package main.physics.dynamic_objects;

import main.game.effects.Effect;
import main.physics.PhysicEngine;
import main.physics.colliders.Collider;
import main.utils.containers.BufferedList;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec3;

public abstract class DynamicPoint {
    protected final DynamicPoint parent;
    protected final BufferedList<DynamicPoint> children = new BufferedList<>();

    protected final Collider mainCollider;

    protected Vec3 currentInput;

    protected Vec2 position;
    protected Vec2 currentVelocity;

    protected double rotation;
    protected double currentRotationSpeed;

    protected final double velocityModifier;
    protected final double rotationModifier;

    public DynamicPoint(
            Collider mainCollider,
            double velocityModifier,
            double rotationModifier,
            Vec2 initialPosition,
            Vec2 initialVelocity,
            double initialRotation
    ) {
        this.mainCollider = mainCollider;
        this.velocityModifier = velocityModifier;
        this.rotationModifier = rotationModifier;
        this.position = initialPosition;
        this.currentVelocity = initialVelocity;
        this.rotation = initialRotation;

        this.parent = null;
    }

    public void addToPhysicsEngine() {
        children.flush();
        PhysicEngine.addDynamicPoint(this);
        if (mainCollider != null) mainCollider.addColliderToColliderList();
        for(DynamicPoint child : children.elements) {
            child.addToPhysicsEngine();
        }
    }

    public void removeFromRemovePhysicsEngine() {
        children.flush();
        PhysicEngine.removeDynamicPoint(this);
        if (mainCollider != null) mainCollider.removeColliderFromColliderList();
        for(DynamicPoint child : children.elements) {
            child.removeFromRemovePhysicsEngine();
        }
    }

    public void addInput(Vec3 input) {
        currentInput = Vec3.add(currentInput, input);
    }

    public void updatePhysics(){
        convertInputToVelocity();
        convertInputToRotation();
        currentInput = new Vec3();
    }

    public void applyPhysics() {
        children.flush();
        applyCurrentVelocity();
        applyCurrentRotationSpeed();
        if (mainCollider != null) mainCollider.updateCollider();
    }

    protected void applyCurrentVelocity() {
        if (mainCollider != null) {
            if (mainCollider.isBlockedAlongAxis().x) {
                currentVelocity.x = 0;
                currentVelocity.y *= mainCollider.getEncounteredInverseFriction();
            }
            if (mainCollider.isBlockedAlongAxis().y) {
                currentVelocity.y = 0;
                currentVelocity.x *= mainCollider.getEncounteredInverseFriction();
            }
        }
        position = Vec2.add(position, currentVelocity);

        for (DynamicPoint child : children.elements) {
            child.readVelocityFromParent();
        }
    }

    protected void applyCurrentRotationSpeed() {
        rotation = (rotation + currentRotationSpeed) % (2 * Math.PI);
    }

    protected void readVelocityFromParent() {
        if (parent == null) {
            System.out.println("Where's dad ???????????????");
        } else {
            position = Vec2.add(position, parent.getCurrentVelocity());
        }
    }

    public Vec2 getPosition() {
        return position;
    }

    public Vec2 getCurrentVelocity() {
        return currentVelocity;
    }

    public void applyEffect(Effect effect, double modifier) {

    }

    public void addChild(DynamicPoint dynamicPoint) {
        children.addElement(dynamicPoint);
    }

    public void removeChild(DynamicPoint dynamicPoint) {
        children.removeElement(dynamicPoint);
    }

    protected abstract void convertInputToVelocity();
    protected abstract void convertInputToRotation();
}
