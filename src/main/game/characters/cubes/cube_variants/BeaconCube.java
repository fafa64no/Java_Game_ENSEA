package main.game.characters.cubes.cube_variants;

import main.game.characters.Target;
import main.game.characters.cubes.CubeHead;
import main.game.characters.cubes.SummoningCube;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.Vec2;

public class BeaconCube extends SummoningCube {
    public BeaconCube(Vec2 position) {
        super(
                position,
                DataGen.getBeaconCubeTexture(),
                DataGen.getBeaconCubeDeadTexture(),
                DataGen.getBeaconCubeDeploymentTextures(),
                DataGen.getBeaconCubeRetractionTextures(),
                DataGen.getBeaconCubeAttackTextures(),
                0,
                Config.beaconCubeHealth,
                10,
                CubeHead.CUBE_HEAD_BEACON,
                Config.largeTileSize,
                500,
                Config.artillerySquareFollowRange,
                6,
                100,
                CubeHead.CUBE_HEAD_FIGHTER
        );
    }

    public BeaconCube(Vec2 position, Target target) {
        super(
                position,
                DataGen.getBeaconCubeTexture(),
                DataGen.getBeaconCubeDeadTexture(),
                DataGen.getBeaconCubeDeploymentTextures(),
                DataGen.getBeaconCubeRetractionTextures(),
                DataGen.getBeaconCubeAttackTextures(),
                0,
                Config.beaconCubeHealth,
                10,
                CubeHead.CUBE_HEAD_BEACON,
                Config.largeTileSize,
                500,
                Config.artillerySquareFollowRange,
                6,
                60,
                CubeHead.CUBE_HEAD_MACHINE_GUN_WHEELS
        );

        this.currentTarget = target;
        super.startAI();
    }
}
