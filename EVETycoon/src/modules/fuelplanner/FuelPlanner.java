/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.fuelplanner;

import com.beimin.eveapi.core.ApiException;
import evedata.Starbase;
import evedata.StarbaseFuel;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import services.DBService;
import tables.BasicTableCell;
import tables.BasicTableColumn;
import tables.BasicTableModel;
import tables.BasicTableRow;

/**
 *
 * @author hrivanov
 */
public class FuelPlanner implements Serializable
{

  /** The list of starbase fuel plans. */
  private List<FuelPlan> starbaseFuelPlans;
  /** The list of starbase fuels. */
  private Map<Integer, PlanFuel> plannerFuelMap;
  /** Generic starbase itemID counter, neplannedFuelMapgative. */
  private long genericItemID;
  /** Table model for the starbase list. */
  transient private BasicTableModel fuelPlanListModel;
  /** Table model for a starbase's fuels list. */
  transient private BasicTableModel fuelPlanDataModel;
  /** Table model for the fuel planner's stored fuels. */
  transient private BasicTableModel storedFuelModel;
  /** Color for the data table's headers row.  */
  private Color planDataHeaderColor;
  /** Color for the data table's subtotals row.  */
  private Color planDataSubTotalsColor;
  /** Color for the data table's totals row. */
  private Color planDataTotalsColor;
  /** Total units of the planned fuels. */
  private Long totalQuantity;
  /** Total volume of the planned fuels. */
  private Float totalVolume;
  /** Planned working time in months. */
  private Integer months;
  /** Planned working time in weeks. */
  private Integer weeks;
  /** Planned working time in days. */
  private Integer days;
  /** Planned working time in hours. */
  private Integer hours;

  /**
   * Default constructor.
   */
  public FuelPlanner()
  {
    starbaseFuelPlans = new ArrayList<FuelPlan>(10);
    genericItemID = -1;

    plannerFuelMap = new HashMap<Integer, PlanFuel>(20);

    this.fuelPlanListModel = new BasicTableModel();
    updateFuelPlanListModel();

    this.fuelPlanDataModel = new BasicTableModel();
    updateFuelPlanDataModel();

    this.storedFuelModel = new BasicTableModel();
    updateStoredFuelModel();

    this.planDataHeaderColor = new Color(80, 80, 80);
    this.planDataSubTotalsColor = new Color(0, 77, 0);
    this.planDataTotalsColor = new Color(0, 102, 0);

    this.totalQuantity = 0l;
    this.totalVolume = 0.0f;

    this.months = 0;
    this.weeks = 0;
    this.days = 0;
    this.hours = 0;
  }

  private void writeObject(ObjectOutputStream out) throws IOException
  {
    out.defaultWriteObject();
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();

    this.fuelPlanListModel = new BasicTableModel();
    updateFuelPlanListModel();

    this.fuelPlanDataModel = new BasicTableModel();
    updateFuelPlanDataModel();
    
    this.storedFuelModel = new BasicTableModel();
    updateStoredFuelModel();
  }

  /**
   * Updates the starbase fuel plans data model.
   */
  private void updateFuelPlanListModel()
  {
    if (fuelPlanListModel.getColumnCount() == 0)
    {
      // "", "Type", "Location", "State", "Fuel status"
      fuelPlanListModel.addColumn(new BasicTableColumn("", Icon.class.getClass()));
      fuelPlanListModel.addColumn(new BasicTableColumn("Type", String.class.getClass()));
      fuelPlanListModel.addColumn(new BasicTableColumn("Location", String.class.getClass()));
      fuelPlanListModel.addColumn(new BasicTableColumn("State", String.class.getClass()));
      fuelPlanListModel.addColumn(new BasicTableColumn("HW / LO consumption", String.class.getClass()));
      fuelPlanListModel.addColumn(new BasicTableColumn("Fuel status", Icon.class.getClass()));
    }

    fuelPlanListModel.clearRows();

    if (!starbaseFuelPlans.isEmpty())
    {
      BasicTableRow newRow = null;
      Starbase currStarbase = null;
      String consumption;
      Map<Integer, StarbaseFuel> fuelsMap;
      for (FuelPlan currFuelPlan : starbaseFuelPlans)
      {
        currStarbase = currFuelPlan.getStarBase();
        newRow = new BasicTableRow(6);

        newRow.addCell(new BasicTableCell(newRow, currFuelPlan.isActive(), true));
        newRow.addCell(new BasicTableCell(newRow, currStarbase.getItemIcon(), false, currStarbase.getTypeName(), currStarbase.getTypeDescription()));
        newRow.addCell(new BasicTableCell(newRow, currStarbase.getLocationDescription(), false));
        newRow.addCell(new BasicTableCell(newRow, currStarbase.getState().stateDescription(), false));
        fuelsMap = currStarbase.getFuelsMap();
        consumption = fuelsMap.get(StarbaseFuel.HEAVY_WATER).getConsumedPerHour() + " / " + fuelsMap.get(StarbaseFuel.LIQUID_OZONE).getConsumedPerHour();

        newRow.addCell(new BasicTableCell(newRow, consumption, false));
        newRow.addCell(new BasicTableCell(newRow, currStarbase.getFuelAlarmIcon(), false));

        fuelPlanListModel.addRow(newRow);
      }
    }
    fuelPlanListModel.fireTableDataChanged();
  }

  /**
   * Adds the fuel plan columns(planned fuels) to the fuel plan table model.
   * @return The number of columns added.
   */
  private int addPlanDataColumns()
  {
    int columnCount = 0;
    // Add the columns
    fuelPlanDataModel.addColumn(new BasicTableColumn("Starbase", String.class.getClass()));
    columnCount++;
    for (PlanFuel plannedFuel : plannerFuelMap.values())
    {
      if (plannedFuel.isActive())
      {
        fuelPlanDataModel.addColumn(new BasicTableColumn(plannedFuel.getItemName(), String.class.getClass(), plannedFuel.getItemIcon(), plannedFuel.getItemName()));
        columnCount++;
      }
    }

    return columnCount;
  }

  /**
   * Adds the fuel plan rows to the fuel plan table model.
   * @param columnCount The number of columns (planned fuels) in the fuel plan.
   */
  private void addPlanDataRows(int columnCount)
  {
    BasicTableRow newStarbaseRow = null;
    BasicTableRow newQuantityRow = null;
    BasicTableRow newVolumeRow = null;
    PlanFuel currPlannedFuel = null;
    Map<Integer, PlanFuel> starbasePlannedFuels;

    // Add the fuel plan rows
    for (FuelPlan fuelPlan : starbaseFuelPlans)
    {
      if (!fuelPlan.isActive())
      {
        continue;
      }
      // Add the starbase row
      newStarbaseRow = new BasicTableRow(columnCount);
      if (fuelPlan.getStarBase().getItemID() < 0)
      {
        newStarbaseRow.addCell(new BasicTableCell(newStarbaseRow, fuelPlan.getStarBase().getTypeName(), false));
      } else
      {
        newStarbaseRow.addCell(new BasicTableCell(newStarbaseRow, fuelPlan.getStarBase().getLocationDescription(), false));
      }

      newStarbaseRow.setRowID(fuelPlan.getStarBase().getItemID());
      newStarbaseRow.fillEmptyCells(columnCount - 1);
      newStarbaseRow.setBgColor(planDataHeaderColor);
      fuelPlanDataModel.addRow(newStarbaseRow);

      // Add the quantity/volume row
      newQuantityRow = new BasicTableRow(columnCount);
      newVolumeRow = new BasicTableRow(columnCount);

      newQuantityRow.setRowID(fuelPlan.getStarBase().getItemID());
      newVolumeRow.setRowID(fuelPlan.getStarBase().getItemID());

      newQuantityRow.addCell(new BasicTableCell(newQuantityRow, "Quantity", false));
      newVolumeRow.addCell(new BasicTableCell(newVolumeRow, "Volume (m3)", false));

      starbasePlannedFuels = fuelPlan.getPlannedFuelsMap();

      for (PlanFuel plannedFuel : plannerFuelMap.values())
      {
        if (plannedFuel.isActive())
        {
          currPlannedFuel = starbasePlannedFuels.get(plannedFuel.getItemID());

          if (currPlannedFuel != null)
          {
            newQuantityRow.addCell(new BasicTableCell(newQuantityRow, currPlannedFuel.getPlannedQuantity(), false));
            newVolumeRow.addCell(new BasicTableCell(newVolumeRow, currPlannedFuel.getPlannedVolume(), false));
          } else
          {
            newQuantityRow.addCell(new BasicTableCell(newQuantityRow, 0d, false));
            newVolumeRow.addCell(new BasicTableCell(newVolumeRow, 0.0f, false));
          }
        }
      }

      fuelPlanDataModel.addRow(newQuantityRow);
      fuelPlanDataModel.addRow(newVolumeRow);
    }
  }

  /**
   * Adds the subtotals rows to the fuel plan table model.
   * @param columnCount The number of columns (planned fuels) in the fuel plan.
   */
  private void addSubtotalsRows(int columnCount)
  {
    BasicTableRow newStarbaseRow;
    BasicTableRow newQuantityRow;
    BasicTableRow newVolumeRow;

    totalQuantity = 0l;
    totalVolume = 0.0f;

    // Add the column total rows
    newStarbaseRow = new BasicTableRow(columnCount);
    newStarbaseRow.addCell(new BasicTableCell(newStarbaseRow, "Column totals", false));
    newStarbaseRow.fillEmptyCells(columnCount - 1);
    newStarbaseRow.setBgColor(planDataHeaderColor);
    fuelPlanDataModel.addRow(newStarbaseRow);

    newQuantityRow = new BasicTableRow(columnCount);
    newVolumeRow = new BasicTableRow(columnCount);

    newQuantityRow.setBgColor(planDataSubTotalsColor);
    newVolumeRow.setBgColor(planDataSubTotalsColor);

    newQuantityRow.addCell(new BasicTableCell(newQuantityRow, "Quantity", false));
    newVolumeRow.addCell(new BasicTableCell(newVolumeRow, "Volume (m3)", false));

    for (PlanFuel plannedFuel : plannerFuelMap.values())
    {
      if (plannedFuel.isActive())
      {
        newQuantityRow.addCell(new BasicTableCell(newQuantityRow, plannedFuel.getPlannedQuantity(), false));
        newVolumeRow.addCell(new BasicTableCell(newVolumeRow, plannedFuel.getPlannedVolume(), false));

        totalQuantity += plannedFuel.getPlannedQuantity();
        totalVolume += plannedFuel.getPlannedVolume();
      }
    }

    fuelPlanDataModel.addRow(newQuantityRow);
    fuelPlanDataModel.addRow(newVolumeRow);
  }

  /**
   * Adds the totals rows to the fuel plan table model.
   * @param columnCount The number of columns (planned fuels) in the fuel plan.
   */
  private void addTotalsRows(int columnCount)
  {
    BasicTableRow newStarbaseRow;
    BasicTableRow newQuantityRow;
    BasicTableRow newVolumeRow;

    // Add the table totals
    newStarbaseRow = new BasicTableRow(columnCount);
    newStarbaseRow.addCell(new BasicTableCell(newStarbaseRow, "Table totals", false));
    newStarbaseRow.fillEmptyCells(columnCount - 1);
    newStarbaseRow.setBgColor(planDataHeaderColor);
    fuelPlanDataModel.addRow(newStarbaseRow);

    newQuantityRow = new BasicTableRow(columnCount);
    newVolumeRow = new BasicTableRow(columnCount);

    newQuantityRow.setBgColor(planDataTotalsColor);
    newVolumeRow.setBgColor(planDataTotalsColor);

    newQuantityRow.addCell(new BasicTableCell(newQuantityRow, "Quantity", false));
    newVolumeRow.addCell(new BasicTableCell(newVolumeRow, "Volume (m3)", false));

    newQuantityRow.addCell(new BasicTableCell(newQuantityRow, totalQuantity, false));
    newVolumeRow.addCell(new BasicTableCell(newVolumeRow, totalVolume, false));

    newQuantityRow.fillEmptyCells(columnCount - 2);
    newVolumeRow.fillEmptyCells(columnCount - 2);


    fuelPlanDataModel.addRow(newQuantityRow);
    fuelPlanDataModel.addRow(newVolumeRow);
  }

  /**
   * Updates the fuel plan table model.
   */
  private void updateFuelPlanDataModel()
  {
    fuelPlanDataModel.clearAll();

    if (!starbaseFuelPlans.isEmpty())
    {
      int columnCount = 0;

      columnCount = addPlanDataColumns();

      // Add the plan data rows.
      addPlanDataRows(columnCount);

      // Add the totals' rows.
      if (fuelPlanDataModel.getRowCount() > 0)
      {
        // Add the subtotals rows.
        addSubtotalsRows(columnCount);

        // Add the totals rows.
        addTotalsRows(columnCount);
      }
    }

    fuelPlanDataModel.fireTableStructureChanged();
  }

  /**
   * Updates the stored fuel table model.
   */
  private void updateStoredFuelModel()
  {
    if (storedFuelModel.getColumnCount() == 0)
    {
      storedFuelModel.addColumn(new BasicTableColumn("Fuel", Icon.class.getClass()));
      storedFuelModel.addColumn(new BasicTableColumn("Planned quantity", String.class.getClass()));
      storedFuelModel.addColumn(new BasicTableColumn("Stored quantity", String.class.getClass()));
    }

    storedFuelModel.clearRows();

    if (!plannerFuelMap.isEmpty())
    {
      BasicTableRow newRow = null;

      List<PlanFuel> sortedFuels = new ArrayList<PlanFuel>(plannerFuelMap.values());
      Collections.sort(sortedFuels);
      
      for (PlanFuel currPlannedFuel : sortedFuels)
      {
        if (currPlannedFuel.isActive())
        {
          newRow = new BasicTableRow(storedFuelModel.getColumnCount());

          newRow.setRowID(currPlannedFuel.getItemID());
          newRow.addCell(new BasicTableCell(newRow, currPlannedFuel.getItemIcon(), false, currPlannedFuel.getItemName(), ""));
          newRow.addCell(new BasicTableCell(newRow, currPlannedFuel.getPlannedQuantity(), false));
          newRow.addCell(new BasicTableCell(newRow, currPlannedFuel.getStoredQuantity(), true));

          storedFuelModel.addRow(newRow);
        }
      }
    }
    storedFuelModel.fireTableDataChanged();
  }

  /**
   * Returns the planned working time in months. 
   * @return Planned working time in months.
   */
  public Long getMonths()
  {
    return months.longValue();
  }

  /**
   * Returns the planned working time in weeks. 
   * @return Planned working time in weeks.
   */
  public Long getWeeks()
  {
    return weeks.longValue();
  }

  /**
   * Returns the planned working time in days. 
   * @return Planned working time in days.
   */
  public Long getDays()
  {
    return days.longValue();
  }

  /**
   * Returns the planned working time in hours. 
   * @return Planned working time in hours.
   */
  public Long getHours()
  {
    return hours.longValue();
  }

  /**
   * Returns the fuel plan's data table model.
   * @return The fuel plan's data table model.
   */
  public BasicTableModel getFuelPlanDataModel()
  {
    return fuelPlanDataModel;
  }

  /**
   * Returns the fuel plan's starbase list table model.
   * @return The fuel plan's starbase list table model.
   */
  public BasicTableModel getFuelPlanListModel()
  {
    return fuelPlanListModel;
  }

  /**
   * Returns the stored fuels table model.
   * @return The stored fuels table model.
   */
  public BasicTableModel getStoredFuelModel()
  {
    return storedFuelModel;
  }

  /**
   * Adds a list of generic starbases.
   * @param newStarbaseList A list of generic starbases.
   */
  public void addGenericStarbaseList(List<Starbase> newStarbaseList)
  {
    FuelPlan newFuelPlan = null;
    for (Starbase newStarbase : newStarbaseList)
    {
      newStarbase.setItemID(genericItemID--);
      newFuelPlan = new FuelPlan(0, newStarbase);
      newFuelPlan.setActive(true);
      starbaseFuelPlans.add(new FuelPlan(0, newStarbase));
    }

    updateFuelPlanListModel();
  }

  /**
   * Removes a generic starbase fuel plan.
   * @param index Index of the fuel plan.
   */
  public void removeStarbase(int index)
  {
    if ((index >= 0) && (index < starbaseFuelPlans.size()))
    {
      if (starbaseFuelPlans.get(index).isActive())
      {
        long itemID = starbaseFuelPlans.get(index).getStarBase().getItemID();
        int detailIndex = 0;
        for (detailIndex = 0; detailIndex < fuelPlanDataModel.getRowCount(); detailIndex++)
        {
          if (fuelPlanDataModel.getRowID(detailIndex) == itemID)
          {
            break;
          }
        }
        fuelPlanDataModel.removeRow(detailIndex);
        fuelPlanDataModel.removeRow(detailIndex);
        fuelPlanDataModel.removeRow(detailIndex);
      }
      starbaseFuelPlans.remove(index);
      fuelPlanListModel.removeRow(index);
    }
  }

  /**
   * Removes all fuel plans.
   */
  public void clearAllFuelPlans()
  {
    starbaseFuelPlans.clear();
    for (PlanFuel plannedFuel : plannerFuelMap.values())
    {
      plannedFuel.setPlannedQuantity(-1);
      plannedFuel.setPlannedVolume(1.0f);
    }
    updateFuelPlanListModel();
    updateFuelPlanDataModel();
  }

  /**
   * Updates the fuel plans list.
   * @param newStarbaseList 
   */
  public void importStarbaseList(List<Starbase> newStarbaseList)
  {
    FuelPlan newFuelPlan = null;
    for (Starbase newStarbase : newStarbaseList)
    {
      newFuelPlan = new FuelPlan(0, newStarbase);
      newFuelPlan.setActive(true);
      starbaseFuelPlans.add(newFuelPlan);
    }

    updateFuelPlanListModel();
  }

  /**
   * Checks if the fuel planner has any plans loaded.
   * @return true if there are any fuel plans, false otherwise.
   */
  public boolean isEmpty()
  {
    return starbaseFuelPlans.isEmpty();
  }

  /**
   * Returns the fuel planner's fuel map.
   * @return 
   */
  public Map<Integer, PlanFuel> getPlannedFuelsMap()
  {
    return plannerFuelMap;
  }

  /**
   * Calculates the required fuel quantities.
   * @param plannedHours Planned workhours of the starbases.
   * @throws SQLException
   * @throws ApiException 
   */
  public void calculatePlans(int months, int weeks, int days, int hours) throws SQLException, ApiException
  {
    if (starbaseFuelPlans.isEmpty())
    {
      return;
    }
    
    this.months = months;
    this.weeks = weeks;
    this.days = days;
    this.hours = hours;

    int plannedHours = (this.months * 30 * 24) + (this.weeks * 7 * 24) + (this.days * 24) + this.hours;
    
    // Reset or initialize the fuel map
    if (plannerFuelMap.isEmpty())
    {
      plannerFuelMap = DBService.getInstance().getStarbaseFuelTypes();
      for (PlanFuel plannerFuel : plannerFuelMap.values())
      {
        plannerFuel.loadItemIcon();
        plannerFuel.setActive(false);
      }
    } 
    else
    {
      for (PlanFuel plannerFuel : plannerFuelMap.values())
      {
        plannerFuel.setPlannedQuantity(0);
        plannerFuel.setPlannedVolume(0.0f);
        plannerFuel.setTotalConsumption(0);
        plannerFuel.setActive(false);
      }
    }

    // Set up the fuel map.
    PlanFuel plannerFuel = null;
    for (FuelPlan currentPlan : starbaseFuelPlans)
    {
      if (currentPlan.isActive())
      {
        for(Map.Entry<Integer, StarbaseFuel> starbaseFuel : currentPlan.getStarBase().getFuelsMap().entrySet())
        {
          plannerFuel = plannerFuelMap.get(starbaseFuel.getKey());
          if(plannerFuel != null)
          {
            plannerFuel.addTotalConsumption(starbaseFuel.getValue().getConsumedPerHour());
            plannerFuel.setActive(true);
          }
        }
      }
    }
    
    // Update the fuel plans.
    Map<Integer, PlanFuel> currPlanFuelMap;
    PlanFuel currPlanFuel;
    for (FuelPlan currentPlan : starbaseFuelPlans)
    {
      if (currentPlan.isActive())
      {
        // Set the stored and total consumption quantities.
        currPlanFuelMap = currentPlan.getPlannedFuelsMap();
        for (Map.Entry<Integer, PlanFuel> currPlannerFuel : plannerFuelMap.entrySet())
        {
          plannerFuel = currPlannerFuel.getValue();
          if(plannerFuel.isActive())
          {
            currPlanFuel = currPlanFuelMap.get(currPlannerFuel.getKey());
            currPlanFuel.setTotalConsumption(plannerFuel.getTotalConsumption());
            currPlanFuel.setStoredQuantity(plannerFuel.getStoredQuantity());
          }
        }
        
        // Calculate the planned quantities
        currentPlan.setPlannedHours(plannedHours);
        currentPlan.calcQuantities();
        
        for (PlanFuel currPlannedFuel : currentPlan.getPlannedFuelsMap().values())
        {
          if (currPlannedFuel.isActive())
          {
            plannerFuel = plannerFuelMap.get(currPlannedFuel.getItemID());
            plannerFuel.addPlannedQuantity(currPlannedFuel.getPlannedQuantity());
          }
        }
      }
    }

    updateFuelPlanDataModel();
    updateStoredFuelModel();
  }

  /**
   * Recalculates the current plans, taking stored fuels into account.
   * @param fuelID 
   */
  public void setStoredFuel(int fuelID, int storedQuantity)
  {
    PlanFuel currentPlanFuel;
    PlanFuel plannerFuel;
    
    plannerFuel = this.plannerFuelMap.get(fuelID);
    plannerFuel.setPlannedQuantity(0);
    
    Integer remainingStoredQty = storedQuantity;
    
    for (FuelPlan currentPlan : starbaseFuelPlans)
    {
      if (currentPlan.isActive())
      {
        // Set the stored and total consumption quantities.
        currentPlanFuel = currentPlan.getPlannedFuelsMap().get(fuelID);
        if(currentPlanFuel != null)
        {
          remainingStoredQty = currentPlanFuel.setStoredQuantity(remainingStoredQty);
          plannerFuel.addPlannedQuantity(currentPlanFuel.getPlannedQuantity());
        }
      }
    }
    
    updateFuelPlanDataModel();
  }

  /**
   * Returns the status of a fuel plan, i.e is included in the calculations.
   * @param index Index number of the plan.
   * @return true if the plan is active, false otehrwise.
   */
  public boolean getPlanActivation(int index)
  {
    return starbaseFuelPlans.get(index).isActive();
  }

  /**
   * Sets a new status for a fuel plan.
   * @param index Index number of the plan.
   * @param active New status of the plan - true (active), false (inactive).
   */
  public void setPlanActivation(int index, boolean active)
  {
    starbaseFuelPlans.get(index).setActive(active);
  }
}
