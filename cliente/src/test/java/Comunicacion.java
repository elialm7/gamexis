import org.gamexis.cliente.Cliente;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Comunicacion {
    private Cliente cliente;
    @Before
    public void setUp() {
        cliente = new Cliente("26.204.82.158"); // Agregar tu ip públic o de radmin
    }
    @Test
    public void comunicarAlServidor() {
        String mensaje = "cerrar";
        cliente.enviarMensaje(mensaje);
        assertEquals(mensaje,cliente.recibirMensaje());
    }
    /*@Before
    public void salir() {
        // Para forzar cerrar el servidor.
        cliente.enviarMensaje("cerrar");
        // Para salir del canal
        cliente.salir();
    }*/
}
