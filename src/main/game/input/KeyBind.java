package main.game.input;

import java.awt.event.KeyEvent;

public enum KeyBind {
    KEY_FORWARD,
    KEY_BACKWARD,
    KEY_TURN_LEFT,
    KEY_TURN_RIGHT,
    KEY_STRAFE_LEFT,
    KEY_STRAFE_RIGHT,

    KEY_SWAP_TO_VEHICLE_1,
    KEY_SWAP_TO_VEHICLE_2,
    KEY_SWAP_TO_VEHICLE_3,
    KEY_SWAP_TO_VEHICLE_4,
    KEY_SWAP_TO_VEHICLE_5,
    KEY_SWAP_TO_VEHICLE_6,
    KEY_SWAP_TO_VEHICLE_7,
    KEY_SWAP_TO_VEHICLE_8,
    KEY_SWAP_TO_VEHICLE_9,
    KEY_SWAP_TO_VEHICLE_0,

    KEY_FIRE_SECONDARY,
    KEY_FIRE_TERTIARY,

    KEY_PAUSE,

    KEY_COUNT,

    KEY_UNKNOWN;

    public final static int[] keyBinds = new int[]{
            KeyEvent.VK_Z,
            KeyEvent.VK_S,
            KeyEvent.VK_Q,
            KeyEvent.VK_D,
            KeyEvent.VK_A,
            KeyEvent.VK_E,

            KeyEvent.VK_1,
            KeyEvent.VK_2,
            KeyEvent.VK_3,
            KeyEvent.VK_4,
            KeyEvent.VK_5,
            KeyEvent.VK_6,
            KeyEvent.VK_7,
            KeyEvent.VK_8,
            KeyEvent.VK_9,
            KeyEvent.VK_0,

            KeyEvent.VK_SPACE,
            KeyEvent.VK_F,

            KeyEvent.VK_ESCAPE
    };

    public static KeyBind getKeyBindFromKeyEvent(KeyEvent event) {
        KeyBind keyBind = KEY_UNKNOWN;
        for (int i =0; i < KEY_COUNT.ordinal(); i++) {
            if(keyBinds[i] == event.getKeyCode()) {
                keyBind = getKeyBind(i);
                break;
            }
        }
        return keyBind;
    }

    public static KeyBind getKeyBind(int i) {
        return switch (i) {
            case 0 -> KEY_FORWARD;
            case 1 -> KEY_BACKWARD;
            case 2 -> KEY_TURN_LEFT;
            case 3 -> KEY_TURN_RIGHT;
            case 4 -> KEY_STRAFE_LEFT;
            case 5 -> KEY_STRAFE_RIGHT;

            case 6 -> KEY_SWAP_TO_VEHICLE_1;
            case 7 -> KEY_SWAP_TO_VEHICLE_2;
            case 8 -> KEY_SWAP_TO_VEHICLE_3;
            case 9 -> KEY_SWAP_TO_VEHICLE_4;
            case 10 -> KEY_SWAP_TO_VEHICLE_5;
            case 11 -> KEY_SWAP_TO_VEHICLE_6;
            case 12 -> KEY_SWAP_TO_VEHICLE_7;
            case 13 -> KEY_SWAP_TO_VEHICLE_8;
            case 14 -> KEY_SWAP_TO_VEHICLE_9;
            case 15 -> KEY_SWAP_TO_VEHICLE_0;

            case 16-> KEY_FIRE_SECONDARY;
            case 17 -> KEY_FIRE_TERTIARY;

            case 18 -> KEY_PAUSE;

            default -> KEY_UNKNOWN;
        };
    }
}
