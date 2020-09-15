package co.edu.eam.sd.examen1.serverside.workers;

import co.edu.eam.sd.examen1.serverside.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

//TODO: esta clase debe ser un hilo que reciba la peticion de calculo del servidor.
// e invoque el metodo executeCommand que recibe la orden y los parametros para ejecutarlos.
public class CalculateRequest implements Runnable{

  public static final int BLOCK_SIZE = 2;

  private Socket connection;
  private ExecutorService pool;

  public CalculateRequest(Socket connection, ExecutorService pool) {
    this.connection = connection;
    this.pool = pool;
  }

  /**
   * Function to perform the request. this method is called by the concurrent method.
   *
   * @param command
   * @param payload
   * @return
   * @throws Exception
   */
  public double executeCommand(String command, String payload) throws Exception {
    System.out.println("executeCommand %s, %s".format(command, payload));

    double numbers[] = Utils.stringToNumbers(payload);
    int blockCount = numbers.length / BLOCK_SIZE;
    int remaining = numbers.length % BLOCK_SIZE;
    blockCount = remaining > 0 ? blockCount + 1 : blockCount;

    //Lista donde quedaran los resultados futuros.
    //TODO: declaras la lista donde quedaran todos los resultados futuros.
    List<Future<Double>> results = new ArrayList<>();

    for (int i = 0; i < blockCount; i++) {
      double block[] = Utils.getBlock(numbers, i, BLOCK_SIZE);
      CalculatorUtil calculatorUtil = new CalculatorUtil(command, block);
      //TODO: agregar al pool la instancia del calculador y agregar el futuro al arreglo de futuros.
      Future<Double> future= pool.submit(calculatorUtil);
      results.add(future);
    }

    double nums[] = new double[results.size()];
    int i = 0;

    //TODO: obtener todos los resultados futuros y llenar el arreglo de nums con esos resultados.
    // tip: el Future retorna Double, se puede obtener el valor primitivo con .doubleValue()
    for (Future<Double> future : results) {
      nums[i] = future.get().doubleValue();// TODO obtener futuro y almacenar el resultado aca...
      System.out.println("result " + command + "=" + nums[i]);
      i++;
    }

    //Aqui se sumariza los calculos que se hicieron concurrentemente.
    return new CalculatorUtil(command, nums).execute();
  }
  
  //TODO: implementar el metodo concurrente
  //  este metodo debe implementar el protocolo para ejecutar el calculo
  //  1. recibir el comando (sum, max, min)
  //  2. recibir el payload: los numeros separados por comas.
  //  3. invocar el metodo de calculo executeCommand(command, payload)
  //  4. enviar el resultado por la salida de la conexion
  @Override
  public void run() {
	// TODO Auto-generated method stub
	try {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		OutputStream salida = connection.getOutputStream();
		
		String comando = entrada.readLine().toLowerCase();
		String payload = "";
		
		salida.write((executeCommand(comando, payload)+"").getBytes());
		salida.flush();
		salida.close();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
  }

}
