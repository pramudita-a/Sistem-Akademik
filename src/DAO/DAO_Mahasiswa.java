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
import Model.varMahasiswa;
import javax.swing.JOptionPane;
import java.util.ArrayList;


/**
 *
 * @author edelweiss
 */
public class DAO_Mahasiswa implements DAO_Interface<varMahasiswa>{
    Connection conn = Database.KoneksiDB();
    String INSERT = "INSERT INTO Mahasiswa(NIM,Nama,Alamat) VALUES (?,?,?)";
    String UPDATE = "UPDATE Mahasiswa SET Nama=?,Alamat=? WHERE NIM=?";
    String DELETE = "DELETE FROM Mahasiswa WHERE NIM=?";
    String SELECT = "SELECT * FROM Mahasiswa";
    String CARI = "SELECT * FROM Mahasiswa WHERE NIM LIKE ?";
    @Override
    public void insert(varMahasiswa object){    
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(CARI);
            st.setString(1,object.getvNIM());
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Data sudah disimpan");
            }
            else{
                st= null;
                st=conn.prepareStatement(INSERT);
                st.setString(1, object.getvNIM());
                st.setString(2, object.getvNama());
                st.setString(3, object.getvAlamat());
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
    public void update(varMahasiswa object){
        PreparedStatement st = null;
        try{
            st= null;
            st=conn.prepareStatement(UPDATE);
            st.setString(1, object.getvNama());
            st.setString(2, object.getvAlamat());
            st.setString(3, object.getvNIM());
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    
    @Override
    public void delete(String NIM){
        PreparedStatement st = null;
        try{
            st= null;
            st=conn.prepareStatement(DELETE);
            st.setString(1, NIM);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    
    @Override
    public List<varMahasiswa> getAll(){
        List<varMahasiswa> List=null;
        PreparedStatement st = null;
        try{
            st= null;
            List = new ArrayList<varMahasiswa>();
            st=conn.prepareStatement(SELECT);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varMahasiswa objMhs = new varMahasiswa();
                objMhs.setvNIM(rs.getNString("NIM"));
                objMhs.setvNama(rs.getNString("Nama"));
                objMhs.setvAlamat(rs.getNString("Alamat"));
                List.add(objMhs);
            }
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
    
    @Override
    public List<varMahasiswa> getCari(String key){
        List<varMahasiswa> List=null;
        PreparedStatement st = null;
        try{
            st= null;
            List = new ArrayList<varMahasiswa>();
            st=conn.prepareStatement(CARI);
            st.setString(1, "%"+key+"%");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varMahasiswa objMhs = new varMahasiswa();
                objMhs.setvNIM(rs.getNString("NIM"));
                objMhs.setvNama(rs.getNString("Nama"));
                objMhs.setvAlamat(rs.getNString("Alamat"));
                List.add(objMhs);
            }
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
}
