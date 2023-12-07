package ch17.onlineGaming.withDomainServices.model;

record Score(int value) {

    Score add(Score amount) {
        return new Score(this.value() + amount.value());
    }

    Score subtract(Score amount) {
        return new Score(this.value() - amount.value());
    }
}
