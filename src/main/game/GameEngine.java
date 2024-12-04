package main.game;

import main.game.input.InputManager;
import main.game.level.Level;
import main.game.level.weapons.projectiles.*;
import main.physics.dynamic_objects.NoControlDynamicPoint;
import main.rendering.RenderEngine;
import main.rendering.camera.Camera;
import main.utils.RequiresUpdates;
import main.utils.containers.BufferedList;
import main.utils.data.DataGen;
import main.utils.Engine;
import main.utils.vectors.Vec2;

import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Engine {
    private static GameEngine instance;

    private final Level[] levels;
    private int currentLevel=0;

    private final BufferedList<RequiresUpdates> requiresUpdates = new BufferedList<>();

    private final List<ProjectileHandler> projectileHandlers = new ArrayList<>();

    private final InputManager inputManager = new InputManager();

    public GameEngine() {
        if(instance==null)instance=this;
        RenderEngine.getInstance().addMouseWheelListener(inputManager);
        RenderEngine.getInstance().addMouseListener(inputManager);
        RenderEngine.getInstance().addKeyListener(inputManager);

        levels=DataGen.genLevels();
        initGame();
    }

    private void initGame() {
        RenderEngine.setCurrentCamera(new Camera(
                new Vec2(),
                new Vec2(3),
                new Vec2(0.5,5),
                new NoControlDynamicPoint(
                        null,
                        new Vec2(),
                        new Vec2(0,0),
                        0
                )
        ));
        initProjectileHandlers();
        currentLevel=0;
        levels[currentLevel].loadLevel();
    }

    @Override
    public void update() {
        requiresUpdates.flush();
        for(RequiresUpdates requiresUpdate : requiresUpdates.elements)requiresUpdate.doUpdate();
    }

    private void goToLevel(int i){
        levels[currentLevel].unloadLevel();
        currentLevel=i;
        levels[currentLevel].loadLevel();
    }

    public static GameEngine getInstance() {
        if(instance!=null)return instance;
        return new GameEngine();
    }

    public static void addRequiresUpdates(RequiresUpdates requiresUpdates){
        instance.requiresUpdates.addElement(requiresUpdates);
    }

    public static void removeRequiresUpdates(RequiresUpdates requiresUpdates){
        instance.requiresUpdates.removeElement(requiresUpdates);
    }

    private void initProjectileHandlers() {
        projectileHandlers.add(new MachineGunBullet());
        projectileHandlers.add(new AerialMachineGunBullet());
        projectileHandlers.add(new ArtilleryShell());
        projectileHandlers.add(new TankShell());
    }

    public static Level getCurrentLevel() {
        return instance.levels[instance.currentLevel];
    }
}
