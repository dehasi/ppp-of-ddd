package accountmanagement.controllers;

import com.eventstore.dbclient.ResolvedEvent;
import com.rometools.rome.feed.atom.*;
import com.rometools.rome.feed.synd.SyndPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
class BeganFollowingController {

    private static final String BEGAN_FOLLOWING_BASE_URL = "http://localhost:8082";

    @Autowired
    private EventRetriever eventRetriever;

    @GetMapping("/account-management/began-following")
    Feed feed() {
        List<Entry> entries = eventRetriever.recentEvents("BeganFollowing").stream()
                .map(BeganFollowingController::mapToEntry)
                .toList();

        Feed feed = new Feed();
        feed.setFeedType("atom_1.0"); // Important, otherwise get IllegalArgumentException: Invalid feed type [null]
        feed.setTitle("BeganFollowing");
        feed.setEntries(entries);
        return feed;
    }

    private static Entry mapToEntry(ResolvedEvent ev) {
        Entry entry = new Entry();
        String beganFollowing = new String(ev.getEvent().getEventData());
        entry.setTitle("BeganFollowingEvent");

        Content content = new Content();
        content.setValue(beganFollowing);
        entry.setContents(List.of(content));

        String eventId = ev.getEvent().getEventId().toString();
        entry.setId(eventId);

        Date postDate = new Date();
        entry.setCreated(postDate);
        entry.setPublished(postDate);
        entry.setUpdated(postDate);

        Link link = new Link();
        link.setType("text/html");
        link.setHref(BEGAN_FOLLOWING_BASE_URL + "/account-management/began-following/" + eventId);
        entry.setAlternateLinks(List.of(link));

        SyndPerson author = new Person();
        author.setName("Wronx");
        author.setEmail("accountManagementBC@WroxPPPDDD.com");
        entry.setAuthors(List.of(author));

        return entry;
    }
}
