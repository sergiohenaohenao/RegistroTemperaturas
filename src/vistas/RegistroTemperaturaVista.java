
package vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;

import datechooser.beans.DateChooserCombo;
import org.jfree.chart.ChartPanel;

public class RegistroTemperaturaVista extends JFrame {

        private DateChooserCombo dccDesde, dccHasta, dccFechaConsulta;

        private JTabbedPane tpTemperaturas;

        private JPanel pnlGraficaPromedios;
        private JPanel pnlTemperaturasExtremas;

        private JButton btnGraficarPromedios;
        private JButton btnConsultarTemperaturasExtremas;

        private JLabel lblCiudadMasCalurosa;
        private JLabel lblTemperaturaMasAlta;
        private JLabel lblCiudadMenosCalurosa;
        private JLabel lblTemperaturaMasBaja;

        public RegistroTemperaturaVista() {

                setTitle("Registro de Temperaturas");
                setSize(800, 450);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                // Barra de herramientas
                JToolBar tb = new JToolBar();

                btnGraficarPromedios = new JButton(
                                new ImageIcon(getClass().getResource("/iconos/Grafica.png")));
                btnGraficarPromedios.setToolTipText("Graficar promedios de temperatura");
                tb.add(btnGraficarPromedios);

                btnConsultarTemperaturasExtremas = new JButton(
                                new ImageIcon(getClass().getResource("/iconos/Datos.png")));
                btnConsultarTemperaturasExtremas.setToolTipText(
                                "Consultar temperaturas extremas");
                tb.add(btnConsultarTemperaturasExtremas);

                // Contenedor principal con BoxLayout vertical
                JPanel pnlRegistro = new JPanel();
                pnlRegistro.setLayout(
                                new BoxLayout(pnlRegistro, BoxLayout.Y_AXIS));

                // Panel para seleccionar las fechas
                JPanel pnlDatosProceso = new JPanel();
                pnlDatosProceso.setPreferredSize(new Dimension(800, 50));
                pnlDatosProceso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                pnlDatosProceso.setLayout(null);

                JLabel lblDesde = new JLabel("Desde:");
                lblDesde.setBounds(10, 10, 50, 25);
                pnlDatosProceso.add(lblDesde);

                dccDesde = new DateChooserCombo();
                dccDesde.setBounds(60, 10, 110, 25);
                pnlDatosProceso.add(dccDesde);

                JLabel lblHasta = new JLabel("Hasta:");
                lblHasta.setBounds(185, 10, 45, 25);
                pnlDatosProceso.add(lblHasta);

                dccHasta = new DateChooserCombo();
                dccHasta.setBounds(230, 10, 110, 25);
                pnlDatosProceso.add(dccHasta);

                JLabel lblFechaConsulta = new JLabel("Fecha de consulta:");
                lblFechaConsulta.setBounds(280, 10, 150, 25);
                pnlDatosProceso.add(lblFechaConsulta);

                dccFechaConsulta = new DateChooserCombo();
                dccFechaConsulta.setBounds(405, 10, 110, 25);
                pnlDatosProceso.add(dccFechaConsulta);

                lblFechaConsulta.setVisible(false);
                dccFechaConsulta.setVisible(false);

                pnlDatosProceso.revalidate();
                pnlDatosProceso.repaint();

                // Al pulsar Consultar temperaturas extremas
                btnConsultarTemperaturasExtremas.addActionListener(evento -> {

                lblDesde.setVisible(false);
                dccDesde.setVisible(false);

                lblHasta.setVisible(false);
                dccHasta.setVisible(false);

                lblFechaConsulta.setVisible(true);
                dccFechaConsulta.setVisible(true);

                pnlDatosProceso.revalidate();
                pnlDatosProceso.repaint();
});

                // Panel de la gráfica
                pnlGraficaPromedios = new JPanel();
                JScrollPane spGrafica = new JScrollPane(pnlGraficaPromedios);

                // Panel de temperaturas extremas
                pnlTemperaturasExtremas = new JPanel();
                pnlTemperaturasExtremas.setLayout(new GridLayout(2, 2, 10, 20));

                lblCiudadMasCalurosa = new JLabel("Ciudad más calurosa:");
                lblTemperaturaMasAlta = new JLabel("Temperatura máxima:");

                lblCiudadMenosCalurosa = new JLabel("Ciudad menos calurosa:");
                lblTemperaturaMasBaja = new JLabel("Temperatura mínima:");

                pnlTemperaturasExtremas.add(lblCiudadMasCalurosa);
                pnlTemperaturasExtremas.add(lblTemperaturaMasAlta);
                pnlTemperaturasExtremas.add(lblCiudadMenosCalurosa);
                pnlTemperaturasExtremas.add(lblTemperaturaMasBaja);

                // Pestañas
                tpTemperaturas = new JTabbedPane();

                tpTemperaturas.addTab("Promedios por ciudad", spGrafica);
                tpTemperaturas.addTab("Temperaturas extremas",
                                pnlTemperaturasExtremas);

                // Agregar componentes al contenedor principal
                pnlRegistro.add(pnlDatosProceso);
                pnlRegistro.add(tpTemperaturas);

                getContentPane().add(tb, BorderLayout.NORTH);
                getContentPane().add(pnlRegistro, BorderLayout.CENTER);
        }

        // Getters de fechas

        public LocalDate getFechaDesde() {
                return dccDesde.getSelectedDate().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDate();
        }

        public LocalDate getFechaHasta() {
                return dccHasta.getSelectedDate().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDate();
        }

        public LocalDate getFechaConsulta() {
                return dccFechaConsulta.getSelectedDate().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDate();
        }

        // Registro de eventos

        public void setGraficarPromediosClick(ActionListener evento) {
                btnGraficarPromedios.addActionListener(evento);
        }

        public void setConsultarTemperaturasExtremasClick(ActionListener evento) {
                btnConsultarTemperaturasExtremas.addActionListener(evento);
        }

        // Métodos públicos para actualizar la interfaz

        public void activarPestaña(int posicion) {
                if (posicion >= 0 && posicion < tpTemperaturas.getTabCount()) {
                        tpTemperaturas.setSelectedIndex(posicion);
                }
        }

        public void graficar(ChartPanel pnlGraficador) {
                pnlGraficaPromedios.removeAll();
                pnlGraficaPromedios.setLayout(new BorderLayout());
                pnlGraficaPromedios.add(pnlGraficador, BorderLayout.CENTER);
                pnlGraficaPromedios.revalidate();
                pnlGraficaPromedios.repaint();
        }

        public void mostrarTemperaturasExtremas(
                        String ciudadMasCalurosa,
                        double temperaturaMaxima,
                        String ciudadMenosCalurosa,
                        double temperaturaMinima) {

                lblCiudadMasCalurosa.setText(
                                "Ciudad más calurosa: " + ciudadMasCalurosa);

                lblTemperaturaMasAlta.setText(
                                String.format("Temperatura máxima: %.2f °C", temperaturaMaxima));

                lblCiudadMenosCalurosa.setText(
                                "Ciudad menos calurosa: " + ciudadMenosCalurosa);

                lblTemperaturaMasBaja.setText(
                                String.format("Temperatura mínima: %.2f °C", temperaturaMinima));

                pnlTemperaturasExtremas.revalidate();
                pnlTemperaturasExtremas.repaint();
        }
}
