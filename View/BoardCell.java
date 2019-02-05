/* file: BoardCell.java
 *
 *  Authors:
 *
 *      Alex Viznytsya
 *
 *  Date:
 *
 *      10/26/2017
 *
 *  About:
 *
 *     This is custom JLabel class that is used to form gameboard
 *     cells and window buttons.
 *
 */
package View;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;

public class BoardCell extends JLabel{

    // Class properties:
    private int inlineValue;
    private int boxedValue;
    private boolean active;
    private boolean selected;

    // Default constructor:
    BoardCell() {
        this.inlineValue = 0;
        this.boxedValue = 0;
        this.active = true;
        this.selected = false;
    }

    // Getter methods:
    public int getInlineValue() {
        return this.inlineValue;
    }

    public int getBoxedValue() {
        return this.boxedValue;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isSelected() {
        return selected;
    }

    // Setter methods:
    public void setInlineValue(int inlineValue) {
        this.inlineValue = inlineValue;
    }

    public void setBoxedValue(int boxedValue) {
        this.boxedValue = boxedValue;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void  setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setCellDefaultCursor() {
        this.active = false;
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void setCellHandCursor() {
        this.active = true;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Other methods:
    public void initialize() {
        setOpaque(true);
        setBackground(Color.white);
        setForeground(Color.black);
        setFont(new Font("Arial", Font.BOLD, 14));
        setVerticalAlignment(JLabel.CENTER);
        setHorizontalAlignment(JLabel.CENTER);
        setText("");
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
