package main;

import main.game.GameEngine;
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

    public static void main(String[] args) {
        Debug.printTimeSinceLast("Test");
        physicEngine=new PhysicEngine();
        renderEngine=new RenderEngine();
        Debug.printTimeSinceLast("Started RenderEngine and PhysicEngine");
        gameEngine=new GameEngine();
        Debug.printTimeSinceLast("Started GameEngine");

        ActionListener updateTasks = e -> {
            physicEngine.update();
            renderEngine.update();
            gameEngine.update();
        };

        Timer timer=new Timer(Config.delayBetweenFrames, updateTasks);
        timer.setRepeats(true);
        timer.start();

        RenderEngine.paint();

        Debug.printTimeSinceLast("End of main");
        Debug.printTime("---");
    }
}