/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.gui;

/**
 *
 * @author hrivanov
 */
public enum FactionTypes
{
  AMARR_EMPIRE("Amarr Empire", 500003),
  AMMATAR_MANDATE("Ammatar Mandate", 500007),
  CALDARI_STATE("Caldari State", 500001),
  GALLENTE_FEDERATION("Gallente Federation", 500004),
  KHANID_KINGDOM("Khanid Kingdom", 500008),
  MINMATAR_REPUBLIC("Minmatar Republic", 500002);
  
  private final String factionName;
  private final int factionID;
  
  FactionTypes(String factionName, int factionID)
  {
    this.factionName = factionName;
    this.factionID = factionID;
  };
  
  public String factionName()
  {
    return factionName;
  }
  
  public int factionID()
  {
    return factionID;
  };

  @Override
  public String toString()
  {
    return factionName;
  }
}
