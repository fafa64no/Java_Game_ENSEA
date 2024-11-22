package game.hud;

import game.DynamicSprite;
import physics.BoxCollider;
import utils.vectors.IVec2;
import utils.vectors.Vec2;

import java.awt.*;

public class Cursor extends DynamicSprite {
    private final Vec2 scale;
    private final IVec2 textureSize;

    public Cursor(String texturePath, Vec2 scale, IVec2 textureSize) {
        super(new IVec2(), texturePath);
        this.scale=scale;
        this.textureSize=textureSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.translate(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);

        g2d.scale(scale.x,scale.y);
        g2d.translate(-textureSize.x/2,-textureSize.y/2);

        g2d.drawRenderedImage(super.texture,
                null
        );
    }
}
