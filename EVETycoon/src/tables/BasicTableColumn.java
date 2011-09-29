/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.io.Serializable;
import javax.swing.Icon;

/**
 *
 * @author hrivanov
 */
public class BasicTableColumn<E> implements Serializable
{
  private String name;
  private Icon  icon;
  private String  tooltip;
  private Class<E> columnClass;

  public BasicTableColumn(String columnName, Class<E> columnClass)
  {
    this.name = columnName;
    this.icon = null;
    this.tooltip = "";
    this.columnClass = columnClass;
  }
  
  public BasicTableColumn(String columnName, Class<E> columnClass, Icon columnIcon, String tooltip)
  {
    this.name = columnName;
    this.icon = columnIcon;
    this.tooltip = tooltip;
    this.columnClass = columnClass;
  }
  
  public String getName()
  {
    return name;
  }

  public Icon getIcon()
  {
    return icon;
  }

  public String getTooltip()
  {
    return tooltip;
  }

  public Class<E> getColumnClass()
  {
    return columnClass;
  }
}
