package ravendbeventstore.application.BusinessUseCases;

import net.ravendb.client.documents.session.IDocumentSession;
import ravendbeventstore.model.PayAsYouGo.IPayAsYouGoAccountRepository;
import ravendbeventstore.model.PayAsYouGo.Money;
import ravendbeventstore.model.PayAsYouGo.PayAsYouGoAccount;

import java.util.UUID;

public class CreateAccount {

    private final IPayAsYouGoAccountRepository payAsYouGoAccountRepository;
    private final IDocumentSession unitOfWork;

    public CreateAccount(IPayAsYouGoAccountRepository payAsYouGoAccountRepository, IDocumentSession unitOfWork) {
        this.payAsYouGoAccountRepository = payAsYouGoAccountRepository;
        this.unitOfWork = unitOfWork;
    }

    public void execute(UUID id) {
        var payAsYouGoAccount = new PayAsYouGoAccount(id, new Money(10));

        payAsYouGoAccountRepository.add(payAsYouGoAccount);

        unitOfWork.saveChanges();
    }
}
