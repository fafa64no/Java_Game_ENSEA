package game.level;

import physics.Collider;
import rendering.Displayable;
import rendering.RenderEngine;
import utils.Cfg;
import utils.DataGen;
import utils.vectors.IVec2;
import utils.PseudoRandom;

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
    private final List<Collider> colliders;

    private final IVec2 tileSize = new IVec2(Cfg.getTileSize(),Cfg.getTileSize());
    private final IVec2 mapOffset = new IVec2();

    private final BufferedImage[] grassTextures;
    private final BufferedImage[] stoneTextures;
    private BufferedImage treeTexture;
    private BufferedImage trapTexture;

    public Level(IVec2 size,String path){
        map=new char[size.y][size.x];
        colliders=new ArrayList<>();
        mapOffset.x=Math.round((float) (-map[0].length * tileSize.x) /2);
        mapOffset.y=Math.round((float) (-map.length * tileSize.y) /2);
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
                int posX=tileSize.x*x+mapOffset.x;
                int posY=tileSize.y*y+ mapOffset.y;
                switch (this.map[y][x]){
                    case 'R':
                        colliders.add(new Collider(posX,posY,posX+tileSize.x,posY+tileSize.y,0.3));
                        break;
                    case 'T':
                        colliders.add(new Collider(posX,posY,posX+tileSize.x,posY+tileSize.y,0.2));
                        break;
                    case 'H':
                        break;
                }
            }
        }
    }

    public List<Collider> getColliders() {
        return colliders;
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
        int x=mapOffset.x;
        int y=mapOffset.y;
        for (char[] line : map){
            for (char c : line){
                switch (c){
                    case ' ':
                    case '.':
                        g2d.translate(x,y);
                        g2d.drawRenderedImage(grassTextures[PseudoRandom.getRandomBetween(0,grassTextures.length,x,y)],null);
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
                        g2d.drawRenderedImage(stoneTextures[PseudoRandom.getRandomBetween(0,stoneTextures.length,x,y)],null);
                        g2d.translate(-x,-y); break;
                }
                x+=tileSize.x;
            }
            y+=tileSize.y;
            x=mapOffset.x;
        }
    }
}
