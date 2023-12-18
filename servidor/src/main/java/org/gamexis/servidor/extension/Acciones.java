package org.gamexis.servidor.extension;

import java.io.IOException;

public interface Acciones {
    void cerrar() throws IOException;
    boolean estaCerrado();
}
