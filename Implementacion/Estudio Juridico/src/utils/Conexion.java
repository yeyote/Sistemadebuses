package utils;

/**
 *
 * @author [GVM - MABH - LCOS]
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    private String user;
    private String password;
    private String host;
    private String url;
    private Connection conn = null;
    private Statement stm;
    private String db;

    public Conexion(String usuario, String contraseña, String servidor, String bd) {
        this.user = usuario;
        this.password = contraseña;
        this.db = bd;
        this.host = servidor;
        this.url = "jdbc:mysql://" + this.host + "/" + this.db;
        conectar();
    }

    private void conectar() {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                stm = conn.createStatement();
                stm.addBatch("use " + db + ";");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error al conectarse \n");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void ejecutar(String consulta) {
        try {
            stm.executeUpdate(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la ejecucion de: " + consulta);
        }
    }

    public ResultSet consultar(String consulta) {
        ResultSet rs = null;
        try {
            rs = stm.executeQuery(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error realizar la Consutla: \n" + consulta + "\n");
        }
        return rs;
    }
}
