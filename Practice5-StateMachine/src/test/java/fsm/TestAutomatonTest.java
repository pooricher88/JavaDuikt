package fsm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TestAutomatonTest {

    private final TestAutomaton automaton = new TestAutomaton();

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "abcTESTabc, F",
            "TEST, F",
            "TESTabc, F",
            "abcTEST, F",
            "abcTES, 3",
            "abcTE, 2",
            "abcT, 1",
            "abc, S",
            "TES, 3",
            "TESx, S",
            "TEx, S",
            "Tx, S",
            "test, S",
            "T E S T, 1",
            "TESTTEST, F",
            "TESTx, F"
    })
    void testFunctionalCases(String input, String expected) {
        assertEquals(expected, automaton.run(input));
    }

    @Test
    void testEmptyString() {
        assertEquals("S", automaton.run(""));
    }

    @Test
    void testNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> automaton.run(null));
    }

    /** Завдання 3*: рядки зі словом TEST, на яких наївний автомат не доходить до F. */
    @ParameterizedTest(name = "fixed: \"{0}\" -> F")
    @ValueSource(strings = {"TTEST", "TETEST", "TTTEST", "aTETESTb", "TETETEST", "TTETEST", "xTTESTx"})
    void testFixedCasesReachFinalState(String input) {
        assertEquals("F", automaton.run(input));
    }

    @ParameterizedTest(name = "naive bug: \"{0}\"")
    @ValueSource(strings = {"TTEST", "TETEST", "aTETESTb", "xTTESTx"})
    void testNaiveAutomatonMissesTheseCases(String input) {
        assertNotEquals("F", new NaiveTestAutomaton().run(input));
    }

    @ParameterizedTest(name = "after fix: \"{0}\" -> {1}")
    @CsvSource({"TT, 1", "TET, 1", "TETE, 2", "TETES, 3"})
    void testRestartFromT(String input, String expected) {
        assertEquals(expected, automaton.run(input));
    }

    @Test
    void testFinalStateIsAbsorbing() {
        assertEquals("F", automaton.run("TEST" + "x".repeat(100)));
        assertEquals(State.F, automaton.getState());
    }
}
