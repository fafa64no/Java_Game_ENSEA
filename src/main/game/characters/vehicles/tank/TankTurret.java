package main.game.characters.vehicles.tank;

import main.game.characters.vehicles.Vehicle;
import main.game.projectiles.ProjectileHandler;
import main.game.projectiles.TankShell;
import main.rendering.RenderEngine;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.*;

public class TankTurret extends Vehicle {
    private final Tank parent;
    private final ProjectileHandler projectile;

    public TankTurret(Vec2 position, String texturePath, int animationFrames, IVec2 textureSize, Tank parent, double rotationSpeed) {
        super(position, texturePath, 0, animationFrames, textureSize, rotationSpeed);
        this.parent = parent;
        this.projectile = TankShell.getInstance();
    }

    public TankTurret(Vec2 position, String texturePath, int animationFrames, IVec2 textureSize, Tank parent, double rotationSpeed, Vec2 scale) {
        super(position, texturePath, 0, animationFrames, textureSize, rotationSpeed, scale);
        this.parent = parent;
        this.projectile = TankShell.getInstance();
    }

    protected void computeNewRotation(double parentRotationModifier){
        this.rotation=(this.rotation+parentRotationModifier)%(2*Math.PI);
        double targetRotation = getTargetRotation();
        int angleSign;  double angleToTravel;

        angleToTravel=Math.abs(targetRotation-rotation);
        angleSign=((targetRotation-rotation>=0)?1:-1)*((angleToTravel<Math.PI)?1:-1);

        this.rotation+=angleSign*Math.min(angleToTravel,this.rotationSpeed)+2*Math.PI;
    }

    private Vec2 getTargetPosition() {
        return new Vec2(
                MouseInfo.getPointerInfo().getLocation().x/RenderEngine.getCurrentCamera().getScale().x+RenderEngine.getCurrentCamera().getOffset().x,
                MouseInfo.getPointerInfo().getLocation().y/RenderEngine.getCurrentCamera().getScale().y+RenderEngine.getCurrentCamera().getOffset().y
        );
    }

    private double getTargetRotation() {
        Vec2 targetPosition=getTargetPosition();
        return (new Vec2(
                targetPosition.x- this.position.x,
                targetPosition.y- this.position.y
        ).getAngle()+5*Math.PI/2)%(2*Math.PI);
    }

    public boolean fireProjectile(){
        if(projectile==null)return false;
        projectile.fireInDirection(this.position,this.rotation);
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);

        g2d.translate(position.x-scale.x*textureSize.x /2, position.y-scale.y*textureSize.y /2);
        g2d.rotate(super.rotation, scale.x*textureSize.x /2,scale.y*textureSize.y /2);
        g2d.scale(super.scale.x,super.scale.y);
        g2d.drawRenderedImage(super.texture.getSubimage(
                0,
                0,
                super.textureSize.x,
                super.textureSize.y
        ),null);
    }
}
