package main.utils.containers;

import main.utils.vectors.IVec2;

import java.awt.image.BufferedImage;

public class SizedTextureArray {
    public final int textureSize;
    public final int textureCount;
    public final BufferedImage[] textures;

    public SizedTextureArray(int textureSize, int textureCount, BufferedImage tileMap, IVec2[] positionsInTileMap) {
        this.textureSize = textureSize;
        this.textureCount = textureCount;
        this.textures = new BufferedImage[textureCount];

        for(int i = 0; i < textureCount; i++) {
            this.textures[i] = tileMap.getSubimage(
                textureSize * positionsInTileMap[i].x,
                textureSize * positionsInTileMap[i].y,
                textureSize,
                textureSize
            );
        }
    }
}
