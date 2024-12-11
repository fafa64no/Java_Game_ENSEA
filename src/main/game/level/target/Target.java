package main.game.level.target;

import main.game.controllers.Controller;
import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.Vec2;

public class Target {
    protected DynamicPoint mainNode;

    protected final double maxHealth;
    protected double currentHealth;
    protected LifeState currentLifeState = LifeState.LIFE_STATE_IDLE;

    protected Controller controller;

    public Target(double maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void addMainNode(DynamicPoint mainNode) {
        if (this.mainNode != null) {
            System.out.println("Trying to overwrite mainNode.");
            return;
        }
        this.mainNode = mainNode;
    }

    public boolean isTargetable() {
        return currentLifeState != LifeState.LIFE_STATE_DEAD;
    }

    public boolean canMove() {
        return currentLifeState != LifeState.LIFE_STATE_DEAD;
    }

    public LifeState getLifeState() {
        return currentLifeState;
    }

    public void dealDamage(double damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            killYourSelf();
            return;
        }
        currentHealth = Math.clamp(currentHealth, 0, maxHealth);
    }

    public double getHealthPercentage() {
        if (maxHealth == 0) return 0;
        return currentHealth/maxHealth;
    }

    public Vec2 getPosition() {
        return mainNode.getPosition();
    }

    public Vec2 getVelocity() {
        return mainNode.getCurrentVelocity();
    }

    protected void killYourSelf() {
        currentLifeState = LifeState.LIFE_STATE_DEAD;
    }

    public Vec2 getCurrentAimPoint() {
        return controller.getAimPoint();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
