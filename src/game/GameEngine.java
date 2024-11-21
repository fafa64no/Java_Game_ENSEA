package game;

import game.characters.vehicles.tank.Tank;
import game.hud.HudManager;
import physics.Collider;
import physics.PhysicEngine;
import rendering.RenderEngine;
import utils.DataGen;
import utils.Engine;
import utils.IVec2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements KeyListener, Engine {
    private static GameEngine instance;

    private final Level[] levels;
    private int currentLevel=0;
    private final Tank[] tanks;
    private int currentTank=0;

    private final IVec2 currentInputDir = new IVec2();
    private final HudManager hudManager=new HudManager();

    public GameEngine() {
        if(instance==null)instance=this;

        levels=DataGen.genLevels();
        tanks=DataGen.genTanks();

        goToLevel(3);
        swapTank(0);

        RenderEngine.getCurrentCamera().setCameraTarget(tanks[currentTank]);
        RenderEngine.getInstance().addKeyListener(instance);
    }

    private void goToLevel(int i){
        for (Collider collider : levels[currentLevel].getColliders())
            PhysicEngine.removeStaticCollider(collider);
        RenderEngine.removeFromRenderList(levels[currentLevel]);
        currentLevel=i;
        for (Collider collider : levels[currentLevel].getColliders())
            PhysicEngine.addStaticCollider(collider);
        RenderEngine.addToRenderList(levels[currentLevel],2);
        RenderEngine.paint();
    }

    private void swapTank(int i){
        RenderEngine.removeFromRenderList(tanks[currentTank]);
        RenderEngine.removeFromRenderList(tanks[currentTank].getTurret());
        PhysicEngine.removeDynamicSprite(tanks[currentTank]);
        currentTank=i;
        PhysicEngine.addDynamicSprite(tanks[currentTank]);
        RenderEngine.addToRenderList(tanks[currentTank],1);
        RenderEngine.addToRenderList(tanks[currentTank].getTurret(),0);
        RenderEngine.paint();
    }

    public static GameEngine getInstance() {
        return instance;
    }

    @Override
    public void update() {
        tanks[currentTank].setInput(currentInputDir);
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
            case 82: // Swap Tank ("R")
                instance.swapTank((currentTank+1)%tanks.length);        break;
            case 84: // Test key ("T")
                instance.goToLevel((currentLevel+1)%levels.length);     break;
            default:
                //System.out.println(e.getKeyCode());
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
