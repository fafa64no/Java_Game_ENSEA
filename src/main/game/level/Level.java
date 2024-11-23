package main.game.level;

import main.physics.colliders.BoxCollider;
import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.utils.data.Cfg;
import main.utils.data.DataGen;
import main.utils.vectors.IVec2;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.Vec2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Level extends JPanel implements Displayable {
    private final char[][] map;
    private final BufferedImage[][] mapTextures;
    private final List<BoxCollider> colliders=new ArrayList<>();

    private final IVec2 tileSize = new IVec2(Cfg.tileSize,Cfg.tileSize);
    private final Vec2 mapOffset = new Vec2();

    private final BufferedImage[][] grassTextures;
    private final BufferedImage[][] stoneTextures;
    private final BufferedImage[][] treeTextures;
    private final BufferedImage trapTexture;
    private final BufferedImage barrierTexture;

    public Level(IVec2 size,String path){
        map=new char[size.y][size.x];
        mapTextures=new BufferedImage[size.y][size.x];
        mapOffset.x=-map[0].length * tileSize.x /2.0;
        mapOffset.y=-map.length * tileSize.y /2.0;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            for (int i = 0; i< size.y; i++) map[i]=bufferedReader.readLine().toCharArray();
        } catch (Exception e){e.printStackTrace();}

        grassTextures = DataGen.getGrassTextures();
        stoneTextures = DataGen.getStoneTextures();
        treeTextures = DataGen.getTreeTextures();
        trapTexture = DataGen.getTrapTexture();
        barrierTexture = DataGen.getBarrierTexture();

        initTextures();
        initColliders();
    }

    private void initTextures(){
        for (int x=0;x<map[0].length;x++) {
            for (int y = 0; y < map.length; y++) {
                switch (map[y][x]){
                    case 'R':
                        mapTextures[y][x]=stoneTextures[PseudoRandom.getRandomBetween(0,stoneTextures.length-1,x,y,Cfg.noiseSizeTerrainColor)]
                                [PseudoRandom.getRandomBetween(0,stoneTextures[0].length-1,x,y,Cfg.noiseSizeTerrainVariant)];
                        break;
                    case 'T':
                        mapTextures[y][x]=treeTextures[PseudoRandom.getRandomBetween(0,treeTextures.length-1,x,y,Cfg.noiseSizeTerrainColor)]
                                [PseudoRandom.getRandomBetween(0,treeTextures[0].length-1,x,y,Cfg.noiseSizeTerrainVariant)];
                        break;
                    case 'H':
                        mapTextures[y][x]=trapTexture;
                        break;
                    default:
                        mapTextures[y][x]=grassTextures[PseudoRandom.getRandomBetween(0,grassTextures.length-1,x,y,Cfg.noiseSizeTerrainColor)]
                                [PseudoRandom.getRandomBetween(0,grassTextures[0].length-1,x,y,Cfg.noiseSizeTerrainVariant)];
                        break;
                }
            }
        }
    }

    private void initColliders(){
        colliders.add(new BoxCollider(
                new Vec2( mapOffset.x, mapOffset.y),
                new Vec2(-mapOffset.x,-mapOffset.y),
                true,
                0.5,
                new Vec2()
        ));
        for (int x=0;x<map[0].length;x++){
            for (int y=0;y<map.length;y++){
                int posX=(int)-Math.round(tileSize.x*(x+0.5)+mapOffset.x);
                int posY=(int)-Math.round(tileSize.y*(y+0.5)+mapOffset.y);
                switch (this.map[y][x]){
                    case 'R':
                        colliders.add(new BoxCollider(
                                new Vec2(-tileSize.x/2.0,-tileSize.x/2.0),
                                new Vec2(tileSize.x/2.0, tileSize.y/2.0),
                                false,
                                0.5,
                                new Vec2(posX,posY)
                        ));
                        break;
                    case 'T':
                        colliders.add(new BoxCollider(
                                new Vec2(-Cfg.treeHitBoxSize*tileSize.x/2.0,-Cfg.treeHitBoxSize*tileSize.x/2.0),
                                new Vec2(Cfg.treeHitBoxSize*tileSize.x/2.0, Cfg.treeHitBoxSize*tileSize.y/2.0),
                                false,
                                0.2,
                                new Vec2(posX,posY)
                        ));
                        break;
                    case 'H':
                        break;
                }
            }
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
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);
        for (int x=-Cfg.mapHorizontalWallThickness;x<map[0].length+Cfg.mapHorizontalWallThickness; x++) {
            for (int y=-Cfg.mapVerticalWallThickness; y < map.length+Cfg.mapVerticalWallThickness; y++) {
                double posX=mapOffset.x+x*tileSize.x;
                double posY=mapOffset.y+y*tileSize.y;
                g2d.translate(posX,posY);
                if(x>=0&&x<map[0].length&&y>=0&&y<map.length){
                    g2d.drawRenderedImage(mapTextures[y][x],null);
                }else{
                    g2d.drawRenderedImage(barrierTexture,null);
                }
                g2d.translate(-posX,-posY);
            }
        }
    }

    public List<BoxCollider> getColliders() {
        return colliders;
    }
}
