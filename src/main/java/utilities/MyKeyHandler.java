package main.java.utilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyHandler extends KeyAdapter {
    private char direction = 'S'; // L, R, U, D, S


    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> this.direction = 'L';
            case KeyEvent.VK_RIGHT -> this.direction = 'R';
            case KeyEvent.VK_UP -> this.direction = 'U';
            case KeyEvent.VK_DOWN -> this.direction = 'D';
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.direction = 'S';
    }

    public char direction() {
        return this.direction;
    }
}
