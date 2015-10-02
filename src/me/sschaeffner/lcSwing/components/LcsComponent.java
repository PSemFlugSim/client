package me.sschaeffner.lcSwing.components;

import me.sschaeffner.lcSwing.LcsPanel;

import java.awt.*;

/**
 * A component to be drawn on a LcsPanel.
 *
 * @author sschaeffner
 */
public abstract class LcsComponent implements LcsComponentInterface{
    private int x, y, width, height;
    private LcsPanel parentPanel;
    private boolean visible;

    private final static float[] dashed = new float[]{8f};
    protected final static BasicStroke editModeStroke = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dashed, 0f);

    /**
     * Cannot construct instance of this class: abstract.
     * @param x         top left corner x
     * @param y         top left corner y
     * @param width     component's width
     * @param height    component's height
     */
    public LcsComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = true;
    }

    /**
     * Cannot construct instance of this class: abstract.
     * @param x         top left corner x
     * @param y         top left corner y
     * @param width     component's width
     * @param height    component's height
     * @param visible   component's visibility
     */
    public LcsComponent(int x, int y, int width, int height, boolean visible) {
        this(x, y, width, height);
        this.visible = visible;
    }

    /**
     * Returns the component's top left corner's x coordinate.
     *
     * @return  the component's top left corner's x coordinate
     */
    public final int getX() {
        return x;
    }

    /**
     * Sets the component's top left corner's x coordinate.
     *
     * @param x   component's top left corner's x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the component's top left corner's y coordinate.
     *
     * @return  the component's top left corner's y coordinate
     */
    public final int getY() {
        return y;
    }

    /**
     * Sets the component's top left corner's y coordinate.
     *
     * @param y   component's top left corner's y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the component's width.
     *
     * @return  component's width.
     */
    public final int getWidth() {
        return width;
    }

    /**
     * Sets the component's width.
     *
     * @param width component's width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the component's height.
     *
     * @return  component's height
     */
    public final int getHeight() {
        return height;
    }

    /**
     * Sets the component's height.
     *
     * @param height    component's height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    abstract public void paint(Graphics2D g, boolean editMode, int panelX, int panelY);

    /**
     * Returns the component's visibility.
     *
     * @return component's visibility
     */
    public final boolean isVisible() {
        return visible;
    }

    /**
     * Sets the component's visibility.
     *
     * This calls a repaint on its parent LcsPanel if it is set.
     *
     * @param visible   component's visibility
     */
    public final void setVisible(boolean visible) {
        this.visible = visible;
        if (parentPanel != null) parentPanel.repaint();
    }

    /**
     * Sets the component's parent panel.
     *
     * This panel is called to repaint on a change in the component.
     *
     * @param parentPanel   component's parent panel
     */
    public final void setParentPanel(LcsPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    /**
     * Repaints the component's parent panel and thereby the component itself.
     *
     * Should be called upon a change in the component to redraw itself.
     */
    protected final void repaint() {
        parentPanel.repaint();
    }
}
