package main.rendering.specific_renderers;

import main.game.level.Level;
import main.rendering.layers.RenderingLayer;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.IVec2;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class LeavesRenderer extends TileMapRenderer {
    public LeavesRenderer(Level mapParent, int tileSize, RenderingLayer renderingLayer) {
        super(mapParent, tileSize, renderingLayer);
    }

    @Override
    protected void initTextureAtPosition(int x, int y) {
        mapTextures[y][x] = switch (map[y][x]){
            case 'T'-> DataGen.getLeavesTextures().textures
                    [PseudoRandom.getRandomBetween(0,DataGen.getLeavesTextures().textureCount.y-1,
                    x,y, Config.noiseSizeTerrainColor)]
                    [PseudoRandom.getRandomBetween(0,DataGen.getLeavesTextures().textureCount.x-1,
                    x,y, Config.noiseSizeTerrainVariant)];
            default -> null;
        };
    }

    @Override
    protected void paintTexture(Graphics2D g2d, int x, int y, AffineTransform affineTransform) {
        if(mapBox.contains(new IVec2(x,y)) && mapTextures[y][x] != null){
            g2d.drawRenderedImage(mapTextures[y][x],affineTransform);
        }
    }
}
