/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evedata;

import java.util.Comparator;

/**
 *
 * @author hrivanov
 */
public class StarbaseFuelComparator implements Comparator<StarbaseFuel>
{

  public int compare(StarbaseFuel o1, StarbaseFuel o2)
  {
    int lastCmp = (int)Math.signum((float)o1.getPurpose() - o2.getPurpose());
    return (lastCmp != 0 ? lastCmp : o1.getItemName().compareTo(o2.getItemName()));
  }
  
}
