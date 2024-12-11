package main.rendering.specific_renderers;

import main.game.GameEngine;
import main.rendering.layers.RenderingLayer;
import main.rendering.sprites.Sprite;
import main.utils.RequiresUpdates;

public class SpriteRenderer implements RequiresUpdates {
    private final SpriteLayerRenderer[] spriteLayerRenderers
            = new SpriteLayerRenderer[RenderingLayer.RENDERING_LAYER_COUNT.ordinal()];

    public SpriteRenderer() {
        for (int i = 0; i < spriteLayerRenderers.length; i++) {
            spriteLayerRenderers[i] = new SpriteLayerRenderer(RenderingLayer.getRenderingLayer(i));
        }
        GameEngine.addRequiresUpdates(this);
    }

    public void addSpriteToRenderList(Sprite sprite, RenderingLayer renderingLayer) {
        spriteLayerRenderers[renderingLayer.ordinal()].addSprite(sprite);
    }

    public void removeSprite(Sprite sprite) {
        for (SpriteLayerRenderer spriteLayerRenderer : spriteLayerRenderers) {
            spriteLayerRenderer.removeSprite(sprite);
        }
    }

    public void load() {
        for (SpriteLayerRenderer spriteLayerRenderer : spriteLayerRenderers) {
            spriteLayerRenderer.addToRenderList();
        }
    }

    public void unLoad() {
        for (SpriteLayerRenderer spriteLayerRenderer : spriteLayerRenderers) {
            spriteLayerRenderer.removeFromRenderList();
        }
    }

    @Override
    public void doUpdate() {
        for (SpriteLayerRenderer spriteLayerRenderer : spriteLayerRenderers) {
            spriteLayerRenderer.flushList();
        }
    }
}
