/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;
import koneksi.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import Model.varPeriode;
import javax.swing.JOptionPane;
import java.util.ArrayList;


/**
 *
 * @author edelweiss
 */
public class DAO_Periode implements DAO_Interface<varPeriode>{

    @Override
    public void update(varPeriode object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String NIM) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<varPeriode> getCari(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    Connection conn = Database.KoneksiDB();
    String INSERT = "INSERT INTO Periode(TA,Semester) VALUES (?,?)";
    String DELETE = "DELETE FROM Periode WHERE TA=? and Semester=?";
    String SELECT = "SELECT * FROM Periode";
    String CARI = "SELECT * FROM Periode WHERE TA LIKE ? and Semester LIKE ?";
    
    @Override
    public void insert(varPeriode object){    
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(CARI);
            st.setString(1,object.getvTA());
            st.setString(2,object.getvSemester());
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Data sudah disimpan");
            }
            else{
                st= null;
                st=conn.prepareStatement(INSERT);
                st.setString(1,object.getvTA());
                st.setString(2, object.getvSemester());
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    public void delete(String TA ,String Semester){
        PreparedStatement st = null;
        try{
            st= null;
            st=conn.prepareStatement(DELETE);
            st.setString(1,TA);
            st.setString(2,Semester);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    
    @Override
    public List<varPeriode> getAll(){
        List<varPeriode> List=null;
        PreparedStatement st = null;
        try{
            st= null;
            List = new ArrayList<varPeriode>();
            st=conn.prepareStatement(SELECT);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varPeriode objPer = new varPeriode();
                objPer.setvTA(rs.getString("TA"));
                objPer.setvSemester(rs.getString("Semester"));
                List.add(objPer);
            }
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
    public List<varPeriode> getCari(String keyTA,String keySemester){
        List<varPeriode> List=null;
        PreparedStatement st = null;
        try{
            st= null;
            List = new ArrayList<varPeriode>();
            st=conn.prepareStatement(CARI);
            st.setString(1, "%"+keyTA+"%");
            st.setString(2, "%"+keySemester+"%" );
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varPeriode objPer = new varPeriode();
                objPer.setvTA(rs.getString("TA"));
                objPer.setvSemester(rs.getString("Semester"));
                List.add(objPer);
            }
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
}
