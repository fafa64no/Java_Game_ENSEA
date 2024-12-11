package main.utils.containers;

import main.utils.vectors.IVec2;

import java.awt.image.BufferedImage;

public class SizedTextureMatrix {
    public final int textureSize;
    public final IVec2 textureCount;
    public final BufferedImage[][] textures;

    public SizedTextureMatrix(TileMap tileMap, IVec2[][] positionsInTileMap) {
        this.textureSize = tileMap.getTextureSize();
        this.textureCount = new IVec2(
                positionsInTileMap[0].length,
                positionsInTileMap.length
        );
        this.textures = new BufferedImage[textureCount.y][textureCount.x];

        for(int x = 0; x < textureCount.x; x++) {
            for(int y = 0; y < textureCount.y; y++) {
                this.textures[y][x] = tileMap.getTextureAt(
                    positionsInTileMap[y][x].x,
                    positionsInTileMap[y][x].y
                );
            }
        }
    }
}
