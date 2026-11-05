import controladores.RegistroTemperaturaControlador;
import vistas.RegistroTemperaturaVista;

public class App {
    public static void main(String[] args) throws Exception {
        var vista = new RegistroTemperaturaVista();
        new RegistroTemperaturaControlador(vista);
        vista.setVisible(true);
    }
}
