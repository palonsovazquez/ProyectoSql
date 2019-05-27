/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosqlite.Data.SQL;

import proyectosqlite.componentes.Etiqueta;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import proyectosqlite.componentes.Etiqueta;
import proyectosqlite.componentes.Tarjeta;

/**
 *
 * @author Pablo Alonso Vazquez <pav.vigo@gmail.com>
 * 
 * 
 * 
 */




public class SQLConnection {

    
       private Connection getConexion() throws SQLException {
        Connection connection = DriverManager.getConnection(preurl + url.getAbsolutePath(), username, password);

        return connection;
    }
    
    private static SQLConnection INSTANCE = null;
    private File url = new File("PATH.db");

    private String preurl = "jdbc:sqlite://";
    private String username = "";
    private String password = "";

    private SQLConnection() {
        if (null == url || !url.canRead()) {
            System.out.println("Error, fichero no encontrado.");

        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                java.sql.Connection conexion = getConexion();
                conexion.close();
                System.out.println("exito");
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println("Error en la conexión de la base de datos");
            }

        }
    }

    public static SQLConnection getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new SQLConnection();

        }
        return INSTANCE;

    }

    public ArrayList<Etiqueta> getEtiquetas() {

        return getEtiquetas("");

    }

    public boolean añadirTarjeta(Tarjeta tarjeta) {
        boolean hecho = false;
        try {

            Connection connection = getConexion();

            PreparedStatement preSt = connection.prepareStatement("INSERT INTO " + Tarjeta.getAlias_tabla() + " ( " + Tarjeta.getAlias_nombre() + "," + Tarjeta.getAlias_ejemplo() + "," + Tarjeta.getAlias_descripcion() + ")VALUES(?,?,?);");

            preSt.setString(1, tarjeta.getNombre());
            preSt.setString(2, tarjeta.getEjemplo());
            preSt.setString(3, tarjeta.getDescripcion());

            System.out.println("prueba rs = " + preSt.toString());
            preSt.executeUpdate();

            // System.out.println(proy.toString());
            preSt.close();
            connection.close();
            hecho = true;

        } catch (SQLException ex) {
            System.out.println(ex);
            hecho = false;
        }

        return hecho;
    }

    public boolean añadirEtiqueta(Etiqueta etiqueta) {
        boolean hecho = false;
        try {

            Connection connection = getConexion();

            PreparedStatement preSt = connection.prepareStatement("INSERT INTO " + Etiqueta.getAlias_tabla() + " ( " + Etiqueta.getAlias_nombre() + "," + Tarjeta.getAlias_descripcion() + ")VALUES(?,?);");

            preSt.setString(1, etiqueta.getNombre());
            preSt.setString(2, etiqueta.getDescripcion());

            System.out.println("prueba rs = " + preSt.toString());
            preSt.executeUpdate();

            // System.out.println(proy.toString());
            preSt.close();
            connection.close();
            hecho = true;

        } catch (SQLException ex) {
            System.out.println(ex);
            hecho = false;
        }

        return hecho;
    }

    public ArrayList<Etiqueta> getEtiquetasdeTarjeta(Tarjeta tarj) {
        ArrayList<Etiqueta> auxArrlist = new ArrayList<Etiqueta>();
        Etiqueta etiqaux;
        try {
            Connection connection = getConexion();

            PreparedStatement preSt = connection.prepareStatement("SELECT " + Etiqueta.getAlias_idEtiqueta() + "," 
                    + Etiqueta.getAlias_nombre() + "," + Etiqueta.getAlias_descripcion() + " FROM " + Etiqueta.getAlias_tabla() +
                    " WHERE " + Etiqueta.getAlias_idEtiqueta() + " IN (SELECT " + Etiqueta.getAlias_idEtiqueta() + " FROM etiquetasDeTarjetas WHERE " +
                    Tarjeta.getAlias_idTarjeta() + " = ?)");
            preSt.setInt(1, tarj.getIdTarjeta());                                                                                                

            System.out.println("prueba rs = " + preSt.toString());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {

                Integer idEtiqueta = rs.getInt(Etiqueta.getAlias_idEtiqueta());
                String nombre = rs.getString(Etiqueta.getAlias_nombre());
                String descripcion = rs.getString(Etiqueta.getAlias_descripcion());

                etiqaux = new Etiqueta(idEtiqueta, nombre, descripcion);
                auxArrlist.add(etiqaux);

            }
            rs.close();
            preSt.close();
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex);

        }
        return auxArrlist;

    }

    public ArrayList<Etiqueta> getEtiquetas(String BusquedaNombre) {
        ArrayList<Etiqueta> auxArrlist = new ArrayList<Etiqueta>();
        Etiqueta etiqaux;
        try {
            Connection connection = getConexion();

            PreparedStatement preSt = connection.prepareStatement("SELECT " + Etiqueta.getAlias_idEtiqueta() + "," + Etiqueta.getAlias_nombre() + "," + Etiqueta.getAlias_descripcion() + " FROM " + Etiqueta.getAlias_tabla() + " WHERE " + Etiqueta.getAlias_nombre() + " LIKE ? ");
            preSt.setString(1, "%" + BusquedaNombre + "%");

            System.out.println("prueba rs = " + preSt.toString());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {

                Integer idEtiqueta = rs.getInt(Etiqueta.getAlias_idEtiqueta());
                String nombre = rs.getString(Etiqueta.getAlias_nombre());
                String descripcion = rs.getString(Etiqueta.getAlias_descripcion());

                etiqaux = new Etiqueta(idEtiqueta, nombre, descripcion);
                auxArrlist.add(etiqaux);

            }
            rs.close();
            preSt.close();
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex);

        }
        return auxArrlist;

    }

    public ArrayList<Tarjeta> getTarjetas(String BusquedaNombre) {
        ArrayList<Tarjeta> auxArrlist = new ArrayList<Tarjeta>();
        Tarjeta etiqaux;
        try {
            Connection connection = getConexion();
            String auxString = "SELECT " + Tarjeta.getAlias_idTarjeta() + "," + Tarjeta.getAlias_nombre() + "," + Tarjeta.getAlias_ejemplo() + "," + Tarjeta.getAlias_descripcion() + " FROM " + Tarjeta.getAlias_tabla() + " WHERE " + Tarjeta.getAlias_nombre() + " LIKE ? ";

            PreparedStatement preSt = connection.prepareStatement(auxString);

            preSt.setString(1, "%" + BusquedaNombre + "%");

            System.out.println("prueba rs = " + preSt.toString());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {

                Integer idEtiqueta = rs.getInt(Tarjeta.getAlias_idTarjeta());
                String nombre = rs.getString(Tarjeta.getAlias_nombre());
                String ejemplo = rs.getString(Tarjeta.getAlias_ejemplo());
                String descripcion = rs.getString(Tarjeta.getAlias_descripcion());

                etiqaux = new Tarjeta(idEtiqueta, nombre, ejemplo, descripcion);
                auxArrlist.add(etiqaux);

            }
            rs.close();
            preSt.close();
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex);

        }
        return auxArrlist;

    }

    public ArrayList<Tarjeta> getTarjetasFiltradas(String BusquedaNombre, List<Etiqueta> arrListEtiquetas) {
        ArrayList<Tarjeta> auxArrlist = new ArrayList<Tarjeta>();
        Tarjeta etiqaux;
        System.out.println("num" + arrListEtiquetas.size());
        try {
            Connection connection = getConexion();
            String auxConsulta = "SELECT " + Tarjeta.getAlias_idTarjeta() + "," + Tarjeta.getAlias_nombre() + 
                    "," + Tarjeta.getAlias_ejemplo() + "," + Tarjeta.getAlias_descripcion() + " FROM " + 
                    Tarjeta.getAlias_tabla() + " WHERE " + Tarjeta.getAlias_nombre() + " LIKE ? ";

            for (int i = 0; i < arrListEtiquetas.size(); i++) {
                if (i == 0) {
                    auxConsulta = auxConsulta + " AND ( " + Tarjeta.getAlias_idTarjeta() + " IN (SELECT " + Tarjeta.getAlias_idTarjeta() 
                            + " FROM etiquetasDeTarjetas  WHERE " + Etiqueta.getAlias_idEtiqueta() + "  = ? ";
                }
                if (i > 0) {

                    auxConsulta = auxConsulta + " OR " + Etiqueta.getAlias_idEtiqueta() + " = ? ";

                }
                if (i == arrListEtiquetas.size() - 1) {
                    auxConsulta = auxConsulta + "))";
                }

            }
            System.out.println("auxC" + auxConsulta);
            PreparedStatement preSt = connection.prepareStatement(auxConsulta);
            preSt.setString(1, "%" + BusquedaNombre + "%");
            for (int i = 0; i < arrListEtiquetas.size(); i++) {
                preSt.setInt(i + 2, arrListEtiquetas.get(i).getIdEtiqueta());

            }

            System.out.println("prueba rs = " + preSt);
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {

                Integer idEtiqueta = rs.getInt(Tarjeta.getAlias_idTarjeta());
                String nombre = rs.getString(Tarjeta.getAlias_nombre());
                String ejemplo = rs.getString(Tarjeta.getAlias_ejemplo());
                String descripcion = rs.getString(Tarjeta.getAlias_descripcion());

                etiqaux = new Tarjeta(idEtiqueta, nombre, ejemplo, descripcion);
                auxArrlist.add(etiqaux);

            }
            rs.close();
            preSt.close();
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex);

        }
        return auxArrlist;

    }

    public void EjecutarSQLFile(File file) {
        System.out.println(url.getAbsolutePath());
        ArrayList<String> consultas = new ArrayList<>();
        boolean ok = true;
        if (file.canRead()) {

            // compruebo si es fichero es valido ( termina en sql)
            if (file.isFile() && file.getName() != "" && (file.getName().endsWith(".sql") || file.getName().endsWith(".SQL"))) {

                try {
                    FileReader filread = new FileReader(file);
                    Scanner sc = new Scanner(filread).useDelimiter(";");
                    while (sc.hasNext()) {
                        consultas.add(sc.next() + ";");
                        System.out.println(consultas.get(consultas.size() - 1));

                    }

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE, null, ex);
                    ok = false;
                }

            } else {
                System.out.println("El fichero no es valido.");
                ok = false;
            }

        } else {
            System.out.println("No se encuentra el archivo SQL");
            ok = false;
        }
        if (ok) {
            try {
                Connection connection = getConexion();

                Statement sta = connection.createStatement();
                for (String aux : consultas) {
                    sta.addBatch(aux);
                }
                sta.executeBatch();

                sta.close();

                connection.close();

            } catch (SQLException ex) {
                System.out.println(ex);

            }
        }
    }

    public boolean PruebaConexion() {
        boolean prueba = false;
        try {

            Connection connection = getConexion();
            prueba = true;
            connection.close();
            JOptionPane.showMessageDialog(null, "Conexion exitosa");
        } catch (SQLException ex) {
            prueba = false;
            JOptionPane.showMessageDialog(null, "Error de conexion, conecte la base de datos, antes de iniciar.");
        }
        return prueba;
    }

 

}
