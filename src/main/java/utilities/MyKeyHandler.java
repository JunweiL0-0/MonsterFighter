package main.java.utilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This is a keyHandler which can monitor the arrow keys on the keyBoard and store the direction inside its 'direction' variable.
 */
public class MyKeyHandler extends KeyAdapter {
    private char direction = 'S'; // L, R, U, D, S

    /**
     * Get and store the direction into the variable 'direction' when a keyPressed event happens.
     *
     * @param e a keyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> this.direction = 'L';
            case KeyEvent.VK_RIGHT -> this.direction = 'R';
            case KeyEvent.VK_UP -> this.direction = 'U';
            case KeyEvent.VK_DOWN -> this.direction = 'D';
        }
    }

    /**
     * Reset the direction of variable 'direction' when a keyReleased event happends.
     *
     * @param e a keyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        this.direction = 'S';
    }

    /**
     * Return the current direction.
     *
     * @return the current direction.
     */
    public char direction() {
        return this.direction;
    }
}
