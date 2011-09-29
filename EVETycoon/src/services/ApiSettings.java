/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.net.Proxy;

/**
 * Class holding different settings for the application.
 * @author hrivanov
 */
public class ApiSettings
{
  /** The application-wide proxy server. */
  private static Proxy currentProxy = Proxy.NO_PROXY;

  /**
   * Returns the current application-wide proxy server.
   * @return The current application-wide proxy server.
   */
  public static Proxy getCurrentProxy()
  {
    return currentProxy;
  }

  /**
   * Sets a new application-wide proxy server.
   * @param newProxy A new application-wide proxy server.
   */
  public static void setCurrentProxy(Proxy newProxy)
  {
    ApiSettings.currentProxy = newProxy;
    ApiService.getInstance().setProxyServer();
  }
}
