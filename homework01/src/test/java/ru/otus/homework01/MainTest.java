package ru.otus.homework01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

    private Main main;

    private static Stream<Arguments> argumentsQuadraticEquation() {
        return Stream.of(
                Arguments.of(1, 0, 1, new double[2]),
                Arguments.of(1, 0, -1, new double[]{1.0, -1.0}),
                Arguments.of(1, 2, 1, new double[]{-1.0, -1.0}),
                Arguments.of(1, 2, 1.00000001, new double[]{-1.0, -1.0})
        );
    }

    private static Stream<Arguments> errorArgumentsQuadraticEquation() {
        return Stream.of(
                Arguments.of(Double.NaN, 0, 0),
                Arguments.of(0, Double.NaN, 0),
                Arguments.of(0, 0, Double.NaN),
                Arguments.of(Double.POSITIVE_INFINITY, 0, 0),
                Arguments.of(0, Double.POSITIVE_INFINITY, 0),
                Arguments.of(0, 0, Double.POSITIVE_INFINITY),
                Arguments.of(Double.NEGATIVE_INFINITY, 0, 0),
                Arguments.of(0, Double.NEGATIVE_INFINITY, 0),
                Arguments.of(0, 0, Double.NEGATIVE_INFINITY)
        );
    }

    @BeforeEach
    public void SetUp() {
        main = new Main();
    }

    @ParameterizedTest
    @MethodSource("argumentsQuadraticEquation")
    public void solveAllowableArguments(double a,
                                        double b,
                                        double c,
                                        double[] expectedResult) {
        assertArrayEquals(expectedResult, main.solve(a, b, c));
    }

    @ParameterizedTest
    @MethodSource("errorArgumentsQuadraticEquation")
    public void solveNotAllowableArguments(double a,
                                           double b,
                                           double c) {
        assertThrows(IllegalArgumentException.class, () -> main.solve(a, b, c));
    }
}
