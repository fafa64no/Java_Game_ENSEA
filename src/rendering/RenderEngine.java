package rendering;

import utils.Engine;
import utils.IVec2;
import utils.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RenderEngine extends JFrame implements Engine {
    private static RenderEngine instance;

    private final List<Displayable> displayableList_layer0;     // Turret
    private final List<Displayable> displayableList_layer1;     // Tank
    private final List<Displayable> displayableList_layer2;     // Terrain

    private Camera currentCamera=new Camera(
            new IVec2(-600,-350),
            new Vec2(
                    1.5,
                    1.5)
    );

    public RenderEngine(){
        super("COHOMA - Simulator - 2024");
        if(instance==null)instance=this;
        instance.setExtendedState(JFrame.MAXIMIZED_BOTH);
        instance.setUndecorated(true);
        instance.setVisible(true);
        instance.setDefaultCloseOperation(EXIT_ON_CLOSE);
        instance.getContentPane().setCursor(
                Toolkit.getDefaultToolkit().createCustomCursor(
                        new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB),
                        new Point(0,0),
                        "blank cursor"
                )
        );

        displayableList_layer0 = new ArrayList<>();
        displayableList_layer1 = new ArrayList<>();
        displayableList_layer2 = new ArrayList<>();
    }

    public void addToRenderList(Displayable displayable,int layer){
        switch (layer){
            case 0:
                displayableList_layer0.add(displayable);    break;
            case 1:
                displayableList_layer1.add(displayable);    break;
            case 2:
                displayableList_layer2.add(displayable);    break;
        }
    }

    public void removeFromRenderList(Displayable displayable){
        displayableList_layer0.remove(displayable);
        displayableList_layer1.remove(displayable);
        displayableList_layer2.remove(displayable);
        displayable.clear();
    }

    public void paint(){
        for (Displayable displayable : displayableList_layer0) {
            displayable.draw();
            displayable.linkCamera(currentCamera);
            instance.setVisible(true);
        }
        for (Displayable displayable : displayableList_layer1) {
            displayable.draw();
            displayable.linkCamera(currentCamera);
            instance.setVisible(true);
        }
        for (Displayable displayable : displayableList_layer2) {
            displayable.draw();
            displayable.linkCamera(currentCamera);
            instance.setVisible(true);
        }
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }

    public static RenderEngine getInstance() {
        return instance;
    }

    @Override
    public void update() {
        this.currentCamera.update();
        SwingUtilities.updateComponentTreeUI(instance);
    }
}
