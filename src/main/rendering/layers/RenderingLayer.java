package main.rendering.layers;

public enum RenderingLayer {
    RENDERING_LAYER_HUD_TOP,
    RENDERING_LAYER_HUD_MIDDLE,
    RENDERING_LAYER_HUD_BOTTOM,

    RENDERING_LAYER_FLYING_TOP,
    RENDERING_LAYER_FLYING_MIDDLE,
    RENDERING_LAYER_FLYING_BOTTOM,

    RENDERING_LAYER_LEAVES_TOP,
    RENDERING_LAYER_LEAVES_MIDDLE,
    RENDERING_LAYER_LEAVES_BOTTOM,

    RENDERING_LAYER_GROUND_TOP,
    RENDERING_LAYER_GROUND_MIDDLE,
    RENDERING_LAYER_GROUND_BOTTOM,

    RENDERING_LAYER_TERRAIN_TOP,
    RENDERING_LAYER_TERRAIN_MIDDLE,
    RENDERING_LAYER_TERRAIN_BOTTOM,

    RENDERING_LAYER_COUNT,

    RENDERING_LAYER_UNKNOWN;

    public static RenderingLayer getRenderingLayer(int i) {
        return switch (i) {
            case 0 -> RENDERING_LAYER_HUD_TOP;
            case 1 -> RENDERING_LAYER_HUD_MIDDLE;
            case 2 -> RENDERING_LAYER_HUD_BOTTOM;

            case 3 -> RENDERING_LAYER_FLYING_TOP;
            case 4 -> RENDERING_LAYER_FLYING_MIDDLE;
            case 5 -> RENDERING_LAYER_FLYING_BOTTOM;

            case 6 -> RENDERING_LAYER_LEAVES_TOP;
            case 7 -> RENDERING_LAYER_LEAVES_MIDDLE;
            case 8 -> RENDERING_LAYER_LEAVES_BOTTOM;

            case 9  -> RENDERING_LAYER_GROUND_TOP;
            case 10 -> RENDERING_LAYER_GROUND_MIDDLE;
            case 11 -> RENDERING_LAYER_GROUND_BOTTOM;

            case 12 -> RENDERING_LAYER_TERRAIN_TOP;
            case 13 -> RENDERING_LAYER_TERRAIN_MIDDLE;
            case 14 -> RENDERING_LAYER_TERRAIN_BOTTOM;

            default -> {
                System.out.println("Unknown rendering layer");
                yield RENDERING_LAYER_UNKNOWN;
            }
        };
    }
}
