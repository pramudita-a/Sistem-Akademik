/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_Interface;
import DAO.DAO_Periode;
import Model.varPeriode;
import view.frmPeriode;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.DefaultTableModel




/**
 *
 * @author DELL
 */
public class Controller_Periode {
    frmPeriode form;
    DAO_Periode model;
    List<varPeriode> list;
    String[] header;
    public Controller_Periode(frmPeriode form){
        this.form = form;
        model = new DAO_Periode();
        list = model.getAll();
        header = new String[]{"Tahun Ajar","Semester"};
        form.getTblPeriode().setShowGrid(true);
        form.getTblPeriode().setShowVerticalLines(true);
        form.getTblPeriode().setGridColor(Color.blue);
    }
    public void reset(){
        form.getTxtTA().setText("");
        form.getTxtSemester().setText("");
        form.getTxtTA().requestFocus();
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
        for(varPeriode objPer : list){
            data[0] = objPer.getvTA();
            data[1] = objPer.getvSemester();
            tblModel.addRow(data);
        }
        form.getTblPeriode().setModel(tblModel);
    }
    public void isiField(int row){
        form.getTxtTA().setText(list.get(row).getvTA());
        form.getTxtSemester().setText(list.get(row).getvSemester());
        form.getTxtTA().requestFocus();
    }
    public void insert(){
        varPeriode objPer = new varPeriode();
        
        objPer.setvTA(form.getTxtTA().getText());
        objPer.setvSemester(form.getTxtSemester().getText());
        
        model.insert(objPer);
    }
    public void delete(){
        if(!form.getTxtSemester().getText().trim().isEmpty() || !form.getTxtTA().getText().trim().isEmpty()){
            String TA = form.getTxtTA().getText();
            String Semester = form.getTxtSemester().getText();
            model.delete(TA,Semester);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
    public void isiTabelCari(){
        list = model.getCari(form.getTxtTA().getText().trim(),form.getTxtSemester().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header);
        Object[] data = new Object[header.length];
        for(varPeriode objPer : list){
            data[0] = objPer.getvTA();
            data[1] = objPer.getvSemester();
            tblModel.addRow(data);
        }
        form.getTblPeriode().setModel(tblModel);
    }
    
}
