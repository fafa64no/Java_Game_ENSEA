package main.rendering;

import main.game.DynamicSprite;
import main.game.GameEngine;
import main.utils.Engine;
import main.utils.data.DataGen;
import main.utils.vectors.IVec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RenderEngine extends JFrame implements Engine {
    private static RenderEngine instance;

    private final List<Displayable> displayableList_layer0 = new ArrayList<>();     // Aerial
    private final List<Displayable> displayableList_layer1 = new ArrayList<>();     // Leaves
    private final List<Displayable> displayableList_layer2 = new ArrayList<>();     // TankTurret
    private final List<Displayable> displayableList_layer3 = new ArrayList<>();     // Tank
    private final List<Displayable> displayableList_layer4 = new ArrayList<>();     // Terrain

    private final Camera[] cameras = DataGen.genCameras();
    private int currentCamera;

    public RenderEngine(){
        super("COHOMA - Simulator - 2024");
        if(instance==null)instance=this;
        instance.setExtendedState(JFrame.MAXIMIZED_BOTH);
        instance.setUndecorated(true);
        instance.setDefaultCloseOperation(EXIT_ON_CLOSE);
        instance.getContentPane().setCursor(
                Toolkit.getDefaultToolkit().createCustomCursor(
                        new BufferedImage(9,9,BufferedImage.TYPE_INT_ARGB),
                        new Point(0,0),
                        "blank cursor"
                )
        );
    }

    public static void addToRenderList(Displayable displayable,RenderingLayers layer){
        switch (layer){
            case RENDERING_LAYER_AIR:
                instance.displayableList_layer0.add(displayable);    break;
            case RENDERING_LAYER_LEAVES:
                instance.displayableList_layer1.add(displayable);    break;
            case RENDERING_LAYER_TURRET:
                instance.displayableList_layer2.add(displayable);    break;
            case RENDERING_LAYER_TANK:
                instance.displayableList_layer3.add(displayable);    break;
            case RENDERING_LAYER_TERRAIN:
                instance.displayableList_layer4.add(displayable);    break;
        }
    }

    public static void removeFromRenderList(Displayable displayable){
        instance.displayableList_layer0.remove(displayable);
        instance.displayableList_layer1.remove(displayable);
        instance.displayableList_layer2.remove(displayable);
        instance.displayableList_layer3.remove(displayable);
        instance.displayableList_layer4.remove(displayable);
        displayable.clear();
    }

    public static void paint(){
        for (Displayable displayable : instance.displayableList_layer0) {
            displayable.draw();
            instance.setVisible(true);
        }
        for (Displayable displayable : instance.displayableList_layer1) {
            displayable.draw();
            instance.setVisible(true);
        }
        for (Displayable displayable : instance.displayableList_layer2) {
            displayable.draw();
            instance.setVisible(true);
        }
        for (Displayable displayable : instance.displayableList_layer3) {
            displayable.draw();
            instance.setVisible(true);
        }
        for (Displayable displayable : instance.displayableList_layer4) {
            displayable.draw();
            instance.setVisible(true);
        }
    }

    public static Camera getCurrentCamera() {
        return instance.cameras[instance.currentCamera];
    }

    public static void setupCameras(DynamicSprite[] dynamicSprites){
        for (int i=0;i<instance.cameras.length;i++){
            instance.cameras[i].setCameraTarget(dynamicSprites[i]);
        }
    }

    public static void setCurrentCamera(GameEngine renderEngine,int i){
        if(renderEngine!=GameEngine.getInstance())return;
        instance.currentCamera=i;
    }

    public static RenderEngine getInstance() {
        return instance;
    }

    public static IVec2 getMiddleOfFrame(){
        return new IVec2(
                -(int)Math.round((float) instance.getContentPane().getSize().width /(2*RenderEngine.getCurrentCamera().getScale().x)),
                -(int)Math.round((float) instance.getContentPane().getSize().height /(2*RenderEngine.getCurrentCamera().getScale().y))
        );
    }

    @Override
    public void update() {
        this.cameras[currentCamera].update();
        SwingUtilities.updateComponentTreeUI(instance);
    }
}
