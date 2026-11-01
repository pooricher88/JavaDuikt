package fsm;

/**
 * Початкова (наївна) реалізація автомата — залишена для порівняння.
 * Помилка: при неочікуваному символі автомат завжди повертається в початковий стан S,
 * навіть якщо цей символ — 'T', з якого може починатися нове входження слова.
 * Через це рядки на кшталт "TTEST", "TETEST", "xTTESTx" не розпізнаються, хоча містять "TEST".
 */
public class NaiveTestAutomaton {

    public String run(String input) {
        State state = State.S0;
        for (char c : input.toCharArray()) {
            state = switch (state) {
                case S0 -> c == 'T' ? State.S1 : State.S0;
                case S1 -> c == 'E' ? State.S2 : State.S0;
                case S2 -> c == 'S' ? State.S3 : State.S0;
                case S3 -> c == 'T' ? State.F : State.S0;
                case F -> State.F;
            };
        }
        return state.getLabel();
    }
}
