package co.edu.eam.sd.examen1.serverside.main;


import co.edu.eam.sd.examen1.serverside.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.Callable;

//TODO: implementar esta clase como un Hilo que retorna un resultado y ejecutar
public class CalculatorRequester implements Callable<Double>{

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
  //  3. recibir la respuesta (resutlado del calculo)
  //  4. retornar dicho resultado
  private double sendCommandToWorker(String ip, int port, String command, String payload) throws IOException {
	  Socket conexion = new Socket(ip, port);
	  BufferedReader entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
	  OutputStream salida = conexion.getOutputStream();
	  salida.write(command.getBytes());
	  salida.write(payload.getBytes());
	  String resultado = entrada.readLine().toLowerCase();
	  salida.flush();
	  salida.close();
	  conexion.close();
	  
	  return Double.parseDouble(resultado);
  }
  
  
  //TODO implementar metodo concurrente cuyo trabajo es invocar a execute solamente.
@Override
public Double call() throws Exception {
	// TODO Auto-generated method stub
	return execute();
}

}
