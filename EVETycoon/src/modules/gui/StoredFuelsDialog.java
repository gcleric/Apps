/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StoredFuelsDialog.java
 *
 * Created on Sep 21, 2011, 1:09:20 PM
 */
package modules.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modules.fuelplanner.PlanFuel;
import services.DBService;
import tables.BasicTableCell;
import tables.BasicTableColumn;
import tables.BasicTableModel;
import tables.BasicTableRow;

/**
 *
 * @author hrivanov
 */
public class StoredFuelsDialog extends javax.swing.JDialog
{
  private Map<Integer, PlanFuel> plannedFuelsMap;
  
  private BasicTableModel dataModel;
  
  /** Creates new form StoredFuelsDialog */
  public StoredFuelsDialog(java.awt.Frame parent, boolean modal)
  {
    super(parent, modal);
    initComponents();
    
    this.plannedFuelsMap = new HashMap<Integer, PlanFuel>();
    
    this.dataModel = new BasicTableModel();
    
    updateStoredFuelModel();
    storedFuelsTable.setModel(dataModel);
  }
  
  private void updateStoredFuelModel()
  {
    if (dataModel.getColumnCount() == 0)
    {
      dataModel.addColumn(new BasicTableColumn("Type", String.class.getClass()));
      dataModel.addColumn(new BasicTableColumn("Stored quantity", Integer.class.getClass()));
    }

    dataModel.clearRows();

    if(!plannedFuelsMap.isEmpty())
    {
      int columnCount = dataModel.getColumnCount();
      
      BasicTableRow newRow = null;
      List<PlanFuel> plannedFuelsList = new ArrayList<PlanFuel>(plannedFuelsMap.values());
      Collections.sort(plannedFuelsList);
      for (PlanFuel plannedFuel : plannedFuelsList)
      {
        newRow = new BasicTableRow(columnCount);
        newRow.setRowID(plannedFuel.getItemID());
        
        newRow.addCell(new BasicTableCell(newRow, plannedFuel.getItemIcon(), false, plannedFuel.getItemName(), ""));
        newRow.addCell(new BasicTableCell(newRow, 0d, true));

        dataModel.addRow(newRow);
      }

      dataModel.fireTableStructureChanged();
    }
  }
  
  public void setFuelsMap()
  {
    try
    {
      this.plannedFuelsMap = DBService.getInstance().getStarbaseFuelTypes();
      updateStoredFuelModel();
      storedFuelsTable.setModel(dataModel);
    } 
    catch (SQLException sqlEx)
    {
      JOptionPane.showMessageDialog(this,
                sqlEx.getMessage(),
                "Exception",
                JOptionPane.ERROR_MESSAGE);
    }
  }
  

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    storedFuelsTable = new javax.swing.JTable();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setName("Form"); // NOI18N

    jScrollPane1.setName("jScrollPane1"); // NOI18N

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(evetycoon.EveTycoonApp.class).getContext().getResourceMap(StoredFuelsDialog.class);
    storedFuelsTable.setBackground(resourceMap.getColor("defaultTable.background")); // NOI18N
    storedFuelsTable.setForeground(resourceMap.getColor("defaultTable.foreground")); // NOI18N
    storedFuelsTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {

      }
    ));
    storedFuelsTable.setFillsViewportHeight(true);
    storedFuelsTable.setGridColor(resourceMap.getColor("defaultTable.gridColor")); // NOI18N
    storedFuelsTable.setName("storedFuelsTable"); // NOI18N
    storedFuelsTable.setSelectionBackground(resourceMap.getColor("defaultTable.selectionBackground")); // NOI18N
    storedFuelsTable.setSelectionForeground(resourceMap.getColor("defaultTable.selectionForeground")); // NOI18N
    jScrollPane1.setViewportView(storedFuelsTable);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(80, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  /**
   * @param args the command line arguments
   */
  public static void main(String args[])
  {
    java.awt.EventQueue.invokeLater(new Runnable()
    {

      public void run()
      {
        StoredFuelsDialog dialog = new StoredFuelsDialog(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter()
        {

          public void windowClosing(java.awt.event.WindowEvent e)
          {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTable storedFuelsTable;
  // End of variables declaration//GEN-END:variables
}