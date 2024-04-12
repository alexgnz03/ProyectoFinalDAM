package engine.combate.peleitas;

import engine.minijuego.MinijuegoController;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class GenerateMonstruoReport {

        public static void generateReport(Monstruo monstruo) {
            try {
                // Cargar el diseño del informe desde un archivo .jrxml
                InputStream jrxmlInputStream = GenerateMonstruoReport.class.getClassLoader().getResourceAsStream("reports/Monstruo.jrxml");

                // Compilar el diseño del informe a un objeto JasperReport
                JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);

                // Crear un mapa para almacenar los parámetros del informe
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("name", monstruo.getName());
                parameters.put("money", monstruo.getMoney());
                parameters.put("vida", monstruo.getVida());
                parameters.put("vida_maxima", monstruo.getVida_maxima());
                parameters.put("ataque", monstruo.getAtaque());
                parameters.put("defensa", monstruo.getDefensa());
                parameters.put("ataque_magico", monstruo.getAtaque_magico());
                parameters.put("defensa_magica", monstruo.getDefensa_magica());
                parameters.put("velocidad", monstruo.getVelocidad());
                // Obtener la fecha actual
                Date fechaGeneracion = new Date();

                // Agregar la fecha de generación al mapa de parámetros
                parameters.put("fecha_generacion", fechaGeneracion);

                // Llenar y generar el informe con parámetros y utilizando un JREmptyDataSource para evitar ambigüedades
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

                // Definir la ruta del archivo PDF en el escritorio
                String userHome = System.getProperty("user.home");
                String pathToFile = userHome + "/Desktop/Monstruo.pdf";

                // Exportar el informe a un archivo PDF
                JasperExportManager.exportReportToPdfFile(jasperPrint, pathToFile);

            } catch (JRException e) {
                e.printStackTrace();
            }
        }
    }