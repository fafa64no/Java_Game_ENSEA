package main.rendering.vfx;

import main.game.GameEngine;
import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayers;
import main.utils.RequiresUpdates;
import main.utils.data.Config;

import javax.swing.*;
import java.awt.*;

public class VfxManager extends JPanel implements RequiresUpdates, Displayable {
    private static VfxManager instance;

    private final Vfx[] vfxList = new Vfx[Config.maxVfxPerVfxHandler];
    private int vfxListPointer = 0;

    public VfxManager(){
        if(instance==null)instance=this;

        this.setOpaque(false);

        RenderEngine.addToRenderList(this, RenderingLayers.RENDERING_LAYER_HUD);
        GameEngine.addRequiresUpdates(this);
    }

    public static VfxManager getInstance(){
        return instance;
    }

    public static int addVfx(Vfx vfx){
        instance.vfxList[instance.vfxListPointer]=vfx;
        instance.vfxListPointer=instance.vfxListPointer%Config.maxVfxPerVfxHandler;
        return instance.vfxListPointer++;
    }

    public static void removeVfx(int id){
        instance.vfxList[id]=null;
    }

    @Override
    public void updateRemainingTime() {
        for(Vfx vfx : vfxList){
            if(vfx == null)continue;
            vfx.updateRemainingTime();
        }
    }

    @Override
    public void draw() {
        RenderEngine.getInstance().remove(this);
        RenderEngine.getInstance().add(this);
    }

    @Override
    public void clear() {
        RenderEngine.getInstance().remove(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);

        for(Vfx vfx : vfxList){
            if(vfx==null)continue;
            g2d.translate(vfx.position.x-vfx.textureSize*0.5,vfx.position.y-vfx.textureSize*0.5);
            g2d.drawRenderedImage(vfx.getCurrentTexture(),null);
            g2d.translate(-vfx.position.x+vfx.textureSize*0.5,-vfx.position.y+vfx.textureSize*0.5);
        }
    }
}
