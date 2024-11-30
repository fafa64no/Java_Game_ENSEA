package main.utils.containers;

import main.utils.vectors.IVec2;

import java.awt.image.BufferedImage;

public class SizedTexture {
    public final int textureSize;
    public final BufferedImage texture;

    public SizedTexture(int textureSize, BufferedImage tileMap, IVec2 positionsInTileMap) {
        this.textureSize = textureSize;
        this.texture = tileMap.getSubimage(
                textureSize * positionsInTileMap.x,
                textureSize * positionsInTileMap.y,
                textureSize,
                textureSize
        );
    }

    public SizedTextureArray toArray() {
        return new SizedTextureArray(
                textureSize,
                1,
                texture,
                new IVec2[]{new IVec2()}
        );
    }
}
