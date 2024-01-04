package ravendbeventstore.model.PayAsYouGo;

import java.util.UUID;

public interface IPayAsYouGoAccountRepository {

    PayAsYouGoAccount findBy(UUID id);

    void add(PayAsYouGoAccount payAsYouGoAccount);

    void save(PayAsYouGoAccount payAsYouGoAccount);
}
