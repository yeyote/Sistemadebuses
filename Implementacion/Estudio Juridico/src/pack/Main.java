package pack;

import com.birosoft.liquid.LiquidLookAndFeel;
import gui.Login;
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
 * @author [GVM - MABH - LCOS]
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        style();
        new Login();
    }
    
    public static void style() {
        try {
            javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            LiquidLookAndFeel.setLiquidDecorations(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void iniciarSecion(int id_user){
        
    }
    
    public static String db;
    public static String host;
    public static String user;
    public static String password;
    public static ManagerArchivo managerArchivo = new ManagerArchivo();
    public static Conexion con = new Conexion(user,password,host,db);
}
