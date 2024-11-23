package main;

import main.game.GameEngine;
import main.physics.PhysicEngine;
import main.rendering.RenderEngine;
import main.utils.data.Cfg;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Main {
    private static final PhysicEngine physicEngine=new PhysicEngine();
    private static final RenderEngine renderEngine=new RenderEngine();
    private static final GameEngine gameEngine=new GameEngine();

    public static void main(String[] args) {
        System.out.println("Starting engines");

        ActionListener updateTasks = e -> {
            physicEngine.update();
            renderEngine.update();
            gameEngine.update();
        };

        Timer timer=new Timer(Cfg.delayBetweenFrames, updateTasks);
        timer.setRepeats(true);
        timer.start();

        System.out.println("Updating engines");
    }
}