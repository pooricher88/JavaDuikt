package fsm;

/**
 * Стани автомата: S0 (позначка "S") — початковий стан, нічого не знайдено, S1 — прочитано "T", S2 — "TE", S3 — "TES",
 * F — слово "TEST" знайдено (фінальний стан, з нього автомат уже не виходить).
 */
public enum State {
    S0("S"), S1("1"), S2("2"), S3("3"), F("F");

    private final String label;

    State(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
