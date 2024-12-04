package main.rendering;

import main.rendering.camera.Camera;
import main.rendering.layers.LayerRenderer;
import main.rendering.layers.RenderingLayer;
import main.utils.Engine;
import main.utils.vectors.Vec2;

import javax.swing.*;

public class RenderEngine extends JFrame implements Engine {
    private static RenderEngine instance;

    private final static LayerRenderer[] layerRenderers
            = new LayerRenderer[RenderingLayer.RENDERING_LAYER_COUNT.ordinal()];

    private static Camera currentCamera;

    public RenderEngine(){
        super("COHOMA - Simulator - 2024");
        if(instance==null)instance=this;
        instance.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //instance.setUndecorated(true);
        instance.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //instance.getContentPane().setCursor(
        //    Toolkit.getDefaultToolkit().createCustomCursor(
        //        new BufferedImage(9,9,BufferedImage.TYPE_INT_ARGB),
        //        new Point(0,0),
        //        "blank cursor"
        //    )
        //);
        instance.setVisible(true);

        initRenderingLayers();
    }

    private void initRenderingLayers() {
        for (int i = 0; i < layerRenderers.length; i++) {
            layerRenderers[i] = new LayerRenderer();
        }
    }

    public static Vec2 getMiddleOfFrame(){
        return new Vec2(
            - 0.5 * instance.getContentPane().getSize().width,
            - 0.5 * instance.getContentPane().getSize().height
        );
    }

    public static Camera getCurrentCamera() {
        return currentCamera;
    }

    public static void setCurrentCamera(Camera camera){
        if(camera == null) {
            System.out.println("Trying to link a null camera");
        } else {
            currentCamera = camera;
        }
    }

    public static RenderEngine getInstance() {
        if(instance!=null)return instance;
        return new RenderEngine();
    }

    @Override
    public void update() {
        SwingUtilities.updateComponentTreeUI(instance);
    }

    private static void updateRenderLists() {
        for (LayerRenderer layerRenderer : layerRenderers) {
            layerRenderer.flushRenderList();
        }
    }

    public static void addToRenderList(Displayable displayable, RenderingLayer layer){
        layerRenderers[layer.ordinal()].addToRenderList(displayable);
    }

    public static void removeFromRenderList(Displayable displayable){
        for (LayerRenderer layerRenderer : layerRenderers) {
            layerRenderer.removeFromRenderList(displayable);
        }
        displayable.clear();
    }

    public static void paint(){
        updateRenderLists();
        for (LayerRenderer layerRenderer : layerRenderers) {
            layerRenderer.drawLayer();
        }
    }
}
