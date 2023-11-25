package discovery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

class BeganFollowingPollingFeedConsumer {

    private static final XmlMapper XML_MAPPER = new XmlMapper();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String ENTRY_POINT_URL = "http://localhost:8080/account-management";

    private static String lastEventIdProcessed;

    public static void main(String[] args) throws Exception {
        while (true) {
            fetchAndProcessNextBatchOfEvents();
            Thread.sleep(1000L);
        }
    }

    private static void fetchAndProcessNextBatchOfEvents() throws Exception {
        var atomFeed = fetchFeed();
        var ups = getUnprocessedEvents(atomFeed.entry());
        if (!ups.isEmpty())
            processEvents(ups);
        else
            System.out.println("No new events to process");
    }

    private static void processEvents(List<MyEntry> events) {
        for (MyEntry ev : events) {
            var event = parseEvent(ev.content());
            System.out.println("Processing event: " + event.accountId() + " - " + event.followerId());
            lastEventIdProcessed = ev.id();
        }
    }

    private static BeganFollowing parseEvent(String content) {
        try {
            return OBJECT_MAPPER.readValue(content, BeganFollowing.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<MyEntry> getUnprocessedEvents(List<MyEntry> events) {
        var lastProcessed = events.stream().filter(s -> s.id().equals(lastEventIdProcessed)).findFirst();

        var indexOfLastProcessedEvent = lastProcessed.map(events::indexOf).orElse(0);

        return events.stream().skip(indexOfLastProcessedEvent + 1).toList();
    }

    private static MyFeed fetchFeed() throws Exception {
        var accountManagementRequest = HttpRequest.newBuilder()
                .uri(new URI(ENTRY_POINT_URL))
                .GET()
                .build();
        HttpResponse<String> accountManagementResponse = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(accountManagementRequest, HttpResponse.BodyHandlers.ofString());
        JsonNode jsonNode = OBJECT_MAPPER.readTree(accountManagementResponse.body());

        String beganFollowingURL = jsonNode.get("_links").get("beganfollowing").get("href").asText();

        var beganFollowingRequest = HttpRequest.newBuilder()
                .uri(new URI(beganFollowingURL))
                .GET()
                .build();
        HttpResponse<String> beganFollowingResponse = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(beganFollowingRequest, HttpResponse.BodyHandlers.ofString());

        return XML_MAPPER.readValue(beganFollowingResponse.body(), MyFeed.class);
    }

    // representing the domain event
    public record BeganFollowing(String accountId, String followerId) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record MyFeed(String title, @JacksonXmlElementWrapper(useWrapping = false) List<MyEntry> entry) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record MyEntry(String title, String id, String content) {}
}
