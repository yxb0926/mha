/**
* MultiLineCellRenderer.java
*
* Created: Mon May 17 09:41:53 1999
*
* @author Thomas Wernitz, Da Vinci Communications Ltd
<thomas_wernitz@xxxxxxxxxxxx>
*
* credit to Zafir Anjum for JTableEx and thanks to SUN for their
source code ;)
*/

package mha.ui.SimpleGUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.border.*;

import java.awt.Component;
import java.awt.Color;

public class MultiLineCellRenderer extends JTextArea implements
TableCellRenderer {
protected static Border noFocusBorder;

private Color unselectedForeground;
private Color unselectedBackground;

public MultiLineCellRenderer() {
super();
noFocusBorder = new EmptyBorder(1, 2, 1, 2);
setLineWrap(true);
setWrapStyleWord(true);
setOpaque(true);
setBorder(noFocusBorder);
}

public void setForeground(Color c) {
super.setForeground(c);
unselectedForeground = c;
}

public void setBackground(Color c) {
super.setBackground(c);
unselectedBackground = c;
}

public void updateUI() {
super.updateUI();
setForeground(null);
setBackground(null);
}

public Component getTableCellRendererComponent(JTable table, Object
value,
boolean isSelected, boolean hasFocus,
int row, int column) {

if (isSelected) {
super.setForeground(table.getSelectionForeground());
super.setBackground(table.getSelectionBackground());
}
else {
super.setForeground((unselectedForeground != null) ?
unselectedForeground
: table.getForeground());
super.setBackground((unselectedBackground != null) ?
unselectedBackground
: table.getBackground());
}

setFont(table.getFont());

if (hasFocus) {
setBorder( UIManager.getBorder("Table.focusCellHighlightBorder")
);
if (table.isCellEditable(row, column)) {
super.setForeground( UIManager.getColor("Table.focusCellForeground")
);
super.setBackground( UIManager.getColor("Table.focusCellBackground")
);
}
} else {
setBorder(noFocusBorder);
}

//setValue(value);
setValue(table.getValueAt(row,column).toString());
setSize(table.getColumnModel().getColumn(column).getWidth(), Integer.MAX_VALUE);
int desiredHeight = (int) getPreferredSize().getHeight();
if (desiredHeight>table.getRowHeight(row)) table.setRowHeight(row,desiredHeight);

return this;
}

protected void setValue(Object value) {
setText((value == null) ? "" : value.toString());
}
}  
