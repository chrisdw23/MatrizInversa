import java.io.*;
import java.util.*;

public class MatrizInversa {

    // Método para calcular la inversa de una matriz cuadrada
    public static double[][] inversa(double[][] matriz) {
        int n = matriz.length;
        double[][] identidad = new double[n][n];
        double[][] copia = new double[n][n];

        // Inicializar identidad y copia
        for (int i = 0; i < n; i++) {
            identidad[i][i] = 1;
            for (int j = 0; j < n; j++) {
                copia[i][j] = matriz[i][j];
            }
        }

        // Método de Gauss-Jordan
        for (int i = 0; i < n; i++) {
            double pivote = copia[i][i];
            if (pivote == 0) {
                throw new ArithmeticException("La matriz no es invertible");
            }

            for (int j = 0; j < n; j++) {
                copia[i][j] /= pivote;
                identidad[i][j] /= pivote;
            }

            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = copia[k][i];
                    for (int j = 0; j < n; j++) {
                        copia[k][j] -= factor * copia[i][j];
                        identidad[k][j] -= factor * identidad[i][j];
                    }
                }
            }
        }

        return identidad;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nombre del archivo de entrada: ");
            String archivoEntrada = sc.nextLine();

            File file = new File(archivoEntrada);
            Scanner lector = new Scanner(file);

            ArrayList<double[]> filas = new ArrayList<>();

            System.out.println("\n--- MATRIZ ORIGINAL ---");
            while (lector.hasNextLine()) {
                String[] partes = lector.nextLine().trim().split("\\s+");
                double[] fila = new double[partes.length];
                for (int i = 0; i < partes.length; i++) {
                    fila[i] = Double.parseDouble(partes[i]);
                }
                filas.add(fila);
                System.out.println(Arrays.toString(fila));
            }
            lector.close();

            double[][] matriz = filas.toArray(new double[0][]);
            double[][] inv = inversa(matriz);

            System.out.print("\nNombre del archivo de salida: ");
            String archivoSalida = sc.nextLine();

            PrintWriter writer = new PrintWriter(archivoSalida);

            System.out.println("\n--- MATRIZ INVERSA ---");
            for (double[] fila : inv) {
                for (double val : fila) {
                    System.out.printf("%8.4f ", val);
                    writer.printf("%8.4f ", val);
                }
                System.out.println();
                writer.println();
            }

            writer.close();
            System.out.println("\n✓ Archivo guardado correctamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
