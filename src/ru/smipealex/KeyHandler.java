package ru.smipealex;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private MovementTo movementTo = MovementTo.RIGHT;

    public MovementTo getVector(){
        return movementTo;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A -> {if(movementTo!= MovementTo.RIGHT)movementTo = MovementTo.LEFT; }
            case KeyEvent.VK_D -> {if(movementTo!= MovementTo.LEFT)movementTo = MovementTo.RIGHT; }
            case KeyEvent.VK_W -> {if(movementTo!= MovementTo.BACK)movementTo = MovementTo.FORWARD; }
            case KeyEvent.VK_S -> {if(movementTo!= MovementTo.FORWARD)movementTo = MovementTo.BACK; }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
