package vcs.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for calculating differences between file versions.
 * Implements a simple line-based diff algorithm.
 */
public class DiffTool {

    /**
     * Calculates the differences between two text files.
     * @param oldContent Content of the old file version
     * @param newContent Content of the new file version
     * @return Array of strings representing the differences
     */
    public static String[] diff(String oldContent, String newContent) {
        // Split content into lines
        String[] oldLines = oldContent.split("\n");
        String[] newLines = newContent.split("\n");

        // Calculate the longest common subsequence
        int[][] lcs = computeLCS(oldLines, newLines);

        // Generate diff based on LCS
        List<String> diff = generateDiff(oldLines, newLines, lcs);

        return diff.toArray(new String[0]);
    }

    /**
     * Computes the longest common subsequence between two arrays of strings.
     * @param a First array of strings
     * @param b Second array of strings
     * @return 2D array representing the LCS lengths
     */
    private static int[][] computeLCS(String[] a, String[] b) {
        int m = a.length;
        int n = b.length;
        int[][] lcs = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a[i - 1].equals(b[j - 1])) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                } else {
                    lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);
                }
            }
        }

        return lcs;
    }

    /**
     * Generates a diff based on the longest common subsequence.
     * @param a First array of strings
     * @param b Second array of strings
     * @param lcs 2D array representing the LCS lengths
     * @return List of strings representing the differences
     */
    private static List<String> generateDiff(String[] a, String[] b, int[][] lcs) {
        List<String> diff = new ArrayList<>();

        int i = a.length;
        int j = b.length;

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && a[i - 1].equals(b[j - 1])) {
                // Lines are the same, no change
                i--;
                j--;
            } else if (j > 0 && (i == 0 || lcs[i][j - 1] >= lcs[i - 1][j])) {
                // Line was added
                diff.add(0, "+ " + b[j - 1]);
                j--;
            } else if (i > 0 && (j == 0 || lcs[i][j - 1] < lcs[i - 1][j])) {
                // Line was removed
                diff.add(0, "- " + a[i - 1]);
                i--;
            }
        }

        return diff;
    }
}
