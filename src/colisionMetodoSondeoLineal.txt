import java.util.Scanner;
import java.text.Normalizer;

public class HashTablaFija {
    static final int TAM_TABLA = 10;
    static String[] tabla = new String[TAM_TABLA];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Integrantes: Lenin Amangandi, Karina Cardenas, Frank Quinatoa");

        int opcion;
        do {
            System.out.print("\n--- MENU PRINCIPAL ---\n" +
                            "1. Insertar palabra o frase\n" +
                            "2. Salir\n" +
                            "Selecciona una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingresa una palabra o frase: ");
                    String palabra = quitarTildes(scanner.nextLine());
                    insertarEnTablaConMenu(palabra, scanner);
                    mostrarTablaHashHorizontal();
                    break;

                case 2:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 2);

        scanner.close();
    }

    public static void insertarEnTablaConMenu(String palabra, Scanner scanner) {
        char[] caracteres = palabra.toCharArray();
        int[] valoresASCII = new int[caracteres.length];
        int[] posicionesAlfabeto = new int[caracteres.length];

        for (int i = 0; i < caracteres.length; i++) {
            valoresASCII[i] = (int) caracteres[i];
            posicionesAlfabeto[i] = obtenerPosicionAlfabeto(caracteres[i]);
        }

        System.out.print(
            "\n--- METODOS DE HASH DISPONIBLES ---\n" +
            "1. Suma de posiciones en el alfabeto\n" +
            "2. Suma de valores ASCII\n" +
            "3. Tamano de la frase\n" +
            "4. Posicion * (n ^ posicion)\n" +
            "Selecciona el metodo de hash: "
        );

        int metodo = scanner.nextInt();
        scanner.nextLine();
        long hash = 0;
        switch (metodo) {
            case 1:
                for (int pos : posicionesAlfabeto) {
                    if (pos != -1) hash += pos;
                }
                break;
            case 2:
                for (int ascii : valoresASCII) {
                    hash += ascii;
                }
                break;
            case 3:
                hash = palabra.length();
                break;
            case 4:
                int n;
                do {
                    System.out.print("Ingresa un numero entre 1 y 10: ");
                    n = scanner.nextInt();
                } while (n < 1 || n > 10);
                for (int i = 0; i < caracteres.length; i++) {
                    int posicion = i + 1;
                    hash += posicion * Math.pow(n, posicion);
                }
                break;
            default:
                System.out.println("Metodo invalido. Se usara metodo 2 (ASCII) por defecto.");
                for (int ascii : valoresASCII) {
                    hash += ascii;
                }
        }

        int posicion = (int) (hash % TAM_TABLA);
        System.out.println("HASH: " + hash + " Posicion en la tabla: " + posicion);
        insertarLineal(palabra, posicion);
    }

    public static void insertarLineal(String palabra, int posicion) {
        if (tabla[posicion] == null) {
            tabla[posicion] = palabra;
            System.out.println("Insertado en la posicion " + posicion);
        } else {
            System.out.println("Colision en la posicion " + posicion + " con: " + tabla[posicion]);
            for (int i = 1; i < TAM_TABLA; i++) {
                int nueva = (posicion + i) % TAM_TABLA;
                if (tabla[nueva] == null) {
                    tabla[nueva] = palabra;
                    System.out.println("Insertado con sondeo en posicion " + nueva);
                    return;
                }
            }
            System.out.println("Tabla llena. No se insertar el valor.");
        }
    }

    public static void mostrarTablaHashHorizontal() {
        System.out.println("\n--- TABLA ---");

        for (int i = 0; i < TAM_TABLA; i++) {
            System.out.printf("[%2d] ", i);
        }
        System.out.println();

        for (int i = 0; i < TAM_TABLA; i++) {
            if (tabla[i] != null)
                System.out.printf(" %-4s", tabla[i].length() > 4 ? tabla[i].substring(0, 4) : tabla[i]);
            else
                System.out.printf(" %-4s", "-");
        }
        System.out.println("\n-------------------------------------------");
    }

    public static int obtenerPosicionAlfabeto(char c) {
        if (c == ' ') return 69;
        if (Character.isLowerCase(c)) return c - 'a' + 1;
        if (Character.isUpperCase(c)) return c - 'A' + 75;
        return -1;
    }

    public static String quitarTildes(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                        .replace('ñ', 'n')
                        .replace('Ñ', 'N');
    }
}
