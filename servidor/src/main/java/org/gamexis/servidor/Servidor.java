package org.gamexis.servidor;


import org.gamexis.servidor.protocolo.ServidorUDP;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor extends Thread {

    public static final int PUERTO_UDP = 8888;
    public static final int PUERTO_TCP = 3100;

    private final ExecutorService servidoresUDP =  Executors.newFixedThreadPool(5);

    private final ServidorUDP UDP_PRINCIPAL;

    public Servidor( ) throws IOException {
        //Uso esa dirección porque es la primera entre el rango de direcciones multicast
        UDP_PRINCIPAL = new ServidorUDP("234.0.1.1");
    }

    public void run() {
        servidoresUDP.submit(this.UDP_PRINCIPAL);
        while(!UDP_PRINCIPAL.estaCerrado());
        System.out.println("Cerrar");
    }

}