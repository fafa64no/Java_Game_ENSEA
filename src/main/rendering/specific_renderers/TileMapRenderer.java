package main.rendering.specific_renderers;

import main.game.level.Level;
import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.rendering.layers.RenderingLayer;
import main.utils.data.Config;
import main.utils.data.datagen.TextureGen;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.IVec2;
import main.utils.vectors.IVec4;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class TileMapRenderer extends JPanel implements Displayable {
    protected final Level mapParent;
    protected final char[][] map;
    protected final IVec4 mapBox;
    protected final BufferedImage[][] mapTextures;

    protected final int tileSize;
    protected final double inverseTileSize;

    protected final RenderingLayer renderingLayer;

    public TileMapRenderer(Level mapParent, int tileSize, RenderingLayer renderingLayer) {
        this.mapParent = mapParent;
        this.map = mapParent.getMap();
        this.mapBox = new IVec4(
                0, map[0].length-1,
                0, map.length-1
        );
        this.mapTextures = new BufferedImage[map.length][map[0].length];

        this.tileSize = tileSize;
        this.inverseTileSize = 1.0/tileSize;

        this.renderingLayer = renderingLayer;

        this.setOpaque(false);

        initTextures();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g.create();
        RenderEngine.getCurrentCamera().transformGraphicsToCamera(g2d);
        // Don't render outside of camera range because lag
        IVec4 tileIDWindow = getTilesIDWindowFromCamera(new IVec2(0,0));
        for (int x = tileIDWindow.x; x < tileIDWindow.y; x++) {
            for (int y = tileIDWindow.z; y < tileIDWindow.w; y++) {
                double posX = mapParent.getMapOffset().x + x * tileSize;
                double posY = mapParent.getMapOffset().y + y * tileSize;
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.translate(posX,posY);
                paintTexture(g2d,x,y,affineTransform);
            }
        }
    }

    protected void paintTexture(Graphics2D g2d, int x, int y, AffineTransform affineTransform) {
        if(mapBox.contains(new IVec2(x,y))){
            if(mapTextures[y][x] == null) return;
            g2d.drawRenderedImage(mapTextures[y][x],affineTransform);
        }else{
            g2d.drawRenderedImage(TextureGen.getBorderTexture().texture,affineTransform);
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
    public void addToRenderList() {
        RenderEngine.addToRenderList(this, renderingLayer);
    }

    @Override
    public void removeFromRenderList() {
        RenderEngine.removeFromRenderList(this);
    }

    private void initTextures() {
        for (int x=0;x<map[0].length;x++) {
            for (int y = 0; y < map.length; y++) {
                initTextureAtPosition(x,y);
            }
        }
    }

    protected void initTextureAtPosition(int x, int y) {
        mapTextures[y][x] = switch (map[y][x]){
            case 'R'-> TextureGen.getStoneTextures().textures
                [PseudoRandom.getRandomBetween(0,TextureGen.getStoneTextures().textureCount.y-1,
                    x,y, Config.noiseSizeTerrainColor)]
                [PseudoRandom.getRandomBetween(0,TextureGen.getStoneTextures().textureCount.x-1,
                    x,y, Config.noiseSizeTerrainVariant)];
            case 'T'-> TextureGen.getTreeTextures().textures
                [PseudoRandom.getRandomBetween(0,TextureGen.getTreeTextures().textureCount.y-1,
                    x,y, Config.noiseSizeTerrainColor)]
                [PseudoRandom.getRandomBetween(0,TextureGen.getTreeTextures().textureCount.x-1,
                    x,y, Config.noiseSizeTerrainVariant)];
            case 'P'-> TextureGen.getPathTextures().textures
                [PseudoRandom.getRandomBetween(0,TextureGen.getPathTextures().textureCount.y-1,
                    x,y, Config.noiseSizeTerrainColor)]
                [PseudoRandom.getRandomBetween(0,TextureGen.getPathTextures().textureCount.x-1,
                    x,y, Config.noiseSizeTerrainVariant)];
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> TextureGen.getCubeSpawnTexture().texture;
            default -> TextureGen.getGrassTextures().textures
                [PseudoRandom.getRandomBetween(0,TextureGen.getGrassTextures().textureCount.y-1,
                    x,y, Config.noiseSizeTerrainColor)]
                [PseudoRandom.getRandomBetween(0,TextureGen.getGrassTextures().textureCount.x-1,
                    x,y, Config.noiseSizeTerrainVariant)];
        };
    }

    private IVec4 getTilesIDWindowFromCamera(IVec2 additionalCameraSize){
        Vec4 visibleWindow = RenderEngine
                .getCurrentCamera()
                .getDisplayWindow(Vec2.multiply(additionalCameraSize, tileSize));
        IVec2 mapOffset = new IVec2(map[0].length/2,map.length/2);
        IVec4 output = new IVec4();
        output.x = (int) Math.floor(visibleWindow.x / tileSize + mapOffset.x);
        output.y = (int) Math.ceil( visibleWindow.y / tileSize + mapOffset.x);
        output.z = (int) Math.floor(visibleWindow.z / tileSize + mapOffset.y);
        output.w = (int) Math.ceil( visibleWindow.w / tileSize + mapOffset.y);
        return output;
    }
}
