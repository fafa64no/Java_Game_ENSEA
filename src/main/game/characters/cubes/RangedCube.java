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

    protected double rotation=0;
    private final double rotationSpeed;
    private final double requiredAccuracy;

    private final BufferedImage[] deploymentTextures;
    private final BufferedImage[] retractionTextures;
    private final BufferedImage[] attackTextures;

    protected Target currentTarget=null;

    protected final Collider detectionZone;

    private final ProjectileHandler projectileHandler = MachineGunBullet.getInstance();

    public RangedCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, BufferedImage[] deploymentTextures, BufferedImage[] retractionTextures, BufferedImage[] attackTextures) {
        super(position, texture, deadTexture, Config.rangedCubeHealth);
        this.lifeState = LifeStates.CURRENTLY_IDLE;
        this.deploymentTextures = deploymentTextures;
        this.retractionTextures = retractionTextures;
        this.attackTextures = attackTextures;

        this.rotationSpeed = 0.05;
        this.requiredAccuracy = Config.defaultCubeRequiredAccuracy;

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

    public RangedCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, BufferedImage[] deploymentTextures, BufferedImage[] retractionTextures, BufferedImage[] attackTextures, double rotationSpeed, double maxHealth) {
        super(position, texture, deadTexture, maxHealth);
        this.lifeState = LifeStates.CURRENTLY_IDLE;
        this.deploymentTextures = deploymentTextures;
        this.retractionTextures = retractionTextures;
        this.attackTextures = attackTextures;

        this.rotationSpeed = rotationSpeed;
        this.requiredAccuracy = Config.defaultCubeRequiredAccuracy;

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
            if(target.isTargetable()&&Vec2.getSquareDistance(position,currentTarget.getPosition())>Vec2.getSquareDistance(position,target.getPosition())){
                currentTarget=target;
            }
            if(!currentTarget.isTargetable()&&target.isTargetable())currentTarget=target;
        }
    }

    private boolean isNotValidTarget(){
        return (currentTarget == null) || Vec2.getSquareDistance(position, currentTarget.getPosition()) > Config.cubeSquareFollowRange || !currentTarget.isTargetable();
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

    protected void deployingState(){
        if(--remainingAnimationTime<0){
            lifeState=LifeStates.CURRENTLY_PURSUING;
        }
    }

    protected void retractingState(){
        if(--remainingAnimationTime<0){
            rotation=0;
            lifeState=LifeStates.CURRENTLY_IDLE;
        }
    }

    protected void pursuingState(){
        if(isNotValidTarget()){
            seekClosestTarget();
            if(isNotValidTarget()){
                remainingAnimationTime=animationDuration;
                lifeState=LifeStates.CURRENTLY_RETRACTING;
                return;
            }
        }
        computeNewRotation();
        if(isRotationGood()){
            remainingAnimationTime=animationDuration;
            lifeState=LifeStates.CURRENTLY_ATTACKING;
        }
    }

    protected void attackingState(){
        if(--remainingAnimationTime<0){
            lifeState=LifeStates.CURRENTLY_PURSUING;
        }
        computeNewRotation();
        projectileHandler.fireInDirection(position,rotation);
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
        }
        currentAnimationFrame=(deploymentTextures.length-1)*(animationDuration-remainingAnimationTime)/animationDuration;
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
