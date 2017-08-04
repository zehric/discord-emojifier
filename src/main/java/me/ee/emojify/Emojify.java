package me.ee.emojify;

/*
 * the discord emojifier
 * limit of 200 emojis per message, the rest will be cut off, copy and paste the output into discord chat
 * instructions included in program
 * - ethan zheng 2017
 */

public class Emojify {
    private String text;
    private String textEmoji;
    private String backEmoji;
    private String borderEmoji;
    private static String[][] full;
    private static int[][] current;
    
    private static final int[][] A = {{0, 1, 0}, {1, 0, 1}, {1, 1, 1}, {1, 0, 1}, {1, 0, 1}};
    private static final int[][] B = {{1, 1, 0}, {1, 0, 1}, {1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
    private static final int[][] C = {{0, 1, 1}, {1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {0, 1, 1}};
    private static final int[][] D = {{1, 1, 0}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 1, 0}};
    private static final int[][] E = {{1, 1, 1}, {1, 0, 0}, {1, 1, 0}, {1, 0, 0}, {1, 1, 1}};
    private static final int[][] F = {{1, 1, 1}, {1, 0, 0}, {1, 1, 0}, {1, 0, 0}, {1, 0, 0}};
    private static final int[][] G = {{0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 0, 1}, {0, 1, 1}};
    private static final int[][] H = {{1, 0, 1}, {1, 0, 1}, {1, 1, 1}, {1, 0, 1}, {1, 0, 1}};
    private static final int[][] I = {{1, 1, 1}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {1, 1, 1}};
    private static final int[][] J = {{0, 1, 1}, {0, 0, 1}, {0, 0, 1}, {1, 0, 1}, {0, 1, 0}};
    private static final int[][] K = {{1, 0, 1}, {1, 0, 1}, {1, 1, 0}, {1, 0, 1}, {1, 0, 1}};
    private static final int[][] L = {{1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {1, 1, 1}};
    private static final int[][] M = {{1, 0, 1}, {1, 1, 1}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}};
    private static final int[][] N = {{1, 1, 0}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}};
    private static final int[][] O = {{0, 1, 0}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {0, 1, 0}};
    private static final int[][] P = {{1, 1, 0}, {1, 0, 1}, {1, 1, 0}, {1, 0, 0}, {1, 0, 0}};
    private static final int[][] Q = {{0, 1, 0}, {1, 0, 1}, {1, 0, 1}, {0, 1, 0}, {0, 0, 1}};
    private static final int[][] R = {{1, 1, 0}, {1, 0, 1}, {1, 1, 0}, {1, 0, 1}, {1, 0, 1}};
    private static final int[][] S = {{0, 1, 1}, {1, 0, 0}, {0, 1, 0}, {0, 0, 1}, {1, 1, 0}};
    private static final int[][] T = {{1, 1, 1}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}};
    private static final int[][] U = {{1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 1, 1}};
    private static final int[][] V = {{1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {0, 1, 0}};
    private static final int[][] W = {{1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {1, 1, 1}, {1, 0, 1}};
    private static final int[][] X = {{1, 0, 1}, {1, 0, 1}, {0, 1, 0}, {1, 0, 1}, {1, 0, 1}};
    private static final int[][] Y = {{1, 0, 1}, {1, 0, 1}, {0, 1, 1}, {0, 0, 1}, {1, 1, 0}};
    private static final int[][] Z = {{1, 1, 1}, {0, 0, 1}, {0, 1, 0}, {1, 0, 0}, {1, 1, 1}};
    private static final int[][][] ABC = {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z};
    
    public Emojify(String text, String textEmoji, String backEmoji, String borderEmoji) {
      this.text = text.toUpperCase();
      this.textEmoji = textEmoji;
      this.backEmoji = backEmoji;
      this.borderEmoji = borderEmoji;
    }

    public String emojify() {
      try {
        return generateEmojiString();
      } catch (ArrayIndexOutOfBoundsException e) {
        return "";
      }
    }

    private String generateEmojiString() {
        int length = 0;

        for (int i = 0; i < text.length(); i++)
        {
            length += ABC[text.charAt(i) - 65][1].length;
        }
        length += text.length() + 3;

        full = new String[9][length];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < length; c++)
                full[r][c] = "";
        makeWord(length);

        for (int r = 0; r < 9; r++)
        {
            for (int c = 0; c < length; c++)
            {
                if (r == 0 || r == 8)
                    full[r][c] = borderEmoji;
                else if (c == 0 || c == length - 1)
                    full[r][c] = borderEmoji;
                else if (full[r][c].equals(""))
                    full[r][c] = backEmoji;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < length; c++) {
                sb.append(full[r][c]);
                if (c == length - 1) sb.append("\n");
            }
        }

        return sb.toString();
    }

    private void makeWord(int length) {
        int[][][] chars = new int[length][5][];
        int starts[] = new int[text.length()];

        for (int i = 0; i < text.length(); i++)
        {
            chars[i] = ABC[text.charAt(i) - 65];
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
                        if (current[row - 2][col - starts[asdf]] == 1)     full[row][col] = textEmoji;
                current = null;
            }
        }
    }
}
