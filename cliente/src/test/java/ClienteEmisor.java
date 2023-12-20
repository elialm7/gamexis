import org.gamexis.cliente.Cliente;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ClienteEmisor {
    private Cliente cliente;
    @Before
    public void setUp() throws IOException {
        cliente = new Cliente("26.204.82.158","234.0.1.1");
        new Thread(cliente).start();
    }
    @Test
    public void comunicarAlServidor() throws InterruptedException {
        while(true){
            String mensaje = "el mensaje";
            cliente.enviarMensaje(mensaje.getBytes(StandardCharsets.UTF_8));
            Thread.sleep(2500);
        }
    }

}
