package main.game.characters.vehicles.tank;

import main.game.characters.LifeStates;
import main.game.characters.vehicles.Vehicle;
import main.physics.colliders.BoxCollider;
import main.physics.colliders.CircleCollider;
import main.physics.colliders.Collider;
import main.physics.colliders.SolidCollider;
import main.rendering.RenderEngine;
import main.utils.vectors.BVec2;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.*;

public class Tank extends Vehicle {
    private final TankTurret tankTurret;
    private final SolidCollider collider;

    public Tank(Vec2 position, String texturePath, String turretTexturePath, String deadTexturePath, String deadTurretTexturePath, int velocityMultiplier, IVec2 textureSize, int animationFrames, double rotationSpeed, double turretRotationSpeed, Vec2 colliderSize) {
        super(position, texturePath, deadTexturePath, velocityMultiplier, animationFrames, textureSize, rotationSpeed);
        this.tankTurret =new TankTurret(position,turretTexturePath,deadTurretTexturePath,1,textureSize,this, turretRotationSpeed);
        this.collider=new BoxCollider(
                new Vec2(-colliderSize.x,-colliderSize.y),
                colliderSize,
                false,
                0,
                new Vec2(),
                this
        );
    }

    public Tank(Vec2 position, String texturePath, String turretTexturePath, int velocityMultiplier, IVec2 textureSize, int animationFrames, double rotationSpeed, double turretRotationSpeed, Vec2 scale, Vec2 colliderSize) {
        super(position, texturePath, velocityMultiplier, animationFrames, textureSize, rotationSpeed, scale);
        this.tankTurret =new TankTurret(position,turretTexturePath,1,textureSize,this, turretRotationSpeed, scale);
        this.collider=new BoxCollider(
                new Vec2(-colliderSize.x,-colliderSize.y),
                colliderSize,
                false,
                0,
                new Vec2(),
                this
        );
    }

    public Tank(Vec2 position, String texturePath, String turretTexturePath, int velocityMultiplier, IVec2 textureSize, int animationFrames, double rotationSpeed, double turretRotationSpeed, double colliderRadius) {
        super(position, texturePath, velocityMultiplier, animationFrames, textureSize, rotationSpeed);
        this.tankTurret =new TankTurret(position,turretTexturePath,1,textureSize,this, turretRotationSpeed);
        this.collider=new CircleCollider(
                colliderRadius,
                false,
                0,
                new Vec2(),
                this
        );
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
        if(lifeState==LifeStates.CURRENTLY_DEAD)return;
        double rotationModifier=super.rotationSpeed*super.currentDir.x;
        super.rotation=(super.rotation+rotationModifier)%(2*Math.PI);
        this.tankTurret.computeNewRotation(rotationModifier);
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
        computeNewRotation();
        return actualVelocity;
    }

    @Override
    public void goToNextPosition(BVec2 canMove, double friction){
        if(canMove.x)   position.x=position.x-currentVelocity.y*Math.sin(rotation);
        if(canMove.y)   position.y=position.y+currentVelocity.y*Math.cos(rotation);
        collider.setOffset(this,this.position);
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
        if(lifeState!=LifeStates.CURRENTLY_DEAD){
            g2d.drawRenderedImage(super.texture.getSubimage(
                    texX,
                    0,
                    super.textureSize.x,
                    super.textureSize.y
            ),null);
        }else if(deadTexture!=null){
            g2d.drawRenderedImage(deadTexture,null);
        }
    }

    @Override
    public void killYourself() {
        super.killYourself();
        tankTurret.killYourself();
    }
}
