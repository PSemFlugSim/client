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

import me.sschaeffner.lcSwing.components.LcsComponent;
import me.sschaeffner.lcSwing.components.LcsComponentInterface;
import me.sschaeffner.lcSwing.components.LcsMouseInteractingComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * A Panel to draw LcsComponents on.
 *
 * @author sschaeffner
 */
public class LcsPanel {

    //panel used to draw on
    private final JPanel jPanel;

    //instance of LcsMouseListener that implements MouseListener, MouseMotionListener and MouseWheelListener
    private final LcsMouseListener mouseListener;

    private static final int PANEL_X = 10, PANEL_Y = 10;

    //list of components to be drawn on this panel
    private final ArrayList<LcsComponentInterface> componentList;

    //whether the max fps should be drawn in the bottom left corner of the panel
    private boolean drawMaxFPS;

    //whether edit mode is enabled (allows components to be moved around, etc.)
    private boolean editMode;

    /**
     * Constructs a new instance of this class.
     */
    public LcsPanel() {
        this.jPanel = new LcsJPanel();
        this.mouseListener = new LcsMouseListener();
        this.jPanel.addMouseListener(mouseListener);
        this.jPanel.addMouseMotionListener(mouseListener);
        this.jPanel.addMouseWheelListener(mouseListener);
        this.componentList = new ArrayList<>();
        this.drawMaxFPS = false;
        this.editMode = false;
    }

    /**
     * Adds a LcsComponent to the panel.
     *
     * @param component component to add
     * @return  true if the component list changed as a result of this call
     */
    public boolean addLcsComponent(LcsComponent component) {
        boolean success = componentList.add(component);
        if (success) component.setParentPanel(this);
        return success;
    }

    /**
     * Removes a LcsComponent from the panel.
     *
     * @param component component to remove
     * @return  true if the component list changed as a result of this call
     */
    public boolean removeLcsComponent(LcsComponent component) {
        boolean success = componentList.remove(component);
        if (success) component.setParentPanel(null);
        return success;
    }

    /**
     * Repaints the LcsPanel.
     */
    public void repaint() {
        jPanel.repaint();
    }

    /**
     * Returns the JPanel that is used to draw on.
     *
     * @return  instance of JPanel
     */
    public JPanel getJPanel() {
        return jPanel;
    }

    /**
     * Sets the option to draw the max fps in the bottom left corner of the panel.
     *
     * This value describes the maximum amount of frames that could be drawn in a single second.
     *
     * @param drawMaxFPS    whether to draw the max fps
     */
    public void setDrawMaxFPS(boolean drawMaxFPS) {
        this.drawMaxFPS = drawMaxFPS;
    }

    /**
     * Sets if the panel is in edit mode or not.
     *
     * In edit mode the components of the panel can be moved around and configured by double clicking on them.
     *
     * @param editMode  whether edit mode is enabled
     */
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    /**
     * Extends JPanel to override its paint method and draw the LcsComponents on the JPanel.
     */
    private class LcsJPanel extends JPanel {

        /**
         * Enables antialiasing and paints all LcsComponents.
         *
         * @param g instance of Graphics
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            //save time before drawing to calculate drawing time afterwards
            long timeBefore = System.currentTimeMillis();
            //enable antialiasing
            Graphics2D g2 = (Graphics2D)g.create();
            RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHints(qualityHints);

            //draw components if visible
            componentList.forEach(component -> {
                if (component.isVisible()) component.paint(g2, editMode, PANEL_X, PANEL_Y);
            });
            g2.dispose();

            if (drawMaxFPS) {
                //calculate drawing time and draw on screen (bottom left corner)
                long drawingTime = System.currentTimeMillis() - timeBefore;
                float maxFps = 1 / ((float) drawingTime / 1000);
                g.drawString("maxFPS: " + (int) maxFps, 0, getHeight());
            }
        }
    }

    /**
     * Implements MouseListener, MouseMotionListener and MouseWheelListener.
     */
    private class LcsMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

        //the currently selected component (the one the mouse hovers above)
        private LcsMouseInteractingComponent selectedComponent;

        //what component the cursor is dragged to when it is dragged
        private LcsMouseInteractingComponent draggedToComponent;

        //whether the cursor is in the panel
        private boolean mouseInPanel;

        //whether the left mouse button is pressed
        private boolean mousePressed;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3 && selectedComponent != null) selectedComponent.onRightClick(-PANEL_X + e.getX(), -PANEL_Y + e.getY());
        }


        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                mousePressed = true;

                if (selectedComponent != null && pixelInComponent(selectedComponent, e.getX(), e.getY())) {
                    selectedComponent.onMouseLeftDown(-PANEL_X + e.getX(), -PANEL_Y + e.getY());
                    draggedToComponent = selectedComponent;
                    repaint();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                mousePressed = false;
                if (selectedComponent != null) {
                    selectedComponent.onMouseLeftUp(-PANEL_X + e.getX(), -PANEL_Y + e.getY());

                    //check whether mouse is still in selected component before executing the click
                    if (selectedComponent == draggedToComponent) {
                        selectedComponent.onLeftClick(-PANEL_X + e.getX(), -PANEL_Y + e.getY());
                    }

                }

                draggedToComponent = null;

                calcSelectedComponent(e.getX(), e.getY());
                repaint();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            mouseInPanel = true;
            if (!mousePressed) calcSelectedComponent(e.getX(), e.getY());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mouseInPanel = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                //do not change selected component on drag

                draggedToComponent = null;
                //calculate draggedToComponent
                for (int i = componentList.size() - 1; i >= 0 && draggedToComponent == null; i--) {
                    LcsComponentInterface component = componentList.get(i);

                    if (component instanceof LcsMouseInteractingComponent) {
                        if (pixelInComponent(component, e.getX(), e.getY())) {
                            draggedToComponent = (LcsMouseInteractingComponent) component;
                        }
                    }
                }
                if (selectedComponent != null) {
                    selectedComponent.onMouseDrag(-PANEL_X + e.getX(), -PANEL_Y + e.getY());
                }

                repaint();
            } else {
                calcSelectedComponent(e.getX(), e.getY());
            }

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            calcSelectedComponent(e.getX(), e.getY());
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (selectedComponent != null && pixelInComponent(selectedComponent, e.getX(), e.getY())) {
                selectedComponent.onMouseScroll(e.getUnitsToScroll());
            }
        }

        /**
         * Calculates the currently selected component given the mouse's coordinates.
         *
         * This considers PANEL_X and PANEL_Y on its own.
         *
         * @param x mouse coordinate x
         * @param y mouse coordinate y
         */
        private void calcSelectedComponent(int x, int y) {
            LcsMouseInteractingComponent selectedBefore = selectedComponent;
            selectedComponent = null;

            //iterate through componentList in reversed order
            //the component that was added last is supposed to be on top (drawn last)

            for (int i = componentList.size() - 1; i >= 0 && selectedComponent == null; i--) {
                LcsComponentInterface component = componentList.get(i);

                if (component.isVisible()) {
                    if (component instanceof LcsMouseInteractingComponent) {
                        if (pixelInComponent(component, x, y)) {
                            selectedComponent = (LcsMouseInteractingComponent) component;
                        }
                    }
                }
            }
            if (selectedBefore != null && selectedComponent != null && selectedBefore != selectedComponent) {
                selectedBefore.onUnhover();
                selectedComponent.onHover();
                repaint();
            } else if (selectedBefore != null && selectedComponent == null) {
                selectedBefore.onUnhover();
                repaint();
            } else if (selectedComponent != null) {
                selectedComponent.onHover();
                repaint();
            }
        }

        /**
         * Calculates whether a certain pixel of the panel is in a specified component.
         *
         * @param component component to check
         * @param x         pixel's x coordinate
         * @param y         pixel's y coordinate
         * @return          whether the pixel is in the component
         */
        private boolean pixelInComponent(LcsComponentInterface component, int x, int y) {
            return component.isVisible() && PANEL_X + component.getX() <= x && (PANEL_X + component.getX() + component.getWidth()) >= x && PANEL_Y + component.getY() <= y && (PANEL_Y + component.getY() + component.getHeight()) >= y;
        }
    }
}
