package main.rendering.sprites;

import main.physics.dynamic_objects.DynamicPoint;
import main.utils.RequiresUpdates;
import main.utils.containers.SizedTexture;
import main.utils.containers.SizedTextureArray;

public class AnimatedSprite extends Sprite implements RequiresUpdates {
    private final SizedTextureArray animationTextures;

    public AnimatedSprite(SizedTexture texture, DynamicPoint parent) {
        super(texture, parent);
    }

    @Override
    public void doUpdate() {

    }
}
