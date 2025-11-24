package academy.tochkavhoda.introduction;

public class ZeroSteps4 {

    public int min(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] < min) min = matrix[i][j];
        return min;
    }

    public int max(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] > max) max = matrix[i][j];
        return max;
    }

    public boolean find(int[][] matrix, int value) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] == value) return true;
        return false;
    }

    public boolean isSortedDescendant(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length - 1; j++)
                if (matrix[i][j] <= matrix[i][j + 1]) return false;
        return true;
    }

    public int hasNoZeroRows(int[][] matrix) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            boolean hasZero = false;
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] == 0) {
                    hasZero = true;
                    break;
                }
            if (!hasZero) count++;
        }
        return count;
    }

    public boolean hasFullZeroRow(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length == 0) continue;
            boolean allZeros = true;
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] != 0) {
                    allZeros = false;
                    break;
                }
            if (allZeros) return true;
        }
        return false;
    }

    public boolean isSymmetric(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length <= i) return false;
            for (int j = 0; j < i; j++)
                if (matrix[j].length <= i || matrix[i][j] != matrix[j][i]) return false;
        }
        return true;
    }

    public int mainDiagonalMax(int[][] matrix) {
        if (matrix.length == 0) return Integer.MIN_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length; i++)
            if (matrix[i].length > i)
                if (matrix[i][i] > max) max = matrix[i][i];
        return max;
    }

    public int mainDiagonalPositivesSum(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++)
            if (matrix[i].length > i)
                if (matrix[i][i] > 0) sum += matrix[i][i];
        return sum;
    }

    public int secondaryDiagonalMax(int[][] matrix) {
        if (matrix.length == 0) return Integer.MIN_VALUE;
        int max = Integer.MIN_VALUE;
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            int colIndex = n - 1 - i;
            if (matrix[i].length > colIndex) {
                int val = matrix[i][colIndex];
                if (val > max) max = val;
            }
        }
        return max;
    }

    public int secondaryDiagonalPositivesSum(int[][] matrix) {
        int sum = 0;
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            int colIndex = n - 1 - i;
            if (matrix[i].length > colIndex) {
                int val = matrix[i][colIndex];
                if (val > 0) sum += val;
            }
        }
        return sum;
    }

    public int[][] matrixSum(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[matrix1.length][];
        for (int i = 0; i < matrix1.length; i++) {
            result[i] = new int[matrix1[i].length];
            for (int j = 0; j < matrix1[i].length; j++) result[i][j] = matrix1[i][j] + matrix2[i][j];
        }
        return result;
    }

    public void transpose(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = i + 1; j < matrix.length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
    }

    public void interchange(int[][] matrix) {
        for (int i = 0; i < matrix.length; i += 2) {
            int[] temp = matrix[i];
            matrix[i] = matrix[i + 1];
            matrix[i + 1] = temp;
        }
    }

    public int[] toLinearByRow(int[][] matrix) {
        int length = 0;
        for (int i = 0; i < matrix.length; i++) length += matrix[i].length;
        int[] result = new int[length];
        int k = 0;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) result[k++] = matrix[i][j];
        return result;
    }

    public int[] toLinearByColumn(int[][] matrix) {
        if (matrix.length == 0) return new int[0];
        int rows = matrix.length;
        int cols = (matrix[0].length > 0) ? matrix[0].length : 0;

        int[] result = new int[rows * cols];
        int k = 0;
        for (int j = 0; j < cols; j++)
            for (int i = 0; i < rows; i++) result[k++] = matrix[i][j];
        return result;
    }

    public int[] toLinearByRowUpperTriangle(int[][] matrix) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++)
            for (int j = i; j < matrix[i].length; j++) count++;
        int[] result = new int[count];
        int k = 0;
        for (int i = 0; i < matrix.length; i++)
            for (int j = i; j < matrix[i].length; j++) result[k++] = matrix[i][j];
        return result;
    }

    public int sumUntilNotFoundInRow(int[][] matrix, int barrier) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == barrier) break;
                sum += matrix[i][j];
            }
        return sum;
    }

    public int sumUntilNotFound(int[][] matrix, int barrier) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == barrier) return sum;
                sum += matrix[i][j];
            }
        return sum;
    }
}
