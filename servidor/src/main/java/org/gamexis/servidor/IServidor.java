package org.gamexis.servidor;

import java.io.IOException;

public interface IServidor {
    void cerrar() throws IOException;
    boolean estaCerrado();
}
