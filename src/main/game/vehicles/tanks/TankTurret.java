package main.game.vehicles.tanks;

import main.game.GameEngine;
import main.game.vehicles.LifeState;
import main.game.projectiles.ProjectileHandler;
import main.game.projectiles.TankShell;
import main.game.vehicles.Vehicle;
import main.rendering.RenderEngine;
import main.utils.RequiresUpdates;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.*;

public class TankTurret extends Vehicle implements RequiresUpdates {
    private final Tank parent;
    private final ProjectileHandler projectile;
    private final int reloadFrames;
    private int remainingReloadFrames = 0;

    public TankTurret(Vec2 position, String texturePath, String deadTexturePath, IVec2 textureSize, Tank parent, double rotationSpeed, int reloadFrames) {
        super(position, texturePath, deadTexturePath, 0, textureSize, rotationSpeed);
        this.parent = parent;
        this.projectile = TankShell.getInstance();
        this.reloadFrames = reloadFrames;

        GameEngine.addRequiresUpdates(this);
    }

    protected void computeNewRotation(double parentRotationModifier){
        this.rotation=(this.rotation+parentRotationModifier)%(2*Math.PI);
        if(lifeState== LifeState.CURRENTLY_DEAD)return;
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
        if(parent != GameEngine.getCurrentTank()) return 0;
        Vec2 targetPosition=getTargetPosition();
        return (new Vec2(
                targetPosition.x- this.position.x,
                targetPosition.y- this.position.y
        ).getAngle()+5*Math.PI/2)%(2*Math.PI);
    }

    public boolean fireProjectile(){
        if(lifeState== LifeState.CURRENTLY_DEAD || remainingReloadFrames>0 || projectile==null)return false;
        projectile.fireInDirection(this.position,this.rotation);
        remainingReloadFrames=reloadFrames;
        return true;
    }

    @Override
    public void doUpdate(){
        if(remainingReloadFrames>0)remainingReloadFrames--;
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
        if(lifeState!= LifeState.CURRENTLY_DEAD){
            g2d.drawRenderedImage(super.texture.getSubimage(
                    0,
                    0,
                    super.textureSize.x,
                    super.textureSize.y
            ),null);
        }else if(deadTexture!=null){
            g2d.drawRenderedImage(deadTexture,null);
        }
    }

    @Override
    public Vec2 getVelocity() {
        return currentVelocity;
    }

    @Override
    public boolean isTargetable() {
        return false;
    }
}
