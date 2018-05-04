package edu.macalester.comp124.breakout;

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.*;


/**
 * Ball object is a GOval with moving capabilities.
 */
public class Ball extends GOval {

    private static final double SIZE = 15; // Diameter of Ball
    private static final int VELOCITY = 6;
    private static final int PAUSE_TIME = 15;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private RandomGenerator rgen = new RandomGenerator();

    public Ball(double x, double y) {
        super(x, y, SIZE, SIZE);
        this.x = x;
        this.y = y;
        setFilled(true);
        setColor(Color.WHITE);
    }


    public Ball() { this(-50, 0); } // Set ball at an arbitrary point off screen when constructed.


    /**
     * Sets the horizontal position of the ball.
     * @param x horizontal position
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the vertical position of the ball.
     * @param y vertical position
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * Sets the horizontal displacement dx to dx.
     * @param dx horizontal displacement
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Sets the vertical displacement dy to dy.
     * @param dy vertical displacement
     */
    public void setDy(double dy) {
        this.dy = dy;
    }


    /**
     * Gets the current horizontal displacement.
     * @return dx horizontal displacement.
     */
    public double getDx() {
        return dx;
    }


    /**
     * Gets the current vertical displacement.
     * @return dy vertical displacement.
     */
    public double getDy() { return dy; }


    /**
     * Sets the horizontal displacement to be positive.
     * This method is called instead of switchDx at times because if used correctly,
     * it ensures that the ball will not get stuck in a brick for an infinite period of time,
     */
    public void setDxPositive() {
        setDx(Math.abs(getDx()));
    }

    /**
     * Sets the horizontal displacement to be negative.
     * This method is called instead of switchDx at times because if used correctly,
     * it ensures that the ball will not get stuck in a brick for an infinite period of time,
     */
    public void setDxNegative() {
        setDx(-Math.abs(getDx()));
    }

    /**
     * Sets the vertical displacement to be positive.
     *  This method is called instead of switchDy at times because if used correctly,
     * it ensures that the ball will not get stuck in a brick for an infinite period of time,
     */
    public void setDyPositive() { setDy(Math.abs(getDy())); }


    /**
     * Sets the vertical displacement to be negative.
     * This method is called instead of switchDy at times because if used correctly,
     * it ensures that the ball will not get stuck in a brick for an infinite period of time,
     */
    public void setDyNegative() {
        setDy(-Math.abs(getDy()));
    }



    /**
     * Switches the sign of dx.
     */
    public void switchDx() {
        dx = -dx;
    }


    /**
     * Switches the sign of dy.
     */
    public void switchDy() {
        dy = -dy;
    }


    /**
     * Gives the ball an initial displacement based on the velocity named constant.
     * This displacement will initially make the ball move upwards so that the player can have more time to adjust.
     */
    public void startBall() {
        setDy(-(rgen.nextInt(VELOCITY - 2, VELOCITY - 1))); // values of dy close to the velocity ensure the ball will move more vertically than horizontally.
        if (rgen.nextBoolean()) { // Ball moves right
            setDx(Math.sqrt((Math.pow(VELOCITY, 2) - Math.pow(dy, 2)))); // This is calculated using the Pythagorean theorem.
        } else { // Ball moves left
            setDx(-Math.sqrt((Math.pow(VELOCITY, 2) - Math.pow(dy, 2))));
        }
    }


    /**
     * Moves the ball according to dx and dy and pauses for a short period of time.
     */
    public void move() {
        move(dx, dy);
        pause(PAUSE_TIME);
    }

    /**
     * String displaying position and displacement of ball.
     * @return String displaying the values of the x, y, dx, and dy instance variables.
     */
    @Override
    public String toString() {
        return ("Ball at (" + x + ", " + y + ") with a displacement vector <" + dx + ", " + dy + ">");
    }

    /**
     * Tests whether ball objects are equal
     * @param other
     * @return boolean true if balls are equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Ball)){
            return false;
        }
        Ball ball = (Ball) other;
        return (ball.x == this.x && ball.y == this.y && ball.dx == this.dx && ball.dy == this.dy);
    }



}
