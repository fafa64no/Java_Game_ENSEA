package main.rendering;

import main.game.GameEngine;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.IVec2;
import main.utils.vectors.IVec4;
import main.utils.vectors.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LeavesRenderer extends JPanel implements Displayable {
    private final BufferedImage[][] leavesTextures;
    private final BufferedImage[][] mapLeavesTextures;
    private final Vec2 mapOffset;

    public LeavesRenderer(char[][] map, Vec2 mapOffset){
        leavesTextures = DataGen.getLeavesTextures();
        mapLeavesTextures = new BufferedImage[map.length][map[0].length];

        this.setOpaque(false);

        for (int x=0; x<map[0].length; x++){
            for (int y=0; y<map.length; y++){
                if(map[y][x]=='T'){
                    mapLeavesTextures[y][x]=leavesTextures[PseudoRandom.getRandomBetween(0,leavesTextures.length-1,x,y, Config.noiseSizeTerrainColor)]
                            [PseudoRandom.getRandomBetween(0,leavesTextures[0].length-1,x,y, Config.noiseSizeTerrainVariant)];
                }else{
                    mapLeavesTextures[y][x]=null;
                }
            }
        }

        this.mapOffset=new Vec2(
                mapOffset.x- (double) (Config.largeTileSize - Config.smallTileSize) /2,
                mapOffset.y- (double) (Config.largeTileSize - Config.smallTileSize) /2
        );
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
        Graphics2D g2d=(Graphics2D)g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);
        // Don't render outside of camera range because lag
        IVec4 tileIDWindow = GameEngine.getCurrentLevel().getTilesIDWindowFromCamera(new IVec2(1,1));
        for (int x = tileIDWindow.x; x < tileIDWindow.y; x++) {
            for (int y = tileIDWindow.z; y < tileIDWindow.w; y++) {
                if(mapLeavesTextures[y][x]==null)continue;
                double posX=mapOffset.x+x* Config.smallTileSize;
                double posY=mapOffset.y+y* Config.smallTileSize;
                g2d.translate(posX,posY);
                g2d.drawRenderedImage(mapLeavesTextures[y][x],null);
                g2d.translate(-posX,-posY);

            }
        }
    }
}
