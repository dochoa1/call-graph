package edu.macalester.comp124.breakout;

import acm.graphics.*;

import java.awt.*;

/**
 * Bar object is a GRect with horizontal moving capabilities.
 */
public class Bar extends GRect {


    private static final double BAR_WIDTH = 100;
    private static final double BAR_HEIGHT = 15;
    private static final int BAR_Speed = 30;
    private double x;
    private double y;

    public Bar(double x, double y) {
        super(x, y, BAR_WIDTH, BAR_HEIGHT);
        this.x = x;
        this.y = y;
        setFilled(true);
        setColor(Color.BLUE);
    }


    public Bar() {
        this(0, 0);
    }




    /**
     * Sets the horizontal position x of the upper left corner of bar to x.
     * @param x horizontal position
     */
    public void setX(double x) {
        this.x = x;
    }


    /**
     * Sets the vertical position y of the upper left corner of bar to y.
     * @param y vertical position
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * Moves the bar to the right.
     */
    public void moveRight() {
        move(BAR_Speed, 0);
    }


    /**
     * Moves the bar to the left.
     */
    public void moveLeft() {
        move(-BAR_Speed, 0);
    }


    /**
     * String displaying position of bar.
     * @return String displaying the values of the x and y instance variables.
     */
    @Override
    public String toString() {
        return ("Bar at (" + x + ", " + y + ")");
    }


    /**
     * Tests whether bar objects are equal
     * @param other
     * @return boolean true if bar are equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Bar)){
            return false;
        }
        Bar bar = (Bar) other;
        return (bar.x == this.x && bar.y == this.y);
    }

}