package main.game.vehicles.cubes;

import main.game.vehicles.LifeStates;
import main.game.projectiles.ProjectileHandler;
import main.physics.ColliderType;
import main.physics.CollisionLayer;
import main.physics.PhysicEngine;
import main.physics.colliders.BoxCollider;
import main.rendering.vfx.VfxType;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public abstract class PlaneCube extends MovingCube{
    protected final int disengagingFrames;
    protected int remainingDisengagingFrames = 0;

    public PlaneCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, BufferedImage[] deploymentTextures, BufferedImage[] retractionTextures, BufferedImage[] attackTextures, double rotationSpeed, double maxHealth, int animationDuration, CubeHead cubeHead, int textureSize, ProjectileHandler projectileHandler, int firingDelay, double attackRange, double followRange, double weaponSpread, double movementSpeed, int disengagingFrames) {
        super(
                position,
                texture,
                deadTexture,
                deploymentTextures,
                retractionTextures,
                attackTextures,
                rotationSpeed,
                maxHealth,
                animationDuration,
                cubeHead,
                textureSize,
                projectileHandler,
                firingDelay,
                attackRange,
                followRange,
                weaponSpread,
                movementSpeed
        );
        this.disengagingFrames = disengagingFrames;
    }

    @Override
    protected void genColliders() {
        collider = new BoxCollider(
                new Vec2(-16,-16),
                new Vec2(16,16),
                false,
                0.1,
                new Vec2(),
                this,
                ColliderType.AERIAL_INERT
        );
        detectionZone = new BoxCollider(
                new Vec2(-Config.cubeDetectionRange,-Config.cubeDetectionRange),
                new Vec2(Config.cubeDetectionRange,Config.cubeDetectionRange),
                false,
                1,
                new Vec2(),
                this,
                ColliderType.NONE_TRIGGER
        );
        damageZone = new BoxCollider(
                new Vec2(-Config.cubeCollisionRange,-Config.cubeCollisionRange),
                new Vec2(Config.cubeCollisionRange,Config.cubeCollisionRange),
                false,
                0.1,
                new Vec2(),
                this,
                ColliderType.NONE_DAMAGE_DEALER,
                3,
                VfxType.VFX_ELECTRICITY,
                15
        );

        PhysicEngine.addCollider(collider, CollisionLayer.COLLISION_LAYER_ENEMIES);
        PhysicEngine.addCollider(damageZone, CollisionLayer.COLLISION_LAYER_ENEMIES);
        PhysicEngine.addCollider(detectionZone, CollisionLayer.COLLISION_LAYER_ENEMIES);
        collider.updateOffset();
        damageZone.updateOffset();
        detectionZone.updateOffset();
    }

    @Override
    protected boolean isNotValidTarget(){
        return (currentTarget == null) || !currentTarget.isTargetable();
    }

    @Override
    protected void updatePosition() {
        updateChildrenPosition();
        double distanceToTravel = Vec2.getDistance(position,currentTarget.getPosition());
        if(distanceToTravel < attackRange){
            lifeState = LifeStates.CURRENTLY_DISENGAGING;
            remainingDisengagingFrames = disengagingFrames;
        }

        setInput(new Vec2(0,-movementSpeed));

        collider.updateOffset();
        damageZone.updateOffset();
        detectionZone.updateOffset();
    }

    @Override
    protected void computeNewRotation() {
        if(lifeState==LifeStates.CURRENTLY_DEAD||lifeState==LifeStates.CURRENTLY_DISENGAGING)return;
        double targetRotation = getTargetRotation();
        int angleSign;  double angleToTravel;

        angleToTravel=Math.abs(targetRotation-rotation);
        angleSign=((targetRotation-rotation>=0)?1:-1)*((angleToTravel<Math.PI)?1:-1);

        this.rotation+=angleSign*Math.min(angleToTravel,this.rotationSpeed)+2*Math.PI;
        this.rotation=this.rotation%(2*Math.PI);
    }

    @Override
    protected void disengagingState() {
        if(--remainingDisengagingFrames<0){
            lifeState = LifeStates.CURRENTLY_PURSUING;
        }
        updatePosition();
    }
}
