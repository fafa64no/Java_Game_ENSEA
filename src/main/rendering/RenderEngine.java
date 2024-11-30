package main.rendering;

import main.utils.Engine;
import main.utils.containers.BufferedList;
import main.utils.vectors.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderEngine extends JFrame implements Engine {
    private static RenderEngine instance;

    private final static BufferedList<Displayable> layer_hud_top       = new BufferedList<>();
    private final static BufferedList<Displayable> layer_hud_middle    = new BufferedList<>();
    private final static BufferedList<Displayable> layer_hud_bottom    = new BufferedList<>();

    private final static BufferedList<Displayable> layer_flying_top       = new BufferedList<>();
    private final static BufferedList<Displayable> layer_flying_middle    = new BufferedList<>();
    private final static BufferedList<Displayable> layer_flying_bottom    = new BufferedList<>();

    private final static BufferedList<Displayable> layer_leaves_top       = new BufferedList<>();
    private final static BufferedList<Displayable> layer_leaves_middle    = new BufferedList<>();
    private final static BufferedList<Displayable> layer_leaves_bottom    = new BufferedList<>();

    private final static BufferedList<Displayable> layer_ground_top       = new BufferedList<>();
    private final static BufferedList<Displayable> layer_ground_middle    = new BufferedList<>();
    private final static BufferedList<Displayable> layer_ground_bottom    = new BufferedList<>();

    private final static BufferedList<Displayable> layer_terrain_top       = new BufferedList<>();
    private final static BufferedList<Displayable> layer_terrain_middle    = new BufferedList<>();
    private final static BufferedList<Displayable> layer_terrain_bottom    = new BufferedList<>();

    private static Camera currentCamera;

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

    public static Vec2 getMiddleOfFrame(){
        return new Vec2(
            - 0.5 * instance.getContentPane().getSize().width /currentCamera.getScale().x,
            - 0.5 * instance.getContentPane().getSize().height/currentCamera.getScale().y
        );
    }

    public static Camera getCurrentCamera() {
        return currentCamera;
    }

    public static void setCurrentCamera(Camera camera){
        if(camera != null) {
            currentCamera = camera;
        } else {
            System.out.println("Trying to link a null camera");
        }
    }

    public static RenderEngine getInstance() {
        if(instance!=null)return instance;
        return new RenderEngine();
    }

    @Override
    public void update() {
        updateRenderLists();
        SwingUtilities.updateComponentTreeUI(instance);
    }

    private static void renderLayer(BufferedList<Displayable> layer) {
        for (Displayable displayable : layer.elements) {
            displayable.draw();
            instance.setVisible(true);
        }
    }

    private static void updateRenderLists() {
        layer_hud_top.flush();
        layer_hud_middle.flush();
        layer_hud_bottom.flush();
        layer_flying_top.flush();
        layer_flying_middle.flush();
        layer_flying_bottom.flush();
        layer_leaves_top.flush();
        layer_leaves_middle.flush();
        layer_leaves_bottom.flush();
        layer_ground_top.flush();
        layer_ground_middle.flush();
        layer_ground_bottom.flush();
        layer_terrain_top.flush();
        layer_terrain_middle.flush();
        layer_terrain_bottom.flush();
    }

    public static void addToRenderList(Displayable displayable, RenderingLayer layer){
        switch (layer){
            case RENDERING_LAYER_HUD_TOP:
                layer_hud_top.addElement(displayable);    break;
            case RENDERING_LAYER_HUD_MIDDLE:
                layer_hud_middle.addElement(displayable);    break;
            case RENDERING_LAYER_HUD_BOTTOM:
                layer_hud_bottom.addElement(displayable);    break;

            case RENDERING_LAYER_FLYING_TOP:
                layer_flying_top.addElement(displayable);    break;
            case RENDERING_LAYER_FLYING_MIDDLE:
                layer_flying_middle.addElement(displayable);    break;
            case RENDERING_LAYER_FLYING_BOTTOM:
                layer_flying_bottom.addElement(displayable);    break;

            case RENDERING_LAYER_LEAVES_TOP:
                layer_leaves_top.addElement(displayable);    break;
            case RENDERING_LAYER_LEAVES_MIDDLE:
                layer_leaves_middle.addElement(displayable);    break;
            case RENDERING_LAYER_LEAVES_BOTTOM:
                layer_leaves_bottom.addElement(displayable);    break;

            case RENDERING_LAYER_GROUND_TOP:
                layer_ground_top.addElement(displayable);    break;
            case RENDERING_LAYER_GROUND_MIDDLE:
                layer_ground_middle.addElement(displayable);    break;
            case RENDERING_LAYER_GROUND_BOTTOM:
                layer_ground_bottom.addElement(displayable);    break;

            case RENDERING_LAYER_TERRAIN_TOP:
                layer_terrain_top.addElement(displayable);    break;
            case RENDERING_LAYER_TERRAIN_MIDDLE:
                layer_terrain_middle.addElement(displayable);    break;
            case RENDERING_LAYER_TERRAIN_BOTTOM:
                layer_terrain_bottom.addElement(displayable);    break;
        }
    }

    public static void removeFromRenderList(Displayable displayable){
        layer_hud_top.removeElement(displayable);
        layer_hud_middle.removeElement(displayable);
        layer_hud_bottom.removeElement(displayable);

        layer_flying_top.removeElement(displayable);
        layer_flying_middle.removeElement(displayable);
        layer_flying_bottom.removeElement(displayable);

        layer_leaves_top.removeElement(displayable);
        layer_leaves_middle.removeElement(displayable);
        layer_leaves_bottom.removeElement(displayable);

        layer_ground_top.removeElement(displayable);
        layer_ground_middle.removeElement(displayable);
        layer_ground_bottom.removeElement(displayable);

        layer_terrain_top.removeElement(displayable);
        layer_terrain_middle.removeElement(displayable);
        layer_terrain_bottom.removeElement(displayable);
        displayable.clear();
    }

    public static void paint(){
        renderLayer(layer_hud_top);
        renderLayer(layer_hud_middle);
        renderLayer(layer_hud_bottom);

        renderLayer(layer_flying_top);
        renderLayer(layer_flying_middle);
        renderLayer(layer_flying_bottom);

        renderLayer(layer_leaves_top);
        renderLayer(layer_leaves_middle);
        renderLayer(layer_leaves_bottom);

        renderLayer(layer_ground_top);
        renderLayer(layer_ground_middle);
        renderLayer(layer_ground_bottom);

        renderLayer(layer_terrain_top);
        renderLayer(layer_terrain_middle);
        renderLayer(layer_terrain_bottom);
    }
}
