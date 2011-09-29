/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author hrivanov
 */
public enum DBQueryTypes
{
  CONTROL_TOWER_TYPES("SELECT typeName "
                  + ", description "
                  + ", graphicID"
                  + ", capacity "
                  + " FROM getStarbaseTypes "
                  + " WHERE typeID = ? "),
  
  STARBASE_LOCATION("SELECT regionName "
                      + ", constellationName "
                      + ", itemName"
                      + ", security "
                      + ", factionID "
                      + " FROM getStarbaseLocation "
                      + " WHERE solarSystemID = ? "
                      + " AND itemID = ? "),
  
  STARBASE_FUELS("SELECT fuelID "
                    + ", fuelName"
                    + ", fuelDescription"
                    + ", volume "
                    + ", consumedPerHour "
                    + ", minSecurityLevel "
                    + ", purpose "  
                    + ", factionID"
                    + " FROM getStarbaseFuels "
                    + " WHERE controlTowerTypeID = ? "
                    + " AND minSecurityLevel <= ? "
                    + " AND ((factionID=? OR factionID=0))"),
  
  CONTROL_TOWER_FUEL_TYPES("SELECT fuelID "
                      + ", fuelName"
                      + ", fuelDescription"
                      + ", volume "
                      + ", consumedPerHour "
                      + ", minSecurityLevel "
                      + ", purpose "  
                      + ", factionID"
                      + " FROM getStarbaseFuels "
                      + " GROUP BY fuelID "
                          + ", fuelName "
                          + ", fuelDescription "
                          + ", volume "
                          + ", purpose "
                          + ", iconID "
                          + ", factionID "
                    + " ORDER BY purpose, fuelName "),
  
  GENERIC_CONTROL_TOWER_TYPES("SELECT typeID "
                  + ", typeName "
                  + ", description "
                  + ", heavyWaterQty"
                  + ", liquidOzoneQty "
                  + " FROM getGenericControlTowerList "
                  + " ORDER BY typeName "), 
  
  GENERIC_CONTROL_TOWER_FUEL_TYPES("SELECT fuelID "
                    + ", fuelName"
                    + ", fuelDescription"
                    + ", volume "
                    + ", iconID "
                    + ", consumedPerHour "
                    + ", minSecurityLevel "
                    + ", purpose "  
                    + ", factionID"
                    + " FROM getStarbaseFuels "
                    + " WHERE controlTowerTypeID = ? ");
  
  /** The SQL query to execute. */
  private final String query;
  
  /**
   * Default constructor.
   * @param query A SQL query.
   */
  private DBQueryTypes(String query)
  {
    this.query = query;
  };
  
  /**
   * Returns the associated SQL query.
   * @return The associated SQL query.
   */
  public String query()
  {
    return query;
  }
}
