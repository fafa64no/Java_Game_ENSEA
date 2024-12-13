package main.game.level.target;

import main.game.controllers.Controller;
import main.game.level.weapons.BasicWeapon;
import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.Vec2;

import java.util.ArrayList;
import java.util.List;

public class Target {
    protected DynamicPoint mainNode;

    protected final double maxHealth;
    protected double currentHealth;
    protected LifeState currentLifeState = LifeState.LIFE_STATE_IDLE;

    protected Controller controller;

    protected final List<BasicWeapon> primaryWeapons = new ArrayList<>();
    protected final List<BasicWeapon> secondaryWeapons = new ArrayList<>();
    protected final List<BasicWeapon> tertiaryWeapons = new ArrayList<>();

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

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Target addPrimaryWeapon(BasicWeapon weapon) {
        primaryWeapons.add(weapon);
        return this;
    }

    public Target addSecondaryWeapon(BasicWeapon weapon) {
        secondaryWeapons.add(weapon);
        return this;
    }

    public Target addTertiaryWeapon(BasicWeapon weapon) {
        tertiaryWeapons.add(weapon);
        return this;
    }

    public List<BasicWeapon> getPrimaryWeapons() {
        return primaryWeapons;
    }

    public List<BasicWeapon> getSecondaryWeapons() {
        return secondaryWeapons;
    }

    public List<BasicWeapon> getTertiaryWeapons() {
        return tertiaryWeapons;
    }
}
