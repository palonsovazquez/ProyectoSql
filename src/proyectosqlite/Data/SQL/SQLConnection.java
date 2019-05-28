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

    
    private static SQLConnection INSTANCE = null;
    private File url = new File("PATH.db");
    private String preurl = "jdbc:sqlite://";
    private String username = "";
    private String password = "";
    /**
 * devuelve una nueva conexion con los parametros cargados.
 * @return 
 * @throws SQLException 
 */
    
       private Connection getConexion() throws SQLException {
        Connection connection = DriverManager.getConnection(preurl + url.getAbsolutePath(), username, password);

        return connection;
    }
/**
 * Constructor privado para el SQLConnection
 */
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
    /**
     * Metodo para construir el unico el unico objeto de este Singleton. 
     * @return devuelve el objeto SQLConnection
     */
    public static SQLConnection getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new SQLConnection();

        }
        return INSTANCE;

    }

    public ArrayList<Etiqueta> getEtiquetas() {

        return getEtiquetas("");

    }
/**
 * Metodo para agregar una tarjeta a la tabla tarjetas
 * @param tarjeta recive una tarjeta
 * @return devuelve un true si lo ha hecho correctamente
 */
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

    /**
     * Metodo para actualizar una tarjeta a la tabla tarjetas
     * @param tarjeta recive una tarjeta
     * @return un true si lo ha hecho correctamente
     */
    public boolean updateTarjeta(Tarjeta tarjeta) {
        boolean hecho = false;
        try {

            Connection connection = getConexion();

            PreparedStatement preSt = connection.prepareStatement("UPDATE " + Tarjeta.getAlias_tabla() + " SET   " + Tarjeta.getAlias_nombre() + " = ? ," + Tarjeta.getAlias_ejemplo() + " = ? ," + Tarjeta.getAlias_descripcion() + " = ? WHERE "+Tarjeta.getAlias_idTarjeta() + " = ?");
                                                                   
            preSt.setString(1, tarjeta.getNombre());
            preSt.setString(2, tarjeta.getEjemplo());
            preSt.setString(3, tarjeta.getDescripcion());
            preSt.setInt(4, tarjeta.getIdTarjeta());
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
    
    
    /**
     * Metodo para eliminar de la base de datos una tarjeta
     * @param tarjeta
     * @return un true si lo ha hecho correctamente
     */
    public boolean deleteTarjeta(Tarjeta tarjeta) {
        
        // falta gestionar las etiquetas asociadas a esta tarjeta en etiquetasDeTarjetas para que tambien las elimine. 
        boolean hecho = false;
        try {

            Connection connection = getConexion();

            PreparedStatement preSt = connection.prepareStatement("DELETE FROM  " + Tarjeta.getAlias_tabla() + " WHERE "+Tarjeta.getAlias_idTarjeta() + " = ?");
                                                                   
            
            preSt.setInt(1, tarjeta.getIdTarjeta());
            System.out.println("prueba rs = " + preSt.toString());
            preSt.executeUpdate();

            
            preSt.close();
            connection.close();
            hecho = true;

        } catch (SQLException ex) {
            System.out.println(ex);
            hecho = false;
        }

        return hecho;
    }
    /**
     * Metodo que añade una etiqueta a la tabla etiqueta
     * @param etiqueta recive una etiqueta
     * @return devuelve true si ha sido correcto.
     */
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
/**
 * Metodo para conseguir las etiquetas asociadas a una tarjeta concreta, esta relacion se encuentra en la tabla etiquetasDeTarjetas.
 * @param tarjeta recibe una tarjeta de la cual extrae el id.
 * @return devuelve un arraylist de etiquetas.
 */
    public ArrayList<Etiqueta> getEtiquetasdeTarjeta(Tarjeta tarjeta) {
        ArrayList<Etiqueta> auxArrlist = new ArrayList<Etiqueta>();
        Etiqueta etiqaux;
        try {
            Connection connection = getConexion();

            PreparedStatement preSt = connection.prepareStatement("SELECT " + Etiqueta.getAlias_idEtiqueta() + "," 
                    + Etiqueta.getAlias_nombre() + "," + Etiqueta.getAlias_descripcion() + " FROM " + Etiqueta.getAlias_tabla() +
                    " WHERE " + Etiqueta.getAlias_idEtiqueta() + " IN (SELECT " + Etiqueta.getAlias_idEtiqueta() + " FROM etiquetasDeTarjetas WHERE " +
                    Tarjeta.getAlias_idTarjeta() + " = ?)");
            preSt.setInt(1, tarjeta.getIdTarjeta());                                                                                                

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

    /**
     * Metodo que devuelve las etiquetas que coinciden con una busqueda
     * @param BusquedaNombre
     * @return 
     */
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

    /**
     * Metodo que devuelve las tarjetas que coinciden con una buasqueda
     * @param BusquedaNombre
     * @return 
     */
    public ArrayList<Tarjeta> getTarjetas(String BusquedaNombre) {
      return  getTarjetasFiltradas(BusquedaNombre,null);
      
    }
/**
 * Metodo que devuelve las Tarjetas que estan asociadas con las etiquetas de la lista que se le envian, ademas de filtrar por el nombre de la tarjeta.
 * @param BusquedaNombre  en el programa por ahora no se usa nunca.
 * @param arrListEtiquetas
 * @return 
 */
    public ArrayList<Tarjeta> getTarjetasFiltradas(String BusquedaNombre, List<Etiqueta> arrListEtiquetas) {
        ArrayList<Tarjeta> auxArrlist = new ArrayList<Tarjeta>();
        Tarjeta etiqaux;
        // consulta base
        try {
            Connection connection = getConexion();
            String auxConsulta = "SELECT " + Tarjeta.getAlias_idTarjeta() + "," + Tarjeta.getAlias_nombre() + 
                    "," + Tarjeta.getAlias_ejemplo() + "," + Tarjeta.getAlias_descripcion() + " FROM " + 
                    Tarjeta.getAlias_tabla() + " WHERE " + Tarjeta.getAlias_nombre() + " LIKE ? ";
           // si la lista no es nula añado un limitante mas a la consulta para que filtre por las etiquetas.
            if(arrListEtiquetas != null){
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

            }}
            System.out.println("auxC" + auxConsulta);
            PreparedStatement preSt = connection.prepareStatement(auxConsulta);
            preSt.setString(1, "%" + BusquedaNombre + "%");
           if(arrListEtiquetas != null){
            for (int i = 0; i < arrListEtiquetas.size(); i++) {
                preSt.setInt(i + 2, arrListEtiquetas.get(i).getIdEtiqueta());

            }
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
/**
 * Metodo para generar la tabla a partir de un fichero sql, parte el fichero en consultas y los va agregando a un batch, luego lo ejecuta.
 * @param file 
 */
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
/**
 * Test de conexion para verificar que funciona bien la base de datos.
 * @return 
 */
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
