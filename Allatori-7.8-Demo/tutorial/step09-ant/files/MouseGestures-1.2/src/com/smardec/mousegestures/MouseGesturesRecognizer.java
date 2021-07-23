/*
MouseGestures - pure Java library for recognition and processing mouse gestures.
Copyright (C) 2003-2004 Smardec

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package com.smardec.mousegestures;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;


/**
 * Responsible for processing mouse events and recognition of mouse movements.
 *
 * @author Smardec
 * @version 1.2
 */
class MouseGesturesRecognizer {
    /**
     * String representation of left movement.
     */
    private static final String LEFT_MOVE = "L";
    /**
     * String representation of right movement.
     */
    private static final String RIGHT_MOVE = "R";
    /**
     * String representation of up movement.
     */
    private static final String UP_MOVE = "U";
    /**
     * String representation of down movement.
     */
    private static final String DOWN_MOVE = "D";


    /**
     * Grid size. Default is 30.
     */
    private int gridSize = 30;
    /**
     * Reference to {@link MouseGestures}.
     */
    private MouseGestures mouseGestures;
    /**
     * Start point for current movement.
     */
    private Point startPoint = null;
    /**
     * String representation of gesture.
     */
    private StringBuffer gesture = new StringBuffer();

    /**
     * Creates MouseGesturesRecognizer.
     *
     * @param mouseGestures Reference to {@link MouseGestures}
     */
    MouseGesturesRecognizer(MouseGestures mouseGestures) {
        this.mouseGestures = mouseGestures;
    }

    /**
     * Processes mouse event.
     *
     * @param mouseEvent MouseEvent
     */
    void processMouseEvent(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof Component))
            return;
        Point mouseEventPoint = mouseEvent.getPoint();
        SwingUtilities.convertPointToScreen(mouseEventPoint, (Component) mouseEvent.getSource());
        if (startPoint == null) {
            startPoint = mouseEventPoint;
            return;
        }
        int deltaX = getDeltaX(startPoint, mouseEventPoint);
        int deltaY = getDeltaY(startPoint, mouseEventPoint);
        int absDeltaX = Math.abs(deltaX);
        int absDeltaY = Math.abs(deltaY);

        if ((absDeltaX < gridSize) && (absDeltaY < gridSize))
            return;
        float absTangent = ((float) absDeltaX) / absDeltaY;
        if (absTangent < 1) {
            if (deltaY < 0)
                saveMove(UP_MOVE);
            else
                saveMove(DOWN_MOVE);
        } else {
            if (deltaX < 0)
                saveMove(LEFT_MOVE);
            else
                saveMove(RIGHT_MOVE);
        }
        startPoint = mouseEventPoint;
    }

    /**
     * Returns delta x.
     *
     * @param a First point
     * @param b Second point
     * @return Delta x
     */
    private int getDeltaX(Point a, Point b) {
        return b.x - a.x;
    }

    /**
     * Returns delta y.
     *
     * @param a First point
     * @param b Second point
     * @return Delta y
     */
    private int getDeltaY(Point a, Point b) {
        return b.y - a.y;
    }

    /**
     * Adds movement to buffer.
     *
     * @param move String representation of recognized movement
     */
    private void saveMove(String move) {
        // should not store two equal moves in succession
        if ((gesture.length() > 0) && (gesture.charAt(gesture.length() - 1) == move.charAt(0)))
            return;
        gesture.append(move);
        mouseGestures.fireGestureMovementRecognized(getGesture());
    }

    /**
     * Returns current grid size (minimum mouse movement length to be recognized).
     *
     * @return Grid size in pixels. Default is 30.
     */
    int getGridSize() {
        return gridSize;
    }

    /**
     * Sets grid size (minimum mouse movement length to be recognized).
     *
     * @param gridSize New grid size in pixels
     */
    void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Returns string representation of mouse gesture.
     *
     * @return String representation of mouse gesture. "L" for left, "R" for right,
     *         "U" for up, "D" for down movements. For example: "ULD".
     */
    String getGesture() {
        return gesture.toString();
    }

    /**
     * Indicates whether any movements were recognized.
     *
     * @return <code>true</code> if there are recognized movements; <code>false</code> otherwise
     */
    boolean isGestureRecognized() {
        return gesture.length() > 0;
    }

    /**
     * Clears temporary info about previous gesture.
     */
    void clearTemporaryInfo() {
        startPoint = null;
        gesture.delete(0, gesture.length());
    }
}
