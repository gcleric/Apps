/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StoredFuelsPanel.java
 *
 * Created on Sep 27, 2011, 5:00:40 PM
 */
package modules.gui;

import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import modules.fuelplanner.FuelPlanner;
import services.ApiData;
import tables.BasicTableModel;

/**
 *
 * @author hrivanov
 */
public class StoredFuelsPanel extends javax.swing.JPanel implements TableModelListener
{
  private FuelPlanner fuelPlanner;
  private BasicTableModel storedFuelsModel;
  private IconLabelRenderer iconLabelRenderer;
  
  /** Creates new form StoredFuelsPanel */
  public StoredFuelsPanel()
  {
    initComponents();
    
    iconLabelRenderer = new IconLabelRenderer();
    
    fuelPlanner = ApiData.getCurrFuelPlanner();
    
    storedFuelsModel = fuelPlanner.getStoredFuelModel();
    storedFuelsTable.setModel(storedFuelsModel);
    storedFuelsTable.setDefaultRenderer(ImageIcon.class, iconLabelRenderer);
    storedFuelsTable.getModel().addTableModelListener(this);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    storedFuelsScrollPane = new javax.swing.JScrollPane();
    storedFuelsTable = new javax.swing.JTable();
    updateBtn = new javax.swing.JButton();

    setName("Form"); // NOI18N

    storedFuelsScrollPane.setName("storedFuelsScrollPane"); // NOI18N

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(evetycoon.EveTycoonApp.class).getContext().getResourceMap(StoredFuelsPanel.class);
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
    storedFuelsTable.setRowHeight(32);
    storedFuelsTable.setSelectionBackground(resourceMap.getColor("defaultTable.selectionBackground")); // NOI18N
    storedFuelsTable.setSelectionForeground(resourceMap.getColor("defaultTable.selectionForeground")); // NOI18N
    storedFuelsScrollPane.setViewportView(storedFuelsTable);

    updateBtn.setText(resourceMap.getString("updateBtn.text")); // NOI18N
    updateBtn.setName("updateBtn"); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(storedFuelsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(updateBtn)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(storedFuelsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
          .addComponent(updateBtn))
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane storedFuelsScrollPane;
  private javax.swing.JTable storedFuelsTable;
  private javax.swing.JButton updateBtn;
  // End of variables declaration//GEN-END:variables

  public void tableChanged(TableModelEvent e)
  {
    int row = e.getFirstRow();
    int column = e.getColumn();
    BasicTableModel model = (BasicTableModel)e.getSource();
    Object data = model.getValueAt(row, column);
   
    if(column == 2)
      fuelPlanner.setStoredFuel(model.getRowID(row).intValue(), ((Integer)data));
  }
}
