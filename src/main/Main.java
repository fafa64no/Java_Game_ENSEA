package main;

import main.game.GameEngine;
import main.game.hud.HudEngine;
import main.physics.PhysicEngine;
import main.rendering.RenderEngine;
import main.utils.data.Config;
import main.utils.debug.Debug;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Main {
    private static PhysicEngine physicEngine;
    private static RenderEngine renderEngine;
    private static GameEngine gameEngine;
    private static HudEngine hudEngine;

    public static void main(String[] args) {
        Debug.printTimeSinceLast(null);

        physicEngine=new PhysicEngine();
        renderEngine=new RenderEngine();
        Debug.printTimeSinceLast("Started RenderEngine and PhysicEngine");

        gameEngine=new GameEngine();
        Debug.printTimeSinceLast("Started GameEngine");

        hudEngine=new HudEngine();

        ActionListener updateTasks = e -> {
            physicEngine.update();
            renderEngine.update();
            gameEngine.update();
            hudEngine.update();
        };

        Timer timer=new Timer(Config.delayBetweenFrames, updateTasks);
        timer.setRepeats(true);
        timer.start();

        RenderEngine.paint();

        Debug.printTimeSinceLast("Reached end of main");
        Debug.printTime("---");
    }
}