package main.utils.data;

import main.utils.vectors.IVec2;

public class TextureMapping {

    /*      ################ - SizedTextures - ################      */

    public final static IVec2 blueBarTexturePosition = new IVec2(0,0);
    public final static IVec2 yellowBarTexturePosition = new IVec2(0,1);
    public final static IVec2 redBarTexturePosition = new IVec2(0,2);
    public final static IVec2 whiteBarTexturePosition = new IVec2(0,3);

    public final static IVec2 cubeSpawnTexturePosition = new IVec2(1,4);
    public final static IVec2 borderTexturePosition = new IVec2(0,4);

    public final static IVec2 tankShellTexturePosition = new IVec2(0,0);
    public final static IVec2 machineGunBulletTexturePosition = new IVec2(1,0);

    public final static IVec2 basicCubeTexturePosition = new IVec2(0,0);
    public final static IVec2 basicCubeDeadTexturePosition = new IVec2(1,0);
    public final static IVec2 machineGunCubeTexturePosition = new IVec2(0,1);
    public final static IVec2 machineGunCubeDeadTexturePosition = new IVec2(7,1);
    public final static IVec2 machineGunWheelsCubeTexturePosition = new IVec2(0,2);
    public final static IVec2 machineGunWheelsCubeDeadTexturePosition = new IVec2(7,2);
    public final static IVec2 beaconCubeTexturePosition = new IVec2(2,0);
    public final static IVec2 beaconCubeDeadTexturePosition = new IVec2(7,0);
    public final static IVec2 fighterCubeTexturePosition = new IVec2(0,3);
    public final static IVec2 fighterCubeDeadTexturePosition = new IVec2(1,3);
    public final static IVec2 artilleryCubeTexturePosition = new IVec2(0,0);
    public final static IVec2 artilleryCubeDeadTexturePosition = new IVec2(3,3);

    /*      ################ - SizedTexturesArray - ################      */

    public final static IVec2[] piercingMetalVfxTexturePositions = new IVec2[]{
            new IVec2(0,0),
            new IVec2(1,0),
            new IVec2(2,0),
            new IVec2(3,0)
    };
    public final static IVec2[] electricVfxTexturePositions = new IVec2[]{
            new IVec2(0,0),
            new IVec2(1,0),
            new IVec2(2,0),
            new IVec2(3,0)
    };
    public final static IVec2[] explosionVfxTexturePositions = new IVec2[]{
            new IVec2(0,1),
            new IVec2(1,1),
            new IVec2(2,1),
            new IVec2(3,1)
    };

    public final static IVec2[] artilleryShellTexturePositions = new IVec2[]{
            new IVec2(0,1),
            new IVec2(1,1),
            new IVec2(2,1),
            new IVec2(3,1)
    };

    public final static IVec2[] machineGunCubeDeploymentTexturePositions = new IVec2[]{
            new IVec2(0,1),
            new IVec2(1,1),
            new IVec2(2,1),
            new IVec2(3,1),
            new IVec2(4,1),
            new IVec2(5,1)
    };
    public final static IVec2[] machineGunCubeRetractionTexturePositions = new IVec2[]{
            new IVec2(5,1),
            new IVec2(4,1),
            new IVec2(3,1),
            new IVec2(2,1),
            new IVec2(1,1),
            new IVec2(0,1)
    };
    public final static IVec2[] machineGunCubeAttackTexturePositions = new IVec2[]{
            new IVec2(6,1),
            new IVec2(5,1),
            new IVec2(6,1),
            new IVec2(5,1),
            new IVec2(6,1),
            new IVec2(5,1)
    };
    public final static IVec2[] machineGunWheelsCubeDeploymentTexturePositions = new IVec2[]{
            new IVec2(0,2),
            new IVec2(1,2),
            new IVec2(2,2),
            new IVec2(3,2),
            new IVec2(4,2),
            new IVec2(5,2)
    };
    public final static IVec2[] machineGunWheelsCubeRetractionTexturePositions = new IVec2[]{
            new IVec2(5,2),
            new IVec2(4,2),
            new IVec2(3,2),
            new IVec2(2,2),
            new IVec2(1,2),
            new IVec2(0,2)
    };
    public final static IVec2[] machineGunWheelsCubeAttackTexturePositions = new IVec2[]{
            new IVec2(6,3),
            new IVec2(5,3),
            new IVec2(6,3),
            new IVec2(5,3),
            new IVec2(6,3),
            new IVec2(5,3)
    };
    public final static IVec2[] beaconCubeDeploymentTexturePositions = new IVec2[]{
            new IVec2(2,0),
            new IVec2(3,0),
            new IVec2(4,0),
            new IVec2(5,0),
            new IVec2(6,0),
            new IVec2(6,0)
    };
    public final static IVec2[] beaconCubeRetractionTexturePositions = new IVec2[]{
            new IVec2(6,0),
            new IVec2(6,0),
            new IVec2(5,0),
            new IVec2(4,0),
            new IVec2(3,0),
            new IVec2(2,0)
    };
    public final static IVec2[] beaconCubeAttackTexturePositions = new IVec2[]{
            new IVec2(6,0),
            new IVec2(6,0),
            new IVec2(5,0),
            new IVec2(5,0),
            new IVec2(6,0),
            new IVec2(6,0)
    };
    public final static IVec2[] fighterCubeDeploymentTexturePositions = new IVec2[]{
            new IVec2(0,3),
            new IVec2(1,3),
            new IVec2(2,3),
            new IVec2(3,3),
            new IVec2(4,3),
            new IVec2(5,3)
    };
    public final static IVec2[] fighterCubeRetractionTexturePositions = new IVec2[]{
            new IVec2(5,3),
            new IVec2(4,3),
            new IVec2(3,3),
            new IVec2(2,3),
            new IVec2(1,3),
            new IVec2(0,3)
    };
    public final static IVec2[] fighterCubeAttackTexturePositions = new IVec2[]{
            new IVec2(6,3),
            new IVec2(5,3),
            new IVec2(6,3),
            new IVec2(5,3),
            new IVec2(6,3),
            new IVec2(5,3)
    };
    public final static IVec2[] artilleryCubeDeploymentTexturePositions = new IVec2[]{
            new IVec2(1,0),
            new IVec2(2,0),
            new IVec2(3,0),
            new IVec2(0,1),
            new IVec2(1,1),
            new IVec2(2,1),
            new IVec2(3,1),
            new IVec2(0,2)
    };
    public final static IVec2[] artilleryCubeRetractionTexturePositions = new IVec2[]{
            new IVec2(0,2),
            new IVec2(3,1),
            new IVec2(2,1),
            new IVec2(1,1),
            new IVec2(0,1),
            new IVec2(3,0),
            new IVec2(2,0),
            new IVec2(1,0)
    };
    public final static IVec2[] artilleryCubeAttackTexturePositions = new IVec2[]{
            new IVec2(2,1),
            new IVec2(3,1),
            new IVec2(3,1),
            new IVec2(0,2),
            new IVec2(0,2),
            new IVec2(0,2),
            new IVec2(0,2),
            new IVec2(0,2)
    };


    /*      ################ - SizedTexturesMatrix - ################      */

    public final static IVec2[][] grassTexturePositions = new IVec2[][]{
            new IVec2[]{
                    new IVec2(0,0),
                    new IVec2(1,0),
                    new IVec2(2,0),
                    new IVec2(3,0)
            },
            new IVec2[]{
                    new IVec2(4,0),
                    new IVec2(5,0),
                    new IVec2(6,0),
                    new IVec2(7,0)
            },
            new IVec2[]{
                    new IVec2(8,0),
                    new IVec2(9,0),
                    new IVec2(10,0),
                    new IVec2(11,0)
            },
            new IVec2[]{
                    new IVec2(12,0),
                    new IVec2(13,0),
                    new IVec2(14,0),
                    new IVec2(15,0)
            },
            new IVec2[]{
                    new IVec2(16,0),
                    new IVec2(17,0),
                    new IVec2(18,0),
                    new IVec2(19,0)
            },
            new IVec2[]{
                    new IVec2(20,0),
                    new IVec2(21,0),
                    new IVec2(22,0),
                    new IVec2(23,0)
            },
            new IVec2[]{
                    new IVec2(24,0),
                    new IVec2(25,0),
                    new IVec2(26,0),
                    new IVec2(27,0)
            },
            new IVec2[]{
                    new IVec2(28,0),
                    new IVec2(29,0),
                    new IVec2(30,0),
                    new IVec2(31,0)
            }
    };
    public final static IVec2[][] stoneTexturePositions = new IVec2[][]{
            new IVec2[]{
                    new IVec2(0,1),
                    new IVec2(1,1),
                    new IVec2(2,1),
                    new IVec2(3,1)
            },
            new IVec2[]{
                    new IVec2(4,1),
                    new IVec2(5,1),
                    new IVec2(6,1),
                    new IVec2(7,1)
            },
            new IVec2[]{
                    new IVec2(8,1),
                    new IVec2(9,1),
                    new IVec2(10,1),
                    new IVec2(11,1)
            },
            new IVec2[]{
                    new IVec2(12,1),
                    new IVec2(13,1),
                    new IVec2(14,1),
                    new IVec2(15,1)
            },
            new IVec2[]{
                    new IVec2(16,1),
                    new IVec2(17,1),
                    new IVec2(18,1),
                    new IVec2(19,1)
            },
            new IVec2[]{
                    new IVec2(20,1),
                    new IVec2(21,1),
                    new IVec2(22,1),
                    new IVec2(23,1)
            },
            new IVec2[]{
                    new IVec2(24,1),
                    new IVec2(25,1),
                    new IVec2(26,1),
                    new IVec2(27,1)
            },
            new IVec2[]{
                    new IVec2(28,1),
                    new IVec2(29,1),
                    new IVec2(30,1),
                    new IVec2(31,1)
            }
    };
    public final static IVec2[][] treeTexturePositions = new IVec2[][]{
            new IVec2[]{
                    new IVec2(0,2)
            },
            new IVec2[]{
                    new IVec2(1,2)
            },
            new IVec2[]{
                    new IVec2(2,2)
            },
            new IVec2[]{
                    new IVec2(3,2)
            },
            new IVec2[]{
                    new IVec2(4,2)
            },
            new IVec2[]{
                    new IVec2(5,2)
            },
            new IVec2[]{
                    new IVec2(6,2)
            },
            new IVec2[]{
                    new IVec2(7,2)
            }
    };
    public final static IVec2[][] pathTexturesPositions = new IVec2[][]{
            new IVec2[]{
                    new IVec2(0,3),
                    new IVec2(1,3),
                    new IVec2(2,3),
                    new IVec2(3,3)
            },
            new IVec2[]{
                    new IVec2(4,3),
                    new IVec2(5,3),
                    new IVec2(6,3),
                    new IVec2(7,3)
            },
            new IVec2[]{
                    new IVec2(8,3),
                    new IVec2(9,3),
                    new IVec2(10,3),
                    new IVec2(11,3)
            },
            new IVec2[]{
                    new IVec2(12,3),
                    new IVec2(13,3),
                    new IVec2(14,3),
                    new IVec2(15,3)
            },
            new IVec2[]{
                    new IVec2(16,3),
                    new IVec2(17,3),
                    new IVec2(18,3),
                    new IVec2(19,3)
            },
            new IVec2[]{
                    new IVec2(20,3),
                    new IVec2(21,3),
                    new IVec2(22,3),
                    new IVec2(23,3)
            },
            new IVec2[]{
                    new IVec2(24,3),
                    new IVec2(25,3),
                    new IVec2(26,3),
                    new IVec2(27,3)
            },
            new IVec2[]{
                    new IVec2(28,3),
                    new IVec2(29,3),
                    new IVec2(30,3),
                    new IVec2(31,3)
            }
    };
    public final static IVec2[][] leavesTexturePositions = new IVec2[][]{
            new IVec2[]{
                    new IVec2(0,0),
                    new IVec2(1,0),
                    new IVec2(2,0),
                    new IVec2(3,0)
            }
    };
}
