package main.rendering.vfx;

import main.game.GameEngine;
import main.physics.dynamic_objects.NoControlDynamicPoint;
import main.rendering.layers.RenderingLayer;
import main.rendering.sprites.Sprite;
import main.utils.RequiresUpdates;
import main.utils.containers.SizedTextureArray;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public class Vfx extends Sprite implements RequiresUpdates {
    protected SizedTextureArray textures;

    protected final int animationDuration;
    protected int remainingAnimationTime;
    protected int currentAnimationFrame;

    public Vfx(
            Vec2 initialPosition,
            VfxType vfxType,
            int animationDuration,
            RenderingLayer renderingLayer
    ) {
        super(
            null,
            new NoControlDynamicPoint(
                initialPosition,
                new Vec2(),
                0
            ),
            renderingLayer
        );
        this.textures = VfxType.getCorrespondingTexture(vfxType);
        this.animationDuration = animationDuration;
        this.remainingAnimationTime = animationDuration;

        this.addToRenderList();
        parent.addToPhysicsEngine();
        GameEngine.addRequiresUpdates(this);
    }

    public Vfx(
            Vec2 initialPosition,
            VfxType vfxType,
            int animationDuration,
            RenderingLayer renderingLayer,
            double initialVelocity,
            double initialRotation
    ) {
        super(
            null,
            new NoControlDynamicPoint(
                initialPosition,
                new Vec2(0,initialVelocity).rotateBy(initialRotation),
                initialRotation
            ),
            renderingLayer
        );
        this.textures = VfxType.getCorrespondingTexture(vfxType);
        this.animationDuration = animationDuration;
        this.remainingAnimationTime = animationDuration;

        this.addToRenderList();
        parent.addToPhysicsEngine();
        GameEngine.addRequiresUpdates(this);
    }

    public void updateRemainingTime(){
    }

    public BufferedImage getCurrentTexture(){
        return textures.textures[currentAnimationFrame];
    }

    @Override
    public int getTextureSize() {
        return textures.textureSize;
    }

    @Override
    public void doUpdate() {
        remainingAnimationTime -= Config.delayBetweenFrames;
        if(remainingAnimationTime<0){
            parent.removeFromRemovePhysicsEngine();
            this.removeFromRenderList();
        }else{
            currentAnimationFrame=(textures.textureCount-1)
                * (animationDuration-remainingAnimationTime)
                / animationDuration;
        }
    }
}
