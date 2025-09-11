/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;
import DAO.DAO_Interface;
import koneksi.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import Model.varMatakuliah;
import javax.swing.JOptionPane;
import java.util.ArrayList;


/**
 *
 * @author edelweiss
 */
public class DAO_Matakuliah implements DAO_Interface<varMatakuliah>{
    Connection conn = Database.KoneksiDB();
    String INSERT = "INSERT INTO Matakuliah(KodeMTK ,NamaMTK,SKS,KodePrasyarat) VALUES (?,?,?,?)";
    String UPDATE = "UPDATE Matakuliah SET NamaMTK=?,SKS=?,KodePrasyarat=? WHERE KodeMTK =?";
    String DELETE = "DELETE FROM Matakuliah WHERE KodeMTK =?";
    String SELECT = "SELECT * FROM Matakuliah";
    String CARI = "SELECT * FROM Matakuliah WHERE KodeMTK LIKE ?";
    @Override
    public void insert(varMatakuliah object){    
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(CARI);
            st.setString(1,object.getvKodeMatakuliah());
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Data sudah disimpan");
            }
            else{
                st= null;
                st=conn.prepareStatement(INSERT);
                st.setString(1, object.getvKodeMatakuliah());
                st.setString(2, object.getvNamaMatakuliah());
                st.setString(3, String.valueOf(object.getvSKS()));
                st.setString(4, object.getvPrasyarat());
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    
    @Override
    public void update(varMatakuliah object){
        PreparedStatement st = null;
        try{
            st= null;
            st=conn.prepareStatement(UPDATE);
            st.setString(1, object.getvNamaMatakuliah());
            st.setString(2, String.valueOf(object.getvSKS()));
            st.setString(3, object.getvPrasyarat());
            st.setString(4, object.getvKodeMatakuliah());
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    
    @Override
    public void delete(String KodeMTK ){
        PreparedStatement st = null;
        try{
            st= null;
            st=conn.prepareStatement(DELETE);
            st.setString(1, KodeMTK );
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    
    @Override
    public List<varMatakuliah> getAll(){
        List<varMatakuliah> List=null;
        PreparedStatement st = null;
        try{
            st= null;
            List = new ArrayList<varMatakuliah>();
            st=conn.prepareStatement(SELECT);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varMatakuliah objMtk = new varMatakuliah();
                objMtk.setvKodeMatakuliah(rs.getString("KodeMTK"));
                objMtk.setvNamaMatakuliah(rs.getString("NamaMTK"));
                objMtk.setvSKS(rs.getInt("SKS"));
                objMtk.setvPrasyarat(rs.getString("KodePrasyarat"));
                List.add(objMtk);
            }
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
    
    @Override
    public List<varMatakuliah> getCari(String key){
        List<varMatakuliah> List=null;
        PreparedStatement st = null;
        try{
            st= null;
            List = new ArrayList<varMatakuliah>();
            st=conn.prepareStatement(CARI);
            st.setString(1, "%"+key+"%");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varMatakuliah objMtk = new varMatakuliah();
                objMtk.setvKodeMatakuliah(rs.getString("KodeMTK"));
                objMtk.setvNamaMatakuliah(rs.getString("NamaMTK"));
                objMtk.setvSKS(rs.getInt("SKS"));
                objMtk.setvPrasyarat(rs.getString("KodePrasyarat"));
                List.add(objMtk);
            }
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
}
