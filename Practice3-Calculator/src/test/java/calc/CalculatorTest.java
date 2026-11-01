package calc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void testBasicOperations() throws InvalidInputException {
        assertEquals(5.0, calculator.add(2, 3), 1e-9);
        assertEquals(-1.0, calculator.subtract(2, 3), 1e-9);
        assertEquals(6.0, calculator.multiply(2, 3), 1e-9);
        assertEquals(2.5, calculator.divide(5, 2), 1e-9);
        assertEquals(3.0, calculator.sqrt(9), 1e-9);
    }

    @Test
    void testDivisionByZeroThrowsArithmeticException() {
        ArithmeticException e = assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
        assertEquals("Ділення на нуль неможливе", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "12a", "", "   ", "1.2.3", "десять"})
    void testStringsInsteadOfNumbers(String input) {
        assertThrows(InvalidInputException.class, () -> Calculator.parseNumber(input));
    }

    @Test
    void testParseNumberAcceptsComma() throws InvalidInputException {
        assertEquals(2.5, Calculator.parseNumber("2,5"), 1e-9);
        assertEquals(-7.0, Calculator.parseNumber(" -7 "), 1e-9);
    }

    @Test
    void testSqrtOfNegativeThrowsCustomException() {
        InvalidInputException e = assertThrows(InvalidInputException.class, () -> calculator.sqrt(-4));
        assertTrue(e.getMessage().contains("від'ємного"));
    }

    @Test
    void testNaNAndInfinityThrowCustomException() {
        assertThrows(InvalidInputException.class, () -> calculator.add(Double.NaN, 1));
        assertThrows(InvalidInputException.class, () -> calculator.multiply(Double.POSITIVE_INFINITY, 2));
        assertThrows(InvalidInputException.class, () -> calculator.divide(1, Double.NaN));
    }

    @Test
    void testUnknownOperationThrowsCustomException() {
        assertThrows(InvalidInputException.class, () -> calculator.calculate(1, 2, "%"));
        assertThrows(InvalidInputException.class, () -> calculator.calculate(1, 2, null));
    }

    @Test
    void testCalculateDispatch() throws InvalidInputException {
        assertEquals(8.0, calculator.calculate(4, 4, "+"), 1e-9);
        assertEquals(4.0, calculator.calculate(16, 0, "sqrt"), 1e-9);
        assertThrows(ArithmeticException.class, () -> calculator.calculate(4, 0, "/"));
    }
}
