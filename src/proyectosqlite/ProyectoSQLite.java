/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosqlite;
import proyectosqlite.Data.SQL.SQLConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import proyectosqlite.GUI.FiltradorTarjetas;

/**
 *
 * @author Pablo Alonso Vazquez <pav.vigo@gmail.com>
 */
public class ProyectoSQLite {
public static FiltradorTarjetas filTar;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//     SQLConnection x = SQLConnection.getInstance();
//     File fil = new File("/home/pablo/NetBeansProjects/prog/proyectoSQLite/creacionTablas.sql");
//     x.EjecutarSQLFile(fil);
//        
       filTar = new FiltradorTarjetas();
       filTar.setVisible(true);
       
        
    }
    
}
