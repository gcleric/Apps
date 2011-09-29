/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import evedata.Account;
import modules.fuelplanner.FuelPlanner;
import modules.starbaseinfo.StarbaseInfo;

/**
 *
 * @author hrivanov
 */
public class ApiData
{
  /** Class fields declaration. */
  
  /** Object holding all of the required account info. */
  private static Account currAccountInfo = new Account();

  /** Object holding all of the required starbase info. */
  private static StarbaseInfo currStarbaseInfo = new StarbaseInfo();
  
  /** Object holding all of the required starbase info. */
  private static FuelPlanner currFuelPlanner = new FuelPlanner();
  
  /** Class methods declaration. */
  
  /**
   * Returns a reference to the account info object.
   * @return A reference to the account info object.
   */
  public static Account getCurrAccountInfo()
  {
    return currAccountInfo;
  }

  /**
   * Sets the account info reference to a new object.
   * @param newAccountInfo A new account info object reference.
   */
  public static void setCurrAccountInfo(Account newAccountInfo)
  {
    ApiData.currAccountInfo = newAccountInfo;
  }

  /**
   * Returns a reference to the current starbase info object.
   * @return A reference to the current starbase info object.
   */
  public static StarbaseInfo getCurrStarbaseInfo()
  {
    return currStarbaseInfo;
  }

  /**
   * Sets the current starbase info reference to a new object.
   * @param currStarbaseInfo A new starbase info object reference.
   */
  public static void setCurrStarbaseInfo(StarbaseInfo currStarbaseInfo)
  {
    ApiData.currStarbaseInfo = currStarbaseInfo;
  }

  /**
   * Returns a reference to the current fuel planner info object.
   * @return A reference to the current fuel planner info object.
   */
  public static FuelPlanner getCurrFuelPlanner()
  {
    return currFuelPlanner;
  }

  /**
   * Sets the current fuel planner reference to a new object.
   * @param currFuelPlanner 
   */
  public static void setCurrFuelPlanner(FuelPlanner currFuelPlanner)
  {
    ApiData.currFuelPlanner = currFuelPlanner;
  }
  
  
}
