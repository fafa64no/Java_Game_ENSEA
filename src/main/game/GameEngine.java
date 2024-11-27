package main.game;

import main.game.characters.AIdriven;
import main.game.characters.Target;
import main.game.characters.vehicles.tank.Tank;
import main.game.hud.HudEngine;
import main.game.level.Level;
import main.game.projectiles.ProjectileHandler;
import main.physics.CollisionLayers;
import main.physics.PhysicEngine;
import main.physics.colliders.Collider;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayers;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.Engine;
import main.utils.debug.Debug;
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
    private final List<AIdriven> aIdrivens = new ArrayList<>();
    private final List<Target> targets = new ArrayList<>();

    private final Vec2 currentInputDir = new Vec2();

    public GameEngine() {
        if(instance==null)instance=this;

        levels=DataGen.genLevels();
        tanks=DataGen.genTanks();

        Debug.printTimeSinceLast("Generated levels from char array");

        goToLevel(0);
        swapTank(1);

        for(Tank tank : tanks){
            RenderEngine.addToRenderList(tank,RenderingLayers.RENDERING_LAYER_TANK);
            RenderEngine.addToRenderList(tank.getTurret(),RenderingLayers.RENDERING_LAYER_TURRET);
            PhysicEngine.addCollider(tank.getCollider(),CollisionLayers.COLLISION_LAYER_ALLIES);
        }

        RenderEngine.setupCameras(tanks);
        RenderEngine.getInstance().addKeyListener(instance);
        RenderEngine.getInstance().addMouseListener(instance);
    }

    private void goToLevel(int i){
        for (Collider collider : levels[currentLevel].getColliders())
            PhysicEngine.removeCollider(collider);
        RenderEngine.removeFromRenderList(levels[currentLevel]);
        RenderEngine.removeFromRenderList(levels[currentLevel].getLeavesRenderer());
        RenderEngine.removeFromRenderList(levels[currentLevel].getCubeRenderer());
        currentLevel=i;
        for (Collider collider : levels[currentLevel].getColliders())
            PhysicEngine.addCollider(collider, CollisionLayers.COLLISION_LAYER_TERRAIN);
        RenderEngine.addToRenderList(levels[currentLevel], RenderingLayers.RENDERING_LAYER_TERRAIN);
        RenderEngine.addToRenderList(levels[currentLevel].getLeavesRenderer(), RenderingLayers.RENDERING_LAYER_LEAVES);
        RenderEngine.addToRenderList(levels[currentLevel].getCubeRenderer(), RenderingLayers.RENDERING_LAYER_TANK);
        RenderEngine.paint();
    }

    private void swapTank(int i){
        currentTank=i;
        RenderEngine.setCurrentCamera(this,i);
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

    public static void addAIdriven(AIdriven aIdriven){
        if(aIdriven!=null)instance.aIdrivens.add(aIdriven);
    }

    public static void addTarget(Target target){
        instance.targets.add(target);
    }

    public static void removeTarget(Target target){
        instance.targets.remove(target);
    }

    public static List<Target> getTargets(){
        return instance.targets;
    }

    public static Tank getCurrentTank(){
        return instance.tanks[instance.currentTank];
    }

    @Override
    public void update() {
        tanks[currentTank].setInput(currentInputDir);
        for(ProjectileHandler projectileHandler : instance.projectileHandlers)projectileHandler.updateLifetimes();
        for(AIdriven aIdriven : aIdrivens)if(aIdriven.isAIenabled())aIdriven.updateAI();
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
            case 84: // Debug key ("T")
                Config.currentlyDebugging=!Config.currentlyDebugging;  break;
            default:
                //if(Config.currentlyDebugging)System.out.println("Key pressed : "+e.getKeyCode());
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
