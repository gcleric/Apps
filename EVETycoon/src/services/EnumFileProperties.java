/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author hrivanov
 */
public enum EnumFileProperties
{
  GENERAL_PATH("files.general_path", ".", ""),
  IMAGE_CACHE_PATH("files.image_cache_path", "images", ""),
  ACCOUNT_FILE("files.account_file", "account.dat", "account file"),
  STARBASE_FILE("files.starbase_file", "starbase.dat", "starbase file"),
  FUELPLANNER_FILE("files.fuelplanner_file", "fuelplanner.dat", "fuel planner file");
  
  /** Name of the property. */
  private final String propertyName;
  /** Default value of the property. */
  private final String defaultValue;
  /** Additional info the property. */
  private final String additionaInfo;

  /**
   * Default constructor
   * @param propertyName Name of the application property.
   * @param additionaInfo Additional info about the property.
   */
  private EnumFileProperties(String propertyName, String defaultValue, String additionaInfo)
  {
    this.propertyName = propertyName;
    this.defaultValue = defaultValue;
    this.additionaInfo = additionaInfo;
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
  
  /**
   * Returns the additional info for the property.
   * @return The additional info for the property.
   */
  public String additionaInfo()
  {
    return additionaInfo;
  }
}
