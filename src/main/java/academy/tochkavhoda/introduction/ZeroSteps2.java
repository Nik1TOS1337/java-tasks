package academy.tochkavhoda.introduction;

public class ZeroSteps2 {
    public int sumSquares(int count) {
        int sum = 0;
        for (int i = 1; i <= count; i++) sum += i * i;
        return sum;
    }

    public int sumOdds(int count) {
        int sum = 0;
        for (int i = 1; i <= count; i++)
            if (i % 2 != 0) sum += i;
        return sum;
    }

    public double sumInverses(int count) {
        double sum = 0;
        for (int i = 1; i <= count; i++) sum += 1.0 / i;
        return sum;
    }

    public long factorial(int count) {
        long result = 1;
        for (int i = 1; i <= count; i++) result *= i;
        return result;
    }

    public int prodDigits(int value) {
        int product = 1;
        int temp = value;
        while (temp > 0) {
            product *= temp % 10;
            temp /= 10;
        }
        return product;
    }

    public int fibonacci(int number) {
        if (number == 0) return 0;
        if (number == 1) return 1;
        int prev2 = 0;
        int prev1 = 1;
        int current = 0;
        for (int i = 2; i <= number; i++) {
            current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        return current;
    }

    public long sum2Powers(int max) {
        long sum = 0;
        long current = 1;
        for (int i = 0; i <= max; i++) {
            sum += current;
            current *= 2;
        }
        return sum;
    }

    public int sumSquaresWithBarrier(int count, int barrier) {
        int sum = 0;
        for (int i = 1; i <= count; i++) {
            sum += i * i;
            if (sum > barrier) break;
        }
        return sum;
    }

    public int sumPairProd(int count1, int count2) {
        int sum = 0;
        for (int i = 1; i <= count1; i++)
            for (int j = 1; j <= count2; j++) sum += i * j;
        return sum;
    }

    public int sumPairProdWithBarrier(int count1, int count2, int barrier) {
        int sum = 0;
        for (int i = 1; i <= count1; i++)
            for (int j = 1; j <= count2; j++)
                if (i * j < barrier) sum += i * j;
        return sum;
    }

    public double sumInversePairProd(int count1, int count2) {
        double sum = 0;
        for (int i = 1; i <= count1; i++)
            for (int j = 1; j <= count2; j++) sum += 1.0 / (i * j);
        return sum;
    }

    public int sumTripleProd(int count1, int count2, int count3) {
        int sum = 0;
        for (int i = 1; i <= count1; i++)
            for (int j = 1; j <= count2; j++)
                for (int k = 1; k <= count3; k++) sum += i * j * k;
        return sum;
    }

    public double calculateE() {
        double sum = 1.0;
        double item = 1.0;
        int i = 1;
        while (item >= 1E-6) {
            item = item / i;
            sum += item;
            i++;
        }
        return sum;
    }

    public double calculatePi() {
        double sum = 0;
        double item = 4.0;
        int i = 1;
        boolean end = true;
        while (item >= 1E-8) {
            if (end) sum += item;
            else sum -= item;
            i += 2;
            item = 4.0 / i;
            end = !end;
        }
        return sum;
    }

    public double calculateCircleSquare(double length, int count) {
        int k = 0;
        double radius = length / 2.0;
        double center = length / 2.0;

        for (int i = 0; i < count; i++) {
            double x = Math.random() * length;
            double y = Math.random() * length;

            double dx = x - center;
            double dy = y - center;

            if (dx * dx + dy * dy <= radius * radius) k++;
        }
        return length * length * k / count;
    }

}

