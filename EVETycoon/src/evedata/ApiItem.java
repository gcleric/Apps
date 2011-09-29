/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evedata;

import com.beimin.eveapi.core.ApiException;
import java.io.Serializable;
import javax.swing.Icon;
import services.ImageService;
import services.ImageTypes;

/**
 * Class representing an item from the API DB.
 * @author hrivanov
 */
public class ApiItem implements Serializable
{
  /** ID of the item in the DB. */
  protected int itemID;
  /** Name of the item. */
  protected String itemName;
  /** Description of the item. */
  protected String itemDescription;
  /** Volume of an item. */
  protected float volume;
  /** Icon of the item */
  protected Icon itemIcon;

  /**
   * Default constructor.
   * @param itemID ID of the item in the DB.
   * @param itemName Name of the item.
   * @param volume Volume of the item.
   * @param iconID Item's icon ID.
   */
  public ApiItem(int itemID, String itemName, float volume)
  {
    this.itemID = itemID;
    this.itemName = itemName;
    this.volume = volume;
  }
  
  /**
   * Copy constructor.
   * @param copyItem ApiItem object to copy from.
   */
  public ApiItem(ApiItem copyItem)
  {
    this.itemID = copyItem.itemID;
    this.itemName = copyItem.itemName;
    this.itemDescription = copyItem.itemDescription;
    this.volume = copyItem.volume;
    this.itemIcon = copyItem.itemIcon;
  }

  /**
   * Returns the ID of the item.
   * @return The ID of the item.
   */
  public int getItemID()
  {
    return itemID;
  }

  /**
   * Returns the name of the item.
   * @return The name of the item.
   */
  public String getItemName()
  {
    return itemName;
  }

  /**
   * Returns the volume of the item.
   * @return The volume of the item.
   */
  public float getVolume()
  {
    return volume;
  }
  
  /**
   * Loads the item's icon image.
   * @throws ApiException 
   */
  public void loadItemIcon() throws ApiException
  {
    itemIcon = ImageService.getInstance().getImage(String.valueOf(itemID), ImageTypes.ICON);
  }

  public Icon getItemIcon()
  {
    return itemIcon;
  }
}
