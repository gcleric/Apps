/*
 * OutPOStApp.java
 */
package evetycoon;

import com.beimin.eveapi.core.ApiException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import services.ApiService;
import services.ApiSettings;
import services.ImageService;
import services.PropertyTypes;

/**
 * The main class of the application.
 */
public class OutPOStApp extends SingleFrameApplication
{
  private Properties appProperties;
  private ApiService apiServices;
  private ImageService imgServices;
  
  /**
   * Reads the application property file if any.
   */
  private void readAppProperties()
  {
    try
    {
      File configFile = new File("config.cfg");
      if(configFile.exists())
      {
        FileInputStream inConfStream = new FileInputStream("config.cfg");
        appProperties.load(inConfStream);
        inConfStream.close();
        
        for(PropertyTypes propType : PropertyTypes.values())
        {
          if(!appProperties.containsKey(propType.propertyName()))
          {
            appProperties.setProperty(propType.propertyName(), propType.defaultValue());
          }
        }
      }
      else
      {
        for(PropertyTypes propType : PropertyTypes.values())
        {
          appProperties.setProperty(propType.propertyName(), propType.defaultValue());
        }
                        
        FileOutputStream outConfStream = new FileOutputStream("config.cfg");
        appProperties.store(outConfStream, "-- Default application properties --");
        outConfStream.close();
      }
    }
    catch(IOException ioEx)
    {
      
    }
  };
  
  /**
   * Sets the application-wide proxy server.
   */
  private void setAppProxy()
  {
    if(appProperties.containsKey("ProxyHost") && appProperties.containsKey("ProxyPort"))
    {
      if(!appProperties.getProperty("ProxyHost").isEmpty())
      {
        Proxy newProxy = new Proxy(Proxy.Type.HTTP, 
                                new InetSocketAddress(appProperties.getProperty("ProxyHost"), 
                                Integer.parseInt(appProperties.getProperty("ProxyPort"))));
        ApiSettings.setCurrentProxy(newProxy);
      }
    }
  }
          
  
  /**
   * At startup create and show the main frame of the application.
   */
  @Override
  protected void startup()
  {
    appProperties = new Properties();
    apiServices = ApiService.getInstance();
    imgServices = ImageService.getInstance();
  
    // Read the application properties.
    readAppProperties();
    
    // Set the application proxy, if any
    setAppProxy();
    
    try
    {
      apiServices.loadAccountInfoFile();
      apiServices.loadStarbaseInfoFile();
      apiServices.loadFuelplannerFile();
    }
    catch(ApiException apiEx)
    {
      JOptionPane.showMessageDialog(null, 
                                    apiEx.getMessage(), 
                                    "Exception", 
                                    JOptionPane.ERROR_MESSAGE);
    }
    
    // Show the main dialog
    show(new OutPOStView(this));
  }

  /**
   * This method is to initialize the specified window by injecting resources.
   * Windows shown in our application come fully initialized from the GUI
   * builder, so this additional configuration is not needed.
   */
  @Override
  protected void configureWindow(java.awt.Window root)
  {
  }

  /**
   * A convenient static getter for the application instance.
   * @return the instance of OutPOStApp
   */
  public static OutPOStApp getApplication()
  {
    return Application.getInstance(OutPOStApp.class);
  }

  @Override
  protected void shutdown()
  {
    super.shutdown();
    
    try
    {
      apiServices.saveAccountInfo();
      apiServices.saveStarbaseInfo();
      apiServices.saveFuelplanner();
    }
    catch(ApiException apiEx)
    {
      JOptionPane.showMessageDialog(null, 
                                    apiEx.getMessage(), 
                                    "Exception", 
                                    JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public Properties getProperties()
  {
    return appProperties;
  }

  /**
   * Main method launching the application.
   */
  public static void main(String[] args)
  {
    launch(OutPOStApp.class, args);
  }
}
