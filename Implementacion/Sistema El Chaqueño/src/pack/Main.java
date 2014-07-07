package pack;

import com.birosoft.liquid.LiquidLookAndFeel;
import gui.AdminSistema;
import gui.Administracion;
import gui.Encargado;
import gui.Login;
import gui.VentaPasajes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import utils.Conexion;
import utils.ManagerArchivo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author [MABH - LCOS - ELOS - YPC]
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        style();
        login = new Login();
    }

    public static void style() {
        try {
            javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            LiquidLookAndFeel.setLiquidDecorations(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void iniciarSecion(int rango) {
        if (rango == Login.ADMINISTRADOR) {
            new AdminSistema();
        }
        if (rango == Login.ENCARGADO) {
            new Encargado();
        }
        if (rango == Login.GERENTE) {
            new Administracion();
        }
        login.dispose();
    }

    public static Login login;
    public static String db;
    public static String host;
    public static String user;
    public static String password;
    public static ManagerArchivo managerArchivo = new ManagerArchivo();
    public static Conexion con = Conexion.getInstance();
}
