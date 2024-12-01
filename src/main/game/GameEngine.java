package main.game;

import main.game.target.Target;
import main.game.vehicles.tanks.Tank;
import main.game.hud.HudManager;
import main.game.level.Level;
import main.game.projectiles.AerialMachineGunBullet;
import main.game.projectiles.ArtilleryShell;
import main.game.projectiles.MachineGunBullet;
import main.physics.CollisionLayer;
import main.physics.PhysicEngine;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayer;
import main.rendering.vfx.VfxManager;
import main.utils.RequiresUpdates;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.Engine;
import main.utils.debug.Debug;
import main.utils.vectors.Vec2;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GameEngine implements KeyListener, Engine, MouseListener, MouseWheelListener {
    private static GameEngine instance;

    private final Level[] levels;
    private final Tank[] tanks;
    private int currentLevel=0;
    private int currentTank=0;

    private final List<AIdriven> aIdrivens = new ArrayList<>();
    private List<AIdriven> aIdrivensToAdd = new ArrayList<>();
    private final List<Target> targets = new ArrayList<>();
    private final List<RequiresUpdates> requiresUpdates = new ArrayList<>();
    private List<RequiresUpdates> requiresUpdatesToAdd = new ArrayList<>();

    private final Vec2 currentInputDir = new Vec2();

    private final MachineGunBullet machineGunBullet;
    private final ArtilleryShell artilleryShell;
    private final AerialMachineGunBullet aerialMachineGunBullet;

    public GameEngine() {
        if(instance==null)instance=this;

        levels=DataGen.genLevels();
        tanks=DataGen.genTanks();

        Debug.printTimeSinceLast("Generated levels from char array");

        goToLevel(0);
        swapTank(1);

        for(Tank tank : tanks){
            RenderEngine.addToRenderList(tank, RenderingLayer.RENDERING_LAYER_TANK);
            RenderEngine.addToRenderList(tank.getTurret(), RenderingLayer.RENDERING_LAYER_TURRET);
            PhysicEngine.addCollider(tank.getCollider(), CollisionLayer.COLLISION_LAYER_ALLIES);
        }

        RenderEngine.setupCameras(tanks);
        RenderEngine.getInstance().addKeyListener(instance);
        RenderEngine.getInstance().addMouseListener(instance);
        RenderEngine.getInstance().addMouseWheelListener(instance);

        machineGunBullet = new MachineGunBullet();
        artilleryShell = new ArtilleryShell();
        aerialMachineGunBullet = new AerialMachineGunBullet();

        new HudManager();
        new VfxManager();
    }

    private void goToLevel(int i){
        levels[currentLevel].unloadLevel();
        currentLevel=i;
        levels[currentLevel].loadLevel();
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

    public static void addAIdriven(AIdriven aIdriven){
        if(aIdriven!=null)instance.aIdrivensToAdd.add(aIdriven);
    }

    public static void addTarget(Target target){
        instance.targets.add(target);
    }

    public static void removeTarget(Target target){
        instance.targets.remove(target);
    }

    public static void addRequiresUpdates(RequiresUpdates requiresUpdates){
        instance.requiresUpdatesToAdd.add(requiresUpdates);
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
        for(AIdriven aIdriven : aIdrivens)if(aIdriven.isAIenabled())aIdriven.updateAI();
        aIdrivens.addAll(aIdrivensToAdd);
        aIdrivensToAdd = new ArrayList<>();
        for(RequiresUpdates requiresUpdate : requiresUpdates)requiresUpdate.doUpdate();
        requiresUpdates.addAll(requiresUpdatesToAdd);
        requiresUpdatesToAdd = new ArrayList<>();
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
                Config.currentlyDebugging=!Config.currentlyDebugging;
                break;
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
                tanks[currentTank].fireProjectile();
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        RenderEngine.getCurrentCamera().changeScale(e.getWheelRotation());
    }
}
