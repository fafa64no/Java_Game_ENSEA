package main.game.characters.cubes.cube_variants;

import main.game.characters.Target;
import main.game.characters.cubes.CubeHead;
import main.game.characters.cubes.RangedCube;
import main.game.projectiles.ArtilleryShell;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.Vec2;

public class ArtilleryCube extends RangedCube {

    public ArtilleryCube(Vec2 position) {
        super(
                position,
                DataGen.getArtilleryCubeTexture(),
                DataGen.getArtilleryCubeDeadTexture(),
                DataGen.getArtilleryCubeDeploymentTextures(),
                DataGen.getArtilleryCubeRetractionTextures(),
                DataGen.getArtilleryCubeAttackTextures(),
                0.05,
                Config.artilleryCubeHealth,
                30,
                CubeHead.CUBE_HEAD_ARTILLERY,
                Config.veryLargeTileSize,
                ArtilleryShell.getInstance(),
                30,
                Config.artillerySquareFollowRange,
                0.5
        );
    }

    public ArtilleryCube(Vec2 position, Target target) {
        super(
                position,
                DataGen.getArtilleryCubeTexture(),
                DataGen.getArtilleryCubeDeadTexture(),
                DataGen.getArtilleryCubeDeploymentTextures(),
                DataGen.getArtilleryCubeRetractionTextures(),
                DataGen.getArtilleryCubeAttackTextures(),
                0.05,
                Config.artilleryCubeHealth,
                30,
                CubeHead.CUBE_HEAD_ARTILLERY,
                Config.veryLargeTileSize,
                ArtilleryShell.getInstance(),
                30,
                Config.artillerySquareFollowRange,
                0.5
        );

        this.currentTarget = target;
        super.startAI();
    }
}
