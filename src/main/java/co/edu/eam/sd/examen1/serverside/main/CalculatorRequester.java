package co.edu.eam.sd.examen1.serverside.main;


import co.edu.eam.sd.examen1.serverside.utils.Utils;

import java.io.IOException;
import java.util.Map;

//TODO: implementar esta clase como un Hilo que retorna un resultado y ejecutar
public class CalculatorRequester {

  private double[] numbers;
  private Map<String, String> node;
  private String command;

  public CalculatorRequester(double[] numbers, Map<String, String> node, String command) {
    this.numbers = numbers;
    this.node = node;
    this.command = command;
  }

  //TODO: este metodo es el que se debe llamar para hacer el calculo concurrente.
  public double execute() throws IOException {
    System.out.println("execute %s".format(node.toString()));

    String ip = node.get("ip");
    int port = Integer.valueOf(node.get("port"));
    String name = node.get("name");

    String numbersAsString = Utils.StringifyArray(numbers);

    return sendCommandToWorker(ip, port, command, numbersAsString);
  }

  //TODO: enviar mensaje al nodo worker para que haga la suma alla siguiendo el protocolo.
  //  0. conectarse al nodo worker con la ip y el puerto que llega por parametro
  //  1. enviar el comando
  //  2. evniar el payload: los numeros separados por comas.
  //  3. recibir la respuesta
  private double sendCommandToWorker(String ip, int port, String command, String payload) throws IOException {
    return 0;
  }
  //TODO implementar metodo concurrente

}
