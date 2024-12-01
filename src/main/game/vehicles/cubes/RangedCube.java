package main.game.vehicles.cubes;

import main.game.GameEngine;
import main.game.vehicles.LifeState;
import main.game.target.Target;
import main.game.projectiles.ProjectileHandler;
import main.physics.ColliderType;
import main.physics.CollisionLayer;
import main.physics.PhysicEngine;
import main.physics.colliders.BoxCollider;
import main.physics.colliders.Collider;
import main.utils.data.Config;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class RangedCube extends BasicCube implements AIdriven {
    protected final int animationDuration;
    protected int remainingAnimationTime=0;
    protected int currentAnimationFrame=0;
    public final CubeHead cubeHead;

    protected double rotation=0;
    protected final double rotationSpeed;
    protected final double requiredAccuracy;

    private final BufferedImage[] deploymentTextures;
    private final BufferedImage[] retractionTextures;
    private final BufferedImage[] attackTextures;

    protected Target currentTarget=null;

    protected Collider detectionZone;
    protected final double followRange;

    protected final ProjectileHandler projectileHandler;
    protected final int firingDelay;
    protected int remainingFiringDelay = 0;
    protected final double weaponSpread;

    public RangedCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, BufferedImage[] deploymentTextures, BufferedImage[] retractionTextures, BufferedImage[] attackTextures, double rotationSpeed, double maxHealth, int animationDuration, CubeHead cubeHead, int textureSize, ProjectileHandler projectileHandler, int firingDelay, double followRange, double weaponSpread) {
        super(position, texture, deadTexture, maxHealth, textureSize);
        this.lifeState = LifeState.CURRENTLY_IDLE;
        this.deploymentTextures = deploymentTextures;
        this.retractionTextures = retractionTextures;
        this.attackTextures = attackTextures;
        this.animationDuration = animationDuration;
        this.cubeHead = cubeHead;

        this.projectileHandler = projectileHandler;
        this.firingDelay = firingDelay;

        this.rotationSpeed = rotationSpeed;
        this.requiredAccuracy = Config.defaultCubeRequiredAccuracy;

        this.followRange = followRange;

        this.weaponSpread = weaponSpread;

        GameEngine.addAIdriven(this);
    }

    @Override
    protected void genColliders() {
        super.genColliders();
        detectionZone = new BoxCollider(
                new Vec2(-Config.cubeDetectionRange,-Config.cubeDetectionRange),
                new Vec2(Config.cubeDetectionRange,Config.cubeDetectionRange),
                false,
                1,
                new Vec2(),
                this,
                ColliderType.NONE_TRIGGER
        );
        PhysicEngine.addCollider(detectionZone, CollisionLayer.COLLISION_LAYER_ENEMIES);
        detectionZone.updateOffset();
    }

    protected void seekClosestTarget(){
        List<Target> targets = GameEngine.getTargets();
        if(targets.isEmpty())return;
        if(currentTarget == null)currentTarget=targets.getFirst();
        for(Target target : targets){
            if(target.isTargetable()&&Vec2.getSquareDistance(position,currentTarget.getPosition())>Vec2.getSquareDistance(position,target.getPosition())){
                currentTarget=target;
            }
            if(!currentTarget.isTargetable()&&target.isTargetable())currentTarget=target;
        }
    }

    protected boolean isNotValidTarget(){
        return (currentTarget == null) || Vec2.getSquareDistance(position, currentTarget.getPosition()) > followRange || !currentTarget.isTargetable();
    }

    private Vec2 getTargetPosition(){
        return Vec2.add(currentTarget.getPosition(),currentTarget.getVelocity());
    }

    protected double getTargetRotation() {
        Vec2 targetPosition=getTargetPosition();
        return (new Vec2(
                targetPosition.x- this.position.x,
                targetPosition.y- this.position.y
        ).getAngle()+5*Math.PI/2)%(2*Math.PI);
    }

    protected void computeNewRotation(){
        if(lifeState== LifeState.CURRENTLY_DEAD)return;
        double targetRotation = getTargetRotation();
        int angleSign;  double angleToTravel;

        angleToTravel=Math.abs(targetRotation-rotation);
        angleSign=((targetRotation-rotation>=0)?1:-1)*((angleToTravel<Math.PI)?1:-1);

        this.rotation+=angleSign*Math.min(angleToTravel,this.rotationSpeed)+2*Math.PI;
        this.rotation=this.rotation%(2*Math.PI);
    }

    protected boolean isRotationGood(){
        double targetRotation = getTargetRotation();
        double angleToTravel = targetRotation - rotation;
        double angleSign = ((targetRotation-rotation>=0)?1:-1)*((angleToTravel<Math.PI)?1:-1);
        return Math.abs(angleToTravel+angleSign*2*Math.PI)%(2*Math.PI) < requiredAccuracy;
    }

    protected void deployingState(){
        if(--remainingAnimationTime<0){
            lifeState= LifeState.CURRENTLY_PURSUING;
        }
    }

    protected void retractingState(){
        if(--remainingAnimationTime<0){
            rotation=0;
            lifeState= LifeState.CURRENTLY_IDLE;
        }
    }

    protected void pursuingState(){
        if(isNotValidTarget()){
            seekClosestTarget();
            if(isNotValidTarget()){
                remainingAnimationTime=animationDuration;
                lifeState= LifeState.CURRENTLY_RETRACTING;
                return;
            }
        }
        computeNewRotation();
        if(isRotationGood()){
            remainingAnimationTime=animationDuration;
            lifeState= LifeState.CURRENTLY_ATTACKING;
        }
    }

    protected void attackingState(){
        if(--remainingAnimationTime<0){
            lifeState= LifeState.CURRENTLY_PURSUING;
        }
        computeNewRotation();
        if(remainingFiringDelay<=0){
            remainingFiringDelay = firingDelay;
            projectileHandler.fireInDirection(position,rotation + weaponSpread*PseudoRandom.getRandomSpread());
        }
    }

    protected void disengagingState(){

    }

    @Override
    public void updateAI() {
        switch (lifeState){
            case CURRENTLY_DEPLOYING:
                deployingState();
                break;
            case CURRENTLY_RETRACTING:
                retractingState();
                break;
            case CURRENTLY_PURSUING:
                pursuingState();
                break;
            case CURRENTLY_ATTACKING:
                attackingState();
                break;
            case CURRENTLY_DISENGAGING:
                disengagingState();
                break;
        }
        currentAnimationFrame=(deploymentTextures.length-1)*(animationDuration-remainingAnimationTime)/animationDuration;
        if(remainingFiringDelay>0)remainingFiringDelay--;
    }

    @Override
    public double getRotation() {
        return rotation;
    }


    @Override
    public void startAI() {
        if(lifeState == LifeState.CURRENTLY_IDLE){
            remainingAnimationTime=animationDuration;
            lifeState= LifeState.CURRENTLY_DEPLOYING;
        }
    }

    @Override
    public void stopAI() {

    }

    @Override
    public boolean isAIenabled(){
        return (lifeState != LifeState.CURRENTLY_DEAD) && (lifeState != LifeState.CURRENTLY_IDLE);
    }

    @Override
    public BufferedImage getTexture() {
        return switch (lifeState){
            case CURRENTLY_DEAD -> deadTexture;
            case CURRENTLY_DEPLOYING -> deploymentTextures[currentAnimationFrame];
            case CURRENTLY_RETRACTING -> retractionTextures[currentAnimationFrame];
            case CURRENTLY_ATTACKING -> attackTextures[currentAnimationFrame];
            case CURRENTLY_PURSUING, CURRENTLY_DISENGAGING -> deploymentTextures[deploymentTextures.length-1];
            case CURRENTLY_IDLE -> texture;
            default -> {
                System.out.println("Warning : Resorting to default texture");
                yield texture;
            }
        };
    }

    @Override
    public void killYourself() {
        super.killYourself();
        PhysicEngine.removeCollider(detectionZone);
    }
}
