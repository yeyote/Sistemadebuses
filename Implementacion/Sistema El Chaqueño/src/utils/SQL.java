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

    //////////////////////////CONSULTA DE DATOS////////////////////////////////
    public static String iniciarSecion(String user, String pass) {
        return "SELECT id_usu FROM `usuario` WHERE `user`='" + user + "' AND `pass`='" + sha1(pass) + "'";
    }

    ///////////////////////// VISTAS DE DATOS//////////////////////////////////
    public static String mostrarDatosUsuario(int id) {
        return "SELECT `titulo`.`titulo`,`persona`.*, `usuario`.`tipo`"
                + "FROM `usuario` INNER JOIN `persona` INNER JOIN `titulo` "
                + "ON `persona`.`ci` = `usuario`.`ci` AND `persona`.`id_titulo` = `titulo`.`id_titulo`"
                + "WHERE `id_user`='" + id + "';";
    }
}
