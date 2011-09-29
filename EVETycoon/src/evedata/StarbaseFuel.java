/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evedata;

import java.io.Serializable;
import javax.swing.Icon;
import services.ImageService;

/**
 * Class representing a starbase fuel.
 * @author hrivanov
 */
public class StarbaseFuel extends ApiItem implements Alarm, Serializable, Comparable<StarbaseFuel>
{
  /** Quantity of the fuel. */
  protected Integer currentQuantity;
  /** Quantity of fuel consumed per hour. */
  protected Integer consumedPerHour;
  /** Required security level. */
  protected Float securityLevel;
  /** The purpose of the fuel.*/
  protected int purpose;
  /** Indicates if there is an alarm. */
  protected Boolean hasAlarm;
  /** Warning time period in hours remaining, below which the alarm goes off. */
  protected Integer warningHours;
  /** Fatal time period in hours remaining, below which the alarm goes off. */
  protected Integer urgentHours;
  /** Holder of the remaining time string. */
  protected StringBuilder remainingTimeString;
  /** Icon indicating the fuel level status */
  protected Icon statusIcon;
  /** ID of the faction requireing the fuel. */
  protected int factionID;
  
  public static final int HEAVY_WATER = 16272;
  
  public static final int LIQUID_OZONE = 16273;

  /**
   * Default constructor.
   * @param itemID ID of the fuel.
   * @param itemName Name of the fuel.
   * @param volume Volume of the fuel.
   * @param consumedPerHour Quantity of fuel consumed per hour.
   * @param securityLevel Security level required for this fuel.
   * @param purpose Purpose of the fuel - online, CPU, powergrid, reinforce
   * @param factionID ID of the faction requireing the fuel.
   */
  public StarbaseFuel(int itemID, String itemName, float volume, int consumedPerHour, float securityLevel, int purpose, int factionID)
  {
    super(itemID, itemName, volume);
    this.currentQuantity = 0;
    this.consumedPerHour = consumedPerHour;
    this.securityLevel = securityLevel;
    this.hasAlarm = false;
    this.purpose = purpose;
    this.warningHours = 48;
    this.urgentHours = 24;
    this.factionID = factionID;
    
    statusIcon = ImageService.getInstance().getGrayLightIcon();
  }
  
  /**
   * Copy constructor
   * @param oldFuel StarbaseFuel object to copy from.
   */
  public StarbaseFuel(StarbaseFuel oldFuel)
  {
    super(oldFuel);
    
    this.currentQuantity = oldFuel.currentQuantity;
    this.consumedPerHour = oldFuel.consumedPerHour;
    this.securityLevel = oldFuel.securityLevel;
    this.purpose = oldFuel.purpose;
    this.hasAlarm = oldFuel.hasAlarm;
    this.warningHours = oldFuel.warningHours;
    this.urgentHours = oldFuel.urgentHours;
    this.statusIcon = oldFuel.statusIcon;
    this.factionID = oldFuel.factionID;
  }

  /**
   * Returns the quantity of fuel consumed per hour.
   * @return Quantity of fuel consumed per hour.
   */
  public Integer getConsumedPerHour()
  {
    return consumedPerHour;
  }

  /**
   * Sets the quantity of fuel consumed per hour.
   * @param quantity New quantity of fuel consumed per hour.
   */
  public void setConsumedPerHour(Integer consumedPerHour)
  {
    this.consumedPerHour = consumedPerHour;
  }

  /**
   * Returns the current fuel quantity.
   * @return The current fuel quantity.
   */
  public Integer getQuantity()
  {
    return currentQuantity;
  }

  /**
   * Sets the current fuel quantity
   * @param quantity A new fuel quantity.
   */
  public void setQuantity(Integer quantity)
  {
    this.currentQuantity = quantity;
  }

  /**
   * Returns the remaining hours until the fuel runs out.
   * @return The remaining hours.
   */
  public Integer getRemainingHours()
  {
    if(this.consumedPerHour > 0)
      return this.currentQuantity / this.consumedPerHour;
    else
      return this.currentQuantity;
  }

  /**
   * Returns the remaining hours until the fuel runs out.
   * @return 
   */
  public String getRemainingTimeString()
  {
    if(this.consumedPerHour == 0)
      return "N/A";
    else
      return String.format("%1$02dD %2$02dH", (getRemainingHours() / 24), (getRemainingHours() % 24));
  }

  public Float getSecurityLevel()
  {
    return securityLevel;
  }

  /**
   * Checks if there is an alarm condition.
   * @return 0 - no alarm, 1 - warning alarm, 2 - urgent alarm.
   */
  public int checkForAlarm()
  {
    if (this.purpose == 4)
    {
      this.hasAlarm = false;
      statusIcon = ImageService.getInstance().getGrayLightIcon();
      return 0;
    }

    Integer remainingHours = getRemainingHours();

    if (remainingHours <= this.urgentHours)
    {
      this.hasAlarm = true;
      statusIcon = ImageService.getInstance().getRedLightIcon();
      return 2;
    } else if (remainingHours <= this.warningHours)
    {
      this.hasAlarm = true;
      statusIcon = ImageService.getInstance().getYellowLightIcon();
      return 1;
    } else
    {
      this.hasAlarm = false;
      statusIcon = ImageService.getInstance().getGreenLightIcon();
      return 0;
    }
  }

  /**
   * Sets a time period in hours, below which the alarm becomes active.
   * @param alarmHours Time period in hours, below which the alarm becomes active.
   */
  public void setAlarmHours(int warningHours, int urgentHours)
  {
    this.warningHours = warningHours;
    this.urgentHours = urgentHours;
  }

  public int getPurpose()
  {
    return purpose;
  }

  public Icon getStatusIcon()
  {
    return statusIcon;
  }

  public int getFactionID()
  {
    return factionID;
  }

  @Override
  public String toString()
  {
    return this.itemName + " | " + this.consumedPerHour + " | " + this.currentQuantity;
  }

  public int compareTo(StarbaseFuel o)
  {
    int lastCmp = (int)Math.signum((float)this.getPurpose() - o.getPurpose());
    return (lastCmp != 0 ? lastCmp : this.getItemName().compareTo(o.getItemName()));
  }
}
