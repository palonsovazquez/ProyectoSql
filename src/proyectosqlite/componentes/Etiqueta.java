/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosqlite.componentes;

/**
 *
 * @author Pablo Alonso Vazquez <pav.vigo@gmail.com>
 */
public class Etiqueta {
    private Integer idEtiqueta;
    private String nombre;
    private String descripcion;
    private static String alias_tabla = "etiquetas";
    private static String alias_idEtiqueta = "idEtiqueta";
    private static String alias_nombre = "nombre";
    private static String alias_descripcion = "descripcion";

    public static String getAlias_tabla() {
        return alias_tabla;
    }

    public static void setAlias_tabla(String alias_tabla) {
        Etiqueta.alias_tabla = alias_tabla;
    }

    public static String getAlias_idEtiqueta() {
        return alias_idEtiqueta;
    }

    public static void setAlias_idEtiqueta(String alias_idEtiqueta) {
        Etiqueta.alias_idEtiqueta = alias_idEtiqueta;
    }

    public static String getAlias_nombre() {
        return alias_nombre;
    }

    public static void setAlias_nombre(String alias_nombre) {
        Etiqueta.alias_nombre = alias_nombre;
    }

    public static String getAlias_descripcion() {
        return alias_descripcion;
    }

    public static void setAlias_descripcion(String alias_descripcion) {
        Etiqueta.alias_descripcion = alias_descripcion;
    }

   

    public Etiqueta(Integer idEtiqueta, String nombre, String descripcion) {
        this.idEtiqueta = idEtiqueta;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(Integer idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
