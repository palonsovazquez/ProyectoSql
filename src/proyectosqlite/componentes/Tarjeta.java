/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosqlite.componentes;

import java.util.ArrayList;

/**
 *
 * @author Pablo Alonso Vazquez <pav.vigo@gmail.com>
 */
public class Tarjeta {
    ArrayList <Etiqueta> arrList_Etiquetas; 
    
    private Integer idTarjeta;
    private String nombre;
    private String ejemplo;
    private String descripcion;
    private static String alias_tabla = "tarjetas";
    private static String alias_idTarjeta = "idTarjeta";
    private static String alias_nombre = "nombre";
    private static String alias_ejemplo = "ejemplo";
    private static String alias_descripcion = "descripcion";

    public static String getAlias_tabla() {
        return alias_tabla;
    }

    public static void setAlias_tabla(String alias_tabla) {
        Tarjeta.alias_tabla = alias_tabla;
    }

    public static String getAlias_idTarjeta() {
        return alias_idTarjeta;
    }

    public static void setAlias_idTarjeta(String alias_idTarjeta) {
        Tarjeta.alias_idTarjeta = alias_idTarjeta;
    }

    public static String getAlias_nombre() {
        return alias_nombre;
    }

    public static void setAlias_nombre(String alias_nombre) {
        Tarjeta.alias_nombre = alias_nombre;
    }

    public static String getAlias_ejemplo() {
        return alias_ejemplo;
    }

    public static void setAlias_ejemplo(String alias_ejemplo) {
        Tarjeta.alias_ejemplo = alias_ejemplo;
    }

    public static String getAlias_descripcion() {
        return alias_descripcion;
    }

    public static void setAlias_descripcion(String alias_descripcion) {
        Tarjeta.alias_descripcion = alias_descripcion;
    }

    public Tarjeta(Integer idTarjeta, String nombre, String ejemplo, String descripcion) {
        this.idTarjeta = idTarjeta;
        this.nombre = nombre;
        this.ejemplo = ejemplo;
        this.descripcion = descripcion;

    }

    public Integer getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Integer idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return  nombre;
    }

}
