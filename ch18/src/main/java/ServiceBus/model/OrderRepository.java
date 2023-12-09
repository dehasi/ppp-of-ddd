package ServiceBus.model;

import java.util.UUID;

public interface OrderRepository {

    OrderForDelivery findBy(UUID id);
}
