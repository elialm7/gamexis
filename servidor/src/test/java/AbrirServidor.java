import org.gamexis.servidor.Servidor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AbrirServidor {
    private Servidor servidor;

    @Before
    public void setUp() throws IOException {
        servidor = new Servidor();

    }
    @Test
    public void iniciarServidor(){
        servidor.start();
    }
    @After
    public void cerrar() throws InterruptedException {
        servidor.join();
    }

}
