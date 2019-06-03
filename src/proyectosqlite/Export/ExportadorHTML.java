/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosqlite.Export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import proyectosqlite.modelo.Tarjeta;

/**
 *
 * @author Pablo Alonso Vazquez <pav.vigo@gmail.com>
 */
public class ExportadorHTML {
 public static void goToURL(String URL){
     URL="file://"+URL;
     System.out.println(URL);
           if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
 
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                
                
                    java.net.URI uri = null;
                try {
                    uri = new java.net.URI(URL);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ExportadorHTML.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    desktop.browse(uri);
                } catch (IOException ex) {
                    Logger.getLogger(ExportadorHTML.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                    
                  
            
            }
        }
    }
    

    public static void ExportarHTML(ArrayList<Tarjeta> arrListTarjetas) {
        
        JFileChooser jfiCho = new JFileChooser();
        jfiCho.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int resultado = jfiCho.showOpenDialog(null);
        System.out.println("resu "+ resultado);
        File fichero = jfiCho.getSelectedFile();
        if ((fichero == null) || (fichero.getName().equals(""))) {
            JOptionPane.showMessageDialog(null, "Nombre de archivo inválido", "Nombre de archivo inválido", JOptionPane.ERROR_MESSAGE);
        } else {
            FileWriter fiWri = null;
            try {
                fiWri = new FileWriter(fichero);
            } catch (IOException ex) {
                Logger.getLogger(ExportadorHTML.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                fiWri.write(generadorHTML(arrListTarjetas));
            } catch (IOException ex) {
                Logger.getLogger(ExportadorHTML.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    fiWri.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExportadorHTML.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
                goToURL(fichero.getAbsolutePath());
            
            
        }

    }


public static String generadorHTML(ArrayList<Tarjeta> arrTar){
    String pagina = "<html> \n <body> \n <Table><tr><th>Nombre</th><th>Ejemplo</th><th>Descripción</th></tr>";
    for(Tarjeta auxTar:arrTar){
    pagina+= "<tr>\n";
    pagina+= "<td>"+auxTar.getNombre()+"</td>\n";
    pagina+= "<td>"+auxTar.getEjemplo()+"</td>\n";
    pagina+= "<td>"+auxTar.getDescripcion()+"</td>\n";
    pagina+= "</tr>\n";
    }
    pagina += " </table>\n</body>\n</html>";
        System.out.println("pagina");
    
    return pagina;}
    
}
