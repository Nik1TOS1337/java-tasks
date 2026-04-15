package academy.tochkavhoda.matrix;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MatrixNonSimilarRows {
    private final int[][] matrix;

    public MatrixNonSimilarRows(int[][] matrix) {
        this.matrix = matrix;
    }

    public Set<int[]> getNonSimilarRows() {
        Set<int[]> result = new LinkedHashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            Set<Integer> distinctValues = toDistinctSet(matrix[i]);
            boolean isSimilarToExisting = false;
            for (int[] existingRow : result) {
                if (distinctValues.equals(toDistinctSet(existingRow))) {
                    isSimilarToExisting = true;
                    break;
                }
            }
            if (!isSimilarToExisting) {
                result.add(matrix[i]);
            }
        }
        return result;
    }

    private Set<Integer> toDistinctSet(int[] row) {
        Set<Integer> set = new HashSet<>();
        for (int value : row) {
            set.add(value);
        }
        return set;
    }
}
