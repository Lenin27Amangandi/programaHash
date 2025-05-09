import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        // Solicitar una palabra al usuario
        System.out.print("Ingresa una palabra: ");
        String palabra = scanner.nextLine().toLowerCase();

        // Crear un arreglo de caracteres
        char[] caracteres = palabra.toCharArray();
        // Crear arreglos para valores ASCII y posiciones del alfabeto
        int[] valoresASCII = new int[caracteres.length];
        int[] posicionesAlfabeto = new int[caracteres.length];
        // Llenar los arreglos
        for (int i = 0; i < caracteres.length; i++) {
            valoresASCII[i] = obtenerValorASCII(caracteres[i]);
            posicionesAlfabeto[i] = obtenerPosicionAlfabeto(caracteres[i]);
        }
        // Imprimir resultados
        System.out.println("\nCaracteres:");
        for (char c : caracteres) {
            System.out.print(c + " ");
        }
        System.out.println("\n\nValores ASCII:");
        for (int ascii : valoresASCII) {
            System.out.print(ascii + " ");
        }
        System.out.println("\n\nPosiciones en el alfabeto:");
        for (int pos : posicionesAlfabeto) {
            if (pos != -1) {
                System.out.print(pos + " ");
            } else {
                System.out.print("NA "); // NA = No aplica (por ejemplo, símbolos)
            }
        }
    }

    // Función para obtener el valor ASCII de un carácter
    public static int obtenerValorASCII(char c) {
        return (int) c;
    }

    // Función para obtener la posición del carácter en el alfabeto inglés
    public static int obtenerPosicionAlfabeto(char c) {
        c = Character.toLowerCase(c); // Convertir a minúscula para uniformidad
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 1;
        } else {
            return -1; // No es una letra del alfabeto
        }
    }
}
