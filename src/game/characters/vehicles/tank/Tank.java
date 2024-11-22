package game.characters.vehicles.tank;

import game.characters.vehicles.Vehicle;
import physics.Collider;
import rendering.RenderEngine;
import utils.vectors.IVec2;

import java.awt.*;

public class Tank extends Vehicle {
    private final Turret turret;

    public Tank(IVec2 position, Collider collider, String texturePath, String turretTexturePath, int velocityMultiplier, IVec2 textureSize, int animationFrames, double rotationSpeed, double turretRotationSpeed) {
        super(position, collider, texturePath, velocityMultiplier, animationFrames, textureSize, rotationSpeed);
        this.turret=new Turret(position,turretTexturePath,1,textureSize,this, turretRotationSpeed);
    }

    public Tank(IVec2 position, Collider collider, String texturePath, String turretTexturePath, int velocityMultiplier, IVec2 textureSize, int animationFrames, double rotationSpeed, double turretRotationSpeed, IVec2 scale) {
        super(position, collider, texturePath, velocityMultiplier, animationFrames, textureSize, rotationSpeed);
        this.turret=new Turret(position,turretTexturePath,1,textureSize,this, turretRotationSpeed, scale);
        super.scale=scale;
    }

    protected IVec2 getTurretMount(){
        return new IVec2(
            (int)Math.round(position.x+textureSize.y*Math.sin(rotation)/8),
            (int)Math.round(position.y-textureSize.y*Math.cos(rotation)/8)
        );
    }

    public Turret getTurret() {
        return turret;
    }

    public void computeNewRotation(){
        double rotationModifier=super.rotationSpeed*super.currentDir.x;
        super.rotation=(super.rotation+rotationModifier)%(2*Math.PI);
        this.turret.computeNewRotation(rotationModifier);
    }

    @Override
    public IVec2 computeNewPosition() {
        super.nextPosition.x=(int)Math.round(super.position.x-super.currentVelocity.y*Math.sin(super.rotation));
        super.nextPosition.y=(int)Math.round(super.position.y+super.currentVelocity.y*Math.cos(super.rotation));
        this.computeNewRotation();
        return super.nextPosition;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);

        animationCounter=(animationCounter+animationSpeed)%(animationFrames*animationFrames);
        int animationFrame=animationCounter/animationFrames;

        int texX;
        if (super.currentDir.isNull())  {texX=0;                                    }
        else                            {texX=animationFrame*super.textureSize.x;   }

        g2d.translate(position.x-scale.x*Math.round((float) textureSize.x /2), position.y-scale.y*Math.round((float) textureSize.y /2));
        g2d.rotate(super.rotation, scale.x*Math.round((float) textureSize.x /2),scale.y*Math.round((float) textureSize.y /2));
        g2d.scale(scale.x, scale.y);
        g2d.drawRenderedImage(super.texture.getSubimage(
                texX,
                0,
                super.textureSize.x,
                super.textureSize.y
        ),null);
    }
}
