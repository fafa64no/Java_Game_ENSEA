package game;

import game.characters.vehicles.tank.Tank;
import game.hud.HudManager;
import game.level.Level;
import physics.BoxCollider;
import physics.CollisionLayers;
import physics.PhysicEngine;
import rendering.RenderEngine;
import utils.data.DataGen;
import utils.Engine;
import utils.vectors.IVec2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements KeyListener, Engine {
    private static GameEngine instance;

    private final Level[] levels;
    private final Tank[] tanks;
    private int currentLevel=0;
    private int currentTank=0;

    private final IVec2 currentInputDir = new IVec2();
    private final HudManager hudManager=new HudManager();

    public GameEngine() {
        if(instance==null)instance=this;

        levels=DataGen.genLevels();
        tanks=DataGen.genTanks();

        goToLevel(3);
        swapTank(1);

        RenderEngine.getCurrentCamera().setCameraTarget(tanks[currentTank]);
        RenderEngine.getInstance().addKeyListener(instance);
    }

    private void goToLevel(int i){
        for (BoxCollider solidCollider : levels[currentLevel].getColliders())
            PhysicEngine.removeCollider(solidCollider);
        RenderEngine.removeFromRenderList(levels[currentLevel]);
        currentLevel=i;
        for (BoxCollider solidCollider : levels[currentLevel].getColliders())
            PhysicEngine.addCollider(solidCollider, CollisionLayers.COLLISION_LAYER_TERRAIN);
        RenderEngine.addToRenderList(levels[currentLevel],4);
        RenderEngine.paint();
    }

    private void swapTank(int i){
        RenderEngine.removeFromRenderList(tanks[currentTank]);
        RenderEngine.removeFromRenderList(tanks[currentTank].getTurret());
        PhysicEngine.removeCollider(tanks[currentTank].getCollider());
        currentTank=i;
        PhysicEngine.addCollider(tanks[currentTank].getCollider(),CollisionLayers.COLLISION_LAYER_ALLIES);
        RenderEngine.addToRenderList(tanks[currentTank],3);
        RenderEngine.addToRenderList(tanks[currentTank].getTurret(),2);
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
                //System.out.println("Key pressed : "+e.getKeyCode());
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
