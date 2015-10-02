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
import me.sschaeffner.lcSwing.api.ButtonListener;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * A button.
 *
 * @author sschaeffner
 */
public class LcsButton extends LcsComponent implements LcsMouseInteractingComponent {

    private String text;
    private int arcSize;
    private Color color, selectedColor, pressedColor, textColor;
    private boolean selected, pressed;
    private ButtonListener listener;

    /**
     * Constructs a new instance of this class.
     *
     * @param x         top left corner x
     * @param y         top left corner y
     * @param width     component's width
     * @param height    component's height
     * @param text      component's description/name
     * @param color     component's color
     */
    public LcsButton(int x, int y, int width, int height, String text, Color color) {
        super(x, y, width, height);
        this.text = text;

        calcArcSize();
        setColor(color);
    }

    @Override
    public void paint(Graphics2D g, boolean editMode, int panelX, int panelY) {

        //draw background color dependent on the button's state
        if (pressed) {
            g.setColor(pressedColor);
        } else if (selected) {
            g.setColor(selectedColor);
        } else {
            g.setColor(color);
        }
        g.fillRoundRect(panelX + getX(), panelY + getY(), getWidth(), getHeight(), arcSize, arcSize);

        //draw black frame
        g.setColor(Color.BLACK);
        Stroke defaultStroke = g.getStroke();
        //set editModeStroke to draw a dashed line in edit mode
        if (editMode) g.setStroke(editModeStroke);
        g.drawRoundRect(panelX + getX(), panelY + getY(), getWidth(), getHeight(), arcSize, arcSize);
        g.setStroke(defaultStroke);

        //draw text
        Rectangle2D textBounds = LcsUtil.getTextBounds(g, text);
        //bottom left coordinate
        int textX = getX() + (getWidth() / 2) - ((int) (textBounds.getWidth() / 2));
        int textY = getY() + (getHeight() / 2) + ((int) (textBounds.getHeight() / 2));
        //g.setColor(Color.RED);
        //g.fillRect(textX, getY() + (getHeight() / 2) - ((int) (textBounds.getHeight() / 2)), (int)textBounds.getWidth(), (int)textBounds.getHeight());
        g.setColor(textColor);
        g.drawString(text, panelX + textX, panelY + textY);

    }

    /**
     * Sets this button's color.
     *
     * @param color the button's color
     */
    public void setColor(Color color) {
        this.color = color;

        int r = color.getRed() - 20;
        int g = color.getGreen() - 20;
        int b = color.getBlue() - 20;
        if (r < 0) r = 0;
        if (g < 0) g = 0;
        if (b < 0) b = 0;
        this.selectedColor = new Color(r, g, b);

        r = r - 40;
        g = g - 40;
        b = b - 40;
        if (r < 0) r = 0;
        if (g < 0) g = 0;
        if (b < 0) b = 0;
        this.pressedColor = new Color(r, g, b);

        //set text color dependent of the background color's luminance
        if (LcsUtil.getColorLuminance(this.color.getRed(), this.color.getGreen(), this.color.getBlue()) > 50) {
            textColor = Color.BLACK;
        } else {
            textColor = Color.WHITE;
        }
    }

    /**
     * Calculates the arc size for the rectangle to be drawn.
     */
    private void calcArcSize() {
        int smallerSize = (getWidth() < getHeight()) ? getWidth() : getHeight();
        arcSize = smallerSize / 5;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        calcArcSize();
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        calcArcSize();
    }

    @Override
    public void onHover() {
        selected = true;
    }

    @Override
    public void onUnhover() {
        selected = false;
    }

    @Override
    public void onMouseLeftDown(int x, int y) {
        pressed = true;
    }

    @Override
    public void onMouseLeftUp(int x, int y) {
        pressed = false;
    }

    @Override
    public void onLeftClick(int x, int y) {
        if (listener != null) listener.onClick();
    }

    @Override
    public void onRightClick(int x, int y) {}

    @Override
    public void onMouseDrag(int x, int y) {}

    @Override
    public void onMouseScroll(int amount) {}

    public void setListener(ButtonListener listener) {
        this.listener = listener;
    }
}
