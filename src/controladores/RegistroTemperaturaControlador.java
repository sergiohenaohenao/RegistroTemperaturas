
package controladores;

import modelos.RegistroTemperatura;
import servicios.RegistroTemperaturaServicio;
import vistas.RegistroTemperaturaVista;

import java.awt.Dimension;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;

public class RegistroTemperaturaControlador {

    private final RegistroTemperaturaVista vista;
    private List<RegistroTemperatura> registrosTemperatura;

    public RegistroTemperaturaControlador(RegistroTemperaturaVista vista) {
        this.vista = vista;

        this.vista.setGraficarPromediosClick(evento -> graficarPromediosTemperaturaPorCiudad());
        this.vista.setConsultarTemperaturasExtremasClick(evento -> consultarTemperaturasExtremas());
        cargarDatos();
    }

    private void cargarDatos() {

        String nombreArchivo = System.getProperty("user.dir") + "/src/datos/Temperaturas.csv";
        registrosTemperatura = RegistroTemperaturaServicio.getDatos(nombreArchivo);

        System.out.println(registrosTemperatura.size());
    }

    private void graficarPromediosTemperaturaPorCiudad() {

        LocalDate desde = vista.getFechaDesde();
        LocalDate hasta = vista.getFechaHasta();

        List<RegistroTemperatura> registrosFiltrados = RegistroTemperaturaServicio.filtrar(
                registrosTemperatura, desde, hasta);

        Map<String, Double> promedios = RegistroTemperaturaServicio.getPromediosPorCiudad(
                registrosFiltrados);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (var dato : promedios.entrySet()) {
            var ciudad = dato.getKey();
            var promedio = dato.getValue();
            dataset.addValue(promedio, "Temperatura promedio por ciudad", ciudad);
        }

        JFreeChart graficador = ChartFactory.createBarChart(
                "Promedio de temperaturas por ciudad",
                "Ciudad",
                "Temperatura (°C)",
                dataset);

        ChartPanel pnlGraficador = new ChartPanel(graficador);
        pnlGraficador.setPreferredSize(new Dimension(700, 350));
        vista.graficar(pnlGraficador);

        vista.activarPestaña(0);

    }
    

    private void consultarTemperaturasExtremas() {

    }
}
