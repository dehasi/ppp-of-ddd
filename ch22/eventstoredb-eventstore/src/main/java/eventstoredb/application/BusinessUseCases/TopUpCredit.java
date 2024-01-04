package eventstoredb.application.BusinessUseCases;

import eventstoredb.infrastructure.Clock;
import eventstoredb.model.PayAsYouGo.IPayAsYouGoAccountRepository;
import eventstoredb.model.PayAsYouGo.Money;

import java.util.UUID;

public class TopUpCredit {

    private final IPayAsYouGoAccountRepository payAsYouGoAccountRepository;
    private final Clock clock;

    public TopUpCredit(IPayAsYouGoAccountRepository payAsYouGoAccountRepository, Clock clock) {
        this.payAsYouGoAccountRepository = payAsYouGoAccountRepository;
        this.clock = clock;
    }

    public void execute(UUID id, double amount) { // In prod use BigDecimal
        try {
            var account = payAsYouGoAccountRepository.findBy(id);
            var credit = new Money(amount);
            account.topUp(credit, clock);
            payAsYouGoAccountRepository.save(account);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
