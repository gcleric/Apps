/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NetworkOptionsPanel.java
 *
 * Created on Sep 19, 2011, 4:32:06 PM
 */
package evetycoon;

import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.EnumNetworkProperties;

/**
 *
 * @author hrivanov
 */
public class NetworkOptionsPanel extends javax.swing.JPanel implements OptionPanelInterface
{
  /** Properties of the network property panel. */
  private Properties networkProperties;
  /** Indicates whether a proxy server is to be used. */
  private Boolean noProxy;

  /** Creates new form NetworkOptionsPanel */
  public NetworkOptionsPanel()
  {
    initComponents();
      
    this.noProxy = false;
    this.networkProperties = new Properties();
    
    for(EnumNetworkProperties propertyName : EnumNetworkProperties.values())
    {
      networkProperties.setProperty(propertyName.propertyName(), propertyName.defaultValue());
    }
  }

  public Properties getProperties()
  {
    this.networkProperties.setProperty(EnumNetworkProperties.NO_PROXY.propertyName(), noProxy.toString());
    this.networkProperties.setProperty(EnumNetworkProperties.PROXY_NAME.propertyName(), proxyHostnameField.getText());
    this.networkProperties.setProperty(EnumNetworkProperties.PROXY_PORT.propertyName(), proxyPortField.getValue().toString());
    
    this.networkProperties.setProperty(EnumNetworkProperties.IMAGE_SERVER.propertyName(), imageServerField.getText());
    
    return networkProperties;
  }

  public void setProperties(Properties networkProperties)
  {
    String propertyName;
    String foundValue;
    String defaultValue;
    
    propertyName = EnumNetworkProperties.NO_PROXY.propertyName();
    defaultValue = EnumNetworkProperties.NO_PROXY.defaultValue();
    foundValue = networkProperties.getProperty(propertyName, defaultValue);
    this.networkProperties.setProperty(propertyName, foundValue);
    this.noProxy = Boolean.valueOf(foundValue);
    this.noProxyCheckBox.setSelected(noProxy);
    
    propertyName = EnumNetworkProperties.PROXY_NAME.propertyName();
    defaultValue = EnumNetworkProperties.PROXY_NAME.defaultValue();
    foundValue = networkProperties.getProperty(propertyName, defaultValue);
    this.networkProperties.setProperty(propertyName, foundValue);
    proxyHostnameField.setText(foundValue);
    proxyHostnameField.setEnabled(!noProxy);
    
    propertyName = EnumNetworkProperties.PROXY_PORT.propertyName();
    defaultValue = EnumNetworkProperties.PROXY_PORT.defaultValue();
    foundValue = networkProperties.getProperty(propertyName, defaultValue);
    this.networkProperties.setProperty(propertyName, foundValue);
    if(foundValue.isEmpty())
      proxyPortField.setText(foundValue);
    else
      proxyPortField.setValue(Integer.parseInt(foundValue));
    try
    {
      proxyPortField.commitEdit();
    } catch (ParseException ex)
    {
      Logger.getLogger(NetworkOptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
    }
    proxyPortField.setEnabled(!noProxy);
    
    propertyName = EnumNetworkProperties.IMAGE_SERVER.propertyName();
    defaultValue = EnumNetworkProperties.IMAGE_SERVER.defaultValue();
    foundValue = networkProperties.getProperty(propertyName, defaultValue);
    this.networkProperties.setProperty(propertyName, foundValue);
    imageServerField.setText(foundValue);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    proxyPanel = new javax.swing.JPanel();
    proxyNameLabel = new javax.swing.JLabel();
    proxyHostnameField = new javax.swing.JTextField();
    proxyPortField = new javax.swing.JFormattedTextField();
    proxyPortLabel = new javax.swing.JLabel();
    noProxyCheckBox = new javax.swing.JCheckBox();
    imageServerPanel = new javax.swing.JPanel();
    imageServerNameLabel = new javax.swing.JLabel();
    imageServerField = new javax.swing.JTextField();

    setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
    setName("Form"); // NOI18N
    setPreferredSize(new java.awt.Dimension(350, 160));

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(evetycoon.EveTycoonApp.class).getContext().getResourceMap(NetworkOptionsPanel.class);
    proxyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("proxyPanel.border.title"))); // NOI18N
    proxyPanel.setName("proxyPanel"); // NOI18N

    proxyNameLabel.setText(resourceMap.getString("proxyNameLabel.text")); // NOI18N
    proxyNameLabel.setName("proxyNameLabel"); // NOI18N

    proxyHostnameField.setText(resourceMap.getString("proxyHostnameField.text")); // NOI18N
    proxyHostnameField.setName("proxyHostnameField"); // NOI18N

    proxyPortField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    proxyPortField.setText(resourceMap.getString("proxyPortField.text")); // NOI18N
    proxyPortField.setName("proxyPortField"); // NOI18N

    proxyPortLabel.setText(resourceMap.getString("proxyPortLabel.text")); // NOI18N
    proxyPortLabel.setName("proxyPortLabel"); // NOI18N

    noProxyCheckBox.setText(resourceMap.getString("noProxyCheckBox.text")); // NOI18N
    noProxyCheckBox.setName("noProxyCheckBox"); // NOI18N
    noProxyCheckBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        noProxyCheckBoxItemStateChanged(evt);
      }
    });

    javax.swing.GroupLayout proxyPanelLayout = new javax.swing.GroupLayout(proxyPanel);
    proxyPanel.setLayout(proxyPanelLayout);
    proxyPanelLayout.setHorizontalGroup(
      proxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(proxyPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(noProxyCheckBox)
        .addContainerGap(231, Short.MAX_VALUE))
      .addGroup(proxyPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(proxyNameLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(proxyHostnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(35, 35, 35)
        .addComponent(proxyPortLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(proxyPortField, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(13, Short.MAX_VALUE))
    );
    proxyPanelLayout.setVerticalGroup(
      proxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(proxyPanelLayout.createSequentialGroup()
        .addComponent(noProxyCheckBox)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(proxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(proxyHostnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(proxyNameLabel)
          .addComponent(proxyPortField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(proxyPortLabel))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    imageServerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("imageServerPanel.border.title"))); // NOI18N
    imageServerPanel.setName("imageServerPanel"); // NOI18N

    imageServerNameLabel.setText(resourceMap.getString("imageServerNameLabel.text")); // NOI18N
    imageServerNameLabel.setName("imageServerNameLabel"); // NOI18N

    imageServerField.setName("imageServerField"); // NOI18N

    javax.swing.GroupLayout imageServerPanelLayout = new javax.swing.GroupLayout(imageServerPanel);
    imageServerPanel.setLayout(imageServerPanelLayout);
    imageServerPanelLayout.setHorizontalGroup(
      imageServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(imageServerPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(imageServerNameLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(imageServerField, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
        .addContainerGap())
    );
    imageServerPanelLayout.setVerticalGroup(
      imageServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(imageServerPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(imageServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(imageServerNameLabel)
          .addComponent(imageServerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(26, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(imageServerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(proxyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(proxyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(imageServerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void noProxyCheckBoxItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_noProxyCheckBoxItemStateChanged
  {//GEN-HEADEREND:event_noProxyCheckBoxItemStateChanged
    noProxy = (evt.getStateChange() == ItemEvent.SELECTED);
    proxyHostnameField.setEnabled(!noProxy);
    proxyPortField.setEnabled(!noProxy);
  }//GEN-LAST:event_noProxyCheckBoxItemStateChanged

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField imageServerField;
  private javax.swing.JLabel imageServerNameLabel;
  private javax.swing.JPanel imageServerPanel;
  private javax.swing.JCheckBox noProxyCheckBox;
  private javax.swing.JTextField proxyHostnameField;
  private javax.swing.JLabel proxyNameLabel;
  private javax.swing.JPanel proxyPanel;
  private javax.swing.JFormattedTextField proxyPortField;
  private javax.swing.JLabel proxyPortLabel;
  // End of variables declaration//GEN-END:variables
}