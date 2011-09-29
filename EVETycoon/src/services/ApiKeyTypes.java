/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author hrivanov
 */
public enum ApiKeyTypes
{
  ACCOUNT("Account"),
  CHARACTER("Character"),
  CORPORATION("Corporation");
  
  private final String description;
  
  ApiKeyTypes(String description)
  {
    this.description = description;
  };
  
  public String description()
  {
    return description;
  }
}
