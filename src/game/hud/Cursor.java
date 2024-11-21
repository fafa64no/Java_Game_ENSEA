package game.hud;

import game.DynamicSprite;
import physics.Collider;
import utils.IVec2;
import utils.Vec2;

import java.awt.*;

public class Cursor extends DynamicSprite {
    private final Vec2 scale = new Vec2(3,3);
    private final IVec2 textureSize = new IVec2(9,9);

    public Cursor(String texturePath) {
        super(new IVec2(), new Collider(), texturePath);
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
