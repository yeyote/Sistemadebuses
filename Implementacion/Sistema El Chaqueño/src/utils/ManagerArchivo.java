/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import pack.Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author [GVM - MABH - LCOS]
 */
public class ManagerArchivo {

    public ManagerArchivo() {
        try {
            escribirLog("[" + new Date() + "] INICIO DEL PROGRAMA");
            FileReader fr = null;
            BufferedReader br = null;
            ArrayList<String> lineas = new ArrayList<String>();
            fr = new FileReader(new File("estudio_juridico.conf"));
            br = new BufferedReader(fr);
            String temp = "";
            while ((temp = br.readLine()) != null) {
                char[] cad = temp.toCharArray();
                int i = 0;
                boolean flag = true;
                String acu = "";
                while (flag && i < temp.length()) {
                    if (cad[i] != '#') {
                        acu += cad[i];
                    } else {
                        flag = false;
                    }
                    i++;
                }
                if (acu.length() > 5) {
                    lineas.add(acu);
                }
            }
            this.procesarCadena(lineas);
            if (null != fr) {
                fr.close();
            }
        } catch (Exception e) {
            escribirLog("[" + new Date() + "] ERROR al abrir Archivo de Configuracion (estudio_juridico.conf)" + e.getMessage());
            escribirLog("[" + new Date() + "] Programa Finalizado -> Revise archivo: \"estudio_juridico.conf\"");
        } finally {
            try {
            } catch (Exception e2) {
                escribirLog("[" + new Date() + "] ERROR al cerrar archivo de configuracion " + e2.getMessage());
            }
        }
    }

    private void procesarCadena(ArrayList<String> lista) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < lista.size(); i++) {
            String linea = lista.get(i);
            String etiqueta = "";
            String valor = "";
            char[] c = linea.toCharArray();
            int k = 0;
            boolean e = true;
            boolean v = false;
            while (k < c.length) {
                if (e) {
                    if (Character.isAlphabetic(c[k])) {
                        etiqueta += c[k];
                    }
                    if (etiqueta.toLowerCase().equals("user")
                            || etiqueta.toLowerCase().equals("password")
                            || etiqueta.toLowerCase().equals("host")
                            || etiqueta.toLowerCase().equals("db")) {
                        e = false;
                    }
                } else {
                    if (v) {
                        if (c[k] != '"') {
                            valor += c[k];
                        }
                    } else {
                        if (c[k] == '"') {
                            v = !v;
                        }
                    }

                }
                k++;
            }
            try {                
                if (map.containsKey(etiqueta.toString().toLowerCase())) {
                    escribirLog("[" + new Date() + "] ADVERTENCIA: El sistema se inicio con la etiqueta \"" + etiqueta + "\" duplicada: Revise ");
                } else {
                    map.put(etiqueta.toLowerCase(), valor);
                }
            } catch (Exception u) {
                escribirLog("[" + new Date() + "] ERROR->" + u.getMessage());
            }
        }
        try {
            Main.user = map.get("user").toString();
            Main.host = map.get("host").toString();
            Main.db = map.get("db").toString();
            Main.password = map.get("password").toString();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR al leer archivo de configuracion: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void escribirLog(String s) {
        BufferedWriter out = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            File file = new File("estudio_juridico.log");
            if (!file.exists()) {
                if (file.createNewFile()) {
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF8"));
                    out.write("[" + new Date() + "] Creacion Log de Programa \"estudio_juridico.log\"");
                    out.newLine();
                    out.close();
                    file = new File("estudio_juridico.log");
                } else {
                    System.out.println("No ha podido ser creado el fichero");
                }
            }
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF8"));
            out.write(s);
            out.newLine();
            out.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger
                    .getLogger(ManagerArchivo.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ManagerArchivo.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManagerArchivo.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();

            } catch (IOException ex) {
                Logger.getLogger(ManagerArchivo.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
