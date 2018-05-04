package edu.macalester.comp124.breakout;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import java.awt.*;
import java.awt.event.*;


/**
 * Main GraphicsProgram for the breakout game described
 * in exercise 10.10 in the Roberts Textbook.
 */
public class BreakoutProgram extends GraphicsProgram {


    public static final double TOP_SPACE = 70;
    public static final double BOTTOM_SPACE = 70;
    public static final double MIDDLE_SPACE = 300;
    private static final int LIVES = 3;
    private Ball ball;
    private BrickWall wall;
    private Bar bar;
    private int lives;
    private GLabel label;


    public void init() {

        lives = LIVES;

        setSize((int) (BrickWall.BRICKS_HORIZONTAL * (Brick.BRICK_WIDTH + BrickWall.BRICK_SPACE)), (int) (BOTTOM_SPACE + MIDDLE_SPACE + TOP_SPACE + BrickWall.BRICKS_VERTICAL * (Brick.BRICK_HEIGHT + BrickWall.BRICK_SPACE)));
        setBackground(Color.BLACK);

        wall = new BrickWall();
        add(wall, 0, TOP_SPACE);

        ball = new Ball();
        add(ball);


        bar = new Bar();
        bar.setLocation(getWidth()/2 - bar.getWidth()/2, getHeight() - TOP_SPACE); // Add bar on the bottom of screen in the middle.
        add(bar);

        addKeyListeners();
    }


    public void run() {

        while (true) {

            label = new GLabel("You have " + lives + " lives remaining.", getWidth() / 6, getHeight() / 2);
            if (lives == 1) {
                label.setLabel("You have " + lives + " life remaining.");
            }
            label.setFont("Helvetica-30");
            label.setColor(Color.WHITE);
            add(label);
            pause(3000); // Allow player to read lives remaining statement.
            remove(label);
            ball.setLocation(getWidth() / 2, getHeight() - BOTTOM_SPACE - (MIDDLE_SPACE / 2)); // Put ball in middle of screen.
            ball.startBall();

            while (true) {

                ball.move();

                checkLeftWall();
                checkRightWall();
                checkTopWall();

                if (checkBottomWall()) {
                    lives -= 1;
                    break;
                }
                checkBrickWall();
                checkPaddle();

                if (wall.getBricksRemaining() == 0) {
                    label.setLabel("You Win!");
                    label.setFont("Helvetica-80");
                    add(label);
                    pause(3000); // Allow player to read statement.
                    System.exit(0);
                }
            }

            if (lives < 0) { // Let user play with 0 lives because most video games do this and it gives user another chance.
                label.setLabel("You Lose!");
                label.setFont("Helvetica-60");
                add(label);
                pause(3000);
                System.exit(0);
            }
        }
    }


    /**
     * Moves the bar left and right when the KeyEvents left arrow and right arrow are pressed.
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (bar.getX() > 0) { // Make sure bar doesn't go off screen left.
                    bar.moveLeft();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (bar.getX() + bar.getWidth() < getWidth()) { // Make sure bar doesn't go off screen right.
                    bar.moveRight();
                }
                break;
        }
    }


    /**
     * Checks if bar contains ball and then creates an array of boolean values for if each corner of the ball is in the bar.
     * It then passes this array to switchDirect which changes the direction of the ball accordingly.
     */
    public void checkPaddle() {
        if (containsBall(bar)) {
            double x = ball.getX();
            double y = ball.getY();

            boolean[] corners = new boolean[4];
            corners[0] = checkPaddleBall(x, y); // Upper Left
            corners[1] = checkPaddleBall(x + ball.getWidth(), y); // Upper Right
            corners[2] = checkPaddleBall(x, y + ball.getHeight()); // Lower Left
            corners[3] = checkPaddleBall(x + ball.getWidth(), y + ball.getHeight()); // Lower Right

            switchDirect(corners);
        }
    }


    /**
     * Takes a coordinate position and returns true if there is an element on the canvas at that position.
     * @param x horizontal coordinate on canvas
     * @param y vertical coordinate on canvas
     * @return true if there is an element on the canvas at the specified position.
     */
    public boolean checkPaddleBall(double x, double y) {
        GObject bar1 = getElementAt(x, y);
        return (bar1 != null);
    }



    /**
     * Checks to see if ball has hit left wall and changes the direction of the ball accordingly.
     */
    public void checkLeftWall() {
        if (ball.getX() <= 0) {
            ball.setX(0); // Ensures ball doesn't get stuck outside of screen.
            ball.setDxPositive(); // Sets the x displacement to be positive.
        }
    }

    /**
     * Checks to see if ball has hit right wall and changes the direction of the ball accordingly.
     */
    public void checkRightWall() {
        if (ball.getX() + ball.getWidth() >= getWidth()) {
            ball.setX(getWidth() - ball.getWidth()); // Ensures ball doesn't get stuck outside of screen.
            ball.setDxNegative(); // Sets the x displacement to be negative
        }
    }

    /**
     * Checks to see if ball has hit top wall and changes the direction of the ball accordingly.
     */
    public void checkTopWall() {
        if (ball.getY() <= 0) {
            ball.setY(0); // Ensures ball doesn't get stuck outside of screen.
            ball.setDyPositive(); // Sets the y displacement to be negative.
        }
    }


    /**
     * Checks to see if ball has hit bottom wall.
     * @return true if ball has hit bottom wall.
     */
    public boolean checkBottomWall() {
        return (ball.getY() + ball.getHeight() >= getHeight());
    }



    /**
     * Checks if wall contains ball and then creates an array of boolean values for if each corner of the ball is in a brick.
     * It then passes this array to switchDirect which changes the direction of the ball accordingly.
     * Then it removes the bricks that each corner of the ball intersects.
     */
    public void checkBrickWall() {
        if (containsBall(wall)) {
            double x = ball.getX();
            double y = ball.getY();

            boolean[] corners = new boolean[4];
            corners[0] = wall.checkBricks(x, y); // Upper Left
            corners[1] = wall.checkBricks(x + ball.getWidth(), y); // Upper Right
            corners[2] = wall.checkBricks(x, y + ball.getHeight()); // Lower Left
            corners[3] = wall.checkBricks(x + ball.getWidth(), y + ball.getHeight()); // Lower Right

            switchDirect(corners);

            wall.removeBrick(x,y);
            wall.removeBrick(x + ball.getWidth(), y);
            wall.removeBrick(x, y + ball.getHeight());
            wall.removeBrick(x + ball.getWidth(), y + ball.getHeight());

        }
    }


    /**
     * Changes the direction of the ball based on what corners (if any) of the ball are at a position where there is an element on the canvas.
     * @param corners boolean array holding the boolean value of the position being at a place where there is an element on the canvas for each corner of the ball.
     */
    private void switchDirect(boolean[] corners) {

        int trues = 0;
        for (boolean corner : corners) {
            if (corner) {
                trues++;
            }
        }

        if (trues > 2) {
            ball.switchDy();
            ball.switchDx();
        } else if (trues == 2) {
            switchDirectTwoCorners(corners);
        } else if (trues == 1) {
            switchDirectOneCorner(corners);
        }
    }


    /**
     * Changes the direction of the ball when one corner is in an element on the canvas.
     * This method calls setDxPositive or similar methods instead of switching the displacement to avoid bugs where a ball gets stuck in the paddle or a brick.
     * This also lets the player reverse the direction of the ball by letting it hit the paddle so that only one corner is in the paddle.
     * This makes gameplay more fun as the player has more control.
     * @param corners boolean array holding the boolean value of the position being at a place where there is an element on the canvas for each corner of the ball.
     */
    private void switchDirectOneCorner(boolean[] corners) {
        if (corners[0]) {
            ball.setDxPositive();
            ball.setDyPositive();
        } else if (corners[1]) {
            ball.setDxNegative();
            ball.setDyPositive();
        } else if (corners[2]) {
            ball.setDxPositive();
            ball.setDyNegative();
        } else {
            ball.setDxNegative();
            ball.setDyNegative();
        }
    }



    /**
     * Changes the direction of the ball when two corners are in an element on the canvas.
     * This method calls setDxPositive or similar methods at times instead of switching the displacement to avoid bugs where a ball gets stuck in the paddle or a brick.
     * @param corners boolean array holding the boolean value of the position being at a place where there is an element on the canvas for each corner of the ball.
     */
    private void switchDirectTwoCorners(boolean[] corners) {
        if (corners[0] && corners[1])  {
            ball.setDyPositive();
        } else if (corners[2] && corners[3]) {
            ball.setDyNegative();
        } else if (corners[0] && corners[2]) {
            ball.setDxPositive();
        } else if (corners[1] && corners[3]) {
            ball.setDxNegative();
        } else {
            ball.switchDy();
            ball.switchDx();
        }
    }




    /**
     * Takes a GObject and returns if the object contains any corner of the ball.
     * @param object object in canvas.
     * @return true if object contains any corner of the ball.
     */
    public boolean containsBall(GObject object) {
        return (object.contains(ball.getX(), ball.getY()) || object.contains(ball.getX() + ball.getWidth(), ball.getY()) || object.contains(ball.getX(), ball.getY() + ball.getHeight()) || object.contains(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight()));
    }

}