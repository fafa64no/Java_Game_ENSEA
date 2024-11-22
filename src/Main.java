import game.GameEngine;
import physics.PhysicEngine;
import rendering.RenderEngine;
import utils.Cfg;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Main {
    private static final PhysicEngine physicEngine=new PhysicEngine();
    private static final RenderEngine renderEngine=new RenderEngine();
    private static final GameEngine gameEngine=new GameEngine();

    public static void main(String[] args) {
        ActionListener updateTasks = e -> {
            physicEngine.update();
            renderEngine.update();
            gameEngine.update();
        };

        Timer timer=new Timer(Cfg.getDelayBetweenFrames(), updateTasks);
        timer.setRepeats(true);
        timer.start();

        System.out.println("Timer launched");
    }
}