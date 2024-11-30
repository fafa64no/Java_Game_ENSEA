package main.game.vehicles.tanks;

import main.game.GameEngine;
import main.game.vehicles.LifeStates;
import main.game.vehicles.Vehicle;
import main.physics.ColliderType;
import main.physics.PhysicEngine;
import main.physics.colliders.BoxCollider;
import main.physics.colliders.Collider;
import main.rendering.RenderEngine;
import main.rendering.vfx.Vfx;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.BVec2;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.*;

public class Tank extends Vehicle {
    private final TankTurret tankTurret;
    private final SolidCollider collider;

    public Tank(Vec2 position, String textureName, int velocityMultiplier, IVec2 textureSize, double rotationSpeed, double turretRotationSpeed, Vec2 colliderSize, int reloadFrames, double maxHealth) {
        super(position, "./assets/textures/tanks/tanks/"+textureName+"/base.png", "./assets/textures/tanks/tanks/"+textureName+"/deadBase.png", velocityMultiplier, textureSize, rotationSpeed, maxHealth);
        this.tankTurret = new TankTurret(position, "./assets/textures/tanks/tanks/"+textureName+"/turret.png", "./assets/textures/tanks/tanks/"+textureName+"/deadTurret.png", textureSize,this, turretRotationSpeed, reloadFrames);
        this.collider = new BoxCollider(
                new Vec2(-colliderSize.x,-colliderSize.y),
                colliderSize,
                false,
                0,
                new Vec2(),
                this,
                ColliderType.SOLID_INERT_ALLY
        );
        GameEngine.addTarget(this);
    }

    protected Vec2 getTurretMount(){
        return new Vec2(
            position.x+textureSize.y*Math.sin(rotation)/8,
            position.y-textureSize.y*Math.cos(rotation)/8
        );
    }

    public TankTurret getTurret() {
        return tankTurret;
    }

    public Collider getCollider() {
        return collider;
    }

    public void computeNewRotation(){
        if(lifeState == LifeStates.CURRENTLY_DEAD || this != GameEngine.getCurrentTank()) return;
        double rotationModifier=super.rotationSpeed*super.currentDir.x;
        super.rotation=(super.rotation+rotationModifier)%(2*Math.PI);
        tankTurret.computeNewRotation(rotationModifier);
    }

    public boolean fireProjectile(){
        if(lifeState==LifeStates.CURRENTLY_DEAD)return false;
        return tankTurret.fireProjectile();
    }

    @Override
    public Vec2 getCurrentVelocity() {
        Vec2 actualVelocity=new Vec2();
        actualVelocity.x=-currentVelocity.y*Math.sin(rotation);
        actualVelocity.y= currentVelocity.y*Math.cos(rotation);
        return actualVelocity;
    }

    @Override
    public void goToNextPosition(BVec2 canMove, double friction){
        if(canMove.x)   position.x=position.x-currentVelocity.y*Math.sin(rotation);
        if(canMove.y)   position.y=position.y+currentVelocity.y*Math.cos(rotation);
        collider.updateOffset();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);

        g2d.translate(position.x-scale.x*Math.round((float) textureSize.x /2), position.y-scale.y*Math.round((float) textureSize.y /2));
        g2d.rotate(super.rotation, scale.x*Math.round((float) textureSize.x /2),scale.y*Math.round((float) textureSize.y /2));
        g2d.scale(scale.x, scale.y);
        if(lifeState!=LifeStates.CURRENTLY_DEAD){
            g2d.drawRenderedImage(texture,null);
        }else if(deadTexture!=null){
            g2d.drawRenderedImage(deadTexture,null);
        }
    }

    @Override
    public void killYourself() {
        super.killYourself();
        tankTurret.killYourself();
        GameEngine.removeTarget(this);
        PhysicEngine.removeCollider(collider);
        new Vfx(position, Config.largeTileSize, DataGen.getExplosionTextures(),20);
    }

    @Override
    public Vec2 getVelocity() {
        return getCurrentVelocity();
    }

    @Override
    public boolean isTargetable() {
        return lifeState!=LifeStates.CURRENTLY_DEAD;
    }
}
