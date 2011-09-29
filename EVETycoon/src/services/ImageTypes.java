/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author hrivanov
 */
public enum ImageTypes
{
  PORTRAIT("/Character/", 128, ".jpg"),
  CORPORATION("/Corporation/", 32, ".png"),
  ALLIANCE("/Alliance/", 32, ".png"),
  ICON("/InventoryType/", 32, ".png");
  
  private final String subDir;
  private final int imageSize;
  private final String fileExtension;
  
  ImageTypes(String subDir, int imageSize, String fileExtension)
  {
    this.subDir = subDir;
    this.imageSize = imageSize;
    this.fileExtension = fileExtension;
  };
  
  public String subDir()
  {
    return subDir;
  }
  
  public int imageSize()
  {
    return imageSize;
  };
  
  public String fileExtension()
  {
    return fileExtension;
  }
}
