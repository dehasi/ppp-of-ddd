package me.dehasi.replacements.contotion;

import java.util.function.Supplier;

public class Check {

    public static CheckConstraint that(boolean assertion) {
        return new CheckConstraint(assertion);
    }

    public static class CheckConstraint {
        private final boolean assertion;

        private CheckConstraint(boolean assertion) {
            this.assertion = assertion;
        }

        public void onConstraintFailure(Supplier<?> onFailure) {
            if (!assertion) onFailure.get();
        }
    }
}
