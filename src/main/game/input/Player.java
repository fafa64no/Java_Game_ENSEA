package main.game.input;

import main.game.GameEngine;
import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.RenderEngine;
import main.rendering.camera.Camera;
import main.utils.RequiresUpdates;
import main.utils.vectors.Vec2;

public class Player implements RequiresUpdates {
    private final InputManager inputManager = new InputManager();

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
        currentVehicle = vehicle;
        camera.setTarget(currentVehicle);
    }

    @Override
    public void doUpdate() {
        if (currentVehicle != null) {
            currentVehicle.setInput(inputManager.getCurrentInputDir());
        }
    }
}
