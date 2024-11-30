package main.rendering.specific_renderers;

import main.game.level.Level;
import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayer;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.IVec2;
import main.utils.vectors.IVec4;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

import javax.swing.*;
import java.awt.*;
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
        IVec4 tileIDWindow = getTilesIDWindowFromCamera(new IVec2(1,1));
        for (int x = tileIDWindow.x; x < tileIDWindow.y; x++) {
            for (int y = tileIDWindow.z; y < tileIDWindow.w; y++) {
                double posX = mapParent.getMapOffset().x + x * tileSize;
                double posY = mapParent.getMapOffset().y + y * tileSize;
                g2d.translate(posX,posY);
                paintTexture(g2d,x,y);
                g2d.translate(-posX,-posY);
            }
        }
    }

    protected void paintTexture(Graphics2D g2d, int x, int y) {
        if(mapBox.contains(new IVec2(x,y))){
            if(mapTextures[y][x] == null) return;
            g2d.drawRenderedImage(mapTextures[y][x],null);
        }else{
            g2d.drawRenderedImage(DataGen.getBorderTexture().texture,null);
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
            case 'R'-> DataGen.getStoneTextures().textures
                [PseudoRandom.getRandomBetween(0,DataGen.getStoneTextures().textureCount.y,
                    x,y, Config.noiseSizeTerrainColor)]
                [PseudoRandom.getRandomBetween(0,DataGen.getStoneTextures().textureCount.x,
                    x,y, Config.noiseSizeTerrainVariant)];
            case 'T'-> DataGen.getTreeTextures().textures
                [PseudoRandom.getRandomBetween(0,DataGen.getTreeTextures().textureCount.y,
                    x,y, Config.noiseSizeTerrainColor)]
                [PseudoRandom.getRandomBetween(0,DataGen.getTreeTextures().textureCount.x,
                    x,y, Config.noiseSizeTerrainVariant)];
            case 'P'-> DataGen.getPathTextures().textures
                [PseudoRandom.getRandomBetween(0,DataGen.getPathTextures().textureCount.y,
                    x,y, Config.noiseSizeTerrainColor)]
                [PseudoRandom.getRandomBetween(0,DataGen.getPathTextures().textureCount.x,
                    x,y, Config.noiseSizeTerrainVariant)];
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> DataGen.getCubeSpawnTexture().texture;
            default -> DataGen.getGrassTextures().textures
                [PseudoRandom.getRandomBetween(0,DataGen.getGrassTextures().textureCount.y,
                    x,y, Config.noiseSizeTerrainColor)]
                [PseudoRandom.getRandomBetween(0,DataGen.getGrassTextures().textureCount.x,
                    x,y, Config.noiseSizeTerrainVariant)];
        };
    }

    private IVec4 getTilesIDWindowFromCamera(IVec2 additionalCameraSize){
        Vec4 visibleWindow = RenderEngine
                .getCurrentCamera()
                .getDisplayWindow(Vec2.multiply(additionalCameraSize,tileSize));
        return new IVec4(
                (int) Math.floor(visibleWindow.x - mapParent.getMapOffset().x * tileSize),
                (int) Math.ceil( visibleWindow.y - mapParent.getMapOffset().x * tileSize),
                (int) Math.floor(visibleWindow.z - mapParent.getMapOffset().y * tileSize),
                (int) Math.ceil( visibleWindow.w - mapParent.getMapOffset().y * tileSize)
        );
    }
}
