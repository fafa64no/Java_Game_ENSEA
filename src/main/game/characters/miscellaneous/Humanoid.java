package main.game.characters.miscellaneous;

import main.game.characters.Character;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.*;

/**
 * MIGHT BE OUTDATED (No idea)
 */

public class Humanoid extends Character {
    private int animationCounter;
    private final int animationFrames;

    public Humanoid(Vec2 position, String texturePath, int velocityMultiplier, int animationFrames, IVec2 textureSize) {
        super(position, texturePath, velocityMultiplier,animationFrames,textureSize);
        this.animationFrames=animationFrames;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        animationCounter=(animationCounter+animationSpeed)%(animationFrames*animationFrames);
        int animationFrame=animationCounter/animationFrames;

        int texX,texY;
        if      (super.currentDir.x>0)   {texX=animationFrame;  texY=3;}
        else if (super.currentDir.x<0)   {texX=animationFrame;  texY=1;}
        else if (super.currentDir.y<0)   {texX=animationFrame;  texY=2;}
        else if (super.currentDir.y>0)   {texX=animationFrame;  texY=0;}
        else if (super.previousDir.x>0)  {texX=0;               texY=3;}
        else if (super.previousDir.x<0)  {texX=0;               texY=1;}
        else if (super.previousDir.y<0)  {texX=0;               texY=2;}
        else if (super.previousDir.y>0)  {texX=0;               texY=0;}
        else                             {texX=0;               texY=0;}

        texX*=this.textureSize.x;
        texY*=this.textureSize.y;

        Graphics2D g2d=(Graphics2D)g.create();
        g2d.translate(this.position.x,this.position.y);
        g2d.drawRenderedImage(this.texture.getSubimage(
                texX,
                texY,
                this.textureSize.x,
                this.textureSize.y
        ),null);
    }
}
