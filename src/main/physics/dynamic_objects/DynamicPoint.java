package main.physics.dynamic_objects;

import main.game.controllers.Controller;
import main.game.level.target.Target;
import main.game.level.target.effects.Effect;
import main.game.level.weapons.projectiles.Projectile;
import main.physics.PhysicEngine;
import main.physics.colliders.Collider;
import main.rendering.sprites.Sprite;
import main.utils.containers.BufferedList;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec3;

public abstract class DynamicPoint {
    protected DynamicPoint parent;
    protected final BufferedList<DynamicPoint> children = new BufferedList<>();
    protected final BufferedList<Sprite> sprites = new BufferedList<>();
    protected Target target;

    protected Collider mainCollider;

    protected Vec3 currentInput;

    protected Vec2 position;
    protected Vec2 currentVelocity;
    protected Vec2 initialVelocity;

    protected double rotation;
    protected double currentRotationSpeed;

    protected final double velocityModifier;
    protected final double rotationModifier;

    public DynamicPoint(
            double velocityModifier,
            double rotationModifier,
            Vec2 initialPosition,
            Vec2 initialVelocity,
            double initialRotation
    ) {
        this.mainCollider = null;
        this.velocityModifier = velocityModifier;
        this.rotationModifier = rotationModifier;
        this.position = initialPosition;
        this.currentVelocity = initialVelocity;
        this.initialVelocity = currentVelocity;
        this.rotation = initialRotation;

        this.parent = null;
        this.target = null;

        this.currentInput = new Vec3();
    }

    public DynamicPoint addToPhysicsEngine() {
        children.flush();
        PhysicEngine.addDynamicPoint(this);
        if (mainCollider != null) mainCollider.addColliderToColliderList();
        for(DynamicPoint child : children.elements) {
            child.addToPhysicsEngine();
        }
        return this;
    }

    public DynamicPoint removeFromRemovePhysicsEngine() {
        children.flush();
        PhysicEngine.removeDynamicPoint(this);
        if (mainCollider != null) mainCollider.removeColliderFromColliderList();
        for(DynamicPoint child : children.elements) {
            child.removeFromRemovePhysicsEngine();
        }
        return this;
    }

    public DynamicPoint addToRenderList() {
        sprites.flush();
        for (Sprite sprite : sprites.elements) {
            sprite.addToRenderList();
        }
        for(DynamicPoint child : children.elements) {
            child.addToRenderList();
        }
        return this;
    }

    public DynamicPoint removeFromRenderList() {
        sprites.flush();
        for (Sprite sprite : sprites.elements) {
            sprite.removeFromRenderList();
        }
        for(DynamicPoint child : children.elements) {
            child.removeFromRenderList();
        }
        return this;
    }

    public void setInput(Vec3 input) {
        if (target != null && !target.canMove()) {
            currentInput = new Vec3();
            return;
        }
        currentInput = input;
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
        for (DynamicPoint child : children.elements) {
            child.readRotationFromParent();
        }

        rotation = (rotation + currentRotationSpeed) % (2 * Math.PI);
    }

    protected void readVelocityFromParent() {
        if (parent == null) {
            System.out.println("Where's my dad ???????????????");
        } else {
            position = Vec2.add(position, parent.getCurrentVelocity());
        }
    }

    protected void readRotationFromParent() {
        if (parent == null) {
            System.out.println("Where's my dad ???????????????");
        } else {
            rotation = (rotation + parent.currentRotationSpeed) % (2 * Math.PI);
        }
    }

    public Vec2 getPosition() {
        return position;
    }

    public Vec2 getCurrentVelocity() {
        return currentVelocity;
    }

    public double getRotation() {
        return rotation;
    }

    public void applyEffect(Effect effect, double modifier) {
        switch (effect) {
            case DAMAGE:
                if (target != null) target.dealDamage(modifier);
                break;
            case BLOCK:
                if (this instanceof Projectile) ((Projectile) this).destroyProjectile();
                break;
        }
    }

    public DynamicPoint addChild(DynamicPoint child) {
        children.addElement(child);
        child.setParent(this);
        return child;
    }

    public void setParent(DynamicPoint parent) {
        if (this.parent == null) {
            this.parent = parent;
            this.position = Vec2.add(position,parent.getPosition());
        } else {
            System.out.println("Trying to overwrite parent.");
        }
    }

    public DynamicPoint setTarget(Target target) {
        if (this.target == null) {
            this.target = target;
        } else {
            System.out.println("Trying to overwrite target.");
        }
        return this;
    }

    public DynamicPoint setMainCollider(Collider collider) {
        if (this.mainCollider == null) {
            this.mainCollider = collider;
            collider.setParent(this);
        } else {
            System.out.println("Trying to overwrite mainCollider.");
        }
        return this;
    }

    public DynamicPoint addSprite(Sprite sprite) {
        sprite.setParent(this);
        sprites.addElement(sprite);
        return this;
    }

    public void setController(Controller controller) {
        if (target == null) return;
        target.setController(controller);
    }

    public Target getTarget() {
        return target;
    }

    protected abstract void convertInputToVelocity();
    protected abstract void convertInputToRotation();
}
