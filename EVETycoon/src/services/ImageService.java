/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.beimin.eveapi.core.ApiException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import evetycoon.EveTycoonApp;

/**
 *
 * @author hrivanov
 */
public class ImageService
{
  /** Reference to the singleton's instance. */
  private static ImageService instance = null;
  /** Reference to the application properties. */
  private Properties appProperties;
  /** Reference to a green light icon. */
  private Icon greenLightIcon;
  /** Reference to a yellow light icon. */
  private Icon yellowLightIcon;
  /** Reference to a red light icon. */
  private Icon redLightIcon;
  /** Reference to a gray light icon. */
  private Icon grayLightIcon;
  
  /**
   * Default constructor.
   */
  protected ImageService()
  {
    appProperties = EveTycoonApp.getApplication().getProperties();
    
    greenLightIcon = new ImageIcon(getClass().getResource("/evetycoon/resources/green_light_16.png"));
    yellowLightIcon = new ImageIcon(getClass().getResource("/evetycoon/resources/yellow_light_16.png"));
    redLightIcon = new ImageIcon(getClass().getResource("/evetycoon/resources/red_light_16.png"));
    grayLightIcon = new ImageIcon(getClass().getResource("/evetycoon/resources/gray_light_16.png"));
  }
  
  /**
   * Returns an instance to the ImageService object.
   * @return The instance of the ImageService object
   */
  public static ImageService getInstance()
  {
    if(instance == null)
    {
      instance = new ImageService();
    }
    
    return instance;
  }

  public Icon getGreenLightIcon()
  {
    return greenLightIcon;
  }

  public Icon getRedLightIcon()
  {
    return redLightIcon;
  }

  public Icon getYellowLightIcon()
  {
    return yellowLightIcon;
  }

  public Icon getGrayLightIcon()
  {
    return grayLightIcon;
  }
  
  
  private BufferedImage loadImageFromFile(String imgFilePath) throws IOException
  {
    File imgFile = new File(imgFilePath);
    if(imgFile.exists())
    {
      return ImageIO.read(imgFile);
    }
    else
    {
      return null;
    }
  }
  
  /**
   * Returns an EVE item image.
   * @param imageParam ID of the image
   * @param imageType Image type enumeration
   * @return An image.
   * @throws ApiException 
   */
  public ImageIcon getImage(String imageParam, ImageTypes imageType) throws ApiException
  {
    try
    {
      BufferedImage bufferImage = null;
      
      // Try to load the image from file.
      StringBuilder imagePath = new StringBuilder();
      imagePath.append(appProperties.getProperty(EnumFileProperties.IMAGE_CACHE_PATH.propertyName()));
      imagePath.append(imageType.subDir());
      imagePath.append(imageParam);
      imagePath.append("_");
      imagePath.append(imageType.imageSize());
      imagePath.append(imageType.fileExtension());
      
      bufferImage = loadImageFromFile(imagePath.toString());
      if(bufferImage == null)
      {
        // Load the image from EVE, after a saved file is not found.
        StringBuilder imageUrlPath = new StringBuilder(appProperties.getProperty(EnumNetworkProperties.IMAGE_SERVER.propertyName()));
        imageUrlPath.append(imageType.subDir());
        imageUrlPath.append(imageParam);
        imageUrlPath.append("_");
        imageUrlPath.append(imageType.imageSize());
        imageUrlPath.append(imageType.fileExtension());

        URL imageUrl = new URL(imageUrlPath.toString());  
        URLConnection imageConn = imageUrl.openConnection(ApiSettings.getCurrentProxy());

        bufferImage = ImageIO.read(imageConn.getInputStream());
        File outImageFile = new File(imagePath.toString());
        if(outImageFile.mkdirs())
        {
          ImageIO.write(bufferImage, imageType.fileExtension().substring(1), outImageFile);
        }
      }
      
      return new ImageIcon(bufferImage);
      
    } 
    catch (Exception genEx)
    {
      throw new ApiException(genEx);
    }
  }
};
