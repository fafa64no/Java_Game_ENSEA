package main.utils.containers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileMap {
    private final BufferedImage texture;
    private final int textureSize;

    public TileMap(int textureSize, String texturePath) {
        this.textureSize = textureSize;
        try {
            texture = ImageIO.read(new File("assets/textures/"+texturePath));
        } catch (IOException e) {
            System.out.println("ERROR : No texture found at : "+"assets/textures/"+texturePath);
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getTextureAt(int x, int y) {
        return texture.getSubimage(
                x * textureSize,
                y * textureSize,
                textureSize,
                textureSize
        );
    }

    public int getTextureSize() {
        return textureSize;
    }
}
