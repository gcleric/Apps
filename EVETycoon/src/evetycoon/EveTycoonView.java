/*
 * EveTycoonView.java
 */
package evetycoon;

import modules.gui.FuelPlannerPanel;
import modules.gui.StarbaseStatusPanel;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class EveTycoonView extends FrameView
{
  /** Starbase fuel levels info panel. */
  private StarbaseStatusPanel starbaseStatusPanel;
  /** Starbase fuel planner's panel. */
  private FuelPlannerPanel fuelPlannerPanel;
  /** */
  private Icon starbaseInfoIcon;
  /** */
  private Icon fuelPlannerIcon;

  public EveTycoonView(SingleFrameApplication app)
  {
    super(app);

    initComponents();

    // status bar initialization - message timeout, idle icon and busy animation, etc
    ResourceMap resourceMap = getResourceMap();
    int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
    messageTimer = new Timer(messageTimeout, new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        statusMessageLabel.setText("");
      }
    });
    messageTimer.setRepeats(false);
    int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
    for (int i = 0; i < busyIcons.length; i++)
    {
      busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
    }
    busyIconTimer = new Timer(busyAnimationRate, new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
        statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
      }
    });
    idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
    statusAnimationLabel.setIcon(idleIcon);
    progressBar.setVisible(false);

    // connecting action tasks to status bar via TaskMonitor
    TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
    taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener()
    {

      public void propertyChange(java.beans.PropertyChangeEvent evt)
      {
        String propertyName = evt.getPropertyName();
        if ("started".equals(propertyName))
        {
          if (!busyIconTimer.isRunning())
          {
            statusAnimationLabel.setIcon(busyIcons[0]);
            busyIconIndex = 0;
            busyIconTimer.start();
          }
          progressBar.setVisible(true);
          progressBar.setIndeterminate(true);
        } else if ("done".equals(propertyName))
        {
          busyIconTimer.stop();
          statusAnimationLabel.setIcon(idleIcon);
          progressBar.setVisible(false);
          progressBar.setValue(0);
        } else if ("message".equals(propertyName))
        {
          String text = (String) (evt.getNewValue());
          statusMessageLabel.setText((text == null) ? "" : text);
          messageTimer.restart();
        } else if ("progress".equals(propertyName))
        {
          int value = (Integer) (evt.getNewValue());
          progressBar.setVisible(true);
          progressBar.setIndeterminate(false);
          progressBar.setValue(value);
        }
      }
    });
    
    // Initialize the starbase status panel
    //addTab(String title, Icon icon, Component component, String tip)
    starbaseInfoIcon = new ImageIcon(getClass().getResource("/evetycoon/resources/starbase_info_32.png"));
    starbaseStatusPanel = new StarbaseStatusPanel();
    starbaseStatusPanel.initPanel();
    modulesPane.addTab("Fuel info", starbaseInfoIcon, starbaseStatusPanel, "Starbase fuel information");
    
    fuelPlannerIcon = new ImageIcon(getClass().getResource("/evetycoon/resources/fuel_planner_32.png"));
    fuelPlannerPanel = new FuelPlannerPanel();
    modulesPane.addTab("Fuel planner", fuelPlannerIcon, fuelPlannerPanel, "Starbase fuel planner");
 
  }
  

  @Action
  public void showAboutBox()
  {
    if (aboutBox == null)
    {
      JFrame mainFrame = EveTycoonApp.getApplication().getMainFrame();
      aboutBox = new EveTycoonAboutBox(mainFrame);
      aboutBox.setLocationRelativeTo(mainFrame);
    }
    EveTycoonApp.getApplication().show(aboutBox);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    mainPanel = new javax.swing.JPanel();
    modulesPane = new javax.swing.JTabbedPane();
    menuBar = new javax.swing.JMenuBar();
    javax.swing.JMenu fileMenu = new javax.swing.JMenu();
    authMenuItem = new javax.swing.JMenuItem();
    optionsMenuItem = new javax.swing.JMenuItem();
    javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
    javax.swing.JMenu helpMenu = new javax.swing.JMenu();
    javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
    statusPanel = new javax.swing.JPanel();
    javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
    statusMessageLabel = new javax.swing.JLabel();
    statusAnimationLabel = new javax.swing.JLabel();
    progressBar = new javax.swing.JProgressBar();

    mainPanel.setName("mainPanel"); // NOI18N

    modulesPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
    modulesPane.setName("modulesPane"); // NOI18N
    modulesPane.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(java.beans.PropertyChangeEvent evt) {
        modulesPanePropertyChange(evt);
      }
    });

    javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
    mainPanel.setLayout(mainPanelLayout);
    mainPanelLayout.setHorizontalGroup(
      mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(modulesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        .addContainerGap())
    );
    mainPanelLayout.setVerticalGroup(
      mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(modulesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
        .addContainerGap())
    );

    menuBar.setName("menuBar"); // NOI18N

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(evetycoon.EveTycoonApp.class).getContext().getResourceMap(EveTycoonView.class);
    fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
    fileMenu.setName("fileMenu"); // NOI18N

    authMenuItem.setText(resourceMap.getString("authMenuItem.text")); // NOI18N
    authMenuItem.setName("authMenuItem"); // NOI18N
    authMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        authMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(authMenuItem);

    optionsMenuItem.setText(resourceMap.getString("optionsMenuItem.text")); // NOI18N
    optionsMenuItem.setName("optionsMenuItem"); // NOI18N
    optionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        optionsMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(optionsMenuItem);

    javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(evetycoon.EveTycoonApp.class).getContext().getActionMap(EveTycoonView.class, this);
    exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
    exitMenuItem.setName("exitMenuItem"); // NOI18N
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
    helpMenu.setName("helpMenu"); // NOI18N

    aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
    aboutMenuItem.setName("aboutMenuItem"); // NOI18N
    helpMenu.add(aboutMenuItem);

    menuBar.add(helpMenu);

    statusPanel.setName("statusPanel"); // NOI18N

    statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

    statusMessageLabel.setName("statusMessageLabel"); // NOI18N

    statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

    progressBar.setName("progressBar"); // NOI18N

    javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
    statusPanel.setLayout(statusPanelLayout);
    statusPanelLayout.setHorizontalGroup(
      statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
      .addGroup(statusPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(statusMessageLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 430, Short.MAX_VALUE)
        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(statusAnimationLabel)
        .addContainerGap())
    );
    statusPanelLayout.setVerticalGroup(
      statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(statusPanelLayout.createSequentialGroup()
        .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(statusMessageLabel)
          .addComponent(statusAnimationLabel)
          .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(3, 3, 3))
    );

    setComponent(mainPanel);
    setMenuBar(menuBar);
    setStatusBar(statusPanel);
  }// </editor-fold>//GEN-END:initComponents

    private void authMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authMenuItemActionPerformed
      AuthInfoDialog dlg = new AuthInfoDialog(this.getFrame(), true);
      dlg.setVisible(true);
    }//GEN-LAST:event_authMenuItemActionPerformed

    private void modulesPanePropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_modulesPanePropertyChange
    {//GEN-HEADEREND:event_modulesPanePropertyChange
      String propertyName = evt.getPropertyName();
      if(propertyName.equals(""))
      {
        
      }
    }//GEN-LAST:event_modulesPanePropertyChange

    private void optionsMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_optionsMenuItemActionPerformed
    {//GEN-HEADEREND:event_optionsMenuItemActionPerformed
      OptionsDialog dlg = new OptionsDialog(this.getFrame(), true);
      dlg.setProperties(EveTycoonApp.getApplication().getProperties());
      dlg.setVisible(true);
    }//GEN-LAST:event_optionsMenuItemActionPerformed

    
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenuItem authMenuItem;
  private javax.swing.JPanel mainPanel;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JTabbedPane modulesPane;
  private javax.swing.JMenuItem optionsMenuItem;
  private javax.swing.JProgressBar progressBar;
  private javax.swing.JLabel statusAnimationLabel;
  private javax.swing.JLabel statusMessageLabel;
  private javax.swing.JPanel statusPanel;
  // End of variables declaration//GEN-END:variables
  private final Timer messageTimer;
  private final Timer busyIconTimer;
  private final Icon idleIcon;
  private final Icon[] busyIcons = new Icon[15];
  private int busyIconIndex = 0;
  private JDialog aboutBox;
}
