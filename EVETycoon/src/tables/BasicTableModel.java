/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hrivanov
 */
public class BasicTableModel extends AbstractTableModel implements Serializable
{
  private List<BasicTableColumn> columns;
  
  private List<BasicTableRow> rows;

  public BasicTableModel()
  {
    this.columns = new ArrayList<BasicTableColumn>();
    this.rows = new ArrayList<BasicTableRow>();
  }
  
  public void addColumn(BasicTableColumn newColumn)
  {
    this.columns.add(newColumn);
  }
  
  public void addRow(BasicTableRow newRow)
  {
    this.rows.add(newRow);
    int index = this.rows.size() - 1;
    this.fireTableRowsInserted(index, index);
  }
  
  public void removeRow(int rowIndex)
  {
    if((rowIndex >=0)&&(rowIndex < this.rows.size()))
    {
      this.rows.remove(rowIndex);
      
      this.fireTableRowsDeleted(rowIndex, rowIndex);
    }
  }
  
  public BasicTableCell getCellAt(int rowIndex, int columnIndex)
  {
    if((rowIndex >= 0)&&(rowIndex < rows.size()))
    {
      if((columnIndex >= 0)&&(columnIndex < this.rows.get(rowIndex).getRowSize()))
        return this.rows.get(rowIndex).getCell(columnIndex);
    }
    return null;
  }
  
  public void clearAll()
  {
    this.columns.clear();
    this.rows.clear();
  }
  
  public void clearColumns()
  {
    this.columns.clear();
  }
  
  public void clearRows()
  {
    this.rows.clear();
  }
  
  public Long getRowID(int rowIndex)
  {
    return rows.get(rowIndex).getRowID();
  }
  
  public Icon getTableColumnIcon(int columnIndex)
  {
    return this.columns.get(columnIndex).getIcon();
  }

  /** AbstractTableModel methods. */
  public int getRowCount()
  {
    return this.rows.size();
  }

  public int getColumnCount()
  {
    return this.columns.size();
  }

  public Object getValueAt(int rowIndex, int columnIndex)
  {
    return this.rows.get(rowIndex).getValueAt(columnIndex);
  }
  
  @Override
  public Class getColumnClass(int c)
  {
    if(rows.size() > 0)
    {
      Object value = getValueAt(0, c);
      if(value != null)
        return value.getClass();
    }
  
    return null;
  }
  
  @Override
  public boolean isCellEditable(int row, int col)
  {
    return this.rows.get(row).isCellEditable(col);
  }
  
  @Override
  public void setValueAt(Object value, int row, int col)
  {
    this.rows.get(row).setValueAt(col, value);
    this.fireTableCellUpdated(row, col);
  }
  
  @Override
  public String getColumnName(int column)
  {
    return this.columns.get(column).getName();
  }
}
