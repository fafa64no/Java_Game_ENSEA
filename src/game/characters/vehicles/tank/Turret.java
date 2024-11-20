package game.characters.vehicles.tank;

import game.characters.vehicles.Vehicle;
import utils.IVec2;
import utils.Vec2;

import java.awt.*;

public class Turret extends Vehicle {
    private final Tank parent;

    public Turret(IVec2 position, String texturePath, int animationFrames, IVec2 textureSize, Tank parent, double rotationSpeed) {
        super(position, null, texturePath, 0, animationFrames, textureSize, rotationSpeed);
        this.parent=parent;
    }

    protected void computeNewRotation(double parentRotationModifier){
        this.rotation=(this.rotation+parentRotationModifier)%(2*Math.PI);
        IVec2 targetPosition=new IVec2(
                (int)Math.round(MouseInfo.getPointerInfo().getLocation().x/currentCamera.getScale().x)+currentCamera.getOffset().x,
                (int)Math.round(MouseInfo.getPointerInfo().getLocation().y/currentCamera.getScale().y)+currentCamera.getOffset().y
        );
        double targetRotation=(new IVec2(
                targetPosition.x-this.position.x- (int)Math.round((double) this.textureSize.x /2),
                targetPosition.y-this.position.y- (int)Math.round((double) this.textureSize.y /2)
        ).getAngle()+5*Math.PI/2)%(2*Math.PI);
        int angleSign;  double angleToTravel;

        angleToTravel=Math.abs(targetRotation-rotation);
        angleSign=((targetRotation-rotation>=0)?1:-1)*((angleToTravel<Math.PI)?1:-1);

        this.rotation+=angleSign*Math.min(angleToTravel,this.rotationSpeed);
        this.rotation=(this.rotation+2*Math.PI)%(2*Math.PI);
    }

    @Override
    public IVec2 computeNewPosition() {
        super.nextPosition=this.parent.getTurretMount();
        return super.nextPosition;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(currentCamera.getScale().x,currentCamera.getScale().y);
        g2d.translate(-currentCamera.getOffset().x,-currentCamera.getOffset().y);

        g2d.translate(super.position.x,super.position.y);
        g2d.rotate(super.rotation, textureSize.x >> 1,textureSize.y >> 1);
        g2d.scale(super.scale.x,super.scale.y);
        g2d.drawRenderedImage(super.texture.getSubimage(
                0,
                0,
                super.textureSize.x,
                super.textureSize.y
        ),null);
    }
}
