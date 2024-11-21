import game.GameEngine;
import physics.PhysicEngine;
import rendering.RenderEngine;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Main {
    private static final PhysicEngine physicEngine=new PhysicEngine();
    private static final RenderEngine renderEngine=new RenderEngine();
    private static final GameEngine gameEngine=new GameEngine();

    public static void main(String[] args) {
        System.out.println("Starting game");

        ActionListener updateTasks = e -> {
            physicEngine.update();
            renderEngine.update();
            gameEngine.update();
        };

        Timer timer=new Timer(20, updateTasks);     // Delay should be 50 but 20 gives better fps
        timer.setRepeats(true);
        timer.start();
    }
}