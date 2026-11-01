package fsm;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        TestAutomaton automaton = new TestAutomaton();
        NaiveTestAutomaton naive = new NaiveTestAutomaton();
        List<String> inputs = List.of("abcTESTabc", "abcTES", "TE", "T", "hello", "",
                "TTEST", "TETEST", "TESTEST", "xTESxTEST");

        System.out.printf("%-12s | %-11s | %s%n", "Рядок", "Виправлений", "Наївний");
        System.out.println("-------------+-------------+--------");
        for (String s : inputs) {
            System.out.printf("%-12s | %-11s | %s%n", "\"" + s + "\"", automaton.run(s), naive.run(s));
        }
    }
}
