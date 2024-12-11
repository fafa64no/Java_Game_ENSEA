package main.game.input;

import main.game.GameEngine;
import main.game.controllers.Controller;
import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.RenderEngine;
import main.rendering.camera.Camera;
import main.utils.RequiresUpdates;
import main.utils.vectors.Vec2;

public class Player implements RequiresUpdates, Controller {
    private final InputManager inputManager = new InputManager();
    private final Vec2 aimPoint = new Vec2();

    public final Camera camera;

    private DynamicPoint currentVehicle;

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
        if (currentVehicle != null) currentVehicle.setController(null);
        currentVehicle = vehicle;
        currentVehicle.setController(this);
        camera.setTarget(currentVehicle);
    }

    @Override
    public void doUpdate() {
        if (currentVehicle != null) {
            currentVehicle.setInput(inputManager.getCurrentInputDir());
        }
    }

    @Override
    public Vec2 getAimPoint() {
        return camera.getCursorPosInCamera();
    }
}
