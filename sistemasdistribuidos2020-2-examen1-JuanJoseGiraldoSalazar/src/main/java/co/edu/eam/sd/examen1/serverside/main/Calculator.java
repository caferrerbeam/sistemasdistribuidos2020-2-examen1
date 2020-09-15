package co.edu.eam.sd.examen1.serverside.main;

import co.edu.eam.sd.examen1.serverside.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//TODO: clase que esperara las solicitudes de conexion de los nodos de calculo
// las solicitudes se esperan concurrentemente pero no se procesan concurrentemente
public class Calculator implements Runnable{
  public static final int BLOCK_SIZE = 1000;
  public static int PORT = 50000;

  private Map<String, Map<String, String>> nodesConfigurations = new HashMap<>();

  //TODO: crear el pool de hilos. 100 hilos maximo
  private ExecutorService pool = Executors.newFixedThreadPool(100);

  public double process(double[] numbers, String command) throws Exception {
    int blockCount = numbers.length / BLOCK_SIZE;
    int remaining = numbers.length % BLOCK_SIZE;
    blockCount = remaining > 0 ? blockCount + 1 : blockCount;

    //Lista donde quedaran los resultados futuros.
    //TODO: crear la lista donde quedaran todos los resultados futuros.
    List<Future<Double>> resultsNodes = new ArrayList<>();

    for (int i = 0; i < blockCount; i++) {
      double block[] = Utils.getBlock(numbers, i, BLOCK_SIZE);
      CalculatorRequester calculatorRequester = new CalculatorRequester(block, getNode(), command);
      //TODO: agendar el procesamiento del bloque y agregar al arreglo de futuros
      Future<Double> futuro = pool.submit(calculatorRequester);
      resultsNodes.add(futuro);

    }

    double nums[] = new double[resultsNodes.size()];
    int i = 0;

    //TODO: obtener todos los resultados futuros y llenar el arreglo de nums con esos resultados.
    // tip: el Future retorna Double, se puede obtener el valor primitivo con .doubleValue()
    for (Future<Double> future : resultsNodes) {
      // TODO: obtener futuro...
      nums[i] = future.get().doubleValue();
      i++;    }

    //Aqui se sumariza los calculos que se hicieron concurrentemente.
    return new CalculatorRequester(nums, getNode(), command).execute();
  }

  /**
   * Methdo to get a random node to process
   *
   * @return
   */
  public Map<String, String> getNode() {
    List<Map<String, String>> nodes = new ArrayList<>(nodesConfigurations.values());

    return nodes.get((int) (Math.random() * nodes.size()));
  }

  public void registerWorkerNode(String ip, int port, String nodeName) {
    System.out.println("registerWorkerNode" + "," + "," + ip + "," + "," + port + "," + nodeName);

    Map<String, String> config = new HashMap<String, String>() {{
      put("ip", ip);
      put("port", String.valueOf(port));
      put("name", String.valueOf(nodeName));
    }};

    nodesConfigurations.put(nodeName, config);
  }


  //TODO: en vista que no hay muchos nodos el metodo concurrente de esta clase aceptara las peticiones
  //  pero su procesamiento no sera concurrentemente.
  // cuando llega una solicitud de un nodo de calculo se hace lo siguiente
  //  implementar el protocolo de notificacion de nuevo nodo de calculo.
  //  2. recibr la cadena: acceptme
  //  3. recibir  ip de nodo
  //  4. recibir  puerto de nodo
  //  5. recibir myname de nodo
  //  6. desconectarse
  //  7. invocar metodo registerWorkerNode(String ip, int port, String nodeName)
  // EL METODO DEL HILOOOOOOOOOOO
  @Override
  public void run() {
	  // TODO Auto-generated method stub
	  try {
		ServerSocket serverSocket = new ServerSocket(PORT);
		Socket conexion = serverSocket.accept();
		BufferedReader entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		String comando = entrada.readLine().toLowerCase();
		String ip = "";
		int port = 0;
		String nodeName = "";
		serverSocket.close();
		registerWorkerNode(ip, port, nodeName);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
