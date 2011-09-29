/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.fuelplanner;

import evedata.Starbase;
import evedata.StarbaseFuel;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hrivanov
 */
public class FuelPlan implements Serializable
{
  /** Planned working hours. */
  private int plannedHours;
  /** Starbase associated with this plan. */
  private Starbase starBase;
  /** Active status of the plan. */
  private boolean active;
  /** Planned fuels map. */
  private Map<Integer, PlanFuel> plannedFuelsMap;

  /**
   * Default constructor.
   * @param plannedHours Planned working hours.
   * @param starBase Starbase associated with this plan.
   */
  public FuelPlan(int plannedHours, Starbase starBase)
  {
    this.plannedHours = plannedHours;
    this.starBase = starBase;
    this.active = false;
    
    plannedFuelsMap = new HashMap<Integer, PlanFuel>(this.starBase.getFuelsMap().size());
    for(Map.Entry<Integer, StarbaseFuel> starbaseFuel : this.starBase.getFuelsMap().entrySet())
    {
      plannedFuelsMap.put(starbaseFuel.getKey(), new PlanFuel(starbaseFuel.getValue(), plannedHours, false));
    }
  }
  
  /**
   * Copy constructor
   * @param oldPlan Old FuelPlan object to copy from.
   */
  public FuelPlan(FuelPlan oldPlan)
  {
    this.plannedHours = oldPlan.plannedHours;
    this.starBase = oldPlan.starBase;
    this.active = oldPlan.active;
    
    plannedFuelsMap = new HashMap<Integer, PlanFuel>(oldPlan.plannedFuelsMap.size());
    for(Map.Entry<Integer, PlanFuel> oldPlannedFuel : oldPlan.plannedFuelsMap.entrySet())
    {
      plannedFuelsMap.put(oldPlannedFuel.getKey(), oldPlannedFuel.getValue());
    }
  }

  /**
   * Returns the associated starbase.
   * @return The associated starbase.
   */
  public Starbase getStarBase()
  {
    return starBase;
  }

  /**
   * Returns the planned hours for this fuel plan.
   * @return The planned hours for this fuel plan.
   */
  public int getPlannedHours()
  {
    return plannedHours;
  }

  /**
   * Sets the planned hours for this fuel plan.
   * @param plannedHours New planned hours for this fuel plan.
   */
  public void setPlannedHours(int plannedHours)
  {
    this.plannedHours = plannedHours;
  }
  
  /**
   * Calculates the required quantities
   */
  public void calcQuantities()
  {
    /*int calcQuantity = 0;
    int consumedPerHour = 0;
    int totalConsumption = 0;*/
    for(PlanFuel plannedFuel : plannedFuelsMap.values())
    {
      plannedFuel.calcPlannedQuantity(plannedHours);
      
     /*if(plannedFuel.getConsumedPerHour() <= 0)
      {
        plannedFuel.setActive(false);
        plannedFuel.setPlannedQuantity(0);
      }
      else
      {
        consumedPerHour = plannedFuel.getConsumedPerHour();
        totalConsumption = plannedFuel.getTotalConsumption();
        totalConsumption = (totalConsumption == 0) ? 1 : totalConsumption;
                
        calcQuantity = ((consumedPerHour * plannedHours) - plannedFuel.getQuantity()) + (plannedFuel.getStoredQuantity() * (consumedPerHour / totalConsumption));
        
        if(calcQuantity < 0)
        {
          plannedFuel.setActive(false);
          plannedFuel.setPlannedQuantity(0);
        }
        else
        {
          plannedFuel.setActive(true);
          plannedFuel.setPlannedQuantity(calcQuantity);
        }
      }*/
    }
  }

  /**
   * Returns the planned fuels as a Map.
   * @return The planned fuels as a Map.
   */
  public Map<Integer, PlanFuel> getPlannedFuelsMap()
  {
    return plannedFuelsMap;
  }

  /**
   * Returns the active status of the plan.
   * @return true if the plan is active, false otherwise.
   */
  public boolean isActive()
  {
    return active;
  }

  /**
   * Sets the active status of the plan.
   * @param active true if the plan is active, false otherwise.
   */
  public void setActive(boolean active)
  {
    this.active = active;
  }
  
}
