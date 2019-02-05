/* file: MenuBarView.java
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
 *    This class crates menu bar for game window.
 *
 */
package View;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MenuBarView {

    // Class properties:
    private JMenuBar menuBar;
    private Map<String, JMenu>  menu;
    private Map<String, JMenuItem> menuItems;
    private Map<String, JCheckBoxMenuItem> checkBoxMenuItems;

    // Default constructor:
    public MenuBarView() {
        this.menuBar = new JMenuBar();
        this.menu = new HashMap<String, JMenu>();
        this.menuItems = new HashMap<String, JMenuItem>();
        this.checkBoxMenuItems = new HashMap<String, JCheckBoxMenuItem>();
        this.createMenus();
        this.createMenuItems();
        this.createCheckBoxMenuItems();
    }

    // Getter functions:
    public Map<String, JMenu> getMenu() {
        return this.menu;
    }

    public Map<String, JMenuItem> getMenuItems() {
        return this.menuItems;
    }

    public Map<String, JCheckBoxMenuItem> getCheckBoxMenuItems() {
        return this.checkBoxMenuItems;
    }

    // Setter functions:
    public void setMenu(Map<String, JMenu> menu) {
        this.menu = menu;
    }

    public void setMenuItems(Map<String, JMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setCheckBoxMenuItems(Map<String, JCheckBoxMenuItem> checkBoxMenuItems) {
        this.checkBoxMenuItems = checkBoxMenuItems;
    }

    // Other methods:
    private void createMenus() {
        this.menu.put("file", new JMenu("File"));
        this.menu.put("hints", new JMenu("Hints"));
        this.menu.put("help", new JMenu("Help"));
    }

    private void createMenuItems() {
        this.menuItems.put("loadPuzzle", new JMenuItem("Load a Puzzle from a  File"));
        this.menuItems.put("storePuzzle", new JMenuItem("Store a Puzzle into a  File"));
        this.menuItems.put("exit", new JMenuItem("Exit"));
        this.menuItems.put("singleAlgorithm", new JMenuItem("Single Algorithm"));
        this.menuItems.put("hiddenSingleAlgorithm", new JMenuItem("Hidden Single Algorithm"));
        this.menuItems.put("lockedCandidateAlgorithm", new JMenuItem("Locked Candidate Algorithm"));
        this.menuItems.put("nakedPairsAlgorithm", new JMenuItem("Naked Pairs Algorithm"));
        this.menuItems.put("fillBlankCells", new JMenuItem("Fill Blank Cells"));
        this.menuItems.put("howToPlay", new JMenuItem("How to Play Sudoku"));
        this.menuItems.put("howToUse", new JMenuItem("How to Use Program"));
        this.menuItems.put("about", new JMenuItem("About"));
    }

    private void createCheckBoxMenuItems() {
        this.checkBoxMenuItems.put("checkOnFill", new JCheckBoxMenuItem("Check on Fill"));
    }

    private void bildFileMenu() {
        this.menu.get("file").add(this.menuItems.get("loadPuzzle"));
        this.menu.get("file").add(this.menuItems.get("storePuzzle"));
        this.menu.get("file").add(this.menuItems.get("exit"));
    }

    private void buildHintsMenu() {
        this.menu.get("hints").add(this.checkBoxMenuItems.get("checkOnFill"));
        this.menu.get("hints").add(this.menuItems.get("singleAlgorithm"));
        this.menu.get("hints").add(this.menuItems.get("hiddenSingleAlgorithm"));
        this.menu.get("hints").add(this.menuItems.get("lockedCandidateAlgorithm"));
        this.menu.get("hints").add(this.menuItems.get("nakedPairsAlgorithm"));
        this.menu.get("hints").add(this.menuItems.get("fillBlankCells"));
    }

    private void buildHelpMenu() {
        this.menu.get("help").add(this.menuItems.get("howToPlay"));
        this.menu.get("help").add(this.menuItems.get("howToUse"));
        this.menu.get("help").add(this.menuItems.get("about"));
    }

    private void addFileMenu() {
        this.bildFileMenu();
        this.menuBar.add(this.menu.get("file"));
    }

    private void addHintsMenu() {
        this.buildHintsMenu();
        this.menuBar.add(this.menu.get("hints"));
    }

    private void addHelpMenu() {
        this.buildHelpMenu();
        this.menuBar.add(this.menu.get("help"));
    }

    public void addMenuItemListener(String menuItem, ActionListener menuItemListener) {
        this.menuItems.get(menuItem).addActionListener(menuItemListener);
    }

    public void addCheckBoxMenuItemListener(String checkBoxMenuItem, ActionListener checkBoxMenuItemListener) {
        this.checkBoxMenuItems.get(checkBoxMenuItem).addActionListener(checkBoxMenuItemListener);
    }

    public JMenuBar buildMenuBar() {
        this.addFileMenu();
        this.addHintsMenu();
        this.addHelpMenu();
        return this.menuBar;
    }
}
