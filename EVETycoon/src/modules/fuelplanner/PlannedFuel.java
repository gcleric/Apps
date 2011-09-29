/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.fuelplanner;

import evedata.ApiItem;
import evedata.StarbaseFuel;
import java.io.Serializable;

/**
 *
 * @author hrivanov
 */
public class PlannedFuel extends ApiItem implements Serializable
{
  /** The planned quantity of the fuel. */
  private Integer plannedQuantity;
  /** The total volume of the fuel. */
  private Float plannedVolume;
  /** Array referencing the data. */
  /** The planned quantity of the fuel. */
  private Integer storedQuantity;

  /**
   * Default constructor.
   * @param itemID ID of the item in the DB.
   * @param itemName Name of the item.
   * @param volume Volume of the item.
   * @param iconID Item's icon ID.
   * @param plannedQuantity Planned quantity of the fuel.
   */
  public PlannedFuel(int itemID, String itemName, float volume, int plannedQuantity)
  {
    super(itemID, itemName, volume);
    this.plannedQuantity = plannedQuantity;
    this.plannedVolume = plannedQuantity * volume;
    this.storedQuantity = 0;
  }

  /**
   * StarbaseFuel copying constructor.
   * @param fuel StarbaseFuel object to copy from.
   * @param plannedQuantity Planned quantity of the fuel.
   */
  public PlannedFuel(StarbaseFuel fuel, int plannedQuantity)
  {
    super(fuel);
    this.plannedQuantity = plannedQuantity;
    this.plannedVolume = plannedQuantity * volume;
    this.storedQuantity = 0;
  }
  
  /**
   * Copy constructor
   * @param oldPlannedFuel Object to copy from
   */
  public PlannedFuel(PlannedFuel oldPlannedFuel)
  {
    super(oldPlannedFuel);
    this.plannedQuantity = oldPlannedFuel.plannedQuantity;
    this.plannedVolume = oldPlannedFuel.plannedVolume;
    this.storedQuantity = oldPlannedFuel.storedQuantity;
  }

  /**
   * Returns the planned fuel quantity.
   * @return The planned fuel quantity.
   */
  public Integer getPlannedQuantity()
  {
    return plannedQuantity;
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
    this.plannedVolume = this.plannedQuantity * this.volume;
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
   * Returns the stored in corporation hangars fuel quantity.
   * @return The stored fuel quantity.
   */
  public Integer getStoredQuantity()
  {
    return storedQuantity;
  }

  /**
   * Sets the stored in corporation hangars fuel quantity.
   * @param storedQuantity The new stored quantity.
   */
  public void setStoredQuantity(Integer storedQuantity)
  {
    this.storedQuantity = storedQuantity;
  }
}
