/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evedata;

import com.beimin.eveapi.account.characters.EveCharacter;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author hrivanov
 */
public class Character implements Serializable
{
  private String name;
  private long characterID;
  private String corporationName;
  private long corporationID;
  private ImageIcon charPortrait;
  private ImageIcon charCorpLogo;
  private ImageIcon charAllianceLogo;

  public Character(EveCharacter tempApiChar)
  {
    this.name = tempApiChar.getName();
    this.characterID = tempApiChar.getCharacterID();
    this.corporationName = tempApiChar.getCorporationName();
    this.corporationID = tempApiChar.getCorporationID();
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public long getCharacterID()
  {
    return characterID;
  }

  public void setCharacterID(long characterID)
  {
    this.characterID = characterID;
  }

  public String getCorporationName()
  {
    return corporationName;
  }

  public void setCorporationName(String corporationName)
  {
    this.corporationName = corporationName;
  }

  public long getCorporationID()
  {
    return corporationID;
  }

  public void setCorporationID(long corporationID)
  {
    this.corporationID = corporationID;
  }

  public ImageIcon getCharAllianceLogo()
  {
    return charAllianceLogo;
  }

  public void setCharAllianceLogo(ImageIcon charAllianceLogo)
  {
    this.charAllianceLogo = charAllianceLogo;
  }

  public ImageIcon getCharCorpLogo()
  {
    return charCorpLogo;
  }

  public void setCharCorpLogo(ImageIcon charCorpLogo)
  {
    this.charCorpLogo = charCorpLogo;
  }

  public ImageIcon getCharPortrait()
  {
    return charPortrait;
  }

  public void setCharPortrait(ImageIcon charPortrait)
  {
    this.charPortrait = charPortrait;
  }
}
