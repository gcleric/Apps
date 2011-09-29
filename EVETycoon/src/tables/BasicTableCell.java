/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.awt.Color;
import java.io.Serializable;
import javax.swing.Icon;

/**
 *
 * @author hrivanov
 */
public class BasicTableCell<T> implements Serializable
{
  private BasicTableRow parent;
  private T value;
  private boolean isEditable;
  private String label;
  private String tooltipText;
  private Color bgColor;
  private Color fgColor;

  public BasicTableCell(BasicTableRow parent, T value, boolean isEditable)
  {
    this.parent = parent;
    this.value = value;
    this.isEditable = isEditable;
    this.label = "";
    this.tooltipText = "";
    this.bgColor = parent.getBgColor();
    this.fgColor = parent.getFgColor();
  }

  public BasicTableCell(BasicTableRow parent, T value, boolean isEditable, String label, String tooltip)
  {
    this.parent = parent;
    this.value = value;
    this.isEditable = isEditable;
    this.label = label;
    this.tooltipText = tooltip;
    this.bgColor = parent.getBgColor();
    this.fgColor = parent.getFgColor();
  }

  public BasicTableCell(BasicTableRow parent, T value, boolean isEditable, String label, String tooltip, Color bgColor, Color fgColor)
  {
    this.parent = parent;
    this.value = value;
    this.isEditable = isEditable;
    this.label = label;
    this.tooltipText = tooltip;
    if(bgColor == null)
      this.bgColor = parent.getBgColor();
    else
      this.bgColor = bgColor;
    
    if(fgColor == null)
      this.fgColor = parent.getFgColor();
    else
      this.fgColor = fgColor;
  }

  public Color getBgColor()
  {
    return bgColor;
  }

  public void setBgColor(Color bgColor)
  {
    this.bgColor = bgColor;
  }

  public Color getFgColor()
  {
    return fgColor;
  }

  public void setFgColor(Color fgColor)
  {
    this.fgColor = fgColor;
  }

  public boolean isIsEditable()
  {
    return isEditable;
  }

  public void setIsEditable(boolean isEditable)
  {
    this.isEditable = isEditable;
  }

  public String getLabel()
  {
    return label;
  }

  public void setLabel(String label)
  {
    this.label = label;
  }

  public BasicTableRow getParent()
  {
    return parent;
  }

  public void setParent(BasicTableRow parent)
  {
    this.parent = parent;
  }

  public String getTooltipText()
  {
    return tooltipText;
  }

  public void setTooltipText(String tooltip)
  {
    this.tooltipText = tooltip;
  }

  public T getValue()
  {
    return value;
  }

  public void setValue(T value)
  {
    this.value = value;
  }
}
