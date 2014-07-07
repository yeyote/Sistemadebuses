/*
 * ESTE PROYECTO ES DESARROLLADO POR GUSTAVO VARGAS MIRANDA- MARCO AURELIO BARBA HENSLER
 * Y LUIS CARLO OSINAGA SORIA, PARA LA MATERIA DE SISTEMAS DE INFORMACION 2
 * DE LA UNIVERSIDAD AUTONOMA GABRIEL RENE MORENO EN LA FACULTAD INTEGRAL DEL CHACO
 * LOS DERECHOS INTELECTUALES DE ESTE SISTEMAS PERTENECEN A DICHA UNIVERSIDAD
 * Y ES DESARROLLADO CON FINES ACADEMICOS, POR LO QUE LA VENTA Y O COPIA PARCIAL O TOTAL
 * SOLO DEBERIA REALIZARSE PARA LOS MISMOS FINES
 */
/**
 *
 * @author [GVM - MABH - LCOS]
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import pack.Main;

public class Conexion {

    private static Conexion myInstance;
    private Connection conn = null;
    private Statement stm;
    private final String url;

    private Conexion() {
        this.url = "jdbc:mysql://" + Main.host + "/" + Main.db;
        conectar();
    }

    public static Conexion getInstance() {
        if (myInstance == null) {
            myInstance = new Conexion();
        }
        return myInstance;
    }

    private void conectar() {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            conn = DriverManager.getConnection(url, Main.user, Main.password);
            if (conn != null) {
                stm = conn.createStatement();
                stm.addBatch("use " + Main.db + ";");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "SE PRODUJO UN ERROR MIENTRAS SE INTENTABA "
                    + "\nCONECTAR A LA BASE DE DATOS.\n\n"
                    + "REVISE EL ARCHIVO DE CONFIGURACION Y\n"
                    + "VERIFIQUE SI SU CONFIGURACION ES CORRECTA\n\n"
                    + "DE PERSISTIR EL PROBLEMA \n"
                    + "CONTACTE CON EL ADMINISTRADOR DE SISTEMAS",
                    "ERROR AL INTENTAR CONECTAR A LA BASE DE DATOS", JOptionPane.ERROR_MESSAGE);
            ManagerArchivo.escribirLog("[" + new Date() + "] ERROR AL INTENTAR CONECTAR A LA BASE DE DATOS ->" + ex.getMessage());
            System.exit(0);
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void ejecutar(String consulta) {
        try {
            stm.executeUpdate(consulta);
            this.generarBackup(consulta);
        } catch (Exception ex) {
            System.out.println("Error en la ejecucion de: " + consulta + "\n" + ex.getMessage());
            ManagerArchivo.escribirLog("[" + new Date() + "] ERROR AL EJECUTAR SQL :'" + consulta + "' :" + ex.getMessage());
        }
    }

    public ResultSet consultar(String consulta) {
        ResultSet rs = null;
        try {
            rs = stm.executeQuery(consulta);
        } catch (Exception ex) {
            System.out.println("Error realizar la Consutla: \n" + consulta + "\n" + ex.getMessage());
            ManagerArchivo.escribirLog("[" + new Date() + "] ERROR AL EJECUTAR SQL :'" + consulta + "' :" + ex.getMessage());
        }
        return rs;
    }

    private void generarBackup(String sql) {
        String consulta = SQL.backup(sql);
        try {
            stm.executeUpdate(consulta);
        } catch (Exception ex) {
            System.out.println("Error en la ejecucion de: " + consulta + "\n" + ex.getMessage());
            ManagerArchivo.escribirLog("[" + new Date() + "] ERROR AL EJECUTAR SQL :'" + consulta + "' :" + ex.getMessage());
        }
    }

}
