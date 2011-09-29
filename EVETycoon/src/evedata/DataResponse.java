/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evedata;

import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiResponse;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Basic data response class, adding a new method to the base ApiResponse
 * @author hrivanov
 */
public class DataResponse implements Serializable
{
  /** Version of the API. */
  protected int version;
  /** The current time when the object was last updated from the API. */
  protected Calendar currentTime;
  /** Date and time until which the data is cached (no new data is given). */
  protected Calendar cachedUntil;
  /** Calendar object used for datetime comparison. */
  protected Calendar compTime;
  /** Indicates that the object is new and no web data has been loaded. */
  protected boolean hasApiData;

  /**
   * Default constructor.
   */
  public DataResponse()
  {
    this.currentTime = Calendar.getInstance();
    this.currentTime.setTimeInMillis(0);
    
    this.cachedUntil = Calendar.getInstance();
    this.cachedUntil.setTimeInMillis(0);
    
    this.hasApiData = false;
  }
  
  /**
   * Copy constructor.
   * @param copyResponse 
   */
  public DataResponse(DataResponse copyResponse)
  {
    this.version = copyResponse.version;
    this.currentTime = Calendar.getInstance();
    this.currentTime.setTimeInMillis(copyResponse.currentTime.getTimeInMillis());
    
    this.cachedUntil = Calendar.getInstance();
    this.cachedUntil.setTimeInMillis(copyResponse.cachedUntil.getTimeInMillis());
    
    this.hasApiData = copyResponse.hasApiData;
  }
  
  /**
   * Copies information from an ApiResponse object.
   * @param apiResp ApiResponse object to copy from.
   */
  public void copyApiResponse(ApiResponse apiResp) throws ApiException
  {
    version = apiResp.getVersion();
    currentTime.setTime(apiResp.getCurrentTime());
    cachedUntil.setTime(apiResp.getCachedUntil());
    
    this.hasApiData = true;
  }

  /**
   * Checks if the current data object can be updated from the API servers.
   * @return true if the object can be updated, false otherwise.
   */
  public boolean canUpdate()
  {
    compTime = Calendar.getInstance();
    if (compTime.compareTo(cachedUntil) > 0)
      return true;
    else
      return false;
  }
  
  /**
   * Indicates if there is no API data loaded.
   * @return true if there is no API data loaded, false otherwise.
   */
  public boolean hasApiData()
  {
    return hasApiData;
  }

  public Calendar getCachedUntil()
  {
    return cachedUntil;
  }

  public String getCachedUntilString()
  {
    return String.format("%1$tD %1$tR", this.cachedUntil);
  }
}
