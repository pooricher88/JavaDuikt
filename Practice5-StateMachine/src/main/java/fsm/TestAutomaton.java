package fsm;

/**
 * Скінченний автомат, що розпізнає наявність у рядку слова "TEST".
 * Автомат читає символи по одному і змінює стан залежно від поточного стану та символу.
 * Коли символи закінчуються, повертається кінцевий стан: "F" — слово знайдено,
 * "S" (початковий), "1".."3" — скільки перших літер слова прочитано наприкінці рядка.
 *
 * Виправлення (завдання 3*): якщо в станах 1–2 прийшов неочікуваний символ 'T',
 * автомат переходить у стан 1 (а не S), бо цей 'T' може бути початком нового "TEST".
 */
public class TestAutomaton {

    public static final String WORD = "TEST";

    private State state = State.S0;

    /** Один крок автомата: перехід за символом. */
    public State next(State current, char c) {
        return switch (current) {
            case S0 -> c == 'T' ? State.S1 : State.S0;
            case S1 -> c == 'E' ? State.S2 : (c == 'T' ? State.S1 : State.S0);
            case S2 -> c == 'S' ? State.S3 : (c == 'T' ? State.S1 : State.S0);
            case S3 -> c == 'T' ? State.F : State.S0;
            case F -> State.F;
        };
    }

    /** Обробляє весь рядок і повертає позначку кінцевого стану. */
    public String run(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Вхідний рядок не може бути null");
        }
        state = State.S0;
        for (char c : input.toCharArray()) {
            state = next(state, c);
        }
        return state.getLabel();
    }

    public State getState() {
        return state;
    }
}
