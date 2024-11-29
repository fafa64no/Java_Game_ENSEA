package main.rendering.vfx;

import main.utils.containers.SizedTextureArray;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public class Vfx {
    public final Vec2 position;
    public final int textureSize;

    private final SizedTextureArray textures;

    private final int animationDuration;
    private int remainingAnimationTime;
    private int currentAnimationFrame;

    private final int idInVfxManager;

    public Vfx(Vec2 position, SizedTextureArray textures, int animationDuration) {
        this.position = position;
        this.textureSize = textures.textureSize;
        this.textures = textures;
        this.animationDuration = animationDuration;
        this.remainingAnimationTime = animationDuration;
        this.idInVfxManager = VfxManager.addVfx(this);
    }

    public void updateRemainingTime(){
        if(--remainingAnimationTime<0){
            VfxManager.removeVfx(idInVfxManager);
        }else{
            currentAnimationFrame=(textures.textureCount-1)*(animationDuration-remainingAnimationTime)/animationDuration;
        }
    }

    public BufferedImage getCurrentTexture(){
        return textures.textures[currentAnimationFrame];
    }
}
