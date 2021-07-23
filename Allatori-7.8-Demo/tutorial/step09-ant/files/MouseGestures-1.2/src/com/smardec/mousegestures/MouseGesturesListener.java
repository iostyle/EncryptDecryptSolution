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

/**
 * Interface for mouse gestures listener.
 *
 * @author Smardec
 * @version 1.2
 */
public interface MouseGesturesListener {
    /**
     * Called when full mouse gesture is recogized (mouse button is released).
     *
     * @param gesture String representation of mouse gesture. "L" for left, "R" for right,
     *                "U" for up, "D" for down movements. For example: "ULD".
     */
    public void processGesture(String gesture);

    /**
     * Called when new mouse movement is recognized but mouse gesture is not yet completed.
     *
     * @param currentGesture String representation of recognized movements. "L" for left, "R" for right,
     *                       "U" for up, "D" for down movements. For example: "ULD".
     */
    public void gestureMovementRecognized(String currentGesture);
}
