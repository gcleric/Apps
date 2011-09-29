/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.fuelplanner;

import evedata.StarbaseFuel;
import java.io.Serializable;

/**
 *
 * @author hrivanov
 */
public class PlanFuel extends StarbaseFuel implements Serializable
{
  /** The planned quantity of the fuel. */
  private Integer plannedQuantity;
  /** The total volume of the fuel. */
  private Float plannedVolume;
  /** The total fuel quantity consumed. */
  private Integer totalConsumption;
  /** Indicates of a fuel is used for the current starbase. */
  private boolean active;

  /**
   * @param itemID itemID ID of the item in the DB.
   * @param itemName Name of the item.
   * @param volume Volume of the item.
   * @param consumedPerHour Comsumed quantity per hour.
   * @param securityLevel Required security level for this fuel.
   * @param purpose Purpose of the fuel
   * @param factionID ID of the faction requiring the fuel.
   * @param plannedQuantity Planned quantity of the fuel.
   */
  public PlanFuel(int itemID, String itemName, float volume, int consumedPerHour, float securityLevel, int purpose, int factionID, Integer plannedQuantity, boolean active)
  {
    super(itemID, itemName, volume, consumedPerHour, securityLevel, purpose, factionID);
    this.plannedQuantity = plannedQuantity;
    this.plannedVolume = this.plannedQuantity * this.volume;
    this.totalConsumption = 0;
    this.active = active;
  }

  /**
   * StarbaseFuel copying constructor.
   * @param fuel StarbaseFuel object to copy from.
   * @param plannedQuantity Planned quantity of the fuel.
   */
  public PlanFuel(StarbaseFuel fuel, int plannedQuantity, boolean active)
  {
    super(fuel);
    this.plannedQuantity = plannedQuantity;
    this.plannedVolume = plannedQuantity * this.volume;
    this.totalConsumption = 0;
    this.active = active;
  }
  
  /**
   * Copy constructor
   * @param oldPlannedFuel Object to copy from
   */
  public PlanFuel(PlanFuel oldPlannedFuel)
  {
    super(oldPlannedFuel);
    this.plannedQuantity = oldPlannedFuel.plannedQuantity;
    this.plannedVolume = oldPlannedFuel.plannedVolume;
    this.totalConsumption = oldPlannedFuel.totalConsumption;
    this.active = oldPlannedFuel.active;
  }
  
  public void calcPlannedQuantity(int plannedHours)
  {
    this.plannedQuantity = ((this.consumedPerHour * plannedHours) - this.currentQuantity);
    this.plannedQuantity = ((this.plannedQuantity < 0) ? 0 : this.plannedQuantity);
    this.plannedVolume = this.plannedQuantity * this.volume;
  }
  

  /**
   * Returns the planned fuel quantity.
   * @return The planned fuel quantity.
   */
  public Integer getPlannedQuantity()
  {
    return this.plannedQuantity;
  }

  /**
   * Sets a new planned fuel quantity.
   * @param plannedQuantity A new planned fuel quantity.
   */
  public void setPlannedQuantity(Integer plannedQuantity)
  {
    this.plannedQuantity = plannedQuantity;
    this.plannedVolume = this.plannedQuantity * this.volume;
  }
  
  /**
   * Adds a new planned fuel quantity.
   * @param plannedQuantity A new planned fuel quantity.
   */
  public void addPlannedQuantity(Integer plannedQuantity)
  {
    if(this.plannedQuantity == -1)
    {
      this.plannedQuantity = plannedQuantity;
    }
    else
    {
      this.plannedQuantity += plannedQuantity;
    }
    
    this.plannedVolume = this.plannedQuantity * this.volume;
  }

  /**
   * Returns the planned fuel volume.
   * @return The planned fuel volume.
   */
  public Float getPlannedVolume()
  {
    return plannedVolume;
  }

  /**
   * Sets a new planned fuel volume.
   * @param plannedVolume 
   */
  public void setPlannedVolume(Float plannedVolume)
  {
    this.plannedVolume = plannedVolume;
  }

  /**
   * Sets the stored in corporation hangars fuel quantity.
   * @param storedQuantity The new stored quantity.
   */
  public Integer setStoredQuantity(Integer storedQuantity)
  {   
    if(storedQuantity < this.plannedQuantity)
    {
      this.plannedQuantity -= storedQuantity;
      return 0;
    }
    else
    {
      storedQuantity -= this.plannedQuantity;
      this.plannedQuantity = 0;
      return storedQuantity;
    }
  }

  /**
   * Indicates if the fuel is in use for this starbase.
   * @return true if the fuel is used, false otehrwise.
   */
  public boolean isActive()
  {
    if(this.purpose == 4)
    {
      return false;
    }
    else
    {
      return active;
    }
  }

  /**
   * Sets the usage status of the fuel.
   * @param active true if used, false otherwise.
   */
  public void setActive(boolean active)
  {
    this.active = active;
  }

  /**
   * Returns the total fuel quantity consumption.
   * @return The total fuel quantity consumption.
   */
  public Integer getTotalConsumption()
  {
    return totalConsumption;
  }

  /**
   * Sets a new total fuel quantity consumption.
   * @param totalConsumption New total fuel quantity consumption.
   */
  public void setTotalConsumption(Integer totalConsumption)
  {
    this.totalConsumption = totalConsumption;
  }
  
  /**
   * 
   * @param quantity 
   */
  public void addTotalConsumption(Integer quantity)
  {
    totalConsumption += quantity;
  }
}
