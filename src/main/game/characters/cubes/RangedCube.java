package main.game.characters.cubes;

import main.game.GameEngine;
import main.game.characters.AIdriven;
import main.game.characters.LifeStates;
import main.game.characters.Target;
import main.game.projectiles.MachineGunBullet;
import main.game.projectiles.ProjectileHandler;
import main.physics.ColliderType;
import main.physics.CollisionLayers;
import main.physics.PhysicEngine;
import main.physics.colliders.BoxCollider;
import main.physics.colliders.Collider;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;
import java.util.List;

public class RangedCube extends BasicCube implements AIdriven {
    private final int animationDuration=10;
    private int remainingAnimationTime=0;
    private int currentAnimationFrame=0;

    private double rotation=0;
    private final double rotationSpeed;
    private final double requiredAccuracy;

    private final BufferedImage[] deploymentTextures;
    private final BufferedImage[] retractionTextures;
    private final BufferedImage[] attackTextures;

    private Target currentTarget=null;

    private final Collider detectionZone;

    private final ProjectileHandler projectileHandler = MachineGunBullet.getInstance();

    public RangedCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, BufferedImage[] deploymentTextures, BufferedImage[] retractionTextures, BufferedImage[] attackTextures) {
        super(position, texture, deadTexture);
        this.lifeState = LifeStates.CURRENTLY_IDLE;
        this.deploymentTextures = deploymentTextures;
        this.retractionTextures = retractionTextures;
        this.attackTextures = attackTextures;

        this.rotationSpeed = 0.05;
        this.requiredAccuracy = 0.1;

        detectionZone = new BoxCollider(
                new Vec2(-Config.cubeDetectionRange,-Config.cubeDetectionRange),
                new Vec2(Config.cubeDetectionRange,Config.cubeDetectionRange),
                false,
                1,
                new Vec2(),
                this,
                ColliderType.NONE_TRIGGER
        );

        GameEngine.addAIdriven(this);
        PhysicEngine.addCollider(detectionZone, CollisionLayers.COLLISION_LAYER_ENNEMIES);

        detectionZone.setOffset();
    }

    private void seekClosestTarget(){
        List<Target> targets = GameEngine.getTargets();
        if(targets.isEmpty())return;
        if(currentTarget == null)currentTarget=targets.getFirst();
        for(Target target : targets){
            if(Vec2.getSquareDistance(position,currentTarget.getPosition())>Vec2.getSquareDistance(position,target.getPosition())){
                currentTarget=target;
            }
        }
    }

    private boolean isNotValidTarget(){
        return (currentTarget == null) || (!(Vec2.getSquareDistance(position, currentTarget.getPosition()) < Config.cubeSquareFollowRange));
    }

    private Vec2 getTargetPosition(){
        return Vec2.add(currentTarget.getPosition(),currentTarget.getVelocity());
    }

    private double getTargetRotation() {
        Vec2 targetPosition=getTargetPosition();
        return (new Vec2(
                targetPosition.x- this.position.x,
                targetPosition.y- this.position.y
        ).getAngle()+5*Math.PI/2)%(2*Math.PI);
    }

    private void computeNewRotation(){
        if(lifeState==LifeStates.CURRENTLY_DEAD)return;
        double targetRotation = getTargetRotation();
        int angleSign;  double angleToTravel;

        angleToTravel=Math.abs(targetRotation-rotation);
        angleSign=((targetRotation-rotation>=0)?1:-1)*((angleToTravel<Math.PI)?1:-1);

        this.rotation+=angleSign*Math.min(angleToTravel,this.rotationSpeed)+2*Math.PI;
        this.rotation=this.rotation%(2*Math.PI);
    }

    private boolean isRotationGood(){
        double targetRotation = getTargetRotation();
        double angleToTravel = targetRotation - rotation;
        double angleSign = ((targetRotation-rotation>=0)?1:-1)*((angleToTravel<Math.PI)?1:-1);
        return Math.abs(angleToTravel+angleSign*2*Math.PI)%(2*Math.PI) < requiredAccuracy;
    }

    @Override
    public double getRotation() {
        return rotation;
    }


    @Override
    public void startAI() {
        if(lifeState == LifeStates.CURRENTLY_IDLE){
            remainingAnimationTime=animationDuration;
            lifeState=LifeStates.CURRENTLY_DEPLOYING;
        }
    }

    @Override
    public void stopAI() {

    }

    @Override
    public void updateAI() {
        switch (lifeState){
            case CURRENTLY_DEPLOYING:
                if(--remainingAnimationTime<0){
                    lifeState=LifeStates.CURRENTLY_PURSUING;
                }
                break;
            case CURRENTLY_RETRACTING:
                if(--remainingAnimationTime<0){
                    rotation=0;
                    lifeState=LifeStates.CURRENTLY_IDLE;
                }
                break;
            case CURRENTLY_PURSUING:
                if(isNotValidTarget()){
                    seekClosestTarget();
                    if(isNotValidTarget()){
                        remainingAnimationTime=animationDuration;
                        lifeState=LifeStates.CURRENTLY_RETRACTING;
                        break;
                    }
                }
                computeNewRotation();
                if(isRotationGood()){
                    remainingAnimationTime=animationDuration;
                    lifeState=LifeStates.CURRENTLY_ATTACKING;
                }
                break;
            case CURRENTLY_ATTACKING:
                if(--remainingAnimationTime<0){
                    lifeState=LifeStates.CURRENTLY_PURSUING;
                }
                computeNewRotation();
                projectileHandler.fireInDirection(position,rotation);
                break;
        }
        currentAnimationFrame=(deploymentTextures.length-1)*(animationDuration-remainingAnimationTime)/animationDuration;
    }

    @Override
    public boolean isAIenabled(){
        return (lifeState != LifeStates.CURRENTLY_DEAD) && (lifeState != LifeStates.CURRENTLY_IDLE);
    }

    @Override
    public BufferedImage getTexture() {
        return switch (lifeState){
            case CURRENTLY_DEAD -> deadTexture;
            case CURRENTLY_DEPLOYING -> deploymentTextures[currentAnimationFrame];
            case CURRENTLY_RETRACTING -> retractionTextures[currentAnimationFrame];
            case CURRENTLY_ATTACKING -> attackTextures[currentAnimationFrame];
            case CURRENTLY_PURSUING -> deploymentTextures[deploymentTextures.length-1];
            default -> texture;
        };
    }

    @Override
    public void killYourself() {
        super.killYourself();
        PhysicEngine.removeCollider(detectionZone);
    }
}
