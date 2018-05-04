package edu.macalester.comp124.breakout;

import acm.graphics.GCompound;
import acm.graphics.GObject;

import java.awt.*;

/**
 * BrickWall is a GCompound made up of bricks.
 */
public class BrickWall extends GCompound {



    public static final int BRICKS_HORIZONTAL = 10;
    public static final int BRICKS_VERTICAL = 10;
    public static final double BRICK_SPACE = 2; // Pixel length of horizontal space in between bricks.
    private int bricksRemaining = BRICKS_HORIZONTAL * BRICKS_VERTICAL;

    public BrickWall() {

        for (int y = 0; y < BRICKS_VERTICAL; y++) {
            for (int x = 0; x < BRICKS_HORIZONTAL; x++) {
                Brick newBrick = new Brick(x * (Brick.BRICK_WIDTH + BRICK_SPACE), y * (Brick.BRICK_HEIGHT + BRICK_SPACE));
                if (y < 2) {
                    newBrick.setColor(Color.RED);
                } else if (y >= 2 && y < 4) {
                    newBrick.setColor(Color.ORANGE);
                } else if (y >= 4 && y < 6) {
                    newBrick.setColor(Color.YELLOW);
                } else if (y >= 6 && y < 8) {
                    newBrick.setColor(Color.GREEN);
                } else {
                    newBrick.setColor(Color.CYAN);
                }
                add(newBrick);
            }
        }

    }


    /**
     * Gets the value of bricksRemaining.
     * @return bricksRemaining the amount of bricks remaining
     */
    public int getBricksRemaining() {
        return this.bricksRemaining;
    }


    /**
     * Takes a position and removes the element at that position.
     * Then this method decrements the brickRemaining instance variable
     * @param x horizontal position
     * @param y vertical position
     */
    public void removeBrick(double x, double y) {
        y -= BreakoutProgram.TOP_SPACE; // Subtract Top Space to convert window coordinate to BrickWall coordinates.
        try {GObject brick = getElementAt(x, y);
            remove(brick);
            bricksRemaining -=1;
        } catch (NullPointerException e) {
        }
    }


    /**
     * Takes a coordinate position and returns true if there is an element on the canvas at that position.
     * @param x horizontal coordinate on canvas
     * @param y vertical coordinate on canvas
     * @return true if there is an element on the canvas at the specified position.
     */
    public boolean checkBricks(double x, double y) {
        y -= BreakoutProgram.TOP_SPACE; // Subtract Top Space to convert window coordinate to BrickWall coordinates.
        GObject brick = getElementAt(x, y);
        return brick != null;
    }




    /**
     * String displaying the amount of bricks remaining.
     * @return String displaying the value of the instance variable bricksRemaining.
     */
    @Override
    public String toString() {
        return ("BrickWall with " + bricksRemaining + "  bricks remaining.");
    }

    /**
     * Tests whether BrickWall objects are equal.
     * For the purposes of my program, two BrickWall objects are equal if they have the same amount of bricksRemaining.
     * @param other
     * @return boolean true if walls are equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof BrickWall)){
            return false;
        }
        BrickWall wall = (BrickWall) other;
        return (wall.bricksRemaining == this.bricksRemaining);
    }



}



