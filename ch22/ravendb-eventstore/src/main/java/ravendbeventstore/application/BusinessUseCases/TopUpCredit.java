package ravendbeventstore.application.BusinessUseCases;

import net.ravendb.client.documents.session.IDocumentSession;
import net.ravendb.client.exceptions.ConcurrencyException;
import ravendbeventstore.infrastructure.Clock;
import ravendbeventstore.model.PayAsYouGo.IPayAsYouGoAccountRepository;
import ravendbeventstore.model.PayAsYouGo.Money;

import java.util.UUID;

public class TopUpCredit {

    private final IPayAsYouGoAccountRepository payAsYouGoAccountRepository;
    private final IDocumentSession unitOfWork;
    private final Clock clock;

    public TopUpCredit(IPayAsYouGoAccountRepository payAsYouGoAccountRepository, IDocumentSession unitOfWork, Clock clock) {
        this.payAsYouGoAccountRepository = payAsYouGoAccountRepository;
        this.unitOfWork = unitOfWork;
        this.clock = clock;
    }

    public void execute(UUID id, double amount) { // In prod take BigDecimal
        try {
            var account = payAsYouGoAccountRepository.findBy(id);
            var credit = new Money(amount);
            account.topUp(credit, clock);
            payAsYouGoAccountRepository.save(account);

            unitOfWork.saveChanges();
        } catch (ConcurrencyException e) {
            unitOfWork.advanced().clear();
            throw new RuntimeException(e);
        }
    }
}
