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
            new IVec2(0,0),
            new Vec2(
                    3,
                    3)
    );

    public RenderEngine(){
        super("COHOMA - Simulator - 2024");
        if(instance==null)instance=this;
        instance.setExtendedState(JFrame.MAXIMIZED_BOTH);
        instance.setUndecorated(true);
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

    public static void addToRenderList(Displayable displayable,int layer){
        switch (layer){
            case 0:
                instance.displayableList_layer0.add(displayable);    break;
            case 1:
                instance.displayableList_layer1.add(displayable);    break;
            case 2:
                instance.displayableList_layer2.add(displayable);    break;
        }
    }

    public static void removeFromRenderList(Displayable displayable){
        instance.displayableList_layer0.remove(displayable);
        instance.displayableList_layer1.remove(displayable);
        instance.displayableList_layer2.remove(displayable);
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
    }

    public static Camera getCurrentCamera() {
        return instance.currentCamera;
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
        this.currentCamera.update();
        SwingUtilities.updateComponentTreeUI(instance);
    }
}
