/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GeneralOptionsPanel.java
 *
 * Created on Sep 19, 2011, 4:24:04 PM
 */
package evetycoon;

import java.util.Properties;

/**
 *
 * @author hrivanov
 */
public class GeneralOptionsPanel extends javax.swing.JPanel implements OptionPanelInterface
{
  /** Properties of the option panel. */
  private Properties generalProperties;
  
  /** Creates new form GeneralOptionsPanel */
  public GeneralOptionsPanel()
  {
    initComponents();
    
    generalProperties = new Properties();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
    setName("Form"); // NOI18N
    setPreferredSize(new java.awt.Dimension(350, 160));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 344, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 159, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables

  public Properties getProperties()
  {
    return generalProperties;
  }

  public void setProperties(Properties newProperties)
  {
    //generalProperties = newProperties;
  }
}
