/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evedata;

import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiResponse;
import com.beimin.eveapi.corporation.starbase.detail.StarbaseDetailResponse;
import com.beimin.eveapi.corporation.starbase.list.ApiStarbase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import services.ImageService;
import services.ImageTypes;

/**
 *
 * @author hrivanov
 */
public class Starbase extends DataResponse implements Serializable
{
  /** Unique ID for this POS. This is used to request details via the StarbaseDetail API page. */
  private long itemID;
  /** Type ID. References the invTypes table. */
  private int typeID;
  /** Name of the starbase type. */
  private String typeName;
  /** Name of the starbase type. */
  private String typeDescription;
  /** Solar system ID. References the mapSolarSystems and mapDenormalize tables. */
  private int locationID;
  /** Celestial object ID. References the mapDenormalize table. */
  private int moonID;
  /** Faction ID of the location. */
  private int locationFactionID;
  /** The description of the location. */
  private String locationDescription;
  /** Security standing of the system. */
  private float securityLevel;
  /** Mode of the POS. */
  private StarbaseStates state;
  /** When a state occured,depends on the state. */
  private Date stateTimestamp;
  /** When the POS will be online or most recently was put online. */
  private Date onlineTimestamp;
  /** The fuel bay capacity of the tower. */
  private int fuelCapacity;
  /** Indicates if the starbase is selected  */
  private int fuelAlarmStatus;
  /** A map of the starbase fuels */
  Map<Integer, StarbaseFuel> fuels;
  /** The icon of the item. */
  private Icon itemIcon;

  /**
   * Default constructor.
   * @param apiStarbase ApiStarbase object to copy from.
   */
  public Starbase(ApiStarbase apiStarbase)
  {
    this.itemID = apiStarbase.getItemID();
    this.typeID = apiStarbase.getTypeID();
    this.locationID = apiStarbase.getLocationID();
    this.moonID = apiStarbase.getMoonID();
    this.state = StarbaseStates.values()[apiStarbase.getState()];
    this.stateTimestamp = apiStarbase.getStateTimestamp();
    this.onlineTimestamp = apiStarbase.getOnlineTimestamp();
    
    this.typeName = "Generic Control Tower";
    this.typeDescription = "Generic Control Tower";
    this.locationDescription = "Somewhere in New Eden";
    this.fuelAlarmStatus = 0;
    this.itemIcon = new ImageIcon();
    
    fuels = new HashMap<Integer, StarbaseFuel>(10);
  }
  
  /**
   * Copy constructor.
   * @param oldStarbase A Starbase object to copy from.
   */
  public Starbase(Starbase oldStarbase)
  {
    super(oldStarbase);
    
    this.itemID = oldStarbase.itemID;
    this.typeID = oldStarbase.typeID;
    this.locationID = oldStarbase.locationID;
    this.moonID = oldStarbase.moonID;
    this.state = oldStarbase.state;
    this.stateTimestamp = oldStarbase.stateTimestamp;
    this.onlineTimestamp = oldStarbase.onlineTimestamp;
    
    this.typeName = oldStarbase.typeName;
    this.typeDescription = oldStarbase.typeDescription;
    this.locationDescription = oldStarbase.locationDescription;
    this.fuelAlarmStatus = oldStarbase.getFuelAlarmStatus();
    this.itemIcon = oldStarbase.itemIcon;
    
    this.fuels = new HashMap<Integer, StarbaseFuel>(oldStarbase.getFuelsMap().size());
    for(Map.Entry<Integer, StarbaseFuel> fuelEntry : oldStarbase.fuels.entrySet())
    {
      this.fuels.put(fuelEntry.getKey(), new StarbaseFuel(fuelEntry.getValue()));
    }
  }
  
  /**
   * Copies information from an ApiResponse object.
   * @param apiResp ApiResponse object to copy from.
   * @throws ApiException
   */
  @Override
  public void copyApiResponse(ApiResponse apiResp) throws ApiException
  {         
    super.copyApiResponse(apiResp);
    StarbaseDetailResponse baseDetails = (StarbaseDetailResponse)apiResp;
    
    StarbaseFuel currFuel = null;
    int cycles = 0;
    
    // Get the starbase cycles, for fuel adjustment
    for(Map.Entry<Integer, Integer> newFuelQty : baseDetails.getFuelMap().entrySet())
    {
      currFuel = this.fuels.get(newFuelQty.getKey());
      if(currFuel != null)
      {
        if((currFuel.getPurpose() != 2)&&(currFuel.getPurpose() != 3))
        {
          cycles = (currFuel.getQuantity() - newFuelQty.getValue()) / currFuel.getConsumedPerHour();
          if(cycles > 0)
            break;
        }
      }
    }
    
    // Adjust the fuel quantities
    for(Map.Entry<Integer, Integer> newFuelQty : baseDetails.getFuelMap().entrySet())
    {
      currFuel = fuels.get(newFuelQty.getKey());
      if(currFuel != null)
      {
        if(((currFuel.getPurpose() == 2)||(currFuel.getPurpose() == 3))&&(cycles > 0))
        {
          if(currFuel.getQuantity() > newFuelQty.getValue())
            currFuel.setConsumedPerHour((currFuel.getQuantity() - newFuelQty.getValue()) / cycles);
        }
        currFuel.setQuantity(newFuelQty.getValue());
      }
    } 
  }
  
  /**
   * Returns the starbase fuel map.
   * @return The starbase fuel map.
   */
  public Map<Integer, StarbaseFuel> getFuelsMap()
  {
    return fuels;
  }
  
  public List<StarbaseFuel> getFuelsValues()
  {
    return new ArrayList(fuels.values());
  }

  public long getItemID()
  {
    return itemID;
  }

  public void setItemID(long itemID)
  {
    this.itemID = itemID;
  }

  public int getTypeID()
  {
    return typeID;
  }
  
  public int getLocationID()
  {
    return locationID;
  }

  public int getMoonID()
  {
    return moonID;
  }

  public String getLocationDescription()
  {
    return locationDescription;
  }

  public void setLocationDescription(String locationDescription)
  {
    this.locationDescription = locationDescription;
  }

  public String getTypeDescription()
  {
    return typeDescription;
  }

  public void setTypeDescription(String typeDescription)
  {
    this.typeDescription = typeDescription;
  }

  public String getTypeName()
  {
    return typeName;
  }

  public void setTypeName(String typeName)
  {
    this.typeName = typeName;
  }

  public int getFuelCapacity()
  {
    return fuelCapacity;
  }

  public void setFuelCapacity(int fuelCapacity)
  {
    this.fuelCapacity = fuelCapacity;
  }

  public int getLocationFactionID()
  {
    return locationFactionID;
  }

  public void setLocationFactionID(int locationFactionID)
  {
    this.locationFactionID = locationFactionID;
  }

  public float getSecurityLevel()
  {
    return securityLevel;
  }

  public void setSecurityLevel(float securityLevel)
  {
    this.securityLevel = securityLevel;
  }

  public StarbaseStates getState()
  {
    return state;
  }

  public int getFuelAlarmStatus()
  {
    fuelAlarmStatus = 0;
    
    for(Map.Entry<Integer, StarbaseFuel> fuel : this.fuels.entrySet())
    {
      if(fuelAlarmStatus < fuel.getValue().checkForAlarm())
         fuelAlarmStatus = fuel.getValue().checkForAlarm();
    }
    
    return fuelAlarmStatus;
  }

  public Icon getItemIcon()
  {
    return itemIcon;
  }
  
  public void loadItemIcon()
  {
    try
    {
      this.itemIcon = ImageService.getInstance().getImage(String.valueOf(typeID), ImageTypes.ICON);
    } 
    catch (ApiException ex)
    {
      this.itemIcon = new ImageIcon();
    }
  }
  
  public Icon getFuelAlarmIcon()
  {
    switch(getFuelAlarmStatus())
    {
      case 0: return ImageService.getInstance().getGreenLightIcon();
      case 1: return ImageService.getInstance().getYellowLightIcon();
      case 2: return ImageService.getInstance().getRedLightIcon();
      default: return ImageService.getInstance().getGrayLightIcon();  
    }
  }
  
  public String getRemainingHoursString()
  {
    int minHours = 9999;
    int minHoursFuelID = 0;
    for(StarbaseFuel currFuel : fuels.values())
    {
      if((minHours > currFuel.getRemainingHours())&&(currFuel.getPurpose() != 4))
      {
        minHours = currFuel.getRemainingHours();
        minHoursFuelID = currFuel.getItemID();
      }
    }
    
    return fuels.get(minHoursFuelID).getRemainingTimeString();
  }
 
  @Override
  public String toString()
  {
    return String.format("%1$s @ %2$s | Cached until: %3$tD %3$tR", this.typeName, this.locationDescription, this.cachedUntil);
  }
}
