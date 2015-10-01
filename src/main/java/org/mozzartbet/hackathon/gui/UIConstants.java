package org.mozzartbet.hackathon.gui;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Shared UI constants.
 */
public interface UIConstants {

    /** The table color. */
    Color TABLE_COLOR = new Color(0, 128, 0); // Dark green

    /** The text color. */
    Color TEXT_COLOR = Color.GREEN;

    /** The border used around labels. */
    Border LABEL_BORDER = new LineBorder(Color.BLACK, 1);
    
    /** The border used around panels. */
    Border PANEL_BORDER = new CompoundBorder(
            new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10));

}
