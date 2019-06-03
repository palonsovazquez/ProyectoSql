/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosqlite.dao;

import proyectosqlite.modelo.Etiqueta;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import proyectosqlite.modelo.Etiqueta;
import proyectosqlite.modelo.Tarjeta;

/**
 *
 * @author Pablo Alonso Vazquez <pav.vigo@gmail.com>
 *
 *
 *
 */
public class SQLConnection {

    private static SQLConnection INSTANCE = null;
    private File url = new File("data" + File.separator + "BD.db");
    private String preurl = "jdbc:sqlite://";
    private String username = "";
    private String password = "";

    public File getUrl() {
        return url;
    }

    /**
     * devuelve una nueva conexion con los parametros cargados.
     *
     * @return
     * @throws SQLException
     */
    public  void crearDefaults() {
        FileWriter filWri= null;
        File archivo = new File("data"+File.separator+"creacionTablas.sql");
        if (!archivo.canRead()) {
            if (!archivo.getParentFile().exists()) {

                archivo.getParentFile().mkdir();
                
            }
            try {
                archivo.createNewFile();
                filWri = new FileWriter(archivo);
                filWri.write("DROP TABLE  if exists etiquetasDeTarjetas;\n" +
"DROP TABLE  if exists etiquetas;\n" +
"DROP TABLE  if exists tarjetas;\n" +
"create table etiquetas(\n" +
"idEtiqueta integer primary key AUTOINCREMENT NOT NULL ,\n" +
"nombre varchar(30),\n" +
"descripcion varchar(320)\n" +
");\n" +
"\n" +
"CREATE TABLE tarjetas(\n" +
"idTarjeta integer primary key AUTOINCREMENT NOT NULL ,\n" +
"nombre varchar(30),\n" +
"ejemplo varchar(320),\n" +
"descripcion varchar(320)\n" +
");\n" +
"\n" +
"CREATE TABLE etiquetasDeTarjetas(\n" +
"idTarjeta integer NOT NULL ,\n" +
"idEtiqueta integer NOT NULL,\n" +
"foreign key (idTarjeta) references tarjetas(idTarjeta),\n" +
"foreign key (idEtiqueta) references Etiquetas(idEtiqueta),\n" +
"primary key (idTarjeta,idEtiqueta)\n" +
");\n" +
"\n" +
"INSERT INTO tarjetas(idTarjeta,nombre,ejemplo,descripcion) values (0,'style=\"\"','style=\"color:blue\"','integra css en una etiqueta html');\n" +
"INSERT INTO tarjetas(idTarjeta,nombre,ejemplo,descripcion) values (1,'html','Ejemplo','Etiqueta raiz de documento html');\n" +
"INSERT INTO tarjetas(idTarjeta,nombre,ejemplo,descripcion) values (2,'<xsl:choose>','Ejemplo','Descripcion');\n" +
"INSERT INTO tarjetas(idTarjeta,nombre,ejemplo,descripcion) values (3,'cd','cd /home/user','Change Directory: programa que sirve para cambiar de directorio');\n" +
"\n" +
"\n" +
"INSERT INTO etiquetas(idEtiqueta,nombre,descripcion) values (0,'Lenguaje de Marcas','');\n" +
"INSERT INTO etiquetas(idEtiqueta,nombre,descripcion) values (1,'Sistemas Informaticos','');\n" +
"INSERT INTO etiquetas(idEtiqueta,nombre,descripcion) values (2,'Contornos','');\n" +
"INSERT INTO etiquetas(idEtiqueta,nombre,descripcion) values (3,'Bases de Datos','');\n" +
"INSERT INTO etiquetas(idEtiqueta,nombre,descripcion) values (4,'XML','');\n" +
"INSERT INTO etiquetas(idEtiqueta,nombre,descripcion) values (5,'XSL','');\n" +
"INSERT INTO etiquetas(idEtiqueta,nombre,descripcion) values (6,'CSS','');\n" +
"INSERT INTO etiquetas(idEtiqueta,nombre,descripcion) values (7,'HTML','');\n" +
"\n" +
"\n" +
"\n" +
"INSERT INTO etiquetasDeTarjetas(idTarjeta,idEtiqueta) values (0,0);\n" +
"INSERT INTO etiquetasDeTarjetas(idTarjeta,idEtiqueta) values (0,4);\n" +
"INSERT INTO etiquetasDeTarjetas(idTarjeta,idEtiqueta) values (1,0);\n" +
"INSERT INTO etiquetasDeTarjetas(idTarjeta,idEtiqueta) values (1,7);\n" +
"INSERT INTO etiquetasDeTarjetas(idTarjeta,idEtiqueta) values (2,4);\n" +
"INSERT INTO etiquetasDeTarjetas(idTarjeta,idEtiqueta) values (2,0);\n" +
"INSERT INTO etiquetasDeTarjetas(idTarjeta,idEtiqueta) values (2,5);\n" +
"INSERT INTO etiquetasDeTarjetas(idTarjeta,idEtiqueta) values (3,1);");
            } catch (IOException ex) {
                Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    filWri.close();
                } catch (IOException ex) {
                    Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
}
        }

    }

    public void setUrl(File url) {
        this.url = url;
        if (!testTablas()) {

            EjecutarSQLFile(new File("data" + File.separator + "creacionTablas.sql"));
        }
    }

    public Connection getConexion() throws SQLException {
        Connection connection = DriverManager.getConnection(preurl + url.getAbsolutePath(), username, password);

        return connection;
    }

    /**
     * Constructor privado para el SQLConnection
     */
    private SQLConnection() {
//        if (null == url || !url.canRead()) {
//            System.out.println("Error, fichero no encontrado.");
//
//        } else {
        try {
            Class.forName("org.sqlite.JDBC");
            java.sql.Connection conexion = getConexion();
            conexion.close();
            System.out.println("exito");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error en la conexión de la base de datos");
        }

//        }
    }

    /**
     * Metodo para construir el unico el unico objeto de este Singleton.
     *
     * @return devuelve el objeto SQLConnection
     */
    public static SQLConnection getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new SQLConnection();

        }
        return INSTANCE;

    }

    /**
     * Metodo para generar la tabla a partir de un fichero sql, parte el fichero
     * en consultas y los va agregando a un batch, luego lo ejecuta.
     *
     * @param file
     */
    public void EjecutarSQLFile(File file) {
        System.out.println(url.getAbsolutePath());
        System.out.println("file-" + file.getAbsolutePath());
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
     *
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

    /**
     * comprueba si el esquema de tablas es correcto en el fichero
     *
     * @param fichero
     * @return devuelve true si es correcto, false si no es correcto
     */
    public boolean testTablas() {
        boolean correcto = true;
        ArrayList<String> auxArrlist = new ArrayList<String>();
        String name = "";
        try {
            Connection connection = getConexion();

            Statement sta = connection.createStatement();
            ResultSet rs = sta.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' order by name;");

            while (rs.next()) {

                name = rs.getString("name");
                System.out.println(name);
                auxArrlist.add(name);
            }
            rs.close();
            sta.close();
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex);

        }
        if (auxArrlist.size() != 4) {
            correcto = false;
        } else {
            if (auxArrlist.get(0).compareToIgnoreCase(Etiqueta.getAlias_tabla()) != 0) {
                correcto = false;
            }
            if (auxArrlist.get(1).compareToIgnoreCase("etiquetasDeTarjetas") != 0) {
                correcto = false;
            }
            if (auxArrlist.get(2).compareToIgnoreCase("sqlite_sequence") != 0) {
                correcto = false;
            }
            if (auxArrlist.get(3).compareToIgnoreCase(Tarjeta.getAlias_tabla()) != 0) {
                correcto = false;
            }
        }

        return correcto;
    }

}
