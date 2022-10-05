package ru.otus.homework01;

public class Main {
    public static final double DELTA = 0.01;

    public double[] solve(double a, double b, double c) {
        validateArguments(a, b, c);
        double d = Math.pow(b, 2) - 4 * a * c;

        if (d > DELTA) {
            return new double[]{(-b + Math.sqrt(d)) / (2 * a), (-b - Math.sqrt(d)) / (2 * a)};
        } else if (d < (-1) * DELTA) {
            return new double[2];
        } else {
            return new double[]{(-b) / (2 * a), (-b) / (2 * a)};
        }
    }

    private void validateArguments(double a, double b, double c) {
        if ((a == 0.0) ||
                (Double.isNaN(a)) ||
                (Double.isNaN(b)) ||
                (Double.isNaN(c)) ||
                (Double.isInfinite(a)) ||
                (Double.isInfinite(b)) ||
                (Double.isInfinite(c))) {
            throw new IllegalArgumentException();
        }
    }
}
