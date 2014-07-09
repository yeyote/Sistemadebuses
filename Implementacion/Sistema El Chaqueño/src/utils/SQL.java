/*
 
 */
package utils;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SQL {

/////////////////////HERRAMIENTAS USADAS PARA LAS CONSULTAS/////////////////////
    public static String sha1(String string) {
        String hash = "";
        try {
            MessageDigest md;
            byte[] buffer, digest;
            buffer = string.getBytes();
            md = MessageDigest.getInstance("SHA1");
            md.update(buffer);
            digest = md.digest();
            for (byte aux : digest) {
                int b = aux & 0xff;
                if (Integer.toHexString(b).length() == 1) {
                    hash += "0";
                }
                hash += Integer.toHexString(b);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hash;
    }

    public static final SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat formatDates = new SimpleDateFormat("yyyy-MM-dd");

    ////////////////////////////REGISTRO DE DATOS/////////////////////////////
    public static String registrarPersona(String ci, String nombre, String app, String apm, String dir, int tipo) {
        return "INSERT INTO `persona`(`ci`,`nombre`,`apellido_parterno`,`apellido_materno`,`direccion`,`id_tit`) VALUES ('"
                + ci + "','"
                + nombre.toUpperCase() + "','"
                + app.toUpperCase() + "','"
                + apm.toUpperCase() + "','"
                + dir.toUpperCase() + "','"
                + tipo + "');";
    }

    public static String registrarPersona(String ci, String nombre, String app, String apm, String dir) {
        return "INSERT INTO `persona`(`ci`,`nombre`,`apellido_parterno`,`apellido_materno`,`direccion`) VALUES ('"
                + ci + "','"
                + nombre.toUpperCase() + "','"
                + app.toUpperCase() + "','"
                + apm.toUpperCase() + "','"
                + dir.toUpperCase() + "');";
    }

    public static String registrarUsuario(String usuario, String pass, int estado, String ci) {
        return "INSERT INTO `usuario`(`id_usu`,`user`,`pass`,`activo`,`ci`) VALUES ( NULL,'"
                + usuario.toUpperCase() + "','"
                + sha1(pass) + "','"
                + estado + "','"
                + ci + "'); ";
    }

    public static String registrarTelefono(String ci, String telf) {
        return "INSERT INTO `telefono`(`ci`,`numero`) VALUES ( '"
                + ci + "','"
                + telf + "'); ";
    }

    public static String registrarTitulo(String titulo, String abrev) {
        return "INSERT INTO `titulo`(`titulo`,`abreviatura`) VALUES ( '"
                + titulo + "','"
                + abrev + "'); ";
    }

    public static String backup(String sql) {
        return "INSERT INTO `backup`(`sql`)VALUES(\"" + sql + "\");";
    }

    public static String registrarCargo(String nombre) {
        return "INSERT INTO `cargo` (`nombre`)VALUES('" + nombre + "');";
    }

    public static String registrarOcupa(int id_car, String ci, Date inic_gest, Date fin_gest, String observacion) {
        return "INSERT INTO `ocupa` (id_car,ci,inic_gest,fin_gest,observacion)VALUES('"
                + id_car + "','"
                + ci + "','"
                + inic_gest + "','"
                + fin_gest + "','"
                + observacion + "');";
    }

    public static String registrarChofer(String id_chofer, String ci, String salario, String telefono, String brevet) {
        return "INSERT INTO `chofer`(`id_chofer`,`ci`,`salario`,`telefono`,`brevet`) VALUES ( '"
                + "NULL"+"','"
                + ci + "','"
                + salario + "','"
                + telefono + "','"
                + brevet+"');"; 
    }

    //////////////////////////CONSULTA DE DATOS////////////////////////////////
    public static String iniciarSecion(String user, String pass) {
        return "SELECT ci FROM `usuario` WHERE `id_user`='" + user + "' AND `password`='" + sha1(pass) + "'";
    }

    ///////////////////////// VISTAS DE DATOS//////////////////////////////////
    public static String mostrarDatosUsuario(int id) {
        return "SELECT `titulo`.`titulo`,`persona`.*, `usuario`.`tipo`"
                + "FROM `usuario` INNER JOIN `persona` INNER JOIN `titulo` "
                + "ON `persona`.`ci` = `usuario`.`ci` AND `persona`.`id_titulo` = `titulo`.`id_titulo`"
                + "WHERE `id_user`='" + id + "';";
    }

    // consultas yeyo
    public static String mostrarAsientos(int id_viaje) {
        return "SELECT asiento FROM boleto WHERE id_viaje='" + id_viaje + "';";
    }

    public static String insertarPersona(String ci, String nombre) {
        return "INSERT INTO `persona` (ci,nombre)VALUES('"
                + ci + "','"
                + nombre + "');";
    }

    public static String insertarBoleto(int id_boleto, int ci, int id_viaje, float costo, int asiento) {
        return "INSERT INTO `persona` (id_boleto,ci,id_viaje,costo,asiento)VALUES('"
                + id_boleto + "','"
                + ci + "','"
                + id_viaje + "','"
                + costo + "','"
                + asiento + "');";
    }

    public static String insertarBoletoViaje(int id_boleto, String ci, String id_viaje, String costo, String asiento) {
        return "INSERT INTO `persona` (id_boleto,ci,id_viaje,costo,asiento)VALUES('"
                + id_boleto + "','"
                + ci + "','"
                + id_viaje + "','"
                + costo + "','"
                + asiento + "');";
    }

    public static String mostrar5Viaje() {
        return "SELECT * FROM `viaje` ORDER BY id_viaje LIMIT 0,5;'";
    }

    public static String consultarTipoUsuario(String ci) {
        return "SELECT rango FROM usuario WHERE ci='" + ci + "';";
    }

    public static String consultarIdDestino(int id_destino, Date id_fecha) {
        return "SELECT id_viaje FROM viaje WHERE id_destino='" + id_destino + "' AND DATE(fecha)='" + formatDates.format(id_fecha) + "');";
    }
}
