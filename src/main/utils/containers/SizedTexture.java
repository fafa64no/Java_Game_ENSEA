package main.utils.containers;

import main.utils.vectors.IVec2;

import java.awt.image.BufferedImage;

public class SizedTexture {
    public final int textureSize;
    public final BufferedImage texture;

    public SizedTexture(TileMap tileMap, IVec2 positionsInTileMap) {
        this.textureSize = tileMap.getTextureSize();
        this.texture = tileMap.getTextureAt(
                positionsInTileMap.x,
                positionsInTileMap.y
        );
    }

    public SizedTextureArray toArray() {
        return new SizedTextureArray(
                textureSize,
                texture,
                new IVec2[]{new IVec2()}
        );
    }
}
