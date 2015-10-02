package me.sschaeffner.lcSwing.components;

/**
 * A component that interacts with the mouse (click, drag, scroll, etc).
 *
 * @author sschaeffner
 */
public interface LcsMouseInteractingComponent extends LcsComponentInterface {
    void onHover();
    void onUnhover();
    void onMouseLeftDown(int x, int y);
    void onMouseLeftUp(int x, int y);
    void onLeftClick(int x, int y);
    void onRightClick(int x, int y);
    void onMouseDrag(int x, int y);
    void onMouseScroll(int amount);
}
