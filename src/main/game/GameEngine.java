package main.game;

import main.game.characters.vehicles.tank.Tank;
import main.game.hud.HudManager;
import main.game.level.Level;
import main.physics.colliders.BoxCollider;
import main.physics.CollisionLayers;
import main.physics.PhysicEngine;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayers;
import main.utils.data.DataGen;
import main.utils.Engine;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameEngine implements KeyListener, Engine, MouseListener {
    private static GameEngine instance;

    private final Level[] levels;
    private final Tank[] tanks;
    private int currentLevel=0;
    private int currentTank=0;

    private final Vec2 currentInputDir = new Vec2();
    private final HudManager hudManager=new HudManager();

    public GameEngine() {
        if(instance==null)instance=this;

        levels=DataGen.genLevels();
        tanks=DataGen.genTanks();

        goToLevel(0);
        swapTank(1);

        RenderEngine.setupCameras(tanks);
        RenderEngine.getInstance().addKeyListener(instance);
        RenderEngine.getInstance().addMouseListener(instance);
    }

    private void goToLevel(int i){
        for (BoxCollider solidCollider : levels[currentLevel].getColliders())
            PhysicEngine.removeCollider(solidCollider);
        RenderEngine.removeFromRenderList(levels[currentLevel]);
        currentLevel=i;
        for (BoxCollider solidCollider : levels[currentLevel].getColliders())
            PhysicEngine.addCollider(solidCollider, CollisionLayers.COLLISION_LAYER_TERRAIN);
        RenderEngine.addToRenderList(levels[currentLevel], RenderingLayers.RENDERING_LAYER_TERRAIN);
        RenderEngine.paint();
    }

    private void swapTank(int i){
        RenderEngine.removeFromRenderList(tanks[currentTank]);
        RenderEngine.removeFromRenderList(tanks[currentTank].getTurret());
        PhysicEngine.removeCollider(tanks[currentTank].getCollider());
        currentTank=i;
        RenderEngine.setCurrentCamera(this,i);
        PhysicEngine.addCollider(tanks[currentTank].getCollider(),CollisionLayers.COLLISION_LAYER_ALLIES);
        RenderEngine.addToRenderList(tanks[currentTank],RenderingLayers.RENDERING_LAYER_TANK);
        RenderEngine.addToRenderList(tanks[currentTank].getTurret(),RenderingLayers.RENDERING_LAYER_TURRET);
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
            case 76: // Swap Level ("L")
                instance.goToLevel((currentLevel+1)%levels.length);     break;
            case 84: // Test key ("T")
                System.out.println(tanks[currentTank].getPosition()+" : "+tanks[currentTank].getCollider().getOffset());   break;
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()){
            case 1:     // Left Click
                System.out.println(tanks[currentTank].getTurret().fireProjectile());
                break;
            case 2:     // Right Click
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
