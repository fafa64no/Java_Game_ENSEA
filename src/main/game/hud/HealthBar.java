package main.game.hud;

import main.game.GameEngine;
import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.utils.vectors.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthBar extends JPanel implements Displayable {
    private final BufferedImage texture;
    private Character target;

    private final Vec2 scale = new Vec2(4,4);
    private final Vec2 offset = new Vec2(8,44);
    private final Color color = new Color(57,115,255);

    public HealthBar(BufferedImage texture){
        this.texture = texture;
        updateTarget();

        this.setOpaque(false);
    }

    public void updateTarget(){
        target = GameEngine.getCurrentTank();
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

        g2d.translate(offset.x*scale.x,getHeight()-offset.y*scale.y);
        g2d.scale(scale.x,scale.y);

        g2d.setColor(color);
        g2d.drawRenderedImage(texture,null);
        g2d.fillRect(2,30,(int)Math.round(60*target.getHealthRatio()),4);
    }
}
