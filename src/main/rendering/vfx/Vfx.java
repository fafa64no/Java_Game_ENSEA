package main.rendering.vfx;

import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public class Vfx {
    public final Vec2 position;
    public final int textureSize;

    private final BufferedImage[] textures;
    private final int animationDuration;
    private final int idInVfxManager;

    private int remainingAnimationTime;
    private int currentAnimationFrame;

    public Vfx(Vec2 position, int textureSize, BufferedImage[] textures, int animationDuration) {
        this.position = position;
        this.textureSize = textureSize;
        this.textures = textures;
        this.animationDuration = animationDuration;
        this.remainingAnimationTime = animationDuration;
        this.idInVfxManager = VfxManager.addVfx(this);
    }

    public void updateRemainingTime(){
        if(--remainingAnimationTime<0){
            VfxManager.removeVfx(idInVfxManager);
        }else{
            currentAnimationFrame=(textures.length-1)*(animationDuration-remainingAnimationTime)/animationDuration;
        }
    }

    public BufferedImage getCurrentTexture(){
        return textures[currentAnimationFrame];
    }
}
