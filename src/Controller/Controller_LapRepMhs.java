/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import java.io.File;
import java.sql.Connection;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import koneksi.Database;


/**
 *
 * @author DELL
 */
public class Controller_LapRepMhs {
    
        public void cetak_preview(){
        try{
            Connection conn = Database.KoneksiDB();
            String path = "src/Report/LapRepMhs.jasper";;
            JasperReport report = (JasperReport) JRLoader.loadObject(path);
            JasperPrint print = JasperFillManager.fillReport(report, null,conn);
            JasperViewer.viewReport(print,false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data tidak dapat dicetak"+e.getMessage(),"Cetak data",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void cetak_excel(){
    try{
        Connection conn = Database.KoneksiDB();
        String path = "src/Report/LapRepMhs.jasper";
        File xlsx = new File("lapStok_2211501180.xlsx");
        
        JasperPrint print = JasperFillManager.fillReport(path, null, conn);
        
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new net.sf.jasperreports.export.SimpleExporterInput(print));
        exporter.setExporterOutput(new net.sf.jasperreports.export.SimpleOutputStreamExporterOutput(xlsx));
        
        net.sf.jasperreports.export.SimpleXlsxReportConfiguration configuration = new net.sf.jasperreports.export.SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(false);
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        
        JOptionPane.showMessageDialog(null, "cek file di root");
    } catch(Exception e){
        JOptionPane.showMessageDialog(null, "Data tidak dapat dicetak " + e.getMessage(), "Cetak data", JOptionPane.ERROR_MESSAGE);
    }
}

    
}
