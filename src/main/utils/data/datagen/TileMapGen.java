package main.utils.data.datagen;

import main.utils.containers.TileMap;
import main.utils.data.TextureConfig;

public class TileMapGen {
    private static boolean didTileMapsGetGenerated = false;

    private static TileMap smallTerrainTiles;
    private static TileMap largeTerrainTiles;

    private static TileMap smallShells;

    private static TileMap largeHudTiles;

    private static TileMap smallVfxTiles;
    private static TileMap largeVfxTiles;

    private static TileMap mediumCubes;
    private static TileMap largeCubes;

    private static TileMap mediumTanks;

    private static void checkTileMapExistence() {
        if(didTileMapsGetGenerated) return;
        genAllTileMaps();
        didTileMapsGetGenerated = true;
    }

    private static void genAllTileMaps() {
        smallTerrainTiles = new TileMap(TextureConfig.smallTileSize, "level/smallTiles.png");
        largeTerrainTiles  = new TileMap(TextureConfig.largeTileSize, "level/largeTiles.png");

        smallShells  = new TileMap(TextureConfig.smallTileSize, "projectiles/smallShells.png");

        largeHudTiles  = new TileMap(TextureConfig.largeTileSize, "hud/largeTiles.png");

        smallVfxTiles  = new TileMap(TextureConfig.smallTileSize, "vfx/smallVFX.png");
        largeVfxTiles  = new TileMap(TextureConfig.largeTileSize, "vfx/largeVFX.png");

        mediumCubes  = new TileMap(TextureConfig.largeTileSize, "cubes/mediumCubes.png");
        largeCubes  = new TileMap(TextureConfig.veryLargeTileSize, "cubes/largeCubes.png");

        mediumTanks  = new TileMap(TextureConfig.veryLargeTileSize, "vehicles/mediumTanksTiles.png");
    }

    /*      #############################################      */
    /*                                                         */
    /*      ###         -      Getters      -         ###      */
    /*                                                         */
    /*      #############################################      */

    public static TileMap getSmallTerrainTiles() {
        checkTileMapExistence();
        return smallTerrainTiles;
    }

    public static TileMap getLargeTerrainTiles() {
        checkTileMapExistence();
        return largeTerrainTiles;
    }

    public static TileMap getSmallShells() {
        checkTileMapExistence();
        return smallShells;
    }

    public static TileMap getLargeHudTiles() {
        checkTileMapExistence();
        return largeHudTiles;
    }

    public static TileMap getSmallVfxTiles() {
        checkTileMapExistence();
        return smallVfxTiles;
    }

    public static TileMap getLargeVfxTiles() {
        checkTileMapExistence();
        return largeVfxTiles;
    }

    public static TileMap getMediumCubes() {
        checkTileMapExistence();
        return mediumCubes;
    }

    public static TileMap getLargeCubes() {
        checkTileMapExistence();
        return largeCubes;
    }

    public static TileMap getMediumTanks() {
        checkTileMapExistence();
        return mediumTanks;
    }
}
