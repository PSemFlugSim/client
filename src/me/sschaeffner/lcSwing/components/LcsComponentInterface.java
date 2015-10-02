package me.sschaeffner.lcSwing.components;

import me.sschaeffner.lcSwing.LcsPanel;

import java.awt.*;

/**
 * @author sschaeffner
 */
public interface LcsComponentInterface {

    /**
     * Returns the component's top left corner's x coordinate.
     *
     * @return  the component's top left corner's x coordinate
     */
    int getX();

    /**
     * Sets the component's top left corner's x coordinate.
     *
     * @param x   component's top left corner's x coordinate
     */
    void setX(int x);

    /**
     * Returns the component's top left corner's y coordinate.
     *
     * @return  the component's top left corner's y coordinate
     */
    int getY();

    /**
     * Sets the component's top left corner's y coordinate.
     *
     * @param y   component's top left corner's y coordinate
     */
    void setY(int y);

    /**
     * Returns the component's width.
     *
     * @return  component's width.
     */
    int getWidth();

    /**
     * Sets the component's width.
     *
     * @param width component's width
     */
    void setWidth(int width);

    /**
     * Returns the component's height.
     *
     * @return  component's height
     */
    int getHeight();

    /**
     * Sets the component's height.
     *
     * @param height    component's height
     */
    void setHeight(int height);

    void paint(Graphics2D g, boolean editMode, int panelX, int panelY);

    /**
     * Returns the component's visibility.
     *
     * @return component's visibility
     */
    boolean isVisible();

    /**
     * Sets the component's visibility.
     *
     * This calls a repaint on its parent LcsPanel if it is set.
     *
     * @param visible   component's visibility
     */
    void setVisible(boolean visible);

    /**
     * Sets the component's parent panel.
     *
     * This panel is called to repaint on a change in the component.
     *
     * @param parentPanel   component's parent panel
     */
    void setParentPanel(LcsPanel parentPanel);
}
