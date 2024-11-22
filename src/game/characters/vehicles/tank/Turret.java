package game.characters.vehicles.tank;

import game.characters.vehicles.Vehicle;
import rendering.RenderEngine;
import utils.vectors.IVec2;

import java.awt.*;

public class Turret extends Vehicle {
    private final Tank parent;

    public Turret(IVec2 position, String texturePath, int animationFrames, IVec2 textureSize, Tank parent, double rotationSpeed) {
        super(position, texturePath, 0, animationFrames, textureSize, rotationSpeed);
        this.parent=parent;
    }

    public Turret(IVec2 position, String texturePath, int animationFrames, IVec2 textureSize, Tank parent, double rotationSpeed, IVec2 scale) {
        super(position, texturePath, 0, animationFrames, textureSize, rotationSpeed);
        this.parent=parent;
        super.scale=scale;
    }

    protected void computeNewRotation(double parentRotationModifier){
        this.rotation=(this.rotation+parentRotationModifier)%(2*Math.PI);
        double targetRotation = getTargetRotation();
        int angleSign;  double angleToTravel;

        angleToTravel=Math.abs(targetRotation-rotation);
        angleSign=((targetRotation-rotation>=0)?1:-1)*((angleToTravel<Math.PI)?1:-1);

        this.rotation+=angleSign*Math.min(angleToTravel,this.rotationSpeed)+2*Math.PI;
    }

    private double getTargetRotation() {
        IVec2 targetPosition=new IVec2(
                (int)Math.round(MouseInfo.getPointerInfo().getLocation().x/RenderEngine.getCurrentCamera().getScale().x)+RenderEngine.getCurrentCamera().getOffset().x,
                (int)Math.round(MouseInfo.getPointerInfo().getLocation().y/RenderEngine.getCurrentCamera().getScale().y)+RenderEngine.getCurrentCamera().getOffset().y
        );
        return (new IVec2(
                targetPosition.x- this.position.x,
                targetPosition.y- this.position.y
        ).getAngle()+5*Math.PI/2)%(2*Math.PI);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);

        g2d.translate(position.x-scale.x*Math.round((float) textureSize.x /2), position.y-scale.y*Math.round((float) textureSize.y /2));
        g2d.rotate(super.rotation, scale.x*Math.round((float) textureSize.x /2),scale.y*Math.round((float) textureSize.y /2));
        g2d.scale(super.scale.x,super.scale.y);
        g2d.drawRenderedImage(super.texture.getSubimage(
                0,
                0,
                super.textureSize.x,
                super.textureSize.y
        ),null);
    }
}
