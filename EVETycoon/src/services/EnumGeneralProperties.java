/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author hrivanov
 */
public enum EnumGeneralProperties
{
  APPLICATION_NAME("general.application_name", "EVE Tycoon");
  
  /** Name of the property. */
  private final String propertyName;
  /** Default value of the property. */
  private final String defaultValue;

  /**
   * Default constructor
   * @param propertyName Name of the application property.
   * @param additionaInfo Additional info about the property.
   */
  private EnumGeneralProperties(String propertyName, String defaultValue)
  {
    this.propertyName = propertyName;
    this.defaultValue = defaultValue;
  }

  /**
   * Returns the name of the property.
   * @return The name of the property.
   */
  public String propertyName()
  {
    return propertyName;
  }

  /**
   * Returns the default value of the property.
   * @return The default value of the property.
   */
  public String defaultValue()
  {
    return defaultValue;
  }
}
