package org.gamexis;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Mensaje implements Serializable {
    @Serial
    private final Long serialID = 1L;
    private String mensaje;
    private String idReceptor;
    private String idEmisor;

    public Mensaje(String mensaje, String idReceptor, String idEmisor) {
        this.mensaje = mensaje;
        this.idReceptor = idReceptor;
        this.idEmisor = idEmisor;
    }

    public Long getSerialID() {
        return serialID;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getIdReceptor() {
        return idReceptor;
    }

    public String getIdEmisor() {
        return idEmisor;
    }
}
