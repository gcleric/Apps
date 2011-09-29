/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.beimin.eveapi.EveApi;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoParser;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.beimin.eveapi.account.characters.EveCharacter;
import com.beimin.eveapi.account.characters.CharactersParser;
import com.beimin.eveapi.account.characters.CharactersResponse;
import com.beimin.eveapi.connectors.ApiConnector;
import com.beimin.eveapi.connectors.ProxyConnector;
import com.beimin.eveapi.core.ApiAuthorization;
import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.corporation.starbase.detail.StarbaseDetailParser;
import com.beimin.eveapi.corporation.starbase.detail.StarbaseDetailResponse;
import com.beimin.eveapi.corporation.starbase.list.StarbaseListParser;
import com.beimin.eveapi.corporation.starbase.list.StarbaseListResponse;
import evedata.Account;
import evedata.Character;
import evedata.Starbase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Proxy;
import java.util.List;
import java.util.Properties;
import modules.fuelplanner.FuelPlanner;
import modules.starbaseinfo.StarbaseInfo;
import evetycoon.EveTycoonApp;

/**
 *
 * @author hrivanov
 */
public class ApiService
{
  /** Reference to the singleton's instance. */
  private static ApiService instance = null;
  /** Reference to the application properties. */
  private Properties appProperties;
  
  /**
   * Default constructor.
   */
  protected ApiService()
  {
    appProperties = EveTycoonApp.getApplication().getProperties();
  }
  
  /**
   * Returns an instance to the ApiService object.
   * @return The instance of the ApiService object
   */
  public static ApiService getInstance()
  {
    if(instance == null)
    {
      instance = new ApiService();
    }
    
    return instance;
  }
  
  /**
   * Loads data from a file.
   * @param fileProperty The application property name to read info from.
   * @return Loaded data object.
   * @throws ApiException 
   */
  private <T> T loadDataFromFile(EnumFileProperties fileProperty) throws ApiException
  {
    String dataFilePath = appProperties.getProperty(fileProperty.propertyName());
    String fileType = fileProperty.additionaInfo();
    
    File dataFile = new File(dataFilePath);
    T loadedObject = null;
    FileInputStream inFileStream = null;
    ObjectInputStream inObjStream = null;
    try
    {
      if(dataFile.exists())
      {
        inFileStream = new FileInputStream(dataFile);
        inObjStream = new ObjectInputStream(inFileStream);

        loadedObject = (T)inObjStream.readObject();
        inObjStream.close();
        inFileStream.close(); 
      }

      return loadedObject;
    }
    catch(FileNotFoundException fnfEx)
    {
      throw new ApiException("Missing " + fileType + " file.", fnfEx.getCause());
    }
    catch(IOException ioEx)
    {
      dataFile.delete();
      throw new ApiException("Error while reading the " + fileType + " file.", ioEx.getCause());
    }
    catch(ClassNotFoundException cnfEx)
    {
      dataFile.delete();
      throw new ApiException("Corrupt " + fileType + " file.", cnfEx.getCause());
    }
    catch(ClassCastException ccEx)
    {
      dataFile.delete();
      throw new ApiException("Corrupt " + fileType + " file.", ccEx.getCause());
    }
  }
  
  /**
   * Saves an object to a file.
   * @param <T> Type of the saved object.
   * @param savedObject A reference to the saved object.
   * @param fileProperty Property holding the file path.
   * @throws ApiException 
   */
  private <T> void saveDataToFile(T savedObject, EnumFileProperties fileProperty) throws ApiException
  {
    try
    {
      String outFilePath = appProperties.getProperty(fileProperty.propertyName());
      FileOutputStream outFileStream = new FileOutputStream(outFilePath);
      ObjectOutputStream outObjStream = new ObjectOutputStream(outFileStream);

      outObjStream.writeObject(savedObject);
      outObjStream.close();
    }
    catch(IOException ioEx)
    {
      throw new ApiException("Error while writing the " + fileProperty.additionaInfo() + " file:" + ioEx.getMessage(), ioEx.getCause());
    }
  }
  
  /**
   * Sets a new proxy server according to the application-wide setting.
   */
  public void setProxyServer()
  {
    if(ApiSettings.getCurrentProxy() != Proxy.NO_PROXY)
    {
      ProxyConnector proxyConn = new ProxyConnector(ApiSettings.getCurrentProxy(), EveApi.getConnector());
      EveApi.setConnector(proxyConn);
    }
    else
    {
      ApiConnector noProxyConn = new ApiConnector();
      EveApi.setConnector(noProxyConn);
    }
  }
  
  public boolean checkApiKey(int keyID, String verificationCode, int accessMask, ApiKeyTypes apiKeyType) throws ApiException
  {
    ApiAuthorization accountAuth = new ApiAuthorization(keyID, 0, verificationCode);
    ApiKeyInfoParser apiKeyInfoParser = ApiKeyInfoParser.getInstance();
    ApiKeyInfoResponse apiKeyInfoResponse = apiKeyInfoParser.getResponse(accountAuth);
    
    return (apiKeyType.description().equals(apiKeyInfoResponse.getType()))&&(accessMask == apiKeyInfoResponse.getAccessMask());
  }
 
  /**
   * Loads account data from the EVE servers.
   * @param keyID User ID of the account.
   * @param verificationCode API key of the account.
   * @return An Account object.
   * @throws ApiException 
   */  
  public Account loadAccountInfoAPI(int keyID, String verificationCode) throws ApiException
  {
    try
    {
      Account currAccountInfo = ApiData.getCurrAccountInfo();
      
      if(!currAccountInfo.canUpdate())
      {
        return currAccountInfo;
      }
      
      // Load the data from the EVE servers
      ApiAuthorization accountAuth = new ApiAuthorization(keyID, 0, verificationCode);
      CharactersParser charParser = CharactersParser.getInstance();
      CharactersResponse charResponse = charParser.getResponse(accountAuth);
      
      if(charResponse.hasError())
      {
        throw new ApiException(charResponse.getError().toString());
      }
         
      Character newApiChar = null;
      String imageParam = null;
      ImageService imgServices = ImageService.getInstance();
      
      // Set the new data fields
      currAccountInfo.setKeyID(keyID);
      currAccountInfo.setVerificationCode(verificationCode);
      
      currAccountInfo.copyApiResponse(charResponse);
      
      // Clear the old and add the new characters
      currAccountInfo.getAccountCharacters().clear();
      
      for(EveCharacter eveChar: charResponse.getAll())
      {
        newApiChar = new Character(eveChar);
        
        imageParam = String.valueOf(newApiChar.getCharacterID());
        newApiChar.setCharPortrait(imgServices.getImage(imageParam, ImageTypes.PORTRAIT));
        
        imageParam = String.valueOf(newApiChar.getCorporationID());
        newApiChar.setCharCorpLogo(imgServices.getImage(imageParam, ImageTypes.CORPORATION));
        
        currAccountInfo.addAccountCharacter(newApiChar);
      }
      
      return currAccountInfo;
    }
    catch(Exception genEx)
    {
      throw new ApiException(genEx);
    }
  }
  
  /**
   * Loads the account data from a file
   * @return An Account object, null if a file is not found.
   * @throws ApiException 
   */
  public Account loadAccountInfoFile() throws ApiException
  {
    Account currAccountInfo = loadDataFromFile(EnumFileProperties.ACCOUNT_FILE);
    if(currAccountInfo != null)
      ApiData.setCurrAccountInfo(currAccountInfo);
    
    return currAccountInfo;
  }
  
  /**
   * Saves the account data to a file.
   * @throws ApiException 
   */
  public void saveAccountInfo() throws ApiException
  {
    Account currAccountInfo = ApiData.getCurrAccountInfo();
    saveDataToFile(currAccountInfo, EnumFileProperties.ACCOUNT_FILE);
  } 
  
  public void updateStarbaseList(StarbaseInfo starbaseInfo) throws ApiException
  {
    Account currAccountInfo = ApiData.getCurrAccountInfo();
    
    if(!currAccountInfo.hasApiData())
      throw new ApiException("No account info loaded. Please add an account.");
    
    // Get the API authorization
    ApiAuthorization accountAuth = new ApiAuthorization(currAccountInfo.getKeyID(), currAccountInfo.getDefaultCharacter().getCharacterID(),  currAccountInfo.getVerificationCode());
    
    // Update the starbase list
    if(true) //starbaseInfo.canUpdate()
    {
      StarbaseListParser baseListParser = StarbaseListParser.getInstance();
      StarbaseListResponse baseListResponse = baseListParser.getResponse(accountAuth);

      if(baseListResponse.hasError())
      {
        throw new ApiException(baseListResponse.getError().toString());
      }

      starbaseInfo.copyApiResponse(baseListResponse);
    }
  }
  
  public void updateStarbaseDetails(List<Starbase> starbaseList) throws ApiException
  {
    Account currAccountInfo = ApiData.getCurrAccountInfo();
    
    if(!currAccountInfo.hasApiData())
      throw new ApiException("No account info loaded. Please add an account.");
    
    // Get the API authorization
    ApiAuthorization accountAuth = new ApiAuthorization(currAccountInfo.getKeyID(), currAccountInfo.getDefaultCharacter().getCharacterID(),  currAccountInfo.getVerificationCode());
    
    // Update the starbase info
    StarbaseDetailParser baseDetailParser = StarbaseDetailParser.getInstance();
    StarbaseDetailResponse baseDetailResponse = null;
    for(Starbase starBase : starbaseList)
    {
      if(starBase.canUpdate())
      {
        baseDetailResponse = baseDetailParser.getResponse(accountAuth, starBase.getItemID());
        starBase.copyApiResponse(baseDetailResponse);
      }
    }
  }
  
  /**
   * Loads the starbase list from a file.
   * @return An StarbaseData object, null if a file is not found.
   * @throws ApiException
   */
  public StarbaseInfo loadStarbaseInfoFile() throws ApiException
  {
    StarbaseInfo currStarbaseInfo = loadDataFromFile(EnumFileProperties.STARBASE_FILE);
    if(currStarbaseInfo != null)
      ApiData.setCurrStarbaseInfo(currStarbaseInfo);
    
    return currStarbaseInfo;
  }
  
  /**
   * Saves the starbase data to a file.
   * @throws ApiException 
   */
  public void saveStarbaseInfo() throws ApiException
  {
    StarbaseInfo currStarbaseInfo = ApiData.getCurrStarbaseInfo();
    if(currStarbaseInfo.hasApiData())
      saveDataToFile(currStarbaseInfo, EnumFileProperties.STARBASE_FILE);
  } 
  
  /**
   * Loads the starbase list from a file.
   * @return An StarbaseData object, null if a file is not found.
   * @throws ApiException
   */
  public FuelPlanner loadFuelplannerFile() throws ApiException
  {
    /*FuelPlanner currFuelPlanner = loadDataFromFile(EnumFileProperties.FUELPLANNER_FILE);
    if(currFuelPlanner != null)
      ApiData.setCurrFuelPlanner(currFuelPlanner);
    
    return currFuelPlanner;*/
    return ApiData.getCurrFuelPlanner();
  }
  
  /**
   * Saves the starbase data to a file.
   * @throws ApiException 
   */
  public void saveFuelplanner() throws ApiException
  {
    /*FuelPlanner currFuelPlanner = ApiData.getCurrFuelPlanner();
    if(!currFuelPlanner.isEmpty())
      saveDataToFile(currFuelPlanner, EnumFileProperties.FUELPLANNER_FILE);*/
  } 
}
