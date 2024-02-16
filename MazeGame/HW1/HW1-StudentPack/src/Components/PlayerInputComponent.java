package Components;

import Actors.AbstractActor;
import Actors.Bullet;
import Core.GameWindow;
import Util.AABB;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class PlayerInputComponent implements IRealTimeComponent, KeyListener {
    // Internal States
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;
    private boolean firePressed;
    private AABB player;
    private AABB bullet;
    public float pSpeed = 110;

    public String lastDirection;

    public AbstractActor newBullet;
    // TODO: Add additional properties if required

    // TODO: Implement a constructor
    private PlayerInputComponent(AABB input) {
        this.player = input;
        // TODO:
    }

    @Override
    public void update(float deltaT) {
        if (leftPressed) {
            player.getPos().x -= pSpeed * deltaT;
            this.lastDirection = "left";
        } else if (rightPressed) {
            player.getPos().x += pSpeed * deltaT;
            this.lastDirection = "right";
        } else if (upPressed) {
            player.getPos().y -= pSpeed * deltaT;
            this.lastDirection = "up";
        } else if (downPressed) {
            player.getPos().y += pSpeed * deltaT;
            this.lastDirection = "down";
        }
        if (firePressed) {
            //try {
            //    bullet = new Bullet(player.getPos(), player.getSizeX(), player.getSizeY(), lastDirection);
            //} catch (IOException e) {
            //    throw new RuntimeException(e);
        //}
        // TODO:
        }

    }

    @Override
    public void keyTyped(KeyEvent e) { /* Do nothing */ }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
        if(e.getKeyCode() == KeyEvent.VK_UP) upPressed = true;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = true;
        // TODO: You can also change this code if you want to handle inputs differently
        // this is given as a guideline to read key events
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_UP) upPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = false;
        // Enforce release operation on fire
        if(e.getKeyCode() == KeyEvent.VK_SPACE) firePressed = true;
        // TODO: You can also change this code if you want to handle inputs differently
        // this is given as a guideline to read key events
    }
    // Singleton Pattern
    private static PlayerInputComponent PIC;
    public static PlayerInputComponent GetInstance(AABB player)
    {
        PIC = new PlayerInputComponent(player);
        return PIC;
    }
}
