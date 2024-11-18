package game.hud;

import game.DynamicSprite;
import physics.Collider;
import utils.IVec2;
import utils.Vec2;

import java.awt.*;

public class Cursor extends DynamicSprite {
    private final Vec2 scale = new Vec2(1,1);
    private final double rotation=0;
    private final IVec2 textureSize = new IVec2(64,64);

    public Cursor(String texturePath) {
        super(new IVec2(), new Collider(), "img/tree.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2d = (Graphics2D) g.create();

        /*
        g2d.translate(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
        g2d.scale(currentCamera.getScale().x,currentCamera.getScale().y);*/


        //g2d.scale(currentCamera.getScale().x,currentCamera.getScale().y);
        //g2d.translate(-currentCamera.getOffset().x,-currentCamera.getOffset().y);
        //g2d.translate(-1920/2,-1080/2);
        //g2d.translate(currentCamera.getOffset().x,currentCamera.getOffset().y);

        // to reverse
        g2d.scale(currentCamera.getScale().x,currentCamera.getScale().y);
        g2d.translate(-currentCamera.getOffset().x,-currentCamera.getOffset().y);

        // find position of cursor on map
        IVec2 mousePosOnScreen=new IVec2(
                MouseInfo.getPointerInfo().getLocation().x,
                MouseInfo.getPointerInfo().getLocation().y
        );

        super.position.x=(int)Math.round(mousePosOnScreen.x/currentCamera.getScale().x)+currentCamera.getOffset().x;
        super.position.y=(int)Math.round(mousePosOnScreen.y/currentCamera.getScale().y)+currentCamera.getOffset().y;

        // the end
        g2d.translate(super.position.x, super.position.y);
        g2d.rotate(this.rotation, this.textureSize.x >> 1, this.textureSize.y >> 1);
        g2d.scale(this.scale.x, this.scale.y);

        g2d.drawRenderedImage(super.texture,
                null
        );
    }
}
