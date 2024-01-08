package gambling.applicationServices.utils;

// DataDog: com.timgroup.statsd.StatsDClient
public interface StatsDClient {
    void incrementCounter(String name);
}
