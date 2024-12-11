package main.game.input;

import main.utils.vectors.Vec3;

import java.awt.event.*;

public class InputManager implements KeyListener, MouseListener, MouseWheelListener {
    private final Vec3 currentInputDir = new Vec3();

    private int lastVehicleRequested = 0;

    private final boolean[] abilitiesTriggered = new boolean[AbilityType.ABILITY_COUNT.ordinal()];

    private boolean pauseGotToggled = false;
    private boolean isZooming = false;
    private int lastScrollFactor = 0;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (KeyBind.getKeyBindFromKeyEvent(e)) {
            case KEY_FORWARD -> currentInputDir.y -= 1;
            case KEY_BACKWARD -> currentInputDir.y += 1;
            case KEY_TURN_LEFT -> currentInputDir.x -= 1;
            case KEY_TURN_RIGHT -> currentInputDir.x += 1;
            case KEY_STRAFE_LEFT -> currentInputDir.z -= 1;
            case KEY_STRAFE_RIGHT -> currentInputDir.z += 1;

            case KEY_SWAP_TO_VEHICLE_1 -> lastVehicleRequested = 1;
            case KEY_SWAP_TO_VEHICLE_2 -> lastVehicleRequested = 2;
            case KEY_SWAP_TO_VEHICLE_3 -> lastVehicleRequested = 3;
            case KEY_SWAP_TO_VEHICLE_4 -> lastVehicleRequested = 4;
            case KEY_SWAP_TO_VEHICLE_5 -> lastVehicleRequested = 5;
            case KEY_SWAP_TO_VEHICLE_6 -> lastVehicleRequested = 6;
            case KEY_SWAP_TO_VEHICLE_7 -> lastVehicleRequested = 7;
            case KEY_SWAP_TO_VEHICLE_8 -> lastVehicleRequested = 8;
            case KEY_SWAP_TO_VEHICLE_9 -> lastVehicleRequested = 9;
            case KEY_SWAP_TO_VEHICLE_0 -> lastVehicleRequested = 0;

            case KEY_FIRE_SECONDARY -> abilitiesTriggered[AbilityType.ABILITY_SECONDARY_FIRE.ordinal()] = true;
            case KEY_FIRE_TERTIARY -> abilitiesTriggered[AbilityType.ABILITY_TERTIARY_FIRE.ordinal()] = true;

            case KEY_PAUSE -> pauseGotToggled = true;
        }

        currentInputDir.x = Math.clamp(currentInputDir.x,-1,1);
        currentInputDir.y = Math.clamp(currentInputDir.y,-1,1);
        currentInputDir.z = Math.clamp(currentInputDir.z,-1,1);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (KeyBind.getKeyBindFromKeyEvent(e)) {
            case KEY_FORWARD -> currentInputDir.y += 1;
            case KEY_BACKWARD -> currentInputDir.y -= 1;
            case KEY_TURN_LEFT -> currentInputDir.x += 1;
            case KEY_TURN_RIGHT -> currentInputDir.x -= 1;
            case KEY_STRAFE_LEFT -> currentInputDir.z += 1;
            case KEY_STRAFE_RIGHT -> currentInputDir.z -= 1;
        }

        currentInputDir.x = Math.clamp(currentInputDir.x,-1,1);
        currentInputDir.y = Math.clamp(currentInputDir.y,-1,1);
        currentInputDir.z = Math.clamp(currentInputDir.z,-1,1);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()){
            case 1:     // Left Click
                abilitiesTriggered[AbilityType.ABILITY_PRIMARY_FIRE.ordinal()] = true;
                break;
            case 2:     // Right Click
                isZooming = true;
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()){
            case 1:     // Left Click
                break;
            case 2:     // Right Click
                isZooming = false;
                break;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        lastScrollFactor = e.getWheelRotation();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean didPauseGetToggled() {
        if (pauseGotToggled) {
            pauseGotToggled = false;
            return true;
        } else {
            return false;
        }
    }

    public int getLastVehicleRequested() {
        return lastVehicleRequested;
    }

    public boolean[] getAbilitiesTriggered() {
        boolean[] output = new boolean[AbilityType.ABILITY_COUNT.ordinal()];
        for (int i = 0; i < AbilityType.ABILITY_COUNT.ordinal(); i++) {
            output[i] = abilitiesTriggered[i];
            abilitiesTriggered[i] = false;
        }
        return output;
    }

    public int getLastScrollFactor() {
        int output = lastScrollFactor;
        lastScrollFactor = 0;
        return output;
    }

    public Vec3 getCurrentInputDir() {
        return currentInputDir;
    }
}
