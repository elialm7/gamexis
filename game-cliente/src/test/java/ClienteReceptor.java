import org.gamexis.cliente.Cliente;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class ClienteReceptor {
    private Cliente cliente;
    @Before
    public void setUp() throws IOException {
        cliente = new Cliente("26.204.82.158","234.0.1.1");
        new Thread(cliente).start();
    }
    @Test
    public void recibirDelServidorEnBucle() {
        cliente.enviarMensaje("Me conecté".getBytes(StandardCharsets.UTF_8));
        while(true);
    }
}
