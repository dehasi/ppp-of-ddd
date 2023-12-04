package ch16.dice.WithSideEffect;

import java.util.Random;
import java.util.UUID;

class Dice {
    private final Random r = new Random();

    public final UUID Id;

    Dice(UUID id) {
        this.Id = id;
    }

    // Bad: looks like a query, but changes every time
    public int value() {
        return r.nextInt(1, 7);
    }
}
