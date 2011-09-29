/*
 * EveTycoonApp.java
 */
package evetycoon;

import com.beimin.eveapi.core.ApiException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import services.ApiService;
import services.ApiSettings;
import services.EnumFileProperties;
import services.EnumGeneralProperties;
import services.EnumNetworkProperties;

/**
 * The main class of the application.
 */
public class EveTycoonApp extends SingleFrameApplication
{
  private Properties appProperties;
  private ApiService apiServices;
  
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
        
        // Add any new general properties.
        for(EnumGeneralProperties propType : EnumGeneralProperties.values())
        {
          if(!appProperties.containsKey(propType.propertyName()))
          {
            appProperties.setProperty(propType.propertyName(), propType.defaultValue());
          }
        }
        // Add any new network properties.
        for(EnumNetworkProperties propType : EnumNetworkProperties.values())
        {
          if(!appProperties.containsKey(propType.propertyName()))
          {
            appProperties.setProperty(propType.propertyName(), propType.defaultValue());
          }
        }
        // Add any new file properties.
        for(EnumFileProperties propType : EnumFileProperties.values())
        {
          if(!appProperties.containsKey(propType.propertyName()))
          {
            appProperties.setProperty(propType.propertyName(), propType.defaultValue());
          }
        }
      }
      else
      {
        // Add any new general properties.
        for(EnumGeneralProperties propType : EnumGeneralProperties.values())
        {
          appProperties.setProperty(propType.propertyName(), propType.defaultValue());
        }
        // Add any new network properties.
        for(EnumNetworkProperties propType : EnumNetworkProperties.values())
        {
          appProperties.setProperty(propType.propertyName(), propType.defaultValue());
        }
        // Add any new file properties.
        for(EnumFileProperties propType : EnumFileProperties.values())
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
   * Saves the application properties.
   */
  public void saveAppProperties()
  {
    FileOutputStream outConfStream;
    try
    {
      outConfStream = new FileOutputStream("config.cfg");
      appProperties.store(outConfStream, "-- Default application properties --");
      outConfStream.close();  
    } 
    catch (FileNotFoundException fnfEx)
    {
      Logger.getLogger(EveTycoonApp.class.getName()).log(Level.SEVERE, null, fnfEx);
    }
    catch (IOException ioEx)
    {
      Logger.getLogger(EveTycoonApp.class.getName()).log(Level.SEVERE, null, ioEx);
    }
  }
  
  /**
   * Sets the application-wide proxy server.
   */
  public void setAppProxy()
  {
    Boolean noProxy = Boolean.valueOf(appProperties.getProperty(EnumNetworkProperties.NO_PROXY.propertyName()));
    
    if(!noProxy)
    {
      String proxyName = appProperties.getProperty(EnumNetworkProperties.PROXY_NAME.propertyName());
      String proxyPort = appProperties.getProperty(EnumNetworkProperties.PROXY_PORT.propertyName());
      if((proxyName != null)&&(proxyPort != null))
      {
        if((!proxyName.isEmpty()) && (!proxyPort.isEmpty()))
        {
          Proxy newProxy = new Proxy(Proxy.Type.HTTP, 
                                new InetSocketAddress(proxyName, 
                                Integer.parseInt(proxyPort)));
          ApiSettings.setCurrentProxy(newProxy);
        }
        else
          ApiSettings.setCurrentProxy(Proxy.NO_PROXY);
      }
    }
    else
      ApiSettings.setCurrentProxy(Proxy.NO_PROXY);
  }
          
  
  /**
   * At startup create and show the main frame of the application.
   */
  @Override
  protected void startup()
  {
    appProperties = new Properties();
    apiServices = ApiService.getInstance();
  
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
    show(new EveTycoonView(this));
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
   * @return the instance of EveTycoonApp
   */
  public static EveTycoonApp getApplication()
  {
    return Application.getInstance(EveTycoonApp.class);
  }

  @Override
  protected void shutdown()
  {
    super.shutdown();
    
    try
    {
      saveAppProperties();
      
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
    launch(EveTycoonApp.class, args);
  }
}
