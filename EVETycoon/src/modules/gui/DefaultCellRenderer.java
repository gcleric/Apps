/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import tables.BasicTableCell;
import tables.BasicTableModel;

/**
 *
 * @author hrivanov
 */
public class DefaultCellRenderer extends DefaultTableCellRenderer
{
  private BasicTableModel model;
  private BasicTableCell tableCell;
  private Component renderedCell;
  
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    model = (BasicTableModel)table.getModel();
    tableCell = model.getCellAt(row, column);
    
    renderedCell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
    if(tableCell != null)
    {
      if((!isSelected))
      {
        if(tableCell.getBgColor() != null)
          renderedCell.setBackground(tableCell.getBgColor());
        else
          renderedCell.setBackground(table.getBackground());
        
        if(tableCell.getFgColor() != null)
          renderedCell.setForeground(tableCell.getFgColor());
        else
          renderedCell.setForeground(table.getForeground());
      }
      else
      {
        renderedCell.setBackground(table.getSelectionBackground());
        renderedCell.setForeground(table.getSelectionForeground());
      }
    }
    
    return renderedCell;
  }
}
