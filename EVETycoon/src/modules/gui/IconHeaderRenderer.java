/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableCellRenderer;
import tables.BasicTableModel;

/**
 *
 * @author hrivanov
 */
public class IconHeaderRenderer implements TableCellRenderer
{
  private JLabel iconLabel = new JLabel();

  public IconHeaderRenderer()
  {
    iconLabel.setOpaque(true);
    iconLabel.setBackground(Color.BLACK);
    iconLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY));
    
    iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
    iconLabel.setForeground(Color.LIGHT_GRAY);
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    BasicTableModel model = (BasicTableModel)table.getModel();
    
    iconLabel.setIcon(null);
    iconLabel.setText("");
    
    if(model.getTableColumnIcon(column) != null)
      iconLabel.setIcon(model.getTableColumnIcon(column));
    else
      iconLabel.setText(model.getColumnName(column));
    
    iconLabel.setToolTipText(model.getColumnName(column));

    return iconLabel;
  }
}
