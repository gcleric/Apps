/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.gui;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import tables.BasicTableCell;
import tables.BasicTableModel;

/**
 *
 * @author hrivanov
 */
public class IconLabelRenderer implements TableCellRenderer
{
  private JLabel labelObject;
  private BasicTableModel model;
  private BasicTableCell cell;

  public IconLabelRenderer()
  {
    labelObject = new JLabel();
    labelObject.setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    model = (BasicTableModel)table.getModel();
    row = table.convertRowIndexToModel(row);
    column = table.convertColumnIndexToModel(column);
    cell = model.getCellAt(row, column);
    
    if(cell.getLabel().isEmpty())
      labelObject.setHorizontalAlignment(JLabel.CENTER);
    else
      labelObject.setHorizontalAlignment(JLabel.LEFT);
    
    labelObject.setIcon((Icon)value);
    labelObject.setText(cell.getLabel());
    labelObject.setToolTipText(cell.getTooltipText());

    if(isSelected)
    {
      labelObject.setForeground(table.getSelectionForeground());
      labelObject.setBackground(table.getSelectionBackground());
    }
    else
    {
      labelObject.setForeground(table.getForeground());
      labelObject.setBackground(table.getBackground());
    }

    return labelObject;
  }
}
