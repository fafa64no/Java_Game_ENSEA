package rendering;

import utils.Engine;
import utils.IVec2;
import utils.Vec2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderEngine extends JFrame implements Engine {
    private final List<Displayable> displayableList_layer0;     // Turret
    private final List<Displayable> displayableList_layer1;     // Tank
    private final List<Displayable> displayableList_layer2;     // Terrain

    private Camera currentCamera=new Camera(
            new IVec2(-600,-350),
            new Vec2(1.5,1.5)
    );

    public RenderEngine(){
        super("COHOMA - Simulator - 2024");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        displayable.clear(this);
    }

    public void paint(){
        for (Displayable displayable : displayableList_layer0) {
            displayable.draw(this);
            displayable.linkCamera(currentCamera);
        }
        this.setVisible(true);
        for (Displayable displayable : displayableList_layer1) {
            displayable.draw(this);
            displayable.linkCamera(currentCamera);
        }
        this.setVisible(true);
        for (Displayable displayable : displayableList_layer2) {
            displayable.draw(this);
            displayable.linkCamera(currentCamera);
        }
        this.setVisible(true);
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }

    @Override
    public void update() {
        this.currentCamera.update();
        this.paint();
        SwingUtilities.updateComponentTreeUI(this);
    }
}
