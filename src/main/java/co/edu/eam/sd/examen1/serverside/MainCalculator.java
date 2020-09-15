package co.edu.eam.sd.examen1.serverside;

import co.edu.eam.sd.examen1.serverside.main.Calculator;
import co.edu.eam.sd.examen1.serverside.workers.CalculatorNode;

import java.util.Arrays;

public class MainCalculator {

  public static double[] getRandomNumbers() {
    double nums[] = new double[10000];
    for (int i = 0; i < nums.length; i++) {
      nums[i] = i;
    }
    return nums;
  }

  public static void main(String[] args) throws Exception {
    Calculator calculator = new Calculator();

    new Thread(calculator).start();
    CalculatorNode node1 = new CalculatorNode(45001, "node1", "localhost", 50000);
    CalculatorNode node2 = new CalculatorNode(45002, "node2", "localhost", 50000);
    CalculatorNode node3 = new CalculatorNode(45003, "node3", "localhost", 50000);

    new Thread(node1).start();
    new Thread(node2).start();
    new Thread(node3).start();

    testMax(calculator);
    testMin(calculator);
    testSum(calculator);
  }

  public static void testSum(Calculator calculator) throws Exception {
    double nums[] = getRandomNumbers();

    Double result = calculator.process(nums, "sum");
    System.out.println("sum=" + result);
    if (!result.equals(Arrays.stream(nums).sum())) {
      throw new RuntimeException("SUM is wrong");
    } else {
      System.out.println("SUMMA IS OK!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
  }

  public static void testMin(Calculator calculator) throws Exception {
    double nums[] = getRandomNumbers();

    Double result = calculator.process(nums, "min");
    System.out.println("min=" + result);

    if (!result.equals(Arrays.stream(nums).min().getAsDouble())) {
      throw new RuntimeException("min is wrong");
    } else {
      System.out.println("MINNN IS OK!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
  }

  public static void testMax(Calculator calculator) throws Exception {
    double nums[] = getRandomNumbers();

    Double result = calculator.process(nums, "max");
    System.out.println("max=" + result);

    if (!result.equals(Arrays.stream(nums).max().getAsDouble())) {
      throw new RuntimeException("MAX is wrong");
    } else {
      System.out.println("MAX IS OK!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
  }

}
