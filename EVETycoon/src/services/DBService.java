/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.corporation.starbase.list.ApiStarbase;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import evedata.Starbase;
import evedata.StarbaseFuel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modules.fuelplanner.PlanFuel;

/**
 * Class implementing database operations.
 * @author hrivanov
 */
public class DBService
{
  private static DBService instance = null;
  
  private SQLServerDataSource mainDS;
  
  private final String sqliteConnString = "jdbc:sqlite:database\\evedb.db";
  
  /**
   * Default constructor.
   */
  protected DBService()
  {
    mainDS = new SQLServerDataSource();
            
    mainDS.setServerName("localhost");
    mainDS.setInstanceName("DEVDB");
    mainDS.setDatabaseName("EVE");
    mainDS.setUser("sa");
    mainDS.setPassword("devdb");
    try
    {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException ex)
    {
      Logger.getLogger(DBService.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  /**
   * Returns an instance to the ApiServices object.
   * @return The instance of the ApiServices object
   */
  public static DBService getInstance()
  {
    if(instance == null)
    {
      instance = new DBService();
    }
    
    return instance;
  }
  
  /**
   * Updates an empty starbase object with data from the DB.
   * @param noInfoBase An empty starbase object.
   * @throws SQLException
   * @throws ApiException 
   */
  public void getStarbaseInfo(Starbase noInfoBase) throws SQLException, ApiException
  {
    //Connection dbConn = mainDS.getConnection();
    Connection dbConn = DriverManager.getConnection(sqliteConnString);
    
    /** Starbase type retrieval. */
    // Prepare the starbase type statement.
    PreparedStatement sbTypeStmt = dbConn.prepareStatement(DBQueryTypes.CONTROL_TOWER_TYPES.query());
    sbTypeStmt.setInt(1, noInfoBase.getTypeID());
    
    
    // Get the starbase type data.
    ResultSet sbTypeRS = sbTypeStmt.executeQuery();
    
    if(sbTypeRS.next())
    {
      noInfoBase.setTypeName(sbTypeRS.getString("typeName"));
      noInfoBase.setTypeDescription(sbTypeRS.getString("description"));
      noInfoBase.setFuelCapacity(sbTypeRS.getInt("capacity"));
      
      if(sbTypeRS.next())
      {
        throw new ApiException("More than one row per starbase found!");
      }
    }
    else
    {
      throw new ApiException("No type data found for starbase ID:" + noInfoBase.getTypeID());
    }
    // Close the data set and statement.
    sbTypeRS.close();
    sbTypeStmt.close();
    
    /** Starbase location retrieval. */
    // Prepare the starbase location statement.
    PreparedStatement sbLocationStmt = dbConn.prepareStatement(DBQueryTypes.STARBASE_LOCATION.query());
    sbLocationStmt.setInt(1, noInfoBase.getLocationID());
    sbLocationStmt.setInt(2, noInfoBase.getMoonID());
    
    // Get the starbase location data.
    ResultSet sbLocationRS = sbLocationStmt.executeQuery();
    if(sbLocationRS.next())
    {
      noInfoBase.setLocationDescription(sbLocationRS.getString("regionName") + " - " + sbLocationRS.getString("constellationName") + " - " + sbLocationRS.getString("itemName"));
      noInfoBase.setSecurityLevel(sbLocationRS.getFloat("security"));
      noInfoBase.setLocationFactionID(sbLocationRS.getInt("factionID"));
              
      if(sbLocationRS.next())
      {
        throw new ApiException("More than one row per starbase found!");
      }
    }
    else
    {
      noInfoBase.setLocationDescription("Unknown location.");
      noInfoBase.setSecurityLevel(1.0f);
      noInfoBase.setLocationFactionID(0);
    }
     // Close the data set and statement.
    sbLocationRS.close();
    sbLocationStmt.close();
    
    /** Starbase fuel info retrieval. */
    // Prepare the starbase fuel info statement.
    PreparedStatement sbFuelInfoStmt = dbConn.prepareStatement(DBQueryTypes.STARBASE_FUELS.query());
    sbFuelInfoStmt.setInt(1, noInfoBase.getTypeID());
    sbFuelInfoStmt.setFloat(2, noInfoBase.getSecurityLevel());
    sbFuelInfoStmt.setInt(3, noInfoBase.getLocationFactionID());
    
    // Get the starbase fuel info data.
    ResultSet sbFuelInfoRS = sbFuelInfoStmt.executeQuery();
    Map<Integer, StarbaseFuel> newFuels = noInfoBase.getFuelsMap();
    StarbaseFuel newFuel = null;
    int itemID;
    String itemName;
    float volume;
    int consumedPerHour;
    float securityLevel;
    int purpose;
    int factionID;
    while(sbFuelInfoRS.next())
    {
      itemID = sbFuelInfoRS.getInt("fuelID");
      itemName = sbFuelInfoRS.getString("fuelName");
      volume = sbFuelInfoRS.getFloat("volume");
      consumedPerHour = sbFuelInfoRS.getInt("consumedPerHour");
      securityLevel = sbFuelInfoRS.getFloat("minSecurityLevel");
      purpose = sbFuelInfoRS.getInt("purpose");
      factionID = sbFuelInfoRS.getInt("factionID");
      
      newFuel = new StarbaseFuel(itemID, itemName, volume, consumedPerHour, securityLevel, purpose, factionID);
      newFuel.loadItemIcon();
      newFuels.put(itemID, newFuel);
    }
    
    if(newFuels.isEmpty())
      throw new ApiException("No fuel info data found for starbase ID:" + noInfoBase.getTypeID());
    
     // Close the data set and statement.
    sbFuelInfoRS.close();
    sbFuelInfoStmt.close();
    
    dbConn.close();
  }
  
  /**
   * Returns all starbase fuel types
   * @return A map with fuelID and the fuel object.
   * @throws SQLException 
   */
  public Map<Integer, PlanFuel> getStarbaseFuelTypes() throws SQLException
  {
    /** Starbase fuel info retrieval. */
    //Connection dbConn = mainDS.getConnection();
    Connection dbConn = DriverManager.getConnection(sqliteConnString);
    // Prepare the starbase fuel info statement.
    PreparedStatement sbFuelInfoStmt = dbConn.prepareStatement(DBQueryTypes.CONTROL_TOWER_FUEL_TYPES.query());
    
    // Get the starbase fuel info data.
    ResultSet sbFuelInfoRS = sbFuelInfoStmt.executeQuery();
    Map<Integer, PlanFuel> retList = new LinkedHashMap<Integer, PlanFuel>(18);

    int itemID;
    String itemName;
    float volume;
    int consumedPerHour;
    float securityLevel;
    int purpose;
    int factionID;
    PlanFuel newPlannedFuel;

    while(sbFuelInfoRS.next())
    {  
      itemID = sbFuelInfoRS.getInt("fuelID");
      itemName = sbFuelInfoRS.getString("fuelName");
      volume = sbFuelInfoRS.getFloat("volume");
      consumedPerHour = sbFuelInfoRS.getInt("consumedPerHour");
      securityLevel = sbFuelInfoRS.getFloat("minSecurityLevel");
      purpose = sbFuelInfoRS.getInt("purpose");
      factionID = sbFuelInfoRS.getInt("factionID");
      
      newPlannedFuel = new PlanFuel(itemID, itemName, volume, consumedPerHour, securityLevel, purpose, factionID, factionID, false);
      retList.put(itemID, newPlannedFuel);
    }
  
     // Close the data set and statement.
    sbFuelInfoRS.close();
    sbFuelInfoStmt.close();
    
    dbConn.close();
    
    return retList;
  }
  
  /**
   * Returns a list of all available in the game control tower types
   * @return 
   */
  public List<Starbase> getControlTowerTypesList() throws SQLException, ApiException
  {
    List<Starbase> retList = null;

    /** Starbase fuel info retrieval. */
    Connection dbConn = DriverManager.getConnection(sqliteConnString);
    // Prepare the starbase fuel info statement.
    Statement ctTypeListStmt = dbConn.createStatement();

    // Get the starbase fuel info data.
    ResultSet ctTypeListRS = ctTypeListStmt.executeQuery(DBQueryTypes.GENERIC_CONTROL_TOWER_TYPES.query());
    retList = new ArrayList<Starbase>(42);

    ApiStarbase newApiStarbase = new ApiStarbase();
    Starbase newStarbase = null;
    
    while(ctTypeListRS.next())
    {
      newApiStarbase.setTypeID(ctTypeListRS.getInt("typeID"));

      newStarbase = new Starbase(newApiStarbase);
      getGenericStarbaseInfo(newStarbase, false);
      newStarbase.loadItemIcon();
      retList.add(newStarbase);
    }

     // Close the data set and statement.
    ctTypeListRS.close();
    ctTypeListStmt.close();

    dbConn.close();
    
    return retList;
  }
  
  public void getGenericStarbaseInfo(Starbase genericStarBase, boolean updateFuels) throws SQLException, ApiException
  {
    Connection dbConn = DriverManager.getConnection(sqliteConnString);
    if(!updateFuels)
    {   
      /** Starbase type retrieval. */
      // Prepare the starbase type statement.
      PreparedStatement sbTypeStmt = dbConn.prepareStatement(DBQueryTypes.CONTROL_TOWER_TYPES.query());
      sbTypeStmt.setInt(1, genericStarBase.getTypeID());

      // Get the starbase type data.
      ResultSet sbTypeRS = sbTypeStmt.executeQuery();

      if(sbTypeRS.next())
      {
        genericStarBase.setTypeName(sbTypeRS.getString("typeName"));
        genericStarBase.setTypeDescription(sbTypeRS.getString("description"));
        genericStarBase.setFuelCapacity(sbTypeRS.getInt("capacity"));

        if(sbTypeRS.next())
        {
          throw new ApiException("More than one row per starbase found!");
        }
      }
      else
      {
        throw new ApiException("No type data found for starbase ID:" + genericStarBase.getTypeID());
      }
      // Close the data set and statement.
      sbTypeRS.close();
      sbTypeStmt.close();
    }
    
    /** Starbase fuel info retrieval. */
    // Prepare the starbase fuel info statement.
    PreparedStatement sbFuelInfoStmt = dbConn.prepareStatement(DBQueryTypes.GENERIC_CONTROL_TOWER_FUEL_TYPES.query());
    sbFuelInfoStmt.setInt(1, genericStarBase.getTypeID());
    
    // Get the starbase fuel info data.
    ResultSet sbFuelInfoRS = sbFuelInfoStmt.executeQuery();
    Map<Integer, StarbaseFuel> newFuels = genericStarBase.getFuelsMap();
    StarbaseFuel newFuel = null;
    int itemID;
    String itemName;
    float volume;
    int consumedPerHour;
    float securityLevel;
    int purpose;
    int factionID;
    while(sbFuelInfoRS.next())
    {
      itemID = sbFuelInfoRS.getInt("fuelID");
      itemName = sbFuelInfoRS.getString("fuelName");
      volume = sbFuelInfoRS.getFloat("volume");
      consumedPerHour = sbFuelInfoRS.getInt("consumedPerHour");
      securityLevel = sbFuelInfoRS.getFloat("minSecurityLevel");
      purpose = sbFuelInfoRS.getInt("purpose");
      factionID = sbFuelInfoRS.getInt("factionID");
      
      newFuel = new StarbaseFuel(itemID, itemName, volume, consumedPerHour, securityLevel, purpose, factionID);
      newFuel.loadItemIcon();
      newFuels.put(itemID, newFuel);
    }
    
    if(newFuels.isEmpty())
      throw new ApiException("No fuel info data found for starbase ID:" + genericStarBase.getTypeID());
    
     // Close the data set and statement.
    sbFuelInfoRS.close();
    sbFuelInfoStmt.close();
    
    dbConn.close();
  }
}

/*
 * 
 */