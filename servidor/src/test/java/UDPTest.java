import org.gamexis.cliente.Cliente;
import org.gamexis.servidor.Servidor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UDPTest {

    private Cliente client;

    @Before
    public void setUp() throws InterruptedException, IOException {
        // Iniciar el servidor antes de cada prueba
        Servidor servidor = new Servidor();
        servidor.start();
        Thread.sleep(1000);
        // Iniciar el cliente con la dirección del servidor
        client = new Cliente("26.204.82.158");
    }

    @Test
    public void cuandoSeEnviaElPaqueteRetornaIgual() throws InterruptedException {
        // Enviar mensaje al servidor
        client.enviarMensaje("puta");
        // Verificar si el mensaje recibido es igual al enviado
        assertEquals("puta", client.recibirMensaje());
        Thread.sleep(1000);
        // Enviar otro mensaje al servidor
        client.enviarMensaje("funciona");
        // Verificar que el nuevo mensaje recibido no sea igual al anterior
        assertNotEquals("puta", client.recibirMensaje());
    }

    @After
    public void tearDown() {
        // Enviar mensaje de cierre al servidor y cerrar el cliente
        System.out.println("Cerrar");
        client.enviarMensaje("cerrar");
        client.salir();
    }
}
