package main.game;

import main.game.characters.vehicles.tank.Tank;
import main.game.hud.HudManager;
import main.game.level.Level;
import main.game.projectiles.ProjectileHandler;
import main.physics.CollisionLayers;
import main.physics.PhysicEngine;
import main.physics.colliders.Collider;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayers;
import main.utils.data.DataGen;
import main.utils.Engine;
import main.utils.vectors.Vec2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class GameEngine implements KeyListener, Engine, MouseListener {
    private static GameEngine instance;

    private final Level[] levels;
    private final Tank[] tanks;
    private int currentLevel=0;
    private int currentTank=0;

    private final List<ProjectileHandler> projectileHandlers = new ArrayList<>();

    private final Vec2 currentInputDir = new Vec2();
    private final HudManager hudManager=new HudManager();

    public GameEngine() {
        if(instance==null)instance=this;

        levels=DataGen.genLevels();
        tanks=DataGen.genTanks();

        goToLevel(1);
        swapTank(1);

        RenderEngine.setupCameras(tanks);
        RenderEngine.getInstance().addKeyListener(instance);
        RenderEngine.getInstance().addMouseListener(instance);
    }

    private void goToLevel(int i){
        for (Collider collider : levels[currentLevel].getColliders())
            PhysicEngine.removeCollider(collider);
        RenderEngine.removeFromRenderList(levels[currentLevel]);
        RenderEngine.removeFromRenderList(levels[currentLevel].getLeavesRenderer());
        currentLevel=i;
        for (Collider collider : levels[currentLevel].getColliders())
            PhysicEngine.addCollider(collider, CollisionLayers.COLLISION_LAYER_TERRAIN);
        RenderEngine.addToRenderList(levels[currentLevel], RenderingLayers.RENDERING_LAYER_TERRAIN);
        RenderEngine.addToRenderList(levels[currentLevel].getLeavesRenderer(), RenderingLayers.RENDERING_LAYER_LEAVES);
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
        if(instance!=null)return instance;
        return new GameEngine();
    }

    public static Level getCurrentLevel() {
        return instance.levels[instance.currentLevel];
    }

    public static void addProjectileHandler(ProjectileHandler projectileHandler){
        if(projectileHandler!=null)instance.projectileHandlers.add(projectileHandler);
    }

    @Override
    public void update() {
        tanks[currentTank].setInput(currentInputDir);
        for(ProjectileHandler projectileHandler : instance.projectileHandlers)projectileHandler.updateLifetimes();
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
                instance.swapTank((currentTank+1)%tanks.length);    break;
            case 76: // Swap Level ("L")
                instance.goToLevel((currentLevel+1)%levels.length); break;
            case 84: // Test key ("T")
                tanks[currentTank].takeDamage(25.0);                   break;
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
                if(!tanks[currentTank].fireProjectile()){
                    System.out.println("Reloading ...");
                }
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
