package game.hud;

import game.DynamicSprite;
import physics.Collider;
import utils.IVec2;

import java.awt.*;

public class Cursor extends DynamicSprite {
    private final IVec2 cursorSize;

    public Cursor(String texturePath) {
        super(new IVec2(), new Collider(), texturePath);
        cursorSize=new IVec2(50,50);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(currentCamera.getScale().x,currentCamera.getScale().y);
        g2d.translate(-currentCamera.getOffset().x,-currentCamera.getOffset().y);

        g2d.drawRenderedImage(super.texture,
                null
        );
    }
}
