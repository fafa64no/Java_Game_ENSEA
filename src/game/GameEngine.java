package game;

import game.characters.Character;
import game.characters.vehicles.tank.Tank;
import game.hud.HudManager;
import physics.Collider;
import physics.PhysicEngine;
import rendering.RenderEngine;
import utils.Engine;
import utils.IVec2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements KeyListener, Engine {
    private static GameEngine instance;

    private final Level[] levels;
    private int currentLevel;

    private final IVec2 currentInputDir = new IVec2();

    private final HudManager hudManager;

    private final Tank currentTank = new Tank(
            new IVec2(320,120),
            new Collider(16,16,48,48),
            "./img/characters/tanks/test/base.png",
            "./img/characters/tanks/test/turret.png",
            10,
            new IVec2(64,64),
            1,
            0.1,
            0.05
    );

    public GameEngine() {
        if(instance==null)instance=this;

        levels=new Level[4];
        levels[0]=new Level(new IVec2(16,9),"./data/level1.txt");
        levels[1]=new Level(new IVec2(16,9),"./data/level2.txt");
        levels[2]=new Level(new IVec2(16,9),"./data/level3.txt");
        levels[3]=new Level(new IVec2(80,40),"./data/level4.txt");
        currentLevel=3;

        RenderEngine.getInstance().getCurrentCamera().setCameraTarget(this.currentTank);

        RenderEngine.getInstance().addKeyListener(this);

        RenderEngine.getInstance().addToRenderList(levels[currentLevel],2);
        RenderEngine.getInstance().addToRenderList(currentTank,1);
        RenderEngine.getInstance().addToRenderList(currentTank.getTurret(),0);
        this.hudManager=new HudManager();
        RenderEngine.getInstance().paint();

        PhysicEngine.getInstance().addDynamicSprite(currentTank);
        for (Collider collider : levels[currentLevel].getColliders())
            PhysicEngine.getInstance().addStaticCollider(collider);
    }

    private void goToLevel(int i){
        for (Collider collider : levels[currentLevel].getColliders())
            PhysicEngine.getInstance().removeStaticCollider(collider);
        RenderEngine.getInstance().removeFromRenderList(levels[currentLevel]);

        this.currentLevel=i;

        for (Collider collider : levels[currentLevel].getColliders())
            PhysicEngine.getInstance().addStaticCollider(collider);
        RenderEngine.getInstance().addToRenderList(levels[currentLevel],2);
        RenderEngine.getInstance().paint();
        currentTank.resetPosition();
    }

    public static GameEngine getInstance() {
        return instance;
    }

    @Override
    public void update() {
        currentTank.setInput(currentInputDir);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case 68: // Move right ("D")
                currentInputDir.x+=1;   break;
            case 81: // Move left ("Q")
                currentInputDir.x-=1;   break;
            case 83: // Move down ("S")
                currentInputDir.y+=1;   break;
            case 90: // Move up ("Z")
                currentInputDir.y-=1;   break;
            case 84: // Test key ("T")
                this.goToLevel((currentLevel+1)%levels.length);     break;
        }
        currentInputDir.x=Math.min(Math.max(currentInputDir.x,-1),1);
        currentInputDir.y=Math.min(Math.max(currentInputDir.y,-1),1);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case 68: // Move right ("D")
                currentInputDir.x-=1;   break;
            case 81: // Move left ("Q")
                currentInputDir.x+=1;   break;
            case 83: // Move down ("S")
                currentInputDir.y-=1;   break;
            case 90: // Move up ("Z")
                currentInputDir.y+=1;   break;
        }
        currentInputDir.x=Math.min(Math.max(currentInputDir.x,-1),1);
        currentInputDir.y=Math.min(Math.max(currentInputDir.y,-1),1);
    }
}
