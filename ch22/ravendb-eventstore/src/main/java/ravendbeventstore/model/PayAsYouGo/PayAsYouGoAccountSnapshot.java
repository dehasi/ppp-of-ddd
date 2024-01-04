package ravendbeventstore.model.PayAsYouGo;

import ravendbeventstore.infrastructure.DomainEvent;

import java.util.List;
import java.util.UUID;

public record PayAsYouGoAccountSnapshot(
        UUID id,
        int version,
        List<DomainEvent> changes, // Q: Do I need it?
        FreeCallAllowance freeCallAllowance,
        Money credit
) {}
