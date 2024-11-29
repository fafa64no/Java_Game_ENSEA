package main.utils.containers;

import main.utils.vectors.IVec2;

import java.awt.image.BufferedImage;

public class SizedTextureMatrix {
    public final int textureSize;
    public final IVec2 textureCount;
    public final BufferedImage[][] textures;

    public SizedTextureMatrix(int textureSize, IVec2 textureCount, BufferedImage tileMap, IVec2[][] positionsInTileMap) {
        this.textureSize = textureSize;
        this.textureCount = textureCount;
        this.textures = new BufferedImage[textureCount.y][textureCount.x];

        for(int x = 0; x < textureCount.x; x++) {
            for(int y = 0; y < textureCount.y; y++){
                this.textures[y][x] = tileMap.getSubimage(
                    textureSize * positionsInTileMap[y][x].x,
                    textureSize * positionsInTileMap[y][x].y,
                    textureSize,
                    textureSize
                );
            }
        }
    }
}
