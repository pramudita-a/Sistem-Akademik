/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import DAO.DAO_Interface;
import DAO.DAO_Mahasiswa;
import Model.varMahasiswa;
import view.frmMahasiswa;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.DefaultTableModel;
/**
 *
 * @author DELL
 */
public class Controller_Mahasiswa{
    frmMahasiswa form;
    DAO_Interface<varMahasiswa> model;
    List<varMahasiswa> list;
    String[] header;
    public Controller_Mahasiswa(frmMahasiswa form){
        this.form = form;
        model = new DAO_Mahasiswa();
        list = model.getAll();
        header = new String[]{"NIM","Nama","Alamat"};
        form.getTblMahasiswa().setShowGrid(true);
        form.getTblMahasiswa().setShowVerticalLines(true);
        form.getTblMahasiswa().setGridColor(Color.blue);
    }
    public void reset(){
        form.getTxtNIM().setText("");
        form.getTxtNama().setText("");
        form.getTxtAlamat().setText("");
        form.getTxtNIM().requestFocus();
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
        for(varMahasiswa objMhs : list){
            data[0] = objMhs.getvNIM();
            data[1] = objMhs.getvNama();
            data[2] = objMhs.getvAlamat();
            tblModel.addRow(data);
        }
        form.getTblMahasiswa().setModel(tblModel);
    }
    public void isiField(int row){
        form.getTxtNIM().setText(list.get(row).getvNIM());
        form.getTxtNama().setText(list.get(row).getvNama());
        form.getTxtAlamat().setText(list.get(row).getvAlamat());
        form.getTxtNama().requestFocus();
    }
    public void insert(){
        varMahasiswa objMhs = new varMahasiswa();
        
        objMhs.setvNIM(form.getTxtNIM().getText());
        objMhs.setvNama(form.getTxtNama().getText());
        objMhs.setvAlamat(form.getTxtAlamat().getText());
        
        model.insert(objMhs);
    }
    public void update(){
        varMahasiswa objMhs = new varMahasiswa();
        
        objMhs.setvNIM(form.getTxtNIM().getText());
        objMhs.setvNama(form.getTxtNama().getText());
        objMhs.setvAlamat(form.getTxtAlamat().getText());
        
        model.update(objMhs);
    }
    public void delete(){
        if(!form.getTxtNIM().getText().trim().isEmpty()){
            String NIM = form.getTxtNIM().getText();
            
            model.delete(NIM);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
    public void isiTabelCari(){
        list = model.getCari(form.getTxtNIM().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header);
        Object[] data = new Object[header.length];
        for(varMahasiswa objMhs : list){
            data[0] = objMhs.getvNIM();
            data[1] = objMhs.getvNama();
            data[2] = objMhs.getvAlamat();
            tblModel.addRow(data);
        }
        form.getTblMahasiswa().setModel(tblModel);
    }
}
