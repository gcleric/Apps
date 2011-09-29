/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evedata;

import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiResponse;
import com.beimin.eveapi.corporation.starbase.list.ApiStarbase;
import com.beimin.eveapi.corporation.starbase.list.StarbaseListResponse;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import services.DBService;

/**
 *
 * @author hrivanov
 */
public class StarbaseData extends DataResponse implements Serializable
{
  /** A list of all star bases. */
  private List<Starbase> starbaseList;
  
  /**
   * Default constructor.
   */
  public StarbaseData()
  {
    starbaseList = new ArrayList<Starbase>();
  }
  
  /**
   * Copy constructor
   * @param oldStarbaseData An old StarbaseData object to copy from.
   */
  public StarbaseData(StarbaseData oldStarbaseData)
  {
    super(oldStarbaseData);
    
    starbaseList = new ArrayList<Starbase>(oldStarbaseData.getStarbaseList().size());
    for(Starbase oldStarbase : oldStarbaseData.getStarbaseList())
    {
      starbaseList.add(new Starbase(oldStarbase));
    }
  }

  /**
   * Returns the list of star bases.
   * @return The list of star bases.
   */
  public List<Starbase> getStarbaseList()
  {
    return starbaseList;
  }
  
  /**
   * Returns a single Starbase object from the list.
   * @param index Index of the Starbase object to retrieve.
   * @return A Starbase object.
   */
  public Starbase getStarbase(int index)
  {   
    return starbaseList.get(index);
  }
  
  /**
   * Copies information from an StarbaseListResponse object.
   * @param apiResp 
   */
  @Override
  public void copyApiResponse(ApiResponse apiResp) throws ApiException
  {
    super.copyApiResponse(apiResp);
    StarbaseListResponse baseListResp = (StarbaseListResponse)apiResp;
    
    try
    {
      boolean found = false;
      Starbase newStarBase;
      DBService dbService = DBService.getInstance();
      for(ApiStarbase apiStarBase : baseListResp.getAll())
      {
        found = false;
        for(Starbase oldStarbase : starbaseList)
        {
          if(oldStarbase.getItemID() == apiStarBase.getItemID())
          {
            found = true;
            break;
          }
        }
        
        if(!found)
        {
          newStarBase = new Starbase(apiStarBase);
          dbService.getStarbaseInfo(newStarBase);
          starbaseList.add(newStarBase);
        }
      }
      
      
      for(Iterator<Starbase> sbIterator = starbaseList.iterator(); sbIterator.hasNext();)
      {
        found = false;
        newStarBase = sbIterator.next();
        for(ApiStarbase apiStarBase : baseListResp.getAll())
        {
          if(newStarBase.getItemID() == apiStarBase.getItemID())
          {
            found = true;
            break;
          }
        }
        
        if(!found)
        {
          sbIterator.remove();
        }
      }
    } 
    catch (SQLException sqlEx)
    {
      throw new ApiException(sqlEx.getMessage(), sqlEx.getCause());
    }
  }
}
