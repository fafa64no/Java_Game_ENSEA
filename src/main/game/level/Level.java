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
    private final List<BoxCollider> colliders=new ArrayList<>();

    private final IVec2 tileSize = new IVec2(Cfg.tileSize,Cfg.tileSize);
    private final Vec2 mapOffset = new Vec2();

    private final BufferedImage[][] grassTextures;
    private final BufferedImage[][] stoneTextures;
    private BufferedImage treeTexture;
    private BufferedImage trapTexture;

    public Level(IVec2 size,String path){
        map=new char[size.y][size.x];
        mapOffset.x=-map[0].length * tileSize.x /2.0;
        mapOffset.y=-map.length * tileSize.y /2.0;
        try{
            treeTexture = ImageIO.read(new File("./assets/textures/level/tree.png"));
            trapTexture = ImageIO.read(new File("./assets/textures/level/trap.png"));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            for (int i = 0; i< size.y; i++) map[i]=bufferedReader.readLine().toCharArray();
        } catch (Exception e){e.printStackTrace();}
        grassTextures = DataGen.getGrassTextures();
        stoneTextures = DataGen.getStoneTextures();

        for (int x=0;x<size.x;x++){
            for (int y=0;y<size.y;y++){
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
                                new Vec2(-tileSize.x/2.0,-tileSize.x/2.0),
                                new Vec2(tileSize.x/2.0, tileSize.y/2.0),
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
        double x=mapOffset.x;
        double y=mapOffset.y;
        for (char[] line : map){
            for (char c : line){
                switch (c){
                    case ' ':
                    case '.':
                        g2d.translate(x,y);
                        g2d.drawRenderedImage(
                                grassTextures[PseudoRandom.getRandomBetween(0,grassTextures.length-1,x,y,Cfg.noiseSizeTerrainColor)]
                                        [PseudoRandom.getRandomBetween(0,grassTextures[0].length-1,x,y,Cfg.noiseSizeTerrainVariant)],
                                null);
                        g2d.translate(-x,-y); break;
                    case 't':
                    case 'T':
                        g2d.translate(x,y);
                        g2d.drawRenderedImage(treeTexture,null);
                        g2d.translate(-x,-y); break;
                    case 'h':
                    case 'H':
                        g2d.translate(x,y);
                        g2d.drawRenderedImage(trapTexture,null);
                        g2d.translate(-x,-y); break;
                    case 'r':
                    case 'R':
                        g2d.translate(x,y);
                        g2d.drawRenderedImage(
                                stoneTextures[PseudoRandom.getRandomBetween(0,stoneTextures.length-1,x,y,Cfg.noiseSizeTerrainColor)]
                                        [PseudoRandom.getRandomBetween(0,stoneTextures[0].length-1,x,y,Cfg.noiseSizeTerrainVariant)],
                                null);
                        g2d.translate(-x,-y); break;
                }
                x+=tileSize.x;
            }
            y+=tileSize.y;
            x=mapOffset.x;
        }
    }

    public List<BoxCollider> getColliders() {
        return colliders;
    }
}
