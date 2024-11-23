package main.game.level;

import main.physics.colliders.BoxCollider;
import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.utils.data.Cfg;
import main.utils.data.DataGen;
import main.utils.vectors.IVec2;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Level extends JPanel implements Displayable {
    private final char[][] map;
    private final BufferedImage[][] mapTextures;
    private final List<BoxCollider> colliders=new ArrayList<>();

    private final Vec2 mapOffset = new Vec2();

    private final BufferedImage[][] grassTextures;
    private final BufferedImage[][] stoneTextures;
    private final BufferedImage[][] treeTextures;
    private final BufferedImage[][] pathTextures;
    private final BufferedImage trapTexture;
    private final BufferedImage barrierTexture;

    public Level(IVec2 size,String path){
        map=new char[size.y][size.x];
        mapTextures=new BufferedImage[size.y][size.x];
        mapOffset.x=-map[0].length * Cfg.tileSize /2.0;
        mapOffset.y=-map.length * Cfg.tileSize /2.0;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            for (int i = 0; i< size.y; i++) map[i]=bufferedReader.readLine().toCharArray();
        } catch (Exception e){e.printStackTrace();}

        grassTextures = DataGen.getGrassTextures();
        stoneTextures = DataGen.getStoneTextures();
        treeTextures = DataGen.getTreeTextures();
        pathTextures = DataGen.getPathTextures();
        trapTexture = DataGen.getTrapTexture();
        barrierTexture = DataGen.getBarrierTexture();

        initTextures();
        initColliders();
    }

    public Level(IVec2 size){
        map=LevelGenerator.genTerrain(size);
        mapTextures=new BufferedImage[size.y][size.x];
        mapOffset.x=-map[0].length * Cfg.tileSize /2.0;
        mapOffset.y=-map.length * Cfg.tileSize /2.0;

        grassTextures = DataGen.getGrassTextures();
        stoneTextures = DataGen.getStoneTextures();
        treeTextures = DataGen.getTreeTextures();
        pathTextures = DataGen.getPathTextures();
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
                    case 'P':
                        mapTextures[y][x]=pathTextures[PseudoRandom.getRandomBetween(0,pathTextures.length-1,x,y,Cfg.noiseSizeTerrainColor)]
                                [PseudoRandom.getRandomBetween(0,pathTextures[0].length-1,x,y,Cfg.noiseSizeTerrainVariant)];
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
                int posX=(int)-Math.round(Cfg.tileSize*(x+0.5)+mapOffset.x);
                int posY=(int)-Math.round(Cfg.tileSize*(y+0.5)+mapOffset.y);
                switch (this.map[y][x]){
                    case 'R':
                        colliders.add(new BoxCollider(
                                new Vec2(-Cfg.tileSize/2.0,-Cfg.tileSize/2.0),
                                new Vec2( Cfg.tileSize/2.0, Cfg.tileSize/2.0),
                                false,
                                0.5,
                                new Vec2(posX,posY)
                        ));
                        break;
                    case 'T':
                        colliders.add(new BoxCollider(
                                new Vec2(-Cfg.treeHitBoxSize*Cfg.tileSize/2.0,-Cfg.treeHitBoxSize*Cfg.tileSize/2.0),
                                new Vec2( Cfg.treeHitBoxSize*Cfg.tileSize/2.0, Cfg.treeHitBoxSize*Cfg.tileSize/2.0),
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

    public double getGroundSpeed(Vec2 position){
        int beforeClamp=(int) Math.round((position.x+mapOffset.x)/Cfg.tileSize);
        int tilePosX = Math.clamp((int) Math.round((position.x-mapOffset.x)/Cfg.tileSize),0,map[0].length);
        int tilePosY = Math.clamp((int) Math.round((position.y-mapOffset.y)/Cfg.tileSize),0,map.length);
        char tile = map[tilePosY][tilePosX];
        System.out.println("Fun : "+tilePosX+" : "+tilePosY+" : "+tile+" : "+beforeClamp);
        return switch (tile) {
            case 'P' -> 1.5;
            case 'H' -> 0.1;
            default -> 1;
        };
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
        // Don't render outside of camera range because lag
        Vec2 cameraCenter=RenderEngine.getCurrentCamera().getTargetOffset();
        Vec2 cameraCornerA=Vec2.add(cameraCenter,new Vec2(
                -RenderEngine.getInstance().getContentPane().getSize().width /(2*Cfg.tileSize*RenderEngine.getCurrentCamera().getScale().x)+map[0].length/2.0,
                -RenderEngine.getInstance().getContentPane().getSize().height/(2*Cfg.tileSize*RenderEngine.getCurrentCamera().getScale().y)+map.length/2.0
        ));
        Vec2 cameraCornerB=Vec2.add(cameraCenter,new Vec2(
                RenderEngine.getInstance().getContentPane().getSize().width /(2*Cfg.tileSize*RenderEngine.getCurrentCamera().getScale().x)+map[0].length/2.0,
                RenderEngine.getInstance().getContentPane().getSize().height/(2*Cfg.tileSize*RenderEngine.getCurrentCamera().getScale().y)+map.length/2.0
        ));
        int minX,maxX,minY,maxY;
        minX= (int) Math.floor(Math.max(-Cfg.mapHorizontalWallThickness,
                cameraCornerA.x
        ));
        maxX= (int) Math.ceil(Math.min(map[0].length+Cfg.mapHorizontalWallThickness,
                cameraCornerB.x
        ));
        minY= (int) Math.floor(Math.max(-Cfg.mapVerticalWallThickness,
                cameraCornerA.y
        ));
        maxY= (int) Math.ceil(Math.min(map.length+Cfg.mapVerticalWallThickness,
                cameraCornerB.y
        ));
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                double posX=mapOffset.x+x*Cfg.tileSize;
                double posY=mapOffset.y+y*Cfg.tileSize;
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
