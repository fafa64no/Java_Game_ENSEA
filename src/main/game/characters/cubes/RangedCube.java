package main.game.characters.cubes;

import main.game.GameEngine;
import main.game.characters.AIdriven;
import main.game.characters.LifeStates;
import main.physics.ColliderType;
import main.physics.CollisionLayers;
import main.physics.PhysicEngine;
import main.physics.colliders.BoxCollider;
import main.physics.colliders.Collider;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public class RangedCube extends BasicCube implements AIdriven {
    private final int animationDuration=100;
    private int remainingAnimationTime=0;
    private int currentAnimationFrame=0;

    private final BufferedImage[] deploymentTextures;
    private final BufferedImage[] retractionTextures;
    private final BufferedImage[] attackTextures;

    private final Collider detectionZone;
    public RangedCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, BufferedImage[] deploymentTextures, BufferedImage[] retractionTextures, BufferedImage[] attackTextures) {
        super(position, texture, deadTexture);
        this.lifeState = LifeStates.CURRENTLY_IDLE;
        this.deploymentTextures = deploymentTextures;
        this.retractionTextures = retractionTextures;
        this.attackTextures = attackTextures;

        detectionZone = new BoxCollider(
                new Vec2(-20,-20),
                new Vec2(20,20),
                false,
                1,
                new Vec2(),
                this,
                ColliderType.NONE_TRIGGER
        );

        GameEngine.addAIdriven(this);
        PhysicEngine.addCollider(detectionZone, CollisionLayers.COLLISION_LAYER_ENNEMIES);
    }

    @Override
    public void startAI() {
        if(lifeState == LifeStates.CURRENTLY_IDLE){
            lifeState=LifeStates.CURRENTLY_DEPLOYING;
            remainingAnimationTime=animationDuration;
        }
    }

    @Override
    public void stopAI() {

    }

    @Override
    public void updateAI() {
        switch (lifeState){
            case CURRENTLY_DEPLOYING:
                System.out.println("Deploying");
                if(--remainingAnimationTime<0)lifeState=LifeStates.CURRENTLY_PURSUING;
                break;
            case CURRENTLY_RETRACTING:
                System.out.println("Retracting");
                if(--remainingAnimationTime<0)lifeState=LifeStates.CURRENTLY_IDLE;
                break;
            case CURRENTLY_PURSUING:
                System.out.println("Pursuing");
                break;
            case CURRENTLY_ATTACKING:
                System.out.println("Attacking");
                if(--remainingAnimationTime<0)lifeState=LifeStates.CURRENTLY_PURSUING;
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
