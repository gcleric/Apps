/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 * Enumeration of the used application properties.
 * @author hrivanov
 */
public enum PropertyTypes
{
  PROXY_HOST("ProxyHost", "proxy.imxbg", ""),      
  PROXY_PORT("ProxyPort", "3128", ""),      
  ACCOUNT_FILE("AccountFile", "account.dat", "account"),
  STARBASE_FILE("StarbaseFile", "starbase.dat", "starbase"),
  FUELPLANNER_FILE("FuelplanFile", "fuelplan.dat", "fuelplan"),
  IMAGE_DIR("ImageCacheDir", "images", "Image cache directory"),
  EVE_IMAGES_URL("EveImagesURL", "http://image.eveonline.com", "The EVE images server URL");
  
  /** The name of the property. */
  private final String propertyName;
  /** The default value of the property. */
  private final String defaultValue;
  /** Additional info about the property. */
  private final String additionaInfo;

  /**
   * Default constructor
   * @param propertyName Name of the application property.
   * @param additionaInfo Additional info about the property.
   */
  private PropertyTypes(String propertyName, String defaultValue, String additionaInfo)
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
   * Returns additional info about the property.
   * @return Additional info about the property.
   */
  public String additionaInfo()
  {
    return additionaInfo;
  }
}
