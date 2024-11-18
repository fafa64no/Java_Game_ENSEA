package game.characters.vehicles.tank;

import game.characters.vehicles.Vehicle;
import physics.Collider;
import rendering.RenderEngine;
import utils.IVec2;

import java.awt.*;

public class Tank extends Vehicle {
    private final Turret turret;

    public Tank(IVec2 position, Collider collider, String texturePath, String turretTexturePath, int velocityMultiplier, IVec2 textureSize, int animationFrames, double rotationSpeed, double turretRotationSpeed) {
        super(position, collider, texturePath, velocityMultiplier, animationFrames, textureSize, rotationSpeed);
        this.turret=new Turret(position,turretTexturePath,1,textureSize,this, turretRotationSpeed);
    }

    protected IVec2 getTurretMount(){
        return new IVec2(
            (int)Math.round(super.position.x+super.textureSize.y*Math.sin(this.rotation)/8),
            (int)Math.round(super.position.y-super.textureSize.y*Math.cos(this.rotation)/8)
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
        g2d.scale(currentCamera.getScale().x,currentCamera.getScale().y);
        g2d.translate(-currentCamera.getOffset().x,-currentCamera.getOffset().y);

        animationCounter=(animationCounter+animationSpeed)%(animationFrames*animationFrames);
        int animationFrame=animationCounter/animationFrames;

        int texX;
        if (super.currentDir.isNull())  {texX=0;                                    }
        else                            {texX=animationFrame*super.textureSize.x;   }

        g2d.translate(super.position.x, super.position.y);
        g2d.rotate(super.rotation, textureSize.x >> 1, textureSize.y >> 1);
        g2d.scale(super.scale.x, super.scale.y);
        g2d.drawRenderedImage(super.texture.getSubimage(
                texX,
                0,
                super.textureSize.x,
                super.textureSize.y
        ),null);
    }
}
