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
package me.sschaeffner.lcSwing;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Provides utilities like calculating the size of a text drawn on a screen or generating a single rgb int from single r, g and b values.
 *
 * @author sschaeffner
 */
public class LcsUtil {

    public static final int MAGIC_TEXT_HEIGHT_CONSTANT = 4;

    /**
     * Enables Mac OS X's native fullscreen feature.
     *
     * Only executes on Mac OS X.
     *
     * @param window    root pane to be used
     */
    public static void enableOSXFullscreen(Window window) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            try {
                Class util = Class.forName("com.apple.eawt.FullScreenUtilities");
                Class params[] = new Class[]{Window.class, Boolean.TYPE};
                Method method = util.getMethod("setWindowCanFullScreen", params);
                method.invoke(util, window, true);
            } catch (ClassNotFoundException | NoSuchMethodException |
                    SecurityException | IllegalAccessException |
                    IllegalArgumentException | InvocationTargetException exp) {
                exp.printStackTrace(System.err);
            }
        }
    }

    /**
     * Toggles Mac OS X's native fullscreen.
     *
     * Only executes on Mac OS X.
     *
     * @param window    root pane to be toggled
     */
    public static void toggleOSXFullscreen(Window window) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            try {
                Class application = Class.forName("com.apple.eawt.Application");
                Method getApplication = application.getMethod("getApplication");
                Object instance = getApplication.invoke(application);
                Method method = application.getMethod("requestToggleFullScreen", Window.class);
                method.invoke(instance, window);
            } catch (ClassNotFoundException | NoSuchMethodException |
                    SecurityException | IllegalAccessException |
                    IllegalArgumentException | InvocationTargetException exp) {
                exp.printStackTrace(System.err);
            }
        }
    }

    /**
     * Calculates the size of a certain String drawn on screen.
     *
     * @param g2    instance of Graphics2D
     * @param text  String to be used for calculation
     * @return      calculated size
     */
    public static Rectangle2D getTextBounds(Graphics2D g2, String text) {
        Font font = g2.getFont();
        FontRenderContext context = g2.getFontRenderContext();
        return font.getStringBounds(text, context);
    }

    /**
     * Converts single r, g and b values of a color into a single rgb integer as in the default RGB ColorModel.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @return  the combined RGB components
     */
    public static int getRGB(int r, int g, int b) {
        return (0xFF << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

    /**
     * Returns the red component of a combined RGB value.
     *
     * @param rgb   the combined RGB components
     * @return      the red component
     */
    public static int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    /**
     * Returns the green component of a combined RGB value.
     *
     * @param rgb   the combined RGB components
     * @return      the green component
     */
    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    /**
     * Returns the blue component of a combined RGB value.
     *
     * @param rgb   the combined RGB components
     * @return      the blue component
     */
    public static int getBlue(int rgb) {
        return rgb & 0xFF;
    }

    /**
     * Returns the color as a hex String (e.g. "#ff00ff") for output.
     *
     * @param rgb   the combined RGB components
     * @return      the hex String
     */
    public static String getColorAsHexString(int rgb) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);

        StringBuilder sb = new StringBuilder();
        String rS = Integer.toHexString(r);
        if (rS.length() > 1) {
            sb.append(rS);
        } else {
            sb.append("0");
            sb.append(rS);
        }

        String gS = Integer.toHexString(g);
        if (gS.length() > 1) {
            sb.append(gS);
        } else {
            sb.append("0");
            sb.append(gS);
        }

        String bS = Integer.toHexString(b);
        if (bS.length() > 1) {
            sb.append(bS);
        } else {
            sb.append("0");
            sb.append(bS);
        }

        return sb.toString().toUpperCase();
    }

    /**
     * Calculates a color's luminance.
     *
     * This calculation is done following the  ITU-R BT.709 conversion.
     * https://en.wikipedia.org/wiki/YCbCr
     *
     * Returns a value between 0 and 100 if input values are between 0 and 255.
     *
     * @param r     the red component
     * @param g     the green component
     * @param b     the blue component
     * @return      the color's luminance
     */
    public static int getColorLuminance(int r, int g, int b) {
        double luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;
        return (int) Math.round(luminance);
    }

    /**
     * Calculates a color's luminance.
     *
     * This calculation is done following the ITU-R BT.601 conversion.
     * https://en.wikipedia.org/wiki/YCbCr
     *
     * Returns a value between 0 and 100 if input values are between 0 and 255.
     *
     * @param rgb   the combined RGB components
     * @return      the color's luminance
     */
    public static int getColorLuminance(int rgb) {
        return getColorLuminance(getRed(rgb), getGreen(rgb), getBlue(rgb));
    }
}
