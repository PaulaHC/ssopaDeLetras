/*AUTHORS: HIGUERA CONSUEGRA, PAULA
           ARNAEZ PEREZ,      BORJA
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ArnaezPerezBorja_HigueraConsuegraPaula {
    public static String[] CleanNull(String[] st) {
        int size = 0;
        for (int i = 0; i < st.length; i++) {
            if (st[i] != null) size++;
        }
        String[] cleaned = new String[size];
        int j = 0;
        for (int i = 0; i < st.length; i++) {
            if (st[i] != null) {
                cleaned[j] = st[i];
                j++;
            }
        }
        return cleaned;
    }

    public static char[][] newMatrix(int rows, int cols, String[] Dict) {
        char[][] matrix = new char[rows][cols];
        final String[] Dict0 = new String[Dict.length];
        for (int i = 0; i < Dict.length; i++) {
            Dict0[i] = Dict[0];
        }
        Fill(matrix);
        Dict = CleanNull(Dict);
        for (int i = 0; i < Dict.length; i++) {
            int rnd = (int) (Math.random() * 2.0);
            if (rnd == 0)
                Dict[i] = Invert(Dict[i]);
        }
        for (int d = 0; d < Dict.length; d++) {
            boolean stored = false;
            String word = Dict[d];
            int rndrow = (int) (Math.random() * 10.0);
            int rndrowsaved = rndrow;
            int rndcol = (int) (Math.random() * 10.0);
            int rndcolsaved = rndcol;
            for (; rndrow >= 0 && !stored; rndrow--) {
                rndcol = rndcolsaved;
                for (; rndcol >= 0 && !stored; rndcol--) {
                    stored = isStored(rows, cols, matrix, stored, word, rndrow, rndcol);
                }
                rndcol = rndcolsaved;
                for (; rndcol < cols && !stored; rndcol++) {
                    stored = isStored(rows, cols, matrix, stored, word, rndrow, rndcol);
                }
            }
            if (!stored) {
                rndrow = rndrowsaved;
                for (; rndrow < rows && !stored; rndrow++) {
                    rndcol = rndcolsaved;
                    for (; rndcol < cols && !stored; rndcol++) {
                        stored = isStored(rows, cols, matrix, stored, word, rndrow, rndcol);
                    }
                    rndcol = rndcolsaved;
                    for (; rndcol >= 0 && !stored; rndcol--) {
                        stored = isStored(rows, cols, matrix, stored, word, rndrow, rndcol);
                    }
                }
            }
            if (stored) Dict = DeleteIndexString(Dict, d);
        }
        char[] letters = new char[0];
        for (int i = 0; i < Dict.length; i++) {
            letters = Letters(letters, Dict[i]);
        }
        letters = ShuffleChars(letters);
        System.out.println();
        for (int z = 0; z < letters.length; z++) {
            boolean stored = false;
            for (int i = 0; i < rows && !stored; i++) {
                for (int j = 0; j < cols && !stored; j++) {
                    if (matrix[i][j] == ' ') {
                        matrix[i][j] = letters[z];
                        stored = true;
                    }
                }
            }
        }
        ApplyGravity(matrix, 10);
        return matrix;
    }

    private static boolean isStored(int rows, int cols, char[][] matrix, boolean stored, String word, int rndrow, int rndcol) {
        if (matrix[rndrow][rndcol] == ' ') {
            //RANDOMIZE ORIENTATION
            int rnd = (int) (Math.random() * 2.0);
            if (rnd == 0 && rndrow + word.length() < rows) {
                //VERTICALLY
                boolean capable = true;
                for (int z = 0; z < word.length() && capable; z++)
                    if (matrix[rndrow + z][rndcol] != ' ') capable = false;
                if (capable) {
                    for (int z = 0; z < word.length(); z++)
                        matrix[rndrow + z][rndcol] = word.charAt(z);
                    stored = true;
                }
            } else if (rnd == 1 && rndcol + word.length() < cols) {
                boolean capable = true;
                for (int z = 0; z < word.length() && capable; z++)
                    if (matrix[rndrow][rndcol + z] != ' ') capable = false;
                if (capable) {
                    for (int z = 0; z < word.length(); z++)
                        matrix[rndrow][rndcol + z] = word.charAt(z);
                    stored = true;
                }
            }
        }
        return stored;
    }

    private static void Fill(char[][] matrix) {
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = ' ';
            }
        }
    }

    private static String Invert(String word) {
        String invword = "";
        for (int i = word.length() - 1; i >= 0; i--)
            invword += word.charAt(i);
        return invword;
    }

    private static char[] DeleteIndexChar(char[] Str, int index) {
        char[] ReducedStr = new char[Str.length - 1];
        Str[index] = ' ';
        int j = 0;
        for (int i = 0; i < Str.length; i++) {
            if (Str[i] != ' ') {
                ReducedStr[j] = Str[i];
                j++;
            }
        }
        return ReducedStr;
    }

    private static char[] Append(char[] arr, char ch) {
        char[] arr1 = new char[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            arr1[i] = arr[i];
        }
        arr1[arr1.length - 1] = ch;
        return arr1;
    }

    private static char[] Letters(char[] arr, String word) {
        char[] retlet = new char[arr.length];
        for (int i = 0; i < arr.length; i++) {
            retlet[i] = arr[i];
        }
        for (int i = 0; i < word.length(); i++) {
            retlet = Append(retlet, word.charAt(i));
        }
        return retlet;
    }

    public static int Read() throws IOException {
        int p;
        Scanner points = new Scanner(new File(System.getProperty("user.dir") + File.separator + "puntos.txt"));
        try {
            p = points.nextInt();
        } catch (Exception e) {
            p = 10;
        }
        points.close();
        return p;
    }

    public static void Save(int p) throws IOException {
        PrintWriter points = new PrintWriter(System.getProperty("user.dir") + File.separator + "puntos.txt");
        points.println(p);
        points.close();
    }


    private static boolean OutOfRange(int row, int col) {
        boolean outofrange = false;
        if (row > 9 || row < 0) outofrange = true;
        if (col > 9 || col < 0) outofrange = true;
        return outofrange;
    }

    private static boolean IsWord(char[][] matrix, int[] data, String str, String[] dict) {
        boolean isword = false;
        String word = "";
        char dir = Character.toUpperCase(str.charAt(2));
        if (data[1] == data[3]) {
            //col constant (n/s)
            int j = data[3];
            if (dir == 'N') {
                for (int i = data[0]; i >= data[2]; i--)
                    word += matrix[i][j];
            } else {
                for (int i = data[0]; i <= data[2]; i++)
                    word += matrix[i][j];
            }
        } else {
            int i = data[2];
            if (dir == 'O') {
                for (int j = data[1]; j >= data[3]; j--) {
                    word += matrix[i][j];
                }
            } else {
                for (int j = data[1]; j <= data[3]; j++)
                    word += matrix[i][j];
            }
        }
        for (int i = 0; i < dict.length && !isword; i++) {
            if (word.equals(dict[i])) isword = true;
            data[4] = i;
        }
        return isword;
    }

    private static int FillData(int index, String Input) {
        int end = 0;
        char dir = Character.toUpperCase(Input.charAt(2));
        if (dir == 'N') {
            if (index == 3) end = Input.charAt(1) - 48;
            else end = Input.charAt(0) - Input.charAt(3) + 1;
        } else if (dir == 'S') {
            if (index == 3) end = Input.charAt(1) - 48;
            else end = Input.charAt(0) + Input.charAt(3) - 48 * 2 - 1;
        } else if (dir == 'O') {
            if (index == 2) end = Input.charAt(0) - 48;
            else end = Input.charAt(1) - Input.charAt(3) + 1;
        } else {
            if (index == 2) end = Input.charAt(0) - 48;
            else end = Input.charAt(1) + Input.charAt(3) - 48 * 2 - 1;
        }
        return end;
    }

    private static void DeleteFromMatrix(char[][] matrix, int[] Data) {
        int from_row = Data[0], from_col = Data[1];
        int to_row = Data[2], to_col = Data[3];
        if (from_row == to_row) {
            if (from_col < to_col) {
                for (; from_col <= to_col; from_col++)
                    matrix[from_row][from_col] = ' ';
            } else {
                for (; to_col <= from_col; to_col++)
                    matrix[from_row][to_col] = ' ';
            }
        } else {
            if (from_row < to_row) {
                for (; from_row <= to_row; from_row++) {
                    matrix[from_row][from_col] = ' ';
                }
            } else {
                for (; to_row <= from_row; to_row++)
                    matrix[to_row][from_col] = ' ';
            }
        }
    }

    private static void ApplyGravity(char[][] matrix, int times) {
        int counter = 1;
        while (counter <= times) {
            for (int i = matrix.length - 2; i >= 0; i--) {
                for (int j = matrix[0].length - 1; j >= 0; j--) {
                    if (matrix[i + 1][j] == ' ') {
                        matrix[i + 1][j] = matrix[i][j];
                        matrix[i][j] = ' ';
                    }
                }
            }
            counter++;
        }
    }

    private static String[] DeleteIndexString(String[] Str, int index) {
        String[] ReducedStr = new String[Str.length - 1];
        Str[index] = null;
        int j = 0;
        for (int i = 0; i < Str.length; i++) {
            if (Str[i] != null) {
                ReducedStr[j] = Str[i];
                j++;
            }
        }
        return ReducedStr;
    }

    private static String[] Shuffle(String[] Str) {
        //BASED ON FISHER-YATES SHUFFLE
        String[] Shuffled = new String[Str.length];
        for (int i = 0; i < Shuffled.length; i++) {
            int rnd_index = (int) (Math.random() * (Str.length));
            Shuffled[i] = Str[rnd_index];
            Str = DeleteIndexString(Str, rnd_index);
        }
        return Shuffled;
    }

    private static char[] ShuffleChars(char[] Str) {
        char[] Shuffled = new char[Str.length];
        for (int i = 0; i < Shuffled.length; i++) {
            int rnd_index = (int) (Math.random() * (Str.length));
            Shuffled[i] = Str[rnd_index];
            Str = DeleteIndexChar(Str, rnd_index);
        }
        return Shuffled;
    }

    private static void Printer(char[][] matrix) {
        int n_row = matrix.length, n_col = matrix[0].length;
        System.out.print(" ");
        for (int i = 0; i < n_row; i++)
            System.out.print(" " + i);
        for (int i = 0; i < n_row; i++) {
            System.out.print("\n" + i);
            for (int j = 0; j < n_row; j++)
                System.out.print(" " + matrix[i][j]);
            System.out.print(" " + i);
        }
        System.out.println();
        System.out.print(" ");
        for (int i = 0; i < n_row; i++)
            System.out.print(" " + i);
    }

    private static boolean MatrixEmpty(char[][] matrix) {
        int n_row = matrix.length, n_col = matrix[0].length;
        boolean empty = true;
        for (int i = 0; i < n_row; i++) {
            for (int j = 0; j < n_col; j++) {
                if (matrix[i][j] <= 'Z' && matrix[i][j] >= 'A')
                    empty = false;
            }
        }
        return empty;
    }

    private static boolean InputWord(String Input) {
        boolean valid = true;
        if (Input.length() == 4) {
            char p0 = Input.charAt(0), p1 = Input.charAt(1), p2 = Input.charAt(2), p3 = Input.charAt(3);
            p2 = Character.toUpperCase(p2);
            if (p0 > '9' || p1 > '9' || p3 > '9') {
                valid = false;
            } else if (p0 < '0' || p1 < '0' || p3 < '0') {
                valid = false;
            } else if (p2 != 'N' && p2 != 'E' && p2 != 'S' && p2 != 'O') {
                valid = false;
            }
        } else valid = false;
        return valid;
    }

    private static boolean InputClue(String Input) {
        boolean clue = false;
        String Input_Cap = "";
        for (int i = 0; i < Input.length(); i++)
            Input_Cap += Character.toUpperCase(Input.charAt(i));
        if (Input_Cap.equals("LET") || Input_Cap.equals("POS") || Input_Cap.equals("PAL"))
            clue = true;
        return clue;
    }

    private static boolean SearchWord(char[][] matrix, int row, int col, String word) {
        int[] x = {0, 0, -1, 1};
        int[] y = {1, -1, 0, 0};
        int max_row = 10, max_col = 10;
        boolean keep;
        if (matrix[row][col] != word.charAt(0))
            return false;
        int size = word.length();
        for (int dir = 0; dir < 4; dir++) {
            keep = true;
            int k, next_row = row + x[dir], next_col = col + y[dir];
            for (k = 1; k < size && keep; k++) {
                if (next_row >= max_row || next_row < 0 || next_col >= max_col || next_col < 0)
                    keep = false;
                if (keep && matrix[next_row][next_col] != word.charAt(k))
                    keep = false;
                if (keep) {
                    next_row += x[dir];
                    next_col += y[dir];
                }
            }
            if (keep && k == size)
                return true;
        }
        return false;
    }

    private static boolean CheckWord(String[] Dict, char[][] matrix, String[] pos) {
        boolean haveword = false;
        for (int z = 0; z < Dict.length && !haveword; z++) {
            for (int i = 0; i < matrix.length && !haveword; i++) {
                for (int j = 0; j < matrix[0].length && !haveword; j++) {
                    if (SearchWord(matrix, i, j, Dict[z])) {
                        haveword = true;
                        pos[0] = Integer.toString(i);
                        pos[1] = Integer.toString(j);
                        pos[2] = Dict[z];
                    }
                }
            }
        }
        return haveword;
    }

    private static int Game(char[][] matrix, String[] Dict, Scanner in, int p) {
        String UserInput;
        boolean MatrixEmpty = false;
        do {
            boolean haveword = false;
            String[] pos = new String[4];
            System.out.println("\nPUNTOS DISPONIBLES: " + p);
            System.out.println();
            int[] data = new int[5];
            Printer(matrix);
            System.out.println("\nEscriba posición inicial, dirección (N, S, E, O) y longitud de la palabra (Ej : 11E4)");
            UserInput = in.next();
            boolean IsClue = InputClue(UserInput);
            haveword = CheckWord(Dict, matrix, pos);
            if (!InputWord(UserInput) && !IsClue)
                System.out.println("Entrada no válida");
            else if (IsClue) {
                String UserInput_Caps = "";
                for (int i = 0; i < UserInput.length(); i++)
                    UserInput_Caps += Character.toUpperCase(UserInput.charAt(i));
                if (haveword) {
                    if (UserInput_Caps.equals("LET")) {
                        if (p >= 1) {
                            --p;
                            System.out.println("\nLa primera letra de palabra es: " + pos[2].charAt(0));
                        } else System.out.println("\nNo tienes puntos suficientes: " + p + " de 1 necesario");
                    } else if (UserInput_Caps.equals("PAL")) {
                        int necessary = pos[2].length();
                        if (p >= necessary) {
                            p -= necessary;
                            System.out.println("\nLa palabra es: " + pos[2]);
                        } else System.out.println("\nNo tienes puntos suficientes");
                    } else {
                        if (p >= 2) {
                            p -= 2;
                            System.out.println("\nLa posición de la palabra es: [" + pos[0] + "," + pos[1] + "]");
                        } else
                            System.out.println("\nNo tienes puntos suficientes: " + p + " de 2 necesarios");
                    }
                }
            } else {
                data[0] = UserInput.charAt(0) - 48; //STATING ROW OF THE WORD
                data[1] = UserInput.charAt(1) - 48; //STARTING COLUMN OF THE WORD
                data[2] = FillData(2, UserInput);  //LAST ROW
                data[3] = FillData(3, UserInput);  //LAST COLUMN
                if (!OutOfRange(data[2], data[3]) && IsWord(matrix, data, UserInput, Dict)) {
                    DeleteFromMatrix(matrix, data);
                    char temp = Character.toUpperCase(UserInput.charAt(2));
                    if (temp == 'N' || temp == 'S')
                        ApplyGravity(matrix, Dict[data[4]].length());
                    else ApplyGravity(matrix, 1);
                    Dict = DeleteIndexString(Dict, data[4]);
                }
                MatrixEmpty = MatrixEmpty(matrix);
                haveword = CheckWord(Dict, matrix, pos);
                String[] CopyDict = new String[10];
                for (int i = 0; i < Dict.length; i++) {
                    CopyDict[i] = Dict[i];
                }
                if (!haveword && !MatrixEmpty) {
                    matrix = newMatrix(10, 10, CopyDict);
                    System.out.println("REORDENANDO TABLERO...");
                }
            }
        }
        while (!MatrixEmpty);
        p += 50;
        return (p);
    }

    public static void main(String[] args) {
        int n_row = 10, n_col = 10;
        char[][] matrix = new char[10][10];
        Scanner in = new Scanner(System.in);
        int p;
        try {
            p = Read();
        } catch (IOException e) {
            p = 10;
        }
        boolean keepplaying = false;
        String[] Chosen_Dict;
        String[] Dict_Normal = {"BONIATO", "BOTARATE", "JAVA", "HELADO", "GRIS", "NARANJA", "VERDE", "MACARRON",
                "UMAMI", "ANGELOTE", "VALLA", "FINN", "JAKE", "ULTIMA", "ALMENDRA", "TABLERO", "ANDROID", "UVA", "DOLOR",
                "ACABAR", "DESTROZO", "COMENTAR", "SUMAR", "PRESENTAR", "EULER", "LEYENDA", "PALABRA", "SONORIDAD", "HONOR",
                "ORDENADOR", "PERFECTO", "AMOR"};
        String[] Dict_Test = {"ABC"};
        System.out.print("Introduce p o P para Prueba o cualquier otra entrada para Normal: ");
        String selector = in.next();
        char pos0 = selector.charAt(0);
        pos0 = Character.toUpperCase(pos0);
        if (selector.length() == 1 && pos0 == 'P')
            Chosen_Dict = Dict_Test;
        else
            Chosen_Dict = Dict_Normal;
        do {
            {
                Chosen_Dict = Shuffle(Chosen_Dict);
                int n_remove = Chosen_Dict.length - 10;
                int removed = 0;
                int i = Chosen_Dict.length - 1;
                do {
                    Chosen_Dict = DeleteIndexString(Chosen_Dict, i);
                    i--;
                    removed++;
                } while (removed != n_remove);
            }
            String[] GameDict = new String[10];
            for (int i = 0; i < Chosen_Dict.length; i++)
                GameDict[i] = Chosen_Dict[i];
            keepplaying = false;
            matrix = newMatrix(10, 10, Chosen_Dict);
            p = Game(matrix, GameDict, in, p);
            String play;
            System.out.println("¿Deseas seguir jugando? y/n");
            play = in.next();
            if (play.equals("y"))
                keepplaying = true;
        } while (keepplaying);
        System.out.println("PUNTOS TOTALES: " + p);
        try {
            Save(p);
        } catch (IOException e) {
        }
    }
}