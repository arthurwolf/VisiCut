/**
 * This file is part of VisiCut.
 * Copyright (C) 2012 Thomas Oster <thomas.oster@rwth-aachen.de>
 * RWTH Aachen University - 52062 Aachen, Germany
 * 
 *     VisiCut is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *    VisiCut is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with VisiCut.  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.t_oster.visicut.gui;

import com.t_oster.liblasercut.LaserCutter;
import com.t_oster.liblasercut.LaserProperty;
import com.t_oster.visicut.gui.beans.EditableTablePanel;
import com.t_oster.visicut.gui.beans.EditableTableProvider;
import com.t_oster.visicut.misc.DialogHelper;
import com.t_oster.visicut.model.LaserProfile;
import com.t_oster.visicut.model.Raster3dProfile;
import com.t_oster.visicut.model.RasterProfile;
import com.t_oster.visicut.model.VectorProfile;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Thomas Oster <thomas.oster@rwth-aachen.de>
 */
public class AdaptSettingsDialog extends javax.swing.JDialog
{

  private DialogHelper dialog = new DialogHelper(this, "VisiCut");
  private List<EditableTablePanel> panels = new LinkedList<EditableTablePanel>();
  private Map<LaserProfile, List<LaserProperty>> map;
  private boolean okPressed = false;

  /** Creates new form AdaptSettingsDialog */
  public AdaptSettingsDialog(java.awt.Frame parent, boolean modal, String heading)
  {
    super(parent, modal);
    initComponents();
    this.heading.setText(heading);
  }

  /**
   * Loads the given MaterialProfile into the Window.
   * All changes to the window occur instantly to this
   * MaterialProfile
   * @param mp 
   */
  public void setLaserProperties(Map<LaserProfile, List<LaserProperty>> map, final LaserCutter lc)
  {
    this.map = map;
    this.panels.clear();
    this.jTabbedPane1.removeAll();
    if (map != null)
    {
      for (final LaserProfile lp : map.keySet())
      {
        if (map.get(lp).isEmpty())
        {
          throw new IllegalArgumentException(java.util.ResourceBundle.getBundle("com/t_oster/visicut/gui/resources/AdaptSettingsDialog").getString("EVERY PROFILE MUST AT LEAST CONTAIN ONE LASERPROPERTY"));
        }
        EditableTablePanel panel = new EditableTablePanel();
        LaserPropertiesTableModel model = new LaserPropertiesTableModel();
        model.setLaserProperties(map.get(lp));
        panel.setObjects((List) map.get(lp));
        panel.setTableModel(model);
        panel.setEditButtonVisible(false);
        panel.setProvider(new EditableTableProvider()
        {

          public Object getNewInstance()
          {
            if (lp instanceof RasterProfile)
            {
              return lc.getLaserPropertyForRasterPart();
            }
            else if (lp instanceof Raster3dProfile)
            {
              return lc.getLaserPropertyForRaster3dPart();
            }
            else if (lp instanceof VectorProfile)
            {
              return lc.getLaserPropertyForVectorPart();
            }
            throw new IllegalArgumentException(java.util.ResourceBundle.getBundle("com/t_oster/visicut/gui/resources/AdaptSettingsDialog").getString("LASERPROFILE IS NOT FROM RASTER,VECTOR,RASTER3D"));
          }

          public Object editObject(Object o)
          {
            return o;
          }
        });
        this.jTabbedPane1.add(lp.getName(), panel);
      }
    }
  }

  /**
   * Returns the MaterialProfile provided by setMaterialProfile.
   * IF Cancel was pressed, it is set to null
   * @return 
   */
  public Map<LaserProfile, List<LaserProperty>> getLaserProperties()
  {
    return this.okPressed ? this.map : null;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        editableTablePanel1 = new com.t_oster.visicut.gui.beans.EditableTablePanel();
        btOK = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        heading = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/t_oster/visicut/gui/resources/AdaptSettingsDialog"); // NOI18N
        setTitle(bundle.getString("TITLE")); // NOI18N
        setName("Form"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        editableTablePanel1.setName("editableTablePanel1"); // NOI18N
        jTabbedPane1.addTab("tab2", editableTablePanel1);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.t_oster.visicut.gui.VisicutApp.class).getContext().getResourceMap(AdaptSettingsDialog.class);
        btOK.setText(resourceMap.getString("btOK.text")); // NOI18N
        btOK.setName("btOK"); // NOI18N
        btOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOKActionPerformed(evt);
            }
        });

        btCancel.setText(resourceMap.getString("btCancel.text")); // NOI18N
        btCancel.setName("btCancel"); // NOI18N
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        heading.setText(resourceMap.getString("heading.text")); // NOI18N
        heading.setName("heading"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btCancel)
                        .addGap(18, 18, 18)
                        .addComponent(btOK))
                    .addComponent(heading)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(heading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btOK)
                    .addComponent(btCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void btOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btOKActionPerformed
  {//GEN-HEADEREND:event_btOKActionPerformed
    //check if every propertylist contains at least one entry
    for(Entry<LaserProfile, List<LaserProperty>> e:this.map.entrySet())
    {
      if (e.getValue() == null || e.getValue().isEmpty())
      {
        dialog.showInfoMessage(java.util.ResourceBundle.getBundle("com/t_oster/visicut/gui/resources/AdaptSettingsDialog").getString("THE PROFILE '")+e.getKey().getName()+java.util.ResourceBundle.getBundle("com/t_oster/visicut/gui/resources/AdaptSettingsDialog").getString("' HAS TO CONTAIN AT LEAST ONE LASERPROPERTY"));
        return;
      }
    }
    this.okPressed = true;
    this.setVisible(false);
  }//GEN-LAST:event_btOKActionPerformed

  private void btCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btCancelActionPerformed
  {//GEN-HEADEREND:event_btCancelActionPerformed
    this.map = null;
    this.setVisible(false);
  }//GEN-LAST:event_btCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btOK;
    private com.t_oster.visicut.gui.beans.EditableTablePanel editableTablePanel1;
    private javax.swing.JLabel heading;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

}
