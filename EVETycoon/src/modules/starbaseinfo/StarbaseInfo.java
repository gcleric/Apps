/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.starbaseinfo;

import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiResponse;
import com.beimin.eveapi.corporation.starbase.list.ApiStarbase;
import com.beimin.eveapi.corporation.starbase.list.StarbaseListResponse;
import evedata.DataResponse;
import evedata.Starbase;
import evedata.StarbaseFuel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import services.ApiService;
import services.DBService;
import tables.BasicTableCell;
import tables.BasicTableColumn;
import tables.BasicTableModel;
import tables.BasicTableRow;

/**
 *
 * @author hrivanov
 */
public class StarbaseInfo extends DataResponse implements Serializable, TableModelListener, ListSelectionListener
{
  /** A list of all star bases. */
  private List<Starbase> starbaseList;
  /** Table model for the starbase list. */
  transient private BasicTableModel starbaseListModel;
  /** Table model for a starbase's fuels list. */
  transient private BasicTableModel starbaseFuelModel;
  /** The currently selected starbase. */
  private int currStarbaseIndex;

  /**
   * Default constructor.
   */
  public StarbaseInfo()
  {
    this.starbaseList = new ArrayList<Starbase>(10);
    
    this.starbaseListModel = new BasicTableModel();
    updateStarbaseListModel();
    
    this.starbaseFuelModel = new BasicTableModel();
    this.starbaseFuelModel.addTableModelListener(this);
    updateStarbaseFuelModel(-1);
    
    currStarbaseIndex = 0;
  }
  
  private void writeObject(ObjectOutputStream out) throws IOException
  {
    out.defaultWriteObject();
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();
    
    this.starbaseListModel = new BasicTableModel();
    
    this.starbaseFuelModel = new BasicTableModel();
    this.starbaseFuelModel.addTableModelListener(this);
    
    updateStarbaseListModel();
    updateStarbaseFuelModel(0);
  }

  /**
   * Returns the starbase list.
   * @return The list of starbases.
   */
  public List<Starbase> getStarbaseList()
  {
    return starbaseList;
  }

  /**
   * Updates the StarbaseInfo object with data from the EVE servers.
   */
  public void updateStarbaseInfo() throws ApiException
  {
    ApiService apiService = ApiService.getInstance();
    apiService.updateStarbaseList(this);
    apiService.updateStarbaseDetails(starbaseList);
    
    updateStarbaseListModel();
    updateStarbaseFuelModel(0);
  }

  /**
   * Returns the current starbase fuel model.
   * @return The current starbase fuel model.
   */
  public BasicTableModel getStarbaseFuelModel()
  {
    return starbaseFuelModel;
  }

  /**
   * Returns the current starbase list model.
   * @return The current starbase list model.
   */
  public BasicTableModel getStarbaseListModel()
  {
    return starbaseListModel;
  }

  @Override
  public void copyApiResponse(ApiResponse apiResp) throws ApiException
  {
    super.copyApiResponse(apiResp);
    StarbaseListResponse starbaseListResp = (StarbaseListResponse) apiResp;
    Starbase newStarBase;
  
    boolean found = false;
    
    
    try
    {
      if (starbaseList.isEmpty())
      {
        for (ApiStarbase apiStarbase : starbaseListResp.getAll())
        {
          newStarBase = createStarbase(apiStarbase);
          starbaseList.add(newStarBase);
        }
        
      } 
      else
      {

        // Add any new starbases
        for (ApiStarbase apiStarbase : starbaseListResp.getAll())
        {
          found = false;
          for (Starbase oldStarbase : starbaseList)
          {
            if (apiStarbase.getItemID() == oldStarbase.getItemID())
            {
              found = true;
              break;
            }
          }

          if (!found)
          {
            newStarBase = createStarbase(apiStarbase);
            starbaseList.add(newStarBase);
          }
        }
        
        // Remove any obsolete starbases
        for (Starbase oldStarbase : starbaseList)
        {
          found = false;
          for (ApiStarbase apiStarbase : starbaseListResp.getAll())
          {
            if (apiStarbase.getItemID() == oldStarbase.getItemID())
            {
              found = true;
              break;
            }
          }
          
          if (!found)
          {
            starbaseList.remove(oldStarbase);
          }
        }
      }
      updateStarbaseListModel();
    } 
    catch (SQLException sqlEx)
    {
      throw new ApiException(sqlEx.getMessage(), sqlEx.getCause());
    }
  }
  
  /**
   * Creates a new Starbase object.
   * @param apiStarbase ApiStarbase object to copy from.
   * @return A new Starbase object.
   * @throws SQLException
   * @throws ApiException 
   */
  private Starbase createStarbase(ApiStarbase apiStarbase) throws SQLException, ApiException
  {
    Starbase newStarBase = new Starbase(apiStarbase);
    DBService.getInstance().getStarbaseInfo(newStarBase);
    newStarBase.loadItemIcon();
    
    return newStarBase;
  }
  
  private void updateStarbaseListModel()
  {
    if (starbaseListModel.getColumnCount() == 0)
    {
      starbaseListModel.addColumn(new BasicTableColumn("Type", String.class));
      starbaseListModel.addColumn(new BasicTableColumn("Location", String.class));
      starbaseListModel.addColumn(new BasicTableColumn("State", String.class));
      starbaseListModel.addColumn(new BasicTableColumn("Fuel status", Icon.class));
      starbaseListModel.addColumn(new BasicTableColumn("Time active left", String.class));
    }

    starbaseListModel.clearRows();
    BasicTableRow newRow = null;
    for (Starbase starbase : starbaseList)
    {
      newRow = new BasicTableRow(5);

      newRow.addCell(new BasicTableCell(newRow, starbase.getItemIcon(), false, starbase.getTypeName(), starbase.getTypeDescription()));
      newRow.addCell(new BasicTableCell(newRow, starbase.getLocationDescription(), false));
      newRow.addCell(new BasicTableCell(newRow, starbase.getState().stateDescription(), false));
      newRow.addCell(new BasicTableCell(newRow, starbase.getFuelAlarmIcon(), false));
      newRow.addCell(new BasicTableCell(newRow, starbase.getRemainingHoursString(), false));

      starbaseListModel.addRow(newRow);
    }

    starbaseListModel.fireTableDataChanged();
  }
  
  private void updateStarbaseFuelModel(int starbaseIndex)
  {
    if (starbaseFuelModel.getColumnCount() == 0)
    {
      starbaseFuelModel.addColumn(new BasicTableColumn("Type", String.class.getClass()));
      starbaseFuelModel.addColumn(new BasicTableColumn("Quantity/h", Integer.class.getClass()));
      starbaseFuelModel.addColumn(new BasicTableColumn("Quantity", Integer.class.getClass()));
      starbaseFuelModel.addColumn(new BasicTableColumn("Remaining time", String.class.getClass()));
      starbaseFuelModel.addColumn(new BasicTableColumn("Status", Icon.class.getClass()));
    }

    starbaseFuelModel.clearRows();

    if(((starbaseIndex >= 0))&&(starbaseIndex < starbaseList.size()))
    {
      BasicTableRow newRow = null;
      Starbase selectedBase = starbaseList.get(starbaseIndex);
      List<StarbaseFuel> sortedFuels = selectedBase.getFuelsValues();
      Collections.sort(sortedFuels);
      for (StarbaseFuel starbaseFuel : sortedFuels)
      {
        newRow = new BasicTableRow(5);
        newRow.setRowID(starbaseFuel.getItemID());
        
        newRow.addCell(new BasicTableCell(newRow, starbaseFuel.getItemIcon(), false, starbaseFuel.getItemName(), ""));

        if ((starbaseFuel.getPurpose() == 2) || (starbaseFuel.getPurpose() == 3))
        {
          newRow.addCell(new BasicTableCell(newRow, starbaseFuel.getConsumedPerHour(), true));
        } else
        {
          newRow.addCell(new BasicTableCell(newRow, starbaseFuel.getConsumedPerHour(), false));
        }

        newRow.addCell(new BasicTableCell(newRow, starbaseFuel.getQuantity(), false));
        newRow.addCell(new BasicTableCell(newRow, starbaseFuel.getRemainingTimeString(), false));
        newRow.addCell(new BasicTableCell(newRow, starbaseFuel.getStatusIcon(), false));

        starbaseFuelModel.addRow(newRow);
      }

      starbaseFuelModel.fireTableStructureChanged();
    }
  }

  public void tableChanged(TableModelEvent e)
  {
    if(((currStarbaseIndex >= 0))&&(currStarbaseIndex < starbaseList.size()))
    {
      int row = e.getFirstRow();
      int column = e.getColumn();
      if(((row >=0 )&&(row < starbaseFuelModel.getRowCount()))&&((column >=0 )&&(column < starbaseFuelModel.getColumnCount())))
      {
        if(column == 1)
        {
          Object data = starbaseFuelModel.getValueAt(row, column);

          Starbase currStarbase = starbaseList.get(currStarbaseIndex);
          
          Map<Integer, StarbaseFuel> fuelsMap = currStarbase.getFuelsMap();
          StarbaseFuel starbaseFuel = fuelsMap.get(starbaseFuelModel.getRowID(row).intValue());
          starbaseFuel.setConsumedPerHour(((Integer)data));
          starbaseFuel.checkForAlarm();
          
          starbaseListModel.setValueAt(currStarbase.getFuelAlarmIcon(), currStarbaseIndex, 3);
          starbaseListModel.setValueAt(currStarbase.getRemainingHoursString(), currStarbaseIndex, 4);
          starbaseListModel.fireTableRowsUpdated(currStarbaseIndex, currStarbaseIndex);

          starbaseFuelModel.setValueAt(starbaseFuel.getRemainingTimeString(), row, 3);
          starbaseFuelModel.setValueAt(starbaseFuel.getStatusIcon(), row, 4);
          starbaseFuelModel.fireTableRowsUpdated(row, row);
        }
      }
    }
  }

  public void valueChanged(ListSelectionEvent e)
  {
    //Ignore extra messages.
    if (e.getValueIsAdjusting())
    {
      return;
    }
    ListSelectionModel tableSelectionModel = (ListSelectionModel)e.getSource();
    if(tableSelectionModel.isSelectionEmpty())
    {
      return;
    }
    
    currStarbaseIndex = tableSelectionModel.getMinSelectionIndex();
    updateStarbaseFuelModel(currStarbaseIndex);
  }
}
