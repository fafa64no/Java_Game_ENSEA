package main.game.input;

import main.game.GameEngine;
import main.game.controllers.Controller;
import main.game.level.target.Target;
import main.game.level.weapons.BasicWeapon;
import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.RenderEngine;
import main.rendering.camera.Camera;
import main.utils.RequiresUpdates;
import main.utils.containers.BufferedList;
import main.utils.vectors.Vec2;

public class Player implements RequiresUpdates, Controller {
    private final InputManager inputManager = new InputManager();

    public final Camera camera;

    private DynamicPoint currentVehicle;

    protected final BufferedList<BasicWeapon> primaryWeapons    = new BufferedList<>();
    protected final BufferedList<BasicWeapon> secondaryWeapons  = new BufferedList<>();
    protected final BufferedList<BasicWeapon> tertiaryWeapons   = new BufferedList<>();

    public Player() {
        camera = new Camera(
                new Vec2(),
                new Vec2(3),
                new Vec2(0.5,5),
                null
        );
        RenderEngine.getInstance().addMouseWheelListener(inputManager);
        RenderEngine.getInstance().addMouseListener(inputManager);
        RenderEngine.getInstance().addKeyListener(inputManager);
        GameEngine.addRequiresUpdates(camera);
        GameEngine.addRequiresUpdates(this);
    }

    public void setCurrentVehicle(DynamicPoint vehicle) {
        removePrecedentVehicle();
        currentVehicle = vehicle;
        currentVehicle.setController(this);

        camera.setTarget(currentVehicle);

        Target target = currentVehicle.getTarget();
        if (target == null) return;
        for (BasicWeapon weapon : target.getPrimaryWeapons()) primaryWeapons.addElement(weapon);
        for (BasicWeapon weapon : target.getSecondaryWeapons()) secondaryWeapons.addElement(weapon);
        for (BasicWeapon weapon : target.getTertiaryWeapons()) tertiaryWeapons.addElement(weapon);

        primaryWeapons.flush();
        secondaryWeapons.flush();
        tertiaryWeapons.flush();
    }

    protected void removePrecedentVehicle() {
        if (currentVehicle == null) return;
        currentVehicle.setController(null);
        Target target = currentVehicle.getTarget();
        if (target == null) return;
        for (BasicWeapon weapon : target.getPrimaryWeapons()) primaryWeapons.removeElement(weapon);
        for (BasicWeapon weapon : target.getSecondaryWeapons()) secondaryWeapons.removeElement(weapon);
        for (BasicWeapon weapon : target.getTertiaryWeapons()) tertiaryWeapons.removeElement(weapon);
    }

    @Override
    public void doUpdate() {
        tryToFirePrimary();
        tryToFireSecondary();
        tryToFireTertiary();
        if (currentVehicle != null) {
            currentVehicle.setInput(inputManager.getCurrentInputDir());
        }
    }

    protected void tryToFirePrimary() {
        if (!isFiringPrimary()) return;
        for (BasicWeapon weapon : primaryWeapons.elements) {
            if (weapon.tryToFire()) {
                System.out.println("Firing primary.");
            }
        }
    }

    protected void tryToFireSecondary() {
        if (!isFiringSecondary()) return;
        for (BasicWeapon weapon : secondaryWeapons.elements) {
            if (weapon.tryToFire()) {
                //System.out.println("Firing secondary.");
            }
        }
    }

    protected void tryToFireTertiary() {
        if (!isFiringTertiary()) return;
        for (BasicWeapon weapon : tertiaryWeapons.elements) {
            if (weapon.tryToFire()) {
                System.out.println("Firing tertiary.");
            }
        }
    }


    @Override
    public Vec2 getAimPoint() {
        return camera.getCursorPosInCamera();
    }

    protected boolean isFiringPrimary() {
        return inputManager.getAbilitiesEnabled()[AbilityType.ABILITY_PRIMARY_FIRE.ordinal()];
    }

    protected boolean isFiringSecondary() {
        return inputManager.getAbilitiesEnabled()[AbilityType.ABILITY_SECONDARY_FIRE.ordinal()];
    }

    protected boolean isFiringTertiary() {
        return inputManager.getAbilitiesEnabled()[AbilityType.ABILITY_TERTIARY_FIRE.ordinal()];
    }
}
