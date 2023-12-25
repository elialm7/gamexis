
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbrirServidor {
    private Servidor servidor;

    @Before
    public void setUp() {
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
