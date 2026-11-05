
package servicios;

import modelos.RegistroTemperatura;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegistroTemperaturaServicio {

    public static List<RegistroTemperatura> getDatos(String nombreArchivo) {

        var formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            Stream<String> lineasArchivo = Files.lines(Paths.get(nombreArchivo));

            return lineasArchivo.skip(1)
                    .map(linea -> linea.split(","))
                    .map(datos -> new RegistroTemperatura(
                            datos[0],
                            LocalDate.parse(datos[1], formatoFecha),
                            Double.parseDouble(datos[2])))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static List<RegistroTemperatura> filtrar(List<RegistroTemperatura> registros, LocalDate desde,
            LocalDate hasta) {

        return registros.stream()
                .filter(registro -> !registro.getFecha().isBefore(desde) &&
                        !registro.getFecha().isAfter(hasta))
                .collect(Collectors.toList());
    }

    public static Map<String, Double> getPromediosPorCiudad(List<RegistroTemperatura> registrosFiltrados) {

        return registrosFiltrados.stream()
                .collect(Collectors.groupingBy(
                        RegistroTemperatura::getCiudad,
                        Collectors.averagingDouble(
                                RegistroTemperatura::getTemperatura)));
    }
}
