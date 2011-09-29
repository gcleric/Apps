/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evedata;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class holding information about the EVE account.
 * @author hrivanov
 */
public class Account extends DataResponse implements Serializable
{
  /** User ID of the account. */
  private int keyID;
  /** API key of the account. */
  private String verificationCode;
  /** The index of the default character. */
  private int defaultCharIndex;
  /** The default character to use for API operations. */
  private Character defaultCharacter;
  /** Collection of the account's characters. */
  private ArrayList<Character> accountCharacters;
  
  /**
   *  Constructor with parameters
   *  @param userID User ID to use for API verification.
   *  @param apiKey API key to use for verification.
   */
  public Account()
  {
    this.keyID = -1;
    this.verificationCode = null;
    this.defaultCharIndex = 0;
    this.accountCharacters = new ArrayList<Character>(3);
  };

  /**
   *  Constructor with parameters
   *  @param userID User ID to use for API verification.
   *  @param apiKey API key to use for verification.
   */
  public Account(int userID, String apiKey)
  {
    this.keyID = userID;
    this.verificationCode = apiKey;
    this.defaultCharIndex = 0;
    this.accountCharacters = new ArrayList<Character>(3);
  };
  
  /**
   * Returns the user ID of the account.
   * @return The user ID of the account.
   */
  public int getKeyID()
  {
    return keyID;
  }

  /**
   * Sets the user ID of the account.
   * @param userID The new user ID of the account.
   */
  public void setKeyID(int userID)
  {
    this.keyID = userID;
  }
  
  /**
   * Returns the API key of the account.
   * @return The API key of the account.
   */
  public String getVerificationCode()
  {
    return verificationCode;
  }

  /**
   * Sets the API key of the account.
   * @param verificationCode The new API key of the account.
   */
  public void setVerificationCode(String verificationCode)
  {
    this.verificationCode = verificationCode;
  }

  /**
   * Returns the index of the default character.
   * @return The index of the default character.
   */
  public int getDefaultCharIndex()
  {
    return defaultCharIndex;
  }

  /**
   * Returns the default character's object.
   * @return The default character's object.
   */
  public Character getDefaultCharacter()
  {
    return defaultCharacter;
  }
  
  /**
   * Sets the default character, used for API requests.
   * @param index Index of the character to use.
   */
  public void setDefaultCharacter(int index)
  {
    this.defaultCharacter = accountCharacters.get(index);
    this.defaultCharIndex = index;
  }
  
  /**
   * Returns the characters of the account as a collection.
   * @return The characters of the account as a collection.
   */
  public ArrayList<Character> getAccountCharacters()
  {
    return accountCharacters;
  }

  /**
   * Sets the characters of the account from a collection.
   * @param accountChars The characters of the account as a collection.
   */
  public void setAccountCharacters(ArrayList<Character> accountChars)
  {
    this.accountCharacters.clear();
    this.accountCharacters.addAll(accountChars);
    this.defaultCharacter = accountCharacters.get(0);
  }
  
  /**
   * Adds a character to the account.
   * @param newAccountChar A new character to add to the account's collection.
   */
  public void addAccountCharacter(Character newAccountChar)
  {
    this.accountCharacters.add(newAccountChar);
  };

  /**
   * Returns a character by its index. If not found throws an exception.
   * @param index Index of the character to retrieve.
   * @return A character of this account.
   * @throws Exception 
   */
  public Character getAccountCharacter(int index) throws Exception
  {
    return this.accountCharacters.get(index);
  }
};
