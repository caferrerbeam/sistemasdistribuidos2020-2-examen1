package co.edu.eam.sd.examen1.serverside.workers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: la solicitudes de conexion de esta clase se deben implementar en un hilo y el procesamiento
//   de esas solicitudes se debe hacer concurrentemente usando el pool de hilos.
public class CalculatorNode {

  public static int SERVER_PORT = 50000;
  public static int POOL_SIZE = 100;

  //TODO: crear el pool de conexiones cuyo tamano sera POOL_SIZE
  private ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

  private int myPort;
  private String myName;
  private String ipServer;
  private int portServer;

  public CalculatorNode(int myPort, String myName, String ipServer, int portServer) throws Exception {
    this.myPort = myPort;
    this.myName = myName;
    this.ipServer = ipServer;
    this.portServer = portServer;

    notifyToCentralServer(ipServer, portServer, myName, myPort);
  }

  //TODO: implementar la recepcion de la solicitud de calculo
  // instanciando CalculateRequest y enviandole por parametro el socket y el pool


  //TODO: avisar al servidor central la presencia de este nodo.
  //  implementar el protocolo de notificacion al servidor.
  //  1. conectarse al servidor
  //  2. enviarle la cadena: acceptme
  //  3. enviar mi ip
  //  4. enviar mi puerto
  //  5. enviar myname
  //  6. desconectarse
  public void notifyToCentralServer(String ipServer, int portServer, String myName, int myPort) throws Exception {

  }
}
