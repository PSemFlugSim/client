/*
 * Copyright (C) 2015  Simon Schaeffner <simon.schaeffner@googlemail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package me.sschaeffner.lcSwing.components;

import me.sschaeffner.lcSwing.LcsUtil;
import me.sschaeffner.lcSwing.api.FaderListener;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * A fader component.
 *
 * @author sschaeffner
 */
public class LcsFader extends LcsComponent implements LcsMouseInteractingComponent {

    private static final int TEXT_TOP_MARGIN = 5;
    private static final int TEXT_BOTTOM_MARGIN = 10;
    private static final int INNER_POLE_WIDTH = 10;
    private static final int INNER_POLE_TOP_MARGIN = 10;
    private static final int PUSHER_SIDE_MARGIN = 10;
    private static final int PUSHER_HEIGHT = INNER_POLE_TOP_MARGIN * 2;
    private static final int PUSHER_ARC = 8;

    private static final double INNER_POLE_COLOR_FACTOR = 0.4;


    public static final int MAX_VALUE = 255;

    private String text;
    private int value;
    private Color color, textColor, innerPoleColor, pusherColor, pusherTextColor;

    private int innerPoleY, innerPoleHeight;

    private FaderListener listener;

    /**
     * Constructs a new instance of this class.
     *
     * @param x         top left corner x
     * @param y         top left corner y
     * @param width     component's width
     * @param height    component's height
     * @param text      component's description/name
     * @param color     component's main color
     */
    public LcsFader(int x, int y, int width, int height, String text, Color color) {
        super(x, y, width, height);
        this.text = text;
        this.value = 200;
        this.pusherColor = Color.LIGHT_GRAY;
        this.pusherTextColor = Color.BLACK;
        setColor(color);
    }

    @Override
    public void paint(Graphics2D g, boolean editMode, int panelX, int panelY) {
        //draw background color
        g.setColor(color);
        g.fillRect(panelX + getX(), panelY + getY(), getWidth(), getHeight());

        //draw black frame
        g.setColor(Color.BLACK);
        Stroke defaultStroke = g.getStroke();
        //set editModeStroke to draw a dashed line in edit mode
        if (editMode) g.setStroke(editModeStroke);
        g.drawRect(panelX + getX(), panelY + getY(), getWidth(), getHeight());
        g.setStroke(defaultStroke);

        //draw text
        Rectangle2D textBounds = LcsUtil.getTextBounds(g, text);
        int textX = getX() + (getWidth() / 2) - (int)(textBounds.getWidth() / 2);
        int textY = getY() + getHeight() - TEXT_BOTTOM_MARGIN;
        g.setColor(textColor);
        g.drawString(text, panelX + textX, panelY + textY);

        //draw inner pole
        int innerPoleX = getX() + (getWidth() / 2) - (INNER_POLE_WIDTH / 2);
        innerPoleY = getY() + INNER_POLE_TOP_MARGIN;
        innerPoleHeight = getHeight() - INNER_POLE_TOP_MARGIN - (TEXT_TOP_MARGIN + (int)textBounds.getHeight() + TEXT_BOTTOM_MARGIN);
        int innerPoleFillHeight = (int)Math.round((value * (double)innerPoleHeight) / 255);
        int innerPoleFillY = innerPoleY + innerPoleHeight - innerPoleFillHeight;
        g.setColor(innerPoleColor);
        g.fillRect(panelX + innerPoleX, panelY + innerPoleFillY, INNER_POLE_WIDTH, innerPoleFillHeight);
        g.setColor(Color.BLACK);
        g.drawRect(panelX + innerPoleX, panelY + innerPoleY, INNER_POLE_WIDTH, innerPoleHeight);

        //draw pusher
        int pusherX = getX() + PUSHER_SIDE_MARGIN;
        int pusherY = getY() + (int) Math.round(((255 - value) * (double)innerPoleHeight) / 255);
        int pusherWidth = getWidth() - 2 * PUSHER_SIDE_MARGIN;
        g.setColor(pusherColor);
        g.fillRoundRect(panelX + pusherX, panelY + pusherY, pusherWidth, PUSHER_HEIGHT, PUSHER_ARC, PUSHER_ARC);
        g.setColor(Color.BLACK);
        g.drawRoundRect(panelX + pusherX, panelY + pusherY, pusherWidth, PUSHER_HEIGHT, PUSHER_ARC, PUSHER_ARC);

        //draw value on pusher
        String valueText = value + "";
        Rectangle2D valueTextBounds = LcsUtil.getTextBounds(g, valueText);
        int valueTextX = pusherX + (pusherWidth / 2) - (int)(valueTextBounds.getWidth() / 2);
        int valueTextY = pusherY + (PUSHER_HEIGHT / 2) + (int)((valueTextBounds.getHeight() - LcsUtil.MAGIC_TEXT_HEIGHT_CONSTANT) / 2);
        g.setColor(pusherTextColor);
        g.drawString(valueText, panelX + valueTextX, panelY + valueTextY);

    }

    /**
     * Sets this fader's color.
     *
     * @param color the fader's color
     */
    public void setColor(Color color) {
        this.color = color;

        int r = (int)Math.round(this.color.getRed() * INNER_POLE_COLOR_FACTOR);
        int g = (int)Math.round(this.color.getGreen() * INNER_POLE_COLOR_FACTOR);
        int b = (int)Math.round(this.color.getBlue() * INNER_POLE_COLOR_FACTOR);
        if (r < 0) r = 0;
        if (g < 0) g = 0;
        if (b < 0) b = 0;
        this.innerPoleColor = new Color(r, g, b);

        //set text color dependent of the background color's luminance
        if (LcsUtil.getColorLuminance(this.color.getRed(), this.color.getGreen(), this.color.getBlue()) > 50) {
            textColor = Color.BLACK;
        } else {
            textColor = Color.WHITE;
        }
    }

    @Override
    public void onHover() {

    }

    @Override
    public void onUnhover() {

    }

    @Override
    public void onMouseLeftDown(int x, int y) {
        onMouseDrag(x, y);
    }

    @Override
    public void onMouseLeftUp(int x, int y) {

    }

    @Override
    public void onLeftClick(int x, int y) {

    }

    @Override
    public void onRightClick(int x, int y) {

    }

    @Override
    public void onMouseDrag(int x, int y) {

        //calculate value
        int newValue = 255 - (int)Math.round((((double) y - innerPoleY) / innerPoleHeight) * 255);

        //if mouse is dragged higher / lower than possible, set to MAX_VALUE / 0
        if (newValue > MAX_VALUE) {
            newValue = MAX_VALUE;
        } else if (newValue < 0) {
            newValue = 0;
        }

        this.value = newValue;

        //notify listener
        if (listener != null) listener.onFaderValueChange(this.value);
    }

    @Override
    public void onMouseScroll(int amount) {

    }

    /**
     * Sets the fader's value.
     *
     * @param value
     */
    public void setValue(int value) {
        //don't allow fader values outside the boundaries
        if (value < 0) value = 0;
        if (value > MAX_VALUE) value = MAX_VALUE;

        this.value = value;
        repaint();
    }

    /**
     * Returns the fader's current value.
     *
     * This value is always greater than or equal to zero
     * and smaller than or equal to MAX_VALUE.
     *
     * @return  the fader's current value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the fader's listener.
     *
     * @param listener  the listener
     */
    public void setListener(FaderListener listener) {
        this.listener = listener;
    }
}
