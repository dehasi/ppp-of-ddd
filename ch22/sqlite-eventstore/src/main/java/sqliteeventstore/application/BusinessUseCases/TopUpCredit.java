package sqliteeventstore.application.BusinessUseCases;

import sqliteeventstore.infrastructure.Clock;
import sqliteeventstore.model.PayAsYouGo.IPayAsYouGoAccountRepository;
import sqliteeventstore.model.PayAsYouGo.Money;

import java.util.UUID;

public class TopUpCredit {

    private final IPayAsYouGoAccountRepository payAsYouGoAccountRepository;
    private final Object unitOfWork;
    private final Clock clock;

    public TopUpCredit(IPayAsYouGoAccountRepository payAsYouGoAccountRepository, Object unitOfWork, Clock clock) {
        this.payAsYouGoAccountRepository = payAsYouGoAccountRepository;
        this.unitOfWork = unitOfWork;
        this.clock = clock;
    }

    public void execute(UUID id, double amount) { // In prod use BigDecimal
        try {
            var account = payAsYouGoAccountRepository.findBy(id);
            var credit = new Money(amount);
            account.topUp(credit, clock);
            payAsYouGoAccountRepository.save(account);

            // unitOfWork.saveChanges();
        } catch (Exception e) {
            // unitOfWork.advanced().clear();
            throw new RuntimeException(e);
        }
    }
}
