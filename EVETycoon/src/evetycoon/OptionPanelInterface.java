/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evetycoon;

import java.util.Properties;

/**
 *
 * @author hrivanov
 */
public interface OptionPanelInterface
{
  /**
   * Returns the properties of the current object.
   * @return The properties of the current object.
   */
  Properties getProperties();
  
  /**
   * Sets new properties for the current object.
   * @param networkProperties New properties.
   */
  void setProperties(Properties newProperties);
}
