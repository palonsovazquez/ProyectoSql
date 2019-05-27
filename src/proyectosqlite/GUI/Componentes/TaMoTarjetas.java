/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosqlite.GUI.Componentes;


import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import proyectosqlite.componentes.Tarjeta;

/**
 *
 * @author pav_g
 */
public class TaMoTarjetas extends AbstractTableModel {

    public ArrayList<Tarjeta> getDataTarjeta() {
        return dataTarjeta;
    }

    public void setDataTarjeta(ArrayList<Tarjeta> dataTarjeta) {
        this.dataTarjeta = dataTarjeta;
    }

    public String[] getNombresColumna() {
        return nombresColumna;
    }

    public void setNombresColumna(String[] nombresColumna) {
        this.nombresColumna = nombresColumna;
    }

    ArrayList<Tarjeta> dataTarjeta;

    public TaMoTarjetas(ArrayList<Tarjeta> dataTarjetas) {
        dataTarjeta = dataTarjetas;

    }

    public String[] nombresColumna = {"Id", "Nombre", "Ejemplo","Descripcion"};

    @Override
    public int getRowCount() {
        int x = dataTarjeta.size();

        return x;
    }

    @Override
    public String getColumnName(int columna) {
        return nombresColumna[columna];

    }

    @Override
    public int getColumnCount() {

        return nombresColumna.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {

        switch (columna) {
            case 0:
                return dataTarjeta.get(fila).getIdTarjeta();
            case 1:
                return dataTarjeta.get(fila);

            case 2:

                return  dataTarjeta.get(fila).getEjemplo();

            case 3:
                return dataTarjeta.get(fila).getDescripcion();

            
        }

        return null;
    }

}
