/**
 * Modifica la práctica 2 para que, en lugar de ordenar las puntuaciones, devuelva aquellas puntuaciones 
 * que se encuentren en un rango de valores que se pasará como parámetro. 
 * Se mantendrá la metodología fork / join, utilizando como caso base un tamaño igual a 100.
 * Se deberá subir un fichero ZIP con todo el código de la práctica modificada.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
	private static Pair[] list;
	private static String INPUT = "contestants.txt";
	private static String OUTPUT = "output.txt";

	public static void main(String[] args) {
		long total = System.nanoTime();
		long insertar = System.nanoTime();
		
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(INPUT));
			list = new Pair[Integer.parseInt(in.readLine())];

			for (int i = 0; i < list.length; i++) {
				String line = in.readLine();
				String name = line.substring(0, line.indexOf(':')+1);
				int score = Integer.parseInt(line.substring(line.indexOf(':')+2));
				list[i] = (new Pair(name, score));
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("NO SE ENCONTRÓ EL ARCHIVO ESPECIFICADO, SALIENDO...");
			System.exit(0);
		}
		catch(IOException e){
			System.out.println("ERROR AL LEER " + OUTPUT + ", SALIENDO...");
			System.exit(0);
		}
		
		System.out.println("INSERTAR EN ARRAY: " + String.format("%f [msec]",(System.nanoTime() - insertar) / 1000000.0));
		long ordenar = System.nanoTime();

		// OBTENEMOS EL RESULTADO
		ForkJoinPool pool = new ForkJoinPool();
		List<Pair> listRes = pool.invoke(new MergeSort(list, 0, list.length, 5000000, 7000000));
		
		System.out.println("ORDENACIÓN: " + String.format("%f [msec]",(System.nanoTime() - ordenar) / 1000000.0));
		long escribir = System.nanoTime();

		// ESCRIBIMOS EL RESULTADO EN UN TXT AEDBB
		try {
			FileWriter writer = new FileWriter(OUTPUT, false);
			writer.write(listRes.size()+"\n");
			for (int i = 0; i < listRes.size(); i++) {
				writer.write(listRes.get(i).getName() + " " +listRes.get(i).getScore()+"\n");
			}

			writer.close();
		} catch (IOException e) {
			System.out.println("ERROR AL ESCRIBIR DATOS EN "+OUTPUT+", SALIENDO...");
			System.exit(0);
		}
		
		System.out.println("ESCRITURA: " + String.format("%f [msec]",(System.nanoTime() - escribir) / 1000000.0));
		System.out.println("TOTAL: " + String.format("%f [msec]",(System.nanoTime() - total) / 1000000.0));
	}
}
