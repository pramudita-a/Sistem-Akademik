/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author edelweiss
 */
public class varMahasiswa {
    private String vNama;
    private String vNIM;
    private String vAlamat;

    public void setvNama(String vNama) {
        this.vNama = vNama;
    }

    public void setvNIM(String vNIM) {
        this.vNIM = vNIM;
    }

    public void setvAlamat(String vAlamat) {
        this.vAlamat = vAlamat;
    }

    public String getvNama() {
        return vNama;
    }

    public String getvNIM() {
        return vNIM;
    }

    public String getvAlamat() {
        return vAlamat;
    }
}
