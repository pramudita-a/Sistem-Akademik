/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.varKRSS;
import java.util.*;
import koneksi.Database;
import java.sql.*;

/**
 *
 * @author DELL
 */
public class DAO_KRSS implements DAO_Interface<varKRSS>{
    Connection conn = Database.KoneksiDB();
    public List<varKRSS> isicomboTA(){
        List<varKRSS> List=null;
        PreparedStatement st;
        try{
            st= null;
            List = new ArrayList<varKRSS>();
            st=conn.prepareStatement("SELECT DISTINCT TA FROM Periode ORDER BY TA");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varKRSS object = new varKRSS();
                object.setvTA(rs.getString("TA"));
                List.add(object);
            }
            rs.close();
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
    public List<varKRSS> isicomboSemester(String TA){
        List<varKRSS> List=null;
        PreparedStatement st;
        try{
            st= null;
            List = new ArrayList<varKRSS>();
            st=conn.prepareStatement("SELECT DISTINCT Semester FROM Periode WHERE TA = ? ORDER BY Semester");
            st.setString(1, TA);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varKRSS object = new varKRSS();
                object.setvSemester(rs.getString("Semester"));
                List.add(object);
            }
            rs.close();
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
    public List<varKRSS> getNamaMahasiswa(String NIM){
        List<varKRSS> List=null;
        PreparedStatement st;
        try{
            st= null;
            List = new ArrayList<varKRSS>();
            st=conn.prepareStatement("SELECT * FROM Mahasiswa WHERE NIM=?");
            st.setString(1, NIM);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                varKRSS object = new varKRSS();
                object.setvNama(rs.getString("Nama"));
                List.add(object);
            }else{
                System.out.println("Data tidak ditemukan");
            }
            rs.close();
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
    public List<varKRSS> getDataMatakuliah(String Kode_Matakuliah){
        List<varKRSS> List=null;
        PreparedStatement st;
        try{
            st= null;
            List = new ArrayList<varKRSS>();
            st=conn.prepareStatement("SELECT * FROM Matakuliah WHERE KodeMTK=?");
            st.setString(1, Kode_Matakuliah);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                varKRSS object = new varKRSS();
                object.setvNamaMTK(rs.getString("NamaMTK"));
                object.setvSKS(rs.getInt("SKS"));
                object.setvKodePrasyarat(rs.getString("KodePrasyarat"));
                List.add(object);
            }else{
                System.out.println("Data tidak ditemukan");
            }
            rs.close();
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
    public List<varKRSS> getAllDetilKRS(String TA,String Semester,String NIM){
        List<varKRSS> List=null;
        PreparedStatement st;
        try{
            st= null;
            List = new ArrayList<varKRSS>();
            st=conn.prepareStatement("SELECT * FROM Detil_KRS a, Matakuliah b WHERE a.TA=? AND a.Semester = ? AND a.NIM=? AND a.KodeMTK=b.KodeMTK");
            st.setString(1, TA);
            st.setString(2, Semester);
            st.setString(3, NIM);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varKRSS object = new varKRSS();
                object.setvKodeMTK(rs.getString("KodeMTK"));
                object.setvNamaMTK(rs.getString("NamaMTK"));
                object.setvSKS(rs.getInt("SKS"));
                object.setvKodePrasyarat(rs.getString("KodePrasyarat"));
                List.add(object);
            }
            rs.close();
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }
    
    
    @Override
    public void insert(varKRSS object) {
        PreparedStatement st;
        ResultSet rs;
        try{
            st=conn.prepareStatement("SELECT * FROM KRS WHERE TA=? AND Semester=? AND NIM=?");
            st.setString(1, object.getvTA());
            st.setString(2, object.getvSemester());
            st.setString(3, object.getvNIM());
            rs = st.executeQuery();
            if(!rs.next()){
                st = conn.prepareStatement("INSERT INTO KRS(TA,Semester,NIM,Tgl_KRS)VALUES(?,?,?,?)");
                st.setString(1, object.getvTA());
                st.setString(2, object.getvSemester());
                st.setString(3, object.getvNIM());
                st.setString(4, object.getvTgl_KRS());
                st.executeUpdate();
            }else{
                System.out.println("Data KRS sudah ada");
            }
            st=conn.prepareStatement("SELECT * FROM Detil_KRS WHERE TA=? AND Semester = ? AND NIM=? AND KodeMTK=?");
            st.setString(1, object.getvTA());
            st.setString(2, object.getvSemester());
            st.setString(3, object.getvNIM());
            st.setString(4, object.getvKodeMTK());
            rs = st.executeQuery();
            if(!rs.next()){
                st = conn.prepareStatement("INSERT INTO Detil_KRS(TA,Semester,NIM,KodeMTK)VALUES(?,?,?,?)");
                st.setString(1, object.getvTA());
                st.setString(2, object.getvSemester());
                st.setString(3, object.getvNIM());
                st.setString(4, object.getvKodeMTK());
                st.executeUpdate();
            }else{
                System.out.println("Data Detil_KRS sudah ada");
            }
            rs.close();
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

    @Override
    public void update(varKRSS object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void delete(String TA,  String Semester, String NIM, String KodeMTK){
        PreparedStatement st;
        ResultSet rs;
        try{
            st=conn.prepareStatement("SELECT * FROM Detil_KRS WHERE TA=? AND Semester = ? AND NIM=? AND KodeMTK=?");
            st.setString(1, TA);
            st.setString(2, Semester);
            st.setString(3, NIM);
            st.setString(4, KodeMTK);
            rs = st.executeQuery();
            if(rs.next()){
                st = conn.prepareStatement("DELETE FROM Detil_KRS WHERE TA=? AND Semester=? AND NIM=? AND KodeMTK=?");
                st.setString(1, TA);
                st.setString(2, Semester);
                st.setString(3, NIM);
                st.setString(4, KodeMTK);
                st.executeUpdate();
            }else{
                st = conn.prepareStatement("DELETE FROM KRS WHERE TA=? AND Semester=? AND NIM=?");
                st.setString(1, TA);
                st.setString(2, Semester);
                st.setString(3, NIM);
                st.executeUpdate();
            }
            rs.close();
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    @Override
    public void delete(String NIM) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<varKRSS> getAll() {
        List<varKRSS> List=null;
        PreparedStatement st = null;
        try{
            st= null;
            List = new ArrayList<varKRSS>();
            st=conn.prepareStatement("SELECT * FROM Detil_KRS");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                varKRSS object = new varKRSS();
                object.setvTA(rs.getString("TA"));
                object.setvSemester(rs.getString("Semester"));
                object.setvNIM(rs.getString("NIM"));
                object.setvNamaMTK(rs.getString("KodeMTK"));
                List.add(object);
            }
            st.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return List;
    }

    @Override
    public List<varKRSS> getCari(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
