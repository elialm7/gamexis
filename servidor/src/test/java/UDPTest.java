import org.gamexis.cliente.Cliente;
import org.gamexis.servidor.Servidor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UDPTest {

    Cliente client;

    @Before
    public void configuracion() throws IOException {
        new Servidor().start();
        client = new Cliente();
    }

    @Test
    public void cuandoSeEnviaElPaqueteRetornaIgual() throws Exception {
        client.enviarMensaje("puta");
        assertEquals("puta", client.recibirMensaje());
        client.enviarMensaje("funciona");
        assertNotEquals("puta", client.recibirMensaje());
    }

    @After
    public void tearDown() throws Exception {
        client.enviarMensaje("cerrar");
        client.close();
    }
}