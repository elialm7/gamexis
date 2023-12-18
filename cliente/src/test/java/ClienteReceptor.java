import org.gamexis.cliente.Cliente;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class ClienteReceptor {
    private Cliente cliente;
    @Before
    public void setUp() throws IOException {
        cliente = new Cliente("26.204.82.158"); // Agregar tu ip públic o de radmin del servidor
        new Thread(cliente).start();
    }
    @Test
    public void recibirDelServidorEnBucle() throws IOException {
        cliente.enviarMensaje("Me conecté");
        while(true);
    }
}
