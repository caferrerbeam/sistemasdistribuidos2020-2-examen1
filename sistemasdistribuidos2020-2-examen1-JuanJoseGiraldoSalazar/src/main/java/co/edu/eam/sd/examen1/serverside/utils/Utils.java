package co.edu.eam.sd.examen1.serverside.utils;

import java.util.Arrays;

public class Utils {

  public  static String StringifyArray(double[] numbers) {
    String acc = "";
    for (double number : numbers) {
      acc = acc += String.valueOf(number) + ",";
    }
    return acc;
  }

  public static double[] stringToNumbers(String numberAsString) {
    String numbersString[] = numberAsString.split(",");
    double numbers[] = new double[numbersString.length];

    for (int i = 0; i< numbersString.length; i++) {
      numbers[i] = Double.valueOf(numbersString[i]);
    }
    return numbers;
  }

  public static double[] getBlock(double[] array, int block, int blocksize) {
    int from = block*blocksize;
    int to = (block + 1) * blocksize;
    to = to >= array.length ? array.length : to;

    return Arrays.copyOfRange(array,from, to);
  }

}
