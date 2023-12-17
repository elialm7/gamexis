import org.gamexis.cliente.Cliente;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ClienteEmisor {
    private Cliente cliente;
    @Before
    public void setUp() throws IOException {
        cliente = new Cliente("26.204.82.158"); // Agregar tu ip públic o de radmin
        new Thread(cliente).start();
    }
    @Test
    public void comunicarAlServidor() throws IOException, InterruptedException {
        while(true){
            String mensaje = "el mensaje";
            cliente.enviarMensaje("Soy coito "+mensaje);
            Thread.sleep(2500);
        }
    }

}
