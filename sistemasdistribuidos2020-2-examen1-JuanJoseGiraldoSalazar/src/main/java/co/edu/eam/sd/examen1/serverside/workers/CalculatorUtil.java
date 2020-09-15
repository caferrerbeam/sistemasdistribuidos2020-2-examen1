package co.edu.eam.sd.examen1.serverside.workers;

import java.util.Arrays;
import java.util.concurrent.Callable;

// TODO: convertir esta clase en un hilo que retorna resultados
public class CalculatorUtil implements Callable<Double>{

  private String command;
  private double numbers[];

  public CalculatorUtil() {
  }

  public CalculatorUtil(String command, double[] numbers) {
    this.command = command;
    this.numbers = numbers;
  }

  private double sum(double[] numbers) {
    return Arrays.stream(numbers).sum();
  }

  private double max(double[] numbers) {
    return Arrays.stream(numbers).max().getAsDouble();
  }

  private double min(double[] numbers) {
    return Arrays.stream(numbers).min().getAsDouble();
  }

  //TODO: este metodo es el que se debe llamar para hacer el calculo concurrente.
  public double execute(){
    System.out.println("Executing "+ command + "with numbers" + Arrays.toString(numbers));

    switch (command){
      case "sum": return sum(numbers);
      case "max": return max(numbers);
      case "min": return min(numbers);
    }
    throw new RuntimeException("Operation not implemented");
  }

  @Override
  public Double call() throws Exception {
	  // TODO Auto-generated method stub
	  return execute();
  }
  
}
