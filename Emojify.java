/*
 * the discord emojifier
 * limit of 200 emojis per message, the rest will be cut off, copy and paste the output into discord chat
 * instructions included in program
 * - ethan zheng 2017
 */

import java.util.*;

public class Emojify
{
    public static String text;
    public static String text_emoji;
    public static String back_emoji;
    public static String border_emoji;
    public static String[][] full;
    public static int[][] current = null;
    
    public static int[][] A = {{0, 1, 0}, {1, 0, 1}, {1, 1, 1}, {1, 0, 1}, {1, 0, 1}};
    public static int[][] B = {{1, 1, 0}, {1, 0, 1}, {1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
    public static int[][] C = {{0, 1, 1}, {1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {0, 1, 1}};
    public static int[][] D = {{1, 1, 0}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 1, 0}};
    public static int[][] E = {{1, 1, 1}, {1, 0, 0}, {1, 1, 0}, {1, 0, 0}, {1, 1, 1}};
    public static int[][] F = {{1, 1, 1}, {1, 0, 0}, {1, 1, 0}, {1, 0, 0}, {1, 0, 0}};
    public static int[][] G = {{0, 1, 1, 0}, {1, 0, 0, 0}, {1, 0, 1, 1}, {1, 0, 0, 1}, {0, 1, 1, 0}};
    public static int[][] H = {{1, 0, 1}, {1, 0, 1}, {1, 1, 1}, {1, 0, 1}, {1, 0, 1}};
    public static int[][] I = {{1, 1, 1}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {1, 1, 1}};
    public static int[][] J = {{0, 1, 1, 1}, {0, 0, 1, 0}, {0, 0, 1, 0}, {1, 0, 1, 0}, {0, 1, 0, 0}};
    public static int[][] K = {{1, 0, 0, 1}, {1, 0, 1, 0}, {1, 1, 0, 0}, {1, 0, 1, 0}, {1, 0, 0, 1}};
    public static int[][] L = {{1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {1, 1, 1}};
    public static int[][] M = {{1, 0, 0, 0, 1}, {1, 1, 0, 1, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}};
    public static int[][] N = {{1, 0, 0, 0, 1}, {1, 1, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 1, 1}, {1, 0, 0, 0, 1}};
    public static int[][] O = {{0, 1, 0}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {0, 1, 0}};
    public static int[][] P = {{1, 1, 0}, {1, 0, 1}, {1, 1, 0}, {1, 0, 0}, {1, 0, 0}};
    public static int[][] Q = {{0, 1, 0, 0}, {1, 0, 1, 0}, {1, 0, 1, 0}, {1, 0, 1, 0}, {0, 1, 1, 1}};
    public static int[][] R = {{1, 1, 0}, {1, 0, 1}, {1, 1, 0}, {1, 0, 1}, {1, 0, 1}};
    public static int[][] S = {{0, 1, 1}, {1, 0, 0}, {0, 1, 0}, {0, 0, 1}, {1, 1, 0}};
    public static int[][] T = {{1, 1, 1}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}};
    public static int[][] U = {{1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 1, 1}};
    public static int[][] V = {{1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {0, 1, 0}};
    public static int[][] W = {{1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 1, 0, 1, 1}, {1, 0, 0, 0, 1}};
    public static int[][] X = {{1, 0, 1}, {1, 0, 1}, {0, 1, 0}, {1, 0, 1}, {1, 0, 1}};
    public static int[][] Y = {{1, 0, 1}, {1, 0, 1}, {0, 1, 1}, {0, 0, 1}, {1, 1, 0}};
    public static int[][] Z = {{1, 1, 1}, {0, 0, 1}, {0, 1, 0}, {1, 0, 0}, {1, 1, 1}};
    public static int[][][] ABC = {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z};
    
    public static void getStuff()
    {
        Scanner s = new Scanner(System.in);
            System.out.print("Enter text (enter ~ to stop): ");
            text = s.nextLine().toUpperCase();
            if (text.equals("~"))    System.exit(0);
            System.out.print("Enter text emoji (include colons): ");
            text_emoji = s.nextLine();
            System.out.print("Enter background emoji (include colons): ");
            back_emoji = s.nextLine();
            System.out.print("Enter border emoji (include colons): ");
            border_emoji = s.nextLine();
    }
    
    public static void printStuff()
    {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int length = 0;
        
        for (int i = 0; i < text.length(); i++)
        {
            length += ABC[alphabet.indexOf(text.substring(i, i + 1))][1].length;
        }
        length += text.length() + 3;
        
        full = new String[9][length];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < length; c++)
                full[r][c] = "";
        word(length);
        
        for (int r = 0; r < 9; r++)
        {
            for (int c = 0; c < length; c++)
            {
                if (r == 0 || r == 8)
                    full[r][c] = border_emoji;
                else if (c == 0 || c == length - 1)
                    full[r][c] = border_emoji;
                else if (full[r][c].equals(""))
                    full[r][c] = back_emoji;
            }
        }
        
        print(length);
    }
    
    public static void word(int length)
    {
        int[][][] chars = new int[length][5][];
        int starts[] = new int[text.length()];
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        for (int i = 0; i < text.length(); i++)
        {
            chars[i] = ABC[alphabet.indexOf(text.substring(i, i + 1))];
            if (i == 0)     starts[i] = 2;
            else            starts[i] = starts[i - 1] + chars[i - 1][1].length + 1;
        }
        
        for (int c = 2; c < length - 2; c++)
        {
            int asdf = 2;
            for (int x = 0; x < starts.length; x++)
                if (c == starts[x] && current == null)
                {
                    current = chars[x];
                    asdf = x;
                }
                
            if (current != null)
            {
                for (int row = 2; row < 7; row++)
                    for (int col = starts[asdf]; col < starts[asdf] + current[1].length; col++)
                        if (current[row - 2][col - starts[asdf]] == 1)     full[row][col] = text_emoji;
                current = null;
            }
        }
    }
    
    public static void print(int length)
    {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < length; c++)
            {
                System.out.print(full[r][c]);
                if (c == length - 1)    System.out.println();
            }
    }
    
    public static void main(String[] args)
    {
        while (true)
        {
            getStuff();
            printStuff();
        }
    }
}