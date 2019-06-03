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
public class SQL_Tarjetas {

   /**
    * Este metodo devuelve la tarjeta con el id mas alto, que al tener configurada la tabla como autoincrement tambien es la ultima tarjeta introducida.
    * @param Id
    * @return 
    */
     public static Tarjeta getUltimaTarjeta() {
        ArrayList<Tarjeta> auxArrlist = new ArrayList<Tarjeta>();
        Tarjeta etiqaux;
        // consulta base
        try{
            Connection connection = SQLConnection.getInstance().getConexion();
            String auxConsulta = "SELECT " + Tarjeta.getAlias_idTarjeta() + "," + Tarjeta.getAlias_nombre()
                    + "," + Tarjeta.getAlias_ejemplo() + "," + Tarjeta.getAlias_descripcion() + " FROM "
                    + Tarjeta.getAlias_tabla() + " WHERE "+ Tarjeta.getAlias_idTarjeta()+ " = (SELECT MAX( "+Tarjeta.getAlias_idTarjeta()+ " ) FROM "+Tarjeta.getAlias_tabla()+" );";
            
            
              

                
        
            Statement sta = connection.createStatement();
       

          
            ResultSet rs = sta.executeQuery(auxConsulta);

            while (rs.next()) {

                Integer idEtiqueta = rs.getInt(Tarjeta.getAlias_idTarjeta());
                String nombre = rs.getString(Tarjeta.getAlias_nombre());
                String ejemplo = rs.getString(Tarjeta.getAlias_ejemplo());
                String descripcion = rs.getString(Tarjeta.getAlias_descripcion());

                etiqaux = new Tarjeta(idEtiqueta, nombre, ejemplo, descripcion);
                auxArrlist.add(etiqaux);

            }
            rs.close();
            sta.close();
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex);

        }
        return auxArrlist.get(0);

    }

    /**
     * Metodo para agregar una tarjeta a la tabla tarjetas
     *
     * @param tarjeta recive una tarjeta
     * @return devuelve un true si lo ha hecho correctamente
     */
    public static boolean añadirTarjeta(Tarjeta tarjeta) {
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

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
     *
     * @param tarjeta recive una tarjeta
     * @return un true si lo ha hecho correctamente
     */
    public static boolean updateTarjeta(Tarjeta tarjeta) {
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("UPDATE " + Tarjeta.getAlias_tabla() + " SET   " + Tarjeta.getAlias_nombre() + " = ? ," + Tarjeta.getAlias_ejemplo() + " = ? ," + Tarjeta.getAlias_descripcion() + " = ? WHERE " + Tarjeta.getAlias_idTarjeta() + " = ?");

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
     *
     * @param tarjeta
     * @return un true si lo ha hecho correctamente
     */
    public static boolean deleteTarjeta(Tarjeta tarjeta) {

        // falta gestionar las etiquetas asociadas a esta tarjeta en etiquetasDeTarjetas para que tambien las elimine. 
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("DELETE FROM  " + Tarjeta.getAlias_tabla() + " WHERE " + Tarjeta.getAlias_idTarjeta() + " = ?");

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
     *
     * @param etiqueta recive una etiqueta
     * @return devuelve true si ha sido correcto.
     */
    public static boolean añadirEtiqueta(Etiqueta etiqueta) {
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

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
     * Metodo que devuelve las Tarjetas que estan asociadas con las etiquetas de
     * la lista que se le envian, ademas de filtrar por el nombre de la tarjeta.
     *
     * @param BusquedaNombre en el programa por ahora no se usa nunca.
     * @param arrListEtiquetas
     * @return
     */
    public static ArrayList<Tarjeta> getTarjetasFiltradas(String BusquedaNombre, List<Etiqueta> arrListEtiquetas) {
        ArrayList<Tarjeta> auxArrlist = new ArrayList<Tarjeta>();
        Tarjeta etiqaux;
        // consulta base
        try {
            Connection connection = SQLConnection.getInstance().getConexion();
            String auxConsulta = "SELECT " + Tarjeta.getAlias_idTarjeta() + "," + Tarjeta.getAlias_nombre()
                    + "," + Tarjeta.getAlias_ejemplo() + "," + Tarjeta.getAlias_descripcion() + " FROM "
                    + Tarjeta.getAlias_tabla() + " WHERE " + Tarjeta.getAlias_nombre() + " LIKE ? ";
            // si la lista no es nula añado un limitante mas a la consulta para que filtre por las etiquetas.
            if (arrListEtiquetas != null) {
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
            }
            System.out.println("auxC" + auxConsulta);
            PreparedStatement preSt = connection.prepareStatement(auxConsulta);
            preSt.setString(1, "%" + BusquedaNombre + "%");
            if (arrListEtiquetas != null) {
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
     * Metodo que devuelve las tarjetas que coinciden con una buasqueda
     *
     * @param BusquedaNombre
     * @return
     */
    public static ArrayList<Tarjeta> getTarjetas(String BusquedaNombre) {
        return getTarjetasFiltradas(BusquedaNombre, null);

    }
    public static ArrayList<Tarjeta> getTarjetas() {
        return getTarjetas("");

    }
}
