/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
        
import DAO.DAO_Interface;
import DAO.DAO_KRSS;
import Model.varKRSS;
import view.frmKRS;
import java.util.*;
import java.text.*;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class Controller_KRSS {
     frmKRS form;
    DAO_Interface<varKRSS> model;
    DAO_KRSS model_internal;
    List<varKRSS> list;
    String[] header;
    public Controller_KRSS(frmKRS form){
        this.form = form;
        model = new DAO_KRSS();
        model_internal = new DAO_KRSS();
        list = model_internal.getAll();
        header = new String[]{"NO.","Kode","Nama Matakuliah","SKS","Kode Prasyarat"};
        form.getTblKRS().setShowGrid(true);
        form.getTblKRS().setShowVerticalLines(true);
        form.getTblKRS().setGridColor(Color.blue);
    }
    public void isiComboTahunAjaran(){
        form.getCboTA().removeAllItems();
        form.getCboTA().addItem("--pilih--");
        for(varKRSS KRSS : model_internal.isicomboTA()){
            form.getCboTA().addItem(KRSS.getvTA());
        }
    }
    public void isiComboSemester(){
        form.getCboSemester().removeAllItems();
        form.getCboSemester().addItem("--pilih--");
        if(form.getCboTA().getSelectedItem()== null || form.getCboTA().getSelectedItem().toString().isEmpty()){
            
        }else{
            for(varKRSS KRSS : model_internal.isicomboSemester(form.getCboTA().getSelectedItem().toString())){
                    form.getCboSemester().addItem(KRSS.getvSemester());
                }
        }
            
        
    }
    public void tampilNamaMahasiswa(){
        for(varKRSS KRSS:model_internal.getNamaMahasiswa(form.getTxtNIM().getText())){
            form.getTxtNama().setText(KRSS.getvNama().toString());
        }
    }
    public void tampilMatakuliah(){
        for(varKRSS KRSS:model_internal.getDataMatakuliah(form.getTxtKodeMatakuliah().getText())){
            form.getTxtNamaMatakuliah().setText(KRSS.getvNamaMTK().toString());
            form.getTxtSKS().setText(String.valueOf(KRSS.getvSKS()));
            form.getTxtKodePrasyarat().setText(KRSS.getvKodePrasyarat().toString());
        }
    }
    

    public void isiTabel(){
        if (form.getCboTA().getSelectedItem()==null||form.getCboSemester().getSelectedItem()==null||form.getTxtNIM()==null){
        
        }else{
            String TA = form.getCboTA().getSelectedItem().toString();
            String Semester = form.getCboSemester().getSelectedItem().toString();
            String NIM = form.getTxtNIM().getText().toString();
            list = model_internal.getAllDetilKRS(TA, Semester, NIM);
            DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header){
                public boolean isCellEditable (int rowIndex, int columnIndex){
                    return false;
                }
            };
            Object[] data = new Object[header.length];
            int i = 1;
            for(varKRSS object : list){
                data[0] = String.valueOf(i);
                data[1] = object.getvKodeMTK();
                data[2] = object.getvNamaMTK();
                data[3] = object.getvSKS();
                data[4] = object.getvKodePrasyarat();
                tblModel.addRow(data);
                i++;
            }
            form.getTblKRS().setModel(tblModel);
            int total=0,sks=0;
            for(i=0;i<tblModel.getRowCount();i++){
                sks = Integer.parseInt(tblModel.getValueAt(i, 3).toString());
                total= total+sks;
            }
            form.getTxtTotalSKS().setText(String.valueOf(total));
        }
    }
    public void reset(){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        form.getTxtTglKRS().setText(date.format(new Date()).toString());
        form.getTxtKodeMatakuliah().setText("");
        form.getTxtKodePrasyarat().setText("");
        form.getTxtNIM().setText("");
        form.getTxtNama().setText("");
        form.getTxtSKS().setText("");
        form.getTxtNamaMatakuliah().setText("");
        form.getTxtTotalSKS().setText("");
        form.getCboTA().setSelectedItem("Pilih");
        form.getCboSemester().setSelectedItem("Pilih");
        form.getTxtNIM().requestFocus();
        isiTabel();
        isiComboTahunAjaran();
        isiComboSemester();
    }
    public void resetDetil(){
        form.getTxtKodeMatakuliah().setText("");
        form.getTxtKodePrasyarat().setText("");
        form.getTxtSKS().setText("");
        form.getTxtNamaMatakuliah().setText("");
        form.getTxtTotalSKS().setText("");
        form.getTxtKodeMatakuliah().requestFocus();
        isiTabel();
    }
    public void isiField(int row){
        form.getTxtKodeMatakuliah().setText(list.get(row).getvKodeMTK());
        form.getTxtNamaMatakuliah().setText(list.get(row).getvNamaMTK());
        form.getTxtSKS().setText(String.valueOf(list.get(row).getvSKS()));
        form.getTxtKodePrasyarat().setText(list.get(row).getvKodePrasyarat());
        form.getTxtNamaMatakuliah().requestFocus();
    }
    public void insert(){
        varKRSS object = new varKRSS();
        object.setvTA(form.getCboTA().getSelectedItem().toString());
        object.setvSemester(form.getCboSemester().getSelectedItem().toString());
        object.setvNIM(form.getTxtNIM().getText());
        object.setvTgl_KRS(form.getTxtTglKRS().getText());
        object.setvKodeMTK(form.getTxtKodeMatakuliah().getText());
        
        model.insert(object);
    }
    public void delete(){
        if(!form.getTxtNIM().getText().trim().isEmpty()){
            String kdMtk = form.getTxtKodeMatakuliah().getText();
            String TA = form.getCboTA().getSelectedItem().toString();
            String Semester = form.getCboSemester().getSelectedItem().toString();
            String NIM = form.getTxtNIM().getText();
            model_internal.delete(TA,Semester,NIM,kdMtk);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
//    public void isiTabelCari(){
//        list = model.getCari(form.getTxtKodeMatakuliah().getText().trim());
//        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header);
//        Object[] data = new Object[header.length];
//        for(varKRSS object : list){
//            data[0] = object.getvKodeMatakuliah();
//            data[1] = object.getvNamaMatakuliah();
//            data[2] = object.getvSKS();
//            data[3] = object.getvPrasyarat();
//            tblModel.addRow(data);
//        }
//        form.getTblMatakuliah().setModel(tblModel);
//    }
        
    
}
