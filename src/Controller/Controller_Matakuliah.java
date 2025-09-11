/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_Interface;
import DAO.DAO_Matakuliah;
import Model.varMatakuliah;
import view.frmMatakuliah;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class Controller_Matakuliah {
    
    frmMatakuliah form;
    DAO_Interface<varMatakuliah> model;
    List<varMatakuliah> list;
    String[] header;
    public Controller_Matakuliah(frmMatakuliah form){
        this.form = form;
        model = new DAO_Matakuliah();
        list = model.getAll();
        header = new String[]{"Kode matakulaih","Nama Matakuliah","SKS","Prasyarat"};
        form.getTblMatakuliah().setShowGrid(true);
        form.getTblMatakuliah().setShowVerticalLines(true);
        form.getTblMatakuliah().setGridColor(Color.blue);
    }
    public void reset(){
        form.getTxtKodeMatakuliah().setText("");
        form.getTxtNama().setText("");
        form.getTxtSKS().setText("");
        form.getTxtPrasyarat().setText("");
        form.getTxtKodeMatakuliah().requestFocus();
        isiTabel();
    }
    public void isiTabel(){
        list = model.getAll();
        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header){
            public boolean isCellEditable (int rowIndex, int columnIndex){
                return false;
            }
        };
        Object[] data = new Object[header.length];
        for(varMatakuliah objMtk : list){
            data[0] = objMtk.getvKodeMatakuliah();
            data[1] = objMtk.getvNamaMatakuliah();
            data[2] = objMtk.getvSKS();
            data[3] = objMtk.getvPrasyarat();
            tblModel.addRow(data);
        }
        form.getTblMatakuliah().setModel(tblModel);
    }
    public void isiField(int row){
        form.getTxtKodeMatakuliah().setText(list.get(row).getvKodeMatakuliah());
        form.getTxtNama().setText(list.get(row).getvNamaMatakuliah());
        form.getTxtSKS().setText(String.valueOf(list.get(row).getvSKS()));
        form.getTxtPrasyarat().setText(list.get(row).getvPrasyarat());
        form.getTxtNama().requestFocus();
    }
    public void insert(){
        varMatakuliah objMtk = new varMatakuliah();
        
        objMtk.setvKodeMatakuliah(form.getTxtKodeMatakuliah().getText());
        objMtk.setvNamaMatakuliah(form.getTxtNama().getText());
        objMtk.setvSKS(Integer.parseInt(form.getTxtSKS().getText()));
        objMtk.setvPrasyarat(form.getTxtPrasyarat().getText());
        
        model.insert(objMtk);
    }
    public void update(){
        varMatakuliah objMtk = new varMatakuliah();
        
        objMtk.setvKodeMatakuliah(form.getTxtKodeMatakuliah().getText());
        objMtk.setvNamaMatakuliah(form.getTxtNama().getText());
        objMtk.setvSKS(Integer.parseInt(form.getTxtSKS().getText()));
        objMtk.setvPrasyarat(form.getTxtPrasyarat().getText());
        
        model.update(objMtk);
    }
    public void delete(){
        if(!form.getTxtKodeMatakuliah().getText().trim().isEmpty()){
            String kdMtk = form.getTxtKodeMatakuliah().getText();
            
            model.delete(kdMtk);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
    public void isiTabelCari(){
        list = model.getCari(form.getTxtKodeMatakuliah().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header);
        Object[] data = new Object[header.length];
        for(varMatakuliah objMtk : list){
            data[0] = objMtk.getvKodeMatakuliah();
            data[1] = objMtk.getvNamaMatakuliah();
            data[2] = objMtk.getvSKS();
            data[3] = objMtk.getvPrasyarat();
            tblModel.addRow(data);
        }
        form.getTblMatakuliah().setModel(tblModel);
    }
    
}
