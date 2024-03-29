/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StarbaseStatusPanel.java
 *
 * Created on Sep 7, 2011, 12:23:18 PM
 */
package modules.gui;

import com.beimin.eveapi.core.ApiException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modules.fuelplanner.FuelPlanner;
import services.ApiData;
import tables.BasicTableModel;

/**
 *
 * @author hrivanov
 */
public class FuelPlannerPanel extends javax.swing.JPanel
{
  private BasicTableModel fuelPlanListModel;
  private FuelPlanner fuelPlanner;
  private IconHeaderRenderer iconHeaderRenderer;
  private ListSelectionModel fuelPlanListSelModel;
  private Integer currIndex;
  private ArrayList<Integer> checkedIndexes;
  private DefaultCellRenderer defaultCellRenderer;
  private IconLabelRenderer iconLabelRenderer;
  

  /** Creates new form StarbaseStatusPanel */
  public FuelPlannerPanel()
  {
    initComponents();
    fuelPlanner = ApiData.getCurrFuelPlanner();
    
    monthsTextField.setValue(fuelPlanner.getMonths());
    weeksTextField.setValue(fuelPlanner.getWeeks());
    daysTextField.setValue(fuelPlanner.getDays());
    hoursTextField.setValue(fuelPlanner.getHours());
    
    checkedIndexes = new ArrayList<Integer>();
    
    iconHeaderRenderer = new IconHeaderRenderer();
    
    fuelPlanListModel = fuelPlanner.getFuelPlanListModel();
    fuelPlanListTable.setModel(fuelPlanListModel);
    
    iconLabelRenderer = new IconLabelRenderer();
    defaultCellRenderer = new DefaultCellRenderer();
    
    fuelPlanListTable.setDefaultRenderer(ImageIcon.class, iconLabelRenderer);
    
    fuelPlanListSelModel = fuelPlanListTable.getSelectionModel();
    fuelPlanListSelModel.addListSelectionListener(new ListSelectionListener()
    {

      public void valueChanged(ListSelectionEvent e)
      {
        //Ignore extra messages.
        if (e.getValueIsAdjusting())
        {
          return;
        }
        
        currIndex = fuelPlanListTable.getSelectedRow();
        if(currIndex >= 0)
        {
          if((checkedIndexes.contains(currIndex))&&(fuelPlanDataTable.getRowCount() > 0))
          {
            fuelPlanDataTable.clearSelection();
            fuelPlanDataTable.addRowSelectionInterval(checkedIndexes.indexOf(currIndex) * 3 + 1,checkedIndexes.indexOf(currIndex) * 3 + 2);
            
          }
        }
      }
    });
    
    fuelPlanDataTable.setModel(fuelPlanner.getFuelPlanDataModel());
    for(int cnt = 0; cnt < fuelPlanDataTable.getColumnCount(); cnt++)
    {
      fuelPlanDataTable.getColumnModel().getColumn(cnt).setHeaderRenderer(iconHeaderRenderer);
      fuelPlanDataTable.getColumnModel().getColumn(cnt).setCellRenderer(defaultCellRenderer);
    }

    fuelPlanListTable.clearSelection();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    timeSettingPanel = new javax.swing.JPanel();
    monthsLabel = new javax.swing.JLabel();
    weeksLabel = new javax.swing.JLabel();
    daysLabel = new javax.swing.JLabel();
    hoursLabel = new javax.swing.JLabel();
    monthsTextField = new javax.swing.JFormattedTextField();
    weeksTextField = new javax.swing.JFormattedTextField();
    daysTextField = new javax.swing.JFormattedTextField();
    hoursTextField = new javax.swing.JFormattedTextField();
    calculatePlanBtn = new javax.swing.JButton();
    starbaseListPanel = new javax.swing.JPanel();
    fuelPlannerListScrollPane = new javax.swing.JScrollPane();
    fuelPlanListTable = new javax.swing.JTable();
    starbaseFuelPanel = new javax.swing.JPanel();
    fuelsTabbedPane = new javax.swing.JTabbedPane();
    fuelPlannerDataScrollPane = new javax.swing.JScrollPane();
    fuelPlanDataTable = new javax.swing.JTable();
    storedFuelsPanel1 = new modules.gui.StoredFuelsPanel();
    starbaseCtrlPanel = new javax.swing.JPanel();
    addTowerBtn = new javax.swing.JButton();
    removeTowerBtn = new javax.swing.JButton();
    importBtn = new javax.swing.JButton();
    clearBtn = new javax.swing.JButton();

    setName("Form"); // NOI18N
    setPreferredSize(new java.awt.Dimension(656, 553));
    addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(java.awt.event.FocusEvent evt) {
        formFocusGained(evt);
      }
    });

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(evetycoon.EveTycoonApp.class).getContext().getResourceMap(FuelPlannerPanel.class);
    timeSettingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), resourceMap.getString("timeSettingPanel.border.title"))); // NOI18N
    timeSettingPanel.setName("timeSettingPanel"); // NOI18N

    monthsLabel.setText(resourceMap.getString("monthsLabel.text")); // NOI18N
    monthsLabel.setName("monthsLabel"); // NOI18N

    weeksLabel.setText(resourceMap.getString("weeksLabel.text")); // NOI18N
    weeksLabel.setName("weeksLabel"); // NOI18N

    daysLabel.setText(resourceMap.getString("daysLabel.text")); // NOI18N
    daysLabel.setName("daysLabel"); // NOI18N

    hoursLabel.setText(resourceMap.getString("hoursLabel.text")); // NOI18N
    hoursLabel.setName("hoursLabel"); // NOI18N

    monthsTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    monthsTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    monthsTextField.setText(resourceMap.getString("monthsTextField.text")); // NOI18N
    monthsTextField.setName("monthsTextField"); // NOI18N

    weeksTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    weeksTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    weeksTextField.setText(resourceMap.getString("weeksTextField.text")); // NOI18N
    weeksTextField.setName("weeksTextField"); // NOI18N

    daysTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    daysTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    daysTextField.setText(resourceMap.getString("daysTextField.text")); // NOI18N
    daysTextField.setName("daysTextField"); // NOI18N

    hoursTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    hoursTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    hoursTextField.setText(resourceMap.getString("hoursTextField.text")); // NOI18N
    hoursTextField.setName("hoursTextField"); // NOI18N

    calculatePlanBtn.setText(resourceMap.getString("calculatePlanBtn.text")); // NOI18N
    calculatePlanBtn.setName("calculatePlanBtn"); // NOI18N
    calculatePlanBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        calculatePlanBtnActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout timeSettingPanelLayout = new javax.swing.GroupLayout(timeSettingPanel);
    timeSettingPanel.setLayout(timeSettingPanelLayout);
    timeSettingPanelLayout.setHorizontalGroup(
      timeSettingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timeSettingPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(timeSettingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(calculatePlanBtn)
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, timeSettingPanelLayout.createSequentialGroup()
            .addComponent(monthsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(weeksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(daysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(hoursTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE))
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, timeSettingPanelLayout.createSequentialGroup()
            .addComponent(monthsLabel)
            .addGap(10, 10, 10)
            .addComponent(weeksLabel)
            .addGap(6, 6, 6)
            .addComponent(daysLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(hoursLabel)))
        .addContainerGap())
    );

    timeSettingPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {daysLabel, hoursLabel, monthsLabel, weeksLabel});

    timeSettingPanelLayout.setVerticalGroup(
      timeSettingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(timeSettingPanelLayout.createSequentialGroup()
        .addGroup(timeSettingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(weeksLabel)
          .addComponent(daysLabel)
          .addComponent(hoursLabel)
          .addComponent(monthsLabel))
        .addGap(1, 1, 1)
        .addGroup(timeSettingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(monthsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(weeksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(daysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(hoursTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(calculatePlanBtn)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    starbaseListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), resourceMap.getString("starbaseListPanel.border.title"))); // NOI18N
    starbaseListPanel.setName("starbaseListPanel"); // NOI18N

    fuelPlannerListScrollPane.setName("fuelPlannerListScrollPane"); // NOI18N

    fuelPlanListTable.setBackground(resourceMap.getColor("fuelPlanListTable.background")); // NOI18N
    fuelPlanListTable.setForeground(resourceMap.getColor("fuelPlanListTable.foreground")); // NOI18N
    fuelPlanListTable.setFillsViewportHeight(true);
    fuelPlanListTable.setGridColor(resourceMap.getColor("fuelPlanListTable.gridColor")); // NOI18N
    fuelPlanListTable.setName("fuelPlanListTable"); // NOI18N
    fuelPlanListTable.setRowHeight(32);
    fuelPlanListTable.setSelectionBackground(resourceMap.getColor("defaultTable.selectionBackground")); // NOI18N
    fuelPlanListTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    fuelPlannerListScrollPane.setViewportView(fuelPlanListTable);

    javax.swing.GroupLayout starbaseListPanelLayout = new javax.swing.GroupLayout(starbaseListPanel);
    starbaseListPanel.setLayout(starbaseListPanelLayout);
    starbaseListPanelLayout.setHorizontalGroup(
      starbaseListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(starbaseListPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(fuelPlannerListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        .addContainerGap())
    );
    starbaseListPanelLayout.setVerticalGroup(
      starbaseListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(starbaseListPanelLayout.createSequentialGroup()
        .addComponent(fuelPlannerListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
        .addContainerGap())
    );

    starbaseFuelPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), resourceMap.getString("starbaseFuelPanel.border.title"))); // NOI18N
    starbaseFuelPanel.setName("starbaseFuelPanel"); // NOI18N
    starbaseFuelPanel.setRequestFocusEnabled(false);

    fuelsTabbedPane.setName("fuelsTabbedPane"); // NOI18N

    fuelPlannerDataScrollPane.setName("fuelPlannerDataScrollPane"); // NOI18N

    fuelPlanDataTable.setBackground(resourceMap.getColor("fuelPlanDataTable.background")); // NOI18N
    fuelPlanDataTable.setForeground(resourceMap.getColor("fuelPlanDataTable.foreground")); // NOI18N
    fuelPlanDataTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {},
        {},
        {},
        {},
        {},
        {},
        {}
      },
      new String [] {

      }
    ));
    fuelPlanDataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    fuelPlanDataTable.setFillsViewportHeight(true);
    fuelPlanDataTable.setGridColor(resourceMap.getColor("fuelPlanDataTable.gridColor")); // NOI18N
    fuelPlanDataTable.setName("fuelPlanDataTable"); // NOI18N
    fuelPlanDataTable.setSelectionBackground(resourceMap.getColor("defaultTable.selectionBackground")); // NOI18N
    fuelPlanDataTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    fuelPlannerDataScrollPane.setViewportView(fuelPlanDataTable);

    fuelsTabbedPane.addTab(resourceMap.getString("fuelPlannerDataScrollPane.TabConstraints.tabTitle"), fuelPlannerDataScrollPane); // NOI18N

    storedFuelsPanel1.setName("storedFuelsPanel1"); // NOI18N
    fuelsTabbedPane.addTab(resourceMap.getString("storedFuelsPanel1.TabConstraints.tabTitle"), storedFuelsPanel1); // NOI18N

    javax.swing.GroupLayout starbaseFuelPanelLayout = new javax.swing.GroupLayout(starbaseFuelPanel);
    starbaseFuelPanel.setLayout(starbaseFuelPanelLayout);
    starbaseFuelPanelLayout.setHorizontalGroup(
      starbaseFuelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(fuelsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
    );
    starbaseFuelPanelLayout.setVerticalGroup(
      starbaseFuelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(fuelsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
    );

    starbaseCtrlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), resourceMap.getString("starbaseCtrlPanel.border.title"))); // NOI18N
    starbaseCtrlPanel.setName("starbaseCtrlPanel"); // NOI18N

    addTowerBtn.setText(resourceMap.getString("addTowerBtn.text")); // NOI18N
    addTowerBtn.setName("addTowerBtn"); // NOI18N
    addTowerBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        addTowerBtnActionPerformed(evt);
      }
    });

    removeTowerBtn.setText(resourceMap.getString("removeTowerBtn.text")); // NOI18N
    removeTowerBtn.setName("removeTowerBtn"); // NOI18N
    removeTowerBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        removeTowerBtnActionPerformed(evt);
      }
    });

    importBtn.setText(resourceMap.getString("importBtn.text")); // NOI18N
    importBtn.setName("importBtn"); // NOI18N
    importBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        importBtnActionPerformed(evt);
      }
    });

    clearBtn.setText(resourceMap.getString("clearBtn.text")); // NOI18N
    clearBtn.setName("clearBtn"); // NOI18N
    clearBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        clearBtnActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout starbaseCtrlPanelLayout = new javax.swing.GroupLayout(starbaseCtrlPanel);
    starbaseCtrlPanel.setLayout(starbaseCtrlPanelLayout);
    starbaseCtrlPanelLayout.setHorizontalGroup(
      starbaseCtrlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(starbaseCtrlPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(starbaseCtrlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(clearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(importBtn))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(starbaseCtrlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(addTowerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
          .addComponent(removeTowerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGap(24, 24, 24))
    );

    starbaseCtrlPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addTowerBtn, importBtn, removeTowerBtn});

    starbaseCtrlPanelLayout.setVerticalGroup(
      starbaseCtrlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(starbaseCtrlPanelLayout.createSequentialGroup()
        .addGroup(starbaseCtrlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(importBtn)
          .addComponent(addTowerBtn))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(starbaseCtrlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(clearBtn)
          .addComponent(removeTowerBtn))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(starbaseFuelPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(starbaseListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
              .addComponent(starbaseCtrlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(timeSettingPanel, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(starbaseCtrlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(timeSettingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(starbaseListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(starbaseFuelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

  private void calculatePlanBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_calculatePlanBtnActionPerformed
  {//GEN-HEADEREND:event_calculatePlanBtnActionPerformed
    checkedIndexes.clear();
    for(int cnt = 0; cnt < fuelPlanListModel.getRowCount(); cnt++)
    {
      if(((Boolean)fuelPlanListModel.getValueAt(cnt, 0)))
      {
        fuelPlanner.setPlanActivation(cnt, true);
        checkedIndexes.add(cnt);
      }
      else
        fuelPlanner.setPlanActivation(cnt, false);
    }
    
    if(fuelPlanner.isEmpty())
      return;
    
    int months = 0;
    int weeks = 0;
    int days = 0;
    int hours = 0;
    
    if(monthsTextField.getValue() != null)
      months = ((Long)monthsTextField.getValue()).intValue();

    if(weeksTextField.getValue() != null)
      weeks = ((Long)weeksTextField.getValue()).intValue();
    
    if(daysTextField.getValue() != null)
      days = ((Long)daysTextField.getValue()).intValue();
    
    if(hoursTextField.getValue() != null)
      hours = ((Long)hoursTextField.getValue()).intValue();
    
    try
    {
      fuelPlanner.calculatePlans(months, weeks, days, hours);
      
      fuelPlanDataTable.setModel(fuelPlanner.getFuelPlanDataModel());
      for(int cnt = 0; cnt < fuelPlanDataTable.getColumnCount(); cnt++)
      {
        fuelPlanDataTable.getColumnModel().getColumn(cnt).setHeaderRenderer(iconHeaderRenderer);
        fuelPlanDataTable.getColumnModel().getColumn(cnt).setCellRenderer(defaultCellRenderer);
      }
      
      fuelPlanListTable.clearSelection();
    } 
    catch (SQLException sqlEx)
    {
       JOptionPane.showMessageDialog(this,
                sqlEx.getMessage(),
                "Exception",
                JOptionPane.ERROR_MESSAGE);
    }
    catch (ApiException apiEx)
    {
       JOptionPane.showMessageDialog(this,
                apiEx.getMessage(),
                "Exception",
                JOptionPane.ERROR_MESSAGE);
    }
  }//GEN-LAST:event_calculatePlanBtnActionPerformed

  private void formFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_formFocusGained
  {//GEN-HEADEREND:event_formFocusGained
 
  }//GEN-LAST:event_formFocusGained

  private void removeTowerBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeTowerBtnActionPerformed
  {//GEN-HEADEREND:event_removeTowerBtnActionPerformed
    if(fuelPlanListTable.getSelectedRow() >= 0)
    {
      fuelPlanner.removeStarbase(fuelPlanListTable.getSelectedRow());
      if(checkedIndexes.contains(((Integer)fuelPlanListTable.getSelectedRow())))
        checkedIndexes.remove(((Integer)fuelPlanListTable.getSelectedRow()));
    }
  }//GEN-LAST:event_removeTowerBtnActionPerformed

  private void addTowerBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addTowerBtnActionPerformed
  {//GEN-HEADEREND:event_addTowerBtnActionPerformed
    GenericStarbaseDlg newGenDlg = new GenericStarbaseDlg(JFrame.getFrames()[0], true);
    newGenDlg.setFuelPlanner(fuelPlanner);
    newGenDlg.setVisible(true);
    
  }//GEN-LAST:event_addTowerBtnActionPerformed

  private void importBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_importBtnActionPerformed
  {//GEN-HEADEREND:event_importBtnActionPerformed
    fuelPlanner.importStarbaseList(ApiData.getCurrStarbaseInfo().getStarbaseList());
  }//GEN-LAST:event_importBtnActionPerformed

  private void clearBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clearBtnActionPerformed
  {//GEN-HEADEREND:event_clearBtnActionPerformed
    fuelPlanner.clearAllFuelPlans();
  }//GEN-LAST:event_clearBtnActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addTowerBtn;
  private javax.swing.JButton calculatePlanBtn;
  private javax.swing.JButton clearBtn;
  private javax.swing.JLabel daysLabel;
  private javax.swing.JFormattedTextField daysTextField;
  private javax.swing.JTable fuelPlanDataTable;
  private javax.swing.JTable fuelPlanListTable;
  private javax.swing.JScrollPane fuelPlannerDataScrollPane;
  private javax.swing.JScrollPane fuelPlannerListScrollPane;
  private javax.swing.JTabbedPane fuelsTabbedPane;
  private javax.swing.JLabel hoursLabel;
  private javax.swing.JFormattedTextField hoursTextField;
  private javax.swing.JButton importBtn;
  private javax.swing.JLabel monthsLabel;
  private javax.swing.JFormattedTextField monthsTextField;
  private javax.swing.JButton removeTowerBtn;
  private javax.swing.JPanel starbaseCtrlPanel;
  private javax.swing.JPanel starbaseFuelPanel;
  private javax.swing.JPanel starbaseListPanel;
  private modules.gui.StoredFuelsPanel storedFuelsPanel1;
  private javax.swing.JPanel timeSettingPanel;
  private javax.swing.JLabel weeksLabel;
  private javax.swing.JFormattedTextField weeksTextField;
  // End of variables declaration//GEN-END:variables

  public void valueChanged(ListSelectionEvent e)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
