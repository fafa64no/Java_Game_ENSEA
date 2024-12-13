package main.rendering.sprites;

import main.game.GameEngine;
import main.game.level.target.Target;
import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.layers.RenderingLayer;
import main.utils.RequiresUpdates;
import main.utils.containers.SizedTextureArray;
import main.utils.data.Config;

import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements RequiresUpdates {
    private Target animationSource;

    private final int animationDuration;
    private int remainingAnimationDuration = 0;
    private int currentAnimationFrame = 0;

    private SizedTextureArray currentAnimation;

    private final SizedTextureArray deadAnimation;
    private final SizedTextureArray idleAnimation;
    private final SizedTextureArray deployingAnimation;
    private final SizedTextureArray retractingAnimation;
    private final SizedTextureArray pursuingAnimation;
    private final SizedTextureArray attackingAnimation;
    private final SizedTextureArray disengagingAnimation;
    private final SizedTextureArray retreatingAnimation;
    private final SizedTextureArray goingToAnimation;

    public AnimatedSprite(
            RenderingLayer renderingLayer,
            Target animationSource,
            int animationDuration,
            SizedTextureArray deadAnimation,
            SizedTextureArray idleAnimation,
            SizedTextureArray deployingAnimation,
            SizedTextureArray retractingAnimation,
            SizedTextureArray pursuingAnimation,
            SizedTextureArray attackingAnimation,
            SizedTextureArray disengagingAnimation,
            SizedTextureArray retreatingAnimation,
            SizedTextureArray goingToAnimation
    ) {
        super(null, renderingLayer);
        this.animationSource = animationSource;
        this.animationDuration = animationDuration;

        this.deadAnimation = deadAnimation;
        this.idleAnimation = idleAnimation;
        this.deployingAnimation = deployingAnimation;
        this.retractingAnimation = retractingAnimation;
        this.pursuingAnimation = pursuingAnimation;
        this.attackingAnimation = attackingAnimation;
        this.disengagingAnimation = disengagingAnimation;
        this.retreatingAnimation = retreatingAnimation;
        this.goingToAnimation = goingToAnimation;
    }

    public AnimatedSprite(
            RenderingLayer renderingLayer,
            Target animationSource,
            int animationDuration,
            SizedTextureArray deadAnimation,
            SizedTextureArray idleAnimation
    ) {
        super(null, renderingLayer);
        this.animationSource = animationSource;
        this.animationDuration = animationDuration;

        this.deadAnimation = deadAnimation;
        this.idleAnimation = idleAnimation;
        this.deployingAnimation = idleAnimation;
        this.retractingAnimation = idleAnimation;
        this.pursuingAnimation = idleAnimation;
        this.attackingAnimation = idleAnimation;
        this.disengagingAnimation = idleAnimation;
        this.retreatingAnimation = idleAnimation;
        this.goingToAnimation = idleAnimation;
    }

    public void setAnimationSource(Target animationSource) {
        if (this.animationSource == null) {
            this.animationSource = animationSource;
        } else {
            System.out.println("Trying to overwrite animationSource.");
        }
    }

    @Override
    public void addToRenderList() {
        super.addToRenderList();
        GameEngine.addRequiresUpdates(this);
    }

    @Override
    public void removeFromRenderList() {
        super.removeFromRenderList();
        GameEngine.removeRequiresUpdates(this);
    }

    @Override
    public BufferedImage getCurrentTexture() {
        if (currentAnimation == null) return idleAnimation.textures[currentAnimationFrame];
        return currentAnimation.textures[currentAnimationFrame];
    }

    public void switchAnimation() {
        currentAnimation = switch (animationSource.getLifeState()) {
            case LIFE_STATE_DEAD -> deadAnimation;
            case LIFE_STATE_DEPLOYING -> deployingAnimation;
            case LIFE_STATE_RETRACTING -> retractingAnimation;
            case LIFE_STATE_PURSUING -> pursuingAnimation;
            case LIFE_STATE_ATTACKING -> attackingAnimation;
            case LIFE_STATE_DISENGAGING -> disengagingAnimation;
            case LIFE_STATE_RETREATING -> retreatingAnimation;
            case LIFE_STATE_GOING_TO -> goingToAnimation;
            default -> idleAnimation;
        };
        remainingAnimationDuration = animationDuration;
        currentAnimationFrame = 0;
    }

    @Override
    public void doUpdate() {
        if (currentAnimation == null) return;
        remainingAnimationDuration -= Config.delayBetweenFrames;
        currentAnimationFrame = Math.round((float)
            (currentAnimation.textureCount - 1)
            * (animationDuration - remainingAnimationDuration)
            / animationDuration
        );
        if (remainingAnimationDuration <= 0 ) remainingAnimationDuration = animationDuration;
    }

    @Override
    public int getTextureSize() {
        return idleAnimation.textureSize;
    }
}
