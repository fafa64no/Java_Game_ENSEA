package main.game.characters.cubes.cubetypes;

import main.game.characters.cubes.CubeHead;
import main.game.characters.cubes.RangedCube;
import main.game.projectiles.MachineGunBullet;
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
                MachineGunBullet.getInstance(),
                50
        );
    }
}
