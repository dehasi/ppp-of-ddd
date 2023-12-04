package ch16.GlobalCounter;

class RandomEntityFactory {

    private static long lastId = 0;

    // I guess it should be thread-safe
    synchronized static RandomEntity createEntity() {
        return new RandomEntity(++lastId);
    }
}
