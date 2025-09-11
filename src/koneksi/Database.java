/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class Database {
        // Deklarasi variabel yang diperlukan untuk koneksi database
    static Connection conn;
    private static String user = "root";
    private static String database = "db_akademik_2211500919";
    private static String pw = ""; // Password MySQL jika ada

    public static Connection KoneksiDB(){
        // Pastikan koneksi hanya dibuat sekali
        if (conn == null) {
            try {
                
                String url = "jdbc:mysql://localhost:3306/" + database;
                
                
                conn = DriverManager.getConnection(url, user, pw);
                System.out.println("Koneksi berhasil!");
                JOptionPane.showMessageDialog(null, "Koneksi Berhasil");
            } catch (SQLException e) {
                
                System.out.println("Error: Koneksi gagal. " + e.getMessage());
                 JOptionPane.showMessageDialog(null, "Koneksi Berhasil" + e.getMessage());
            }
        }
        return conn; 
    }
    
}
