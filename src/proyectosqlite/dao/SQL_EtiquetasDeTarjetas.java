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
public class SQL_EtiquetasDeTarjetas {

    /**
     * Metodo para eliminar de la base de datos una asociacion de Etiqueta y
     * Tarjeta
     *
     * @param etiqueta
     * @param tarjeta
     *
     * @return un true si lo ha hecho correctamente
     */
    public static boolean deleteEtiquetaDeTarjeta(Etiqueta etiqueta, Tarjeta tarjeta) {

        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();
                String auxString ="DELETE FROM  etiquetasDeTarjetas WHERE " + Tarjeta.getAlias_idTarjeta() + " = ? AND " + Etiqueta.getAlias_idEtiqueta() + " = ? ;";
                System.out.println("auxe-"+auxString);
                PreparedStatement preSt = connection.prepareStatement(auxString);

            preSt.setInt(1, tarjeta.getIdTarjeta());
            preSt.setInt(2, etiqueta.getIdEtiqueta());
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

    public static boolean deleteEtiquetasDeTarjeta(Tarjeta tarjeta) {

        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("DELETE FROM  etiquetasDeTarjetas WHERE " + Tarjeta.getAlias_idTarjeta() + "= ? ;");

            preSt.setInt(1, tarjeta.getIdTarjeta());
            
            
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
    public static boolean añadirEtiquetaATarjeta(Tarjeta tarjeta, Etiqueta etiqueta) {
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("INSERT INTO etiquetasDeTarjetas ( " + Etiqueta.getAlias_idEtiqueta() + "," + Tarjeta.getAlias_idTarjeta() + ")VALUES(?,?);");
            preSt.setInt(1, etiqueta.getIdEtiqueta());
            preSt.setInt(2, tarjeta.getIdTarjeta());

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
     * Metodo para conseguir las etiquetas asociadas a una tarjeta concreta,
     * esta relacion se encuentra en la tabla etiquetasDeTarjetas.
     *
     * @param tarjeta recibe una tarjeta de la cual extrae el id.
     * @return devuelve un arraylist de etiquetas.
     */
    public static ArrayList<Etiqueta> getEtiquetasdeTarjeta(Tarjeta tarjeta) {
        ArrayList<Etiqueta> auxArrlist = new ArrayList<Etiqueta>();

        Etiqueta etiqaux;
        try {
            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("SELECT " + Etiqueta.getAlias_idEtiqueta() + ","
                    + Etiqueta.getAlias_nombre() + "," + Etiqueta.getAlias_descripcion() + " FROM " + Etiqueta.getAlias_tabla()
                    + " WHERE " + Etiqueta.getAlias_idEtiqueta() + " IN (SELECT " + Etiqueta.getAlias_idEtiqueta() + " FROM etiquetasDeTarjetas WHERE "
                    + Tarjeta.getAlias_idTarjeta() + " = ?)");
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

}
