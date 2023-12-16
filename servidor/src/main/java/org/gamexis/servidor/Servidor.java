package org.gamexis.servidor;


import org.gamexis.servidor.protocolos.ServidorUDP;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Servidor extends Thread {

    private final int PUERTO_UDP = 8888;
    private final int PUERTO_TCP = 3100;

    private final ExecutorService servidoresUDP =  Executors.newFixedThreadPool(5);

    private final ServidorUDP UDP_PRINCIPAL;

    public Servidor( ) throws IOException {
        UDP_PRINCIPAL = new ServidorUDP(PUERTO_UDP,"224.0.0.1");
    }

    public void run() {
        servidoresUDP.submit(this.UDP_PRINCIPAL);
        while(!UDP_PRINCIPAL.isCerrado());
        System.out.println("Cerrar");
    }

}