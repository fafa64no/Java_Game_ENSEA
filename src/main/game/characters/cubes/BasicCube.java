package main.game.characters.cubes;

import main.game.characters.Character;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayers;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.*;

public class BasicCube extends Character implements RedCube{
    public BasicCube(Vec2 position) {
        super(position, DataGen.getBasicCubeTexture(), 0, 0, new IVec2(Config.largeTileSize,Config.largeTileSize));
        RenderEngine.addToRenderList(this, RenderingLayers.RENDERING_LAYER_TURRET);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);

        g2d.translate(position.x-Math.round((float) textureSize.x /2), position.y-Math.round((float) textureSize.y /2));

        g2d.drawRenderedImage(texture,null);
    }
}
