package edu.macalester.comp124.breakout;

import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.*;

/**
 * Brick object is a GRect.
 */
public class Brick extends GRect {

    public static final double BRICK_WIDTH = 50;
    public static final double BRICK_HEIGHT = 20;
    private double x;
    private double y;

    public Brick(double x, double y) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        this.x = x;
        this.y = y;
        setFilled(true);
        setColor(Color.RED);
    }

    /**
     * Sets the horizontal position x of the upper left corner of bar to x.
     * @param x horizontal position
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the vertical position y of the upper left corner of the brick to y.
     * @param y vertical position
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * String displaying position of brick.
     * @return String displaying the values of the x and y instance variables.
     */
    @Override
    public String toString() {
        return ("Brick at (" + x + ", " + y + ")");
    }


    /**
     * Tests whether brick objects are equal
     * @param other
     * @return boolean true if bricks are equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Brick)){
            return false;
        }
        Brick brick = (Brick) other;
        return (brick.x == this.x && brick.y == this.y);
    }

}
