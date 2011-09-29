/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author hrivanov
 */
public class BasicTableRow implements Serializable
{
  private Long rowID;
  private Color bgColor;
  private Color fgColor;
  private ArrayList< BasicTableCell> cells;

  public BasicTableRow()
  {
    cells = new ArrayList<BasicTableCell>();
    this.bgColor = null;
    this.fgColor = null;
  }
  
  public BasicTableRow(int initialCapacity)
  {
    cells = new ArrayList<BasicTableCell>(initialCapacity);
    this.bgColor = null;
    this.fgColor = null;
  }

  public BasicTableRow(long rowID, Color bgColor, Color fgColor)
  {
    this.rowID = rowID;
    this.bgColor = bgColor;
    this.fgColor = fgColor;
  }
  
  public void fillEmptyCells(int cellsNumber)
  {
    while(cellsNumber > 0)
    {
      cells.add(new BasicTableCell(this, "", false));
      cellsNumber--;
    }
  }
  
  public int getRowSize()
  {
    return cells.size();
  }

  public Long getRowID()
  {
    return rowID;
  }

  public void setRowID(long rowID)
  {
    this.rowID = rowID;
  }
  
  public void addCell(BasicTableCell newCell)
  {
    cells.add(newCell);
  }
  
  public BasicTableCell getCell(int columnIndex)
  {
    if((columnIndex >= 0)&&(columnIndex < cells.size()))
    {
      return cells.get(columnIndex);
    }
    else
      return null;
  }
  
  public void setValueAt(int columnIndex, Object newValue)
  {
    if((columnIndex >= 0)&&(columnIndex <= cells.size() - 1))
    {
      cells.get(columnIndex).setValue(newValue);
    }
  }
  
  public Object getValueAt(int columnIndex)
  {
    if((columnIndex >= 0)&&(columnIndex <= cells.size() - 1))
    {
      return cells.get(columnIndex).getValue();
    }
    else
    {
      return null;
    }
  }
  
  public boolean isCellEditable(int columnIndex)
  {
    if((columnIndex >= 0)&&(columnIndex <= cells.size() - 1))
    {
      return cells.get(columnIndex).isIsEditable();
    }
    else
    {
      return false;
    }
  }

  public Color getBgColor()
  {
    return this.bgColor;
  }

  public void setBgColor(Color bgColor)
  {
    this.bgColor = bgColor;
    for(BasicTableCell cell : cells)
    {
      cell.setBgColor(bgColor);
    }
  }

  public Color getFgColor()
  {
    return fgColor;
  }

  public void setFgColor(Color fgColor)
  {
    this.fgColor = fgColor;
    for(BasicTableCell cell : cells)
    {
      cell.setFgColor(fgColor);
    }
  }
}
