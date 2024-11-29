package main.rendering;

import main.game.characters.cubes.RedCube;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FlyingCubeRenderer extends JPanel implements Displayable {
    private static FlyingCubeRenderer instance;

    private final List<RedCube> redCubes = new ArrayList<>();
    private List<RedCube> redCubesToAdd = new ArrayList<>();

    public FlyingCubeRenderer() {
        if(instance == null)instance = this;
        this.setOpaque(false);
    }

    public static void addCube(RedCube redCube){
        instance.redCubesToAdd.add(redCube);
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

        redCubes.addAll(redCubesToAdd);
        redCubesToAdd = new ArrayList<>();

        for(RedCube redCube : redCubes){
            if(!RenderEngine.getCurrentCamera().getDisplayWindow(new Vec2(Config.largeTileSize)).contains(redCube.getPosition()))continue;
            Vec2 position = redCube.getPosition();
            int textureSize = redCube.getTextureSize();
            Vec2 translation = new Vec2(
                    position.x-Math.round((float)0.5*textureSize),
                    position.y-Math.round((float)0.5*textureSize)
            );
            g2d.translate(translation.x,translation.y);
            g2d.rotate(redCube.getRotation(), textureSize/2.0,textureSize/2.0);
            g2d.drawRenderedImage(redCube.getTexture(),null);
            g2d.rotate(-redCube.getRotation(), textureSize/2.0,textureSize/2.0);
            g2d.translate(-translation.x,-translation.y);
        }
    }
}
