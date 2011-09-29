/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evedata;

/**
 *
 * @author hrivanov
 */
public enum StarbaseStates
{
  UNANCHORED(0, "Unanchored"),
  ANCHORED(1, "Anchored"),
  ONLINING(2, "Onlining"),
  REINFORCED(3, "Reinforced mode"),
  ONLINE(4, "Online");
  
  private final int stateID;
  private final String stateDescription;
  
  private StarbaseStates(int stateID, String stateDescription)
  {
    this.stateID = stateID;
    this.stateDescription = stateDescription;
  };
  
  public int stateID()
  {
    return stateID;
  };
    
  public String stateDescription()
  {
    return stateDescription;
  }
}
