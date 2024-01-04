package sqliteeventstore.application.BusinessUseCases;

import sqliteeventstore.model.PayAsYouGo.IPayAsYouGoAccountRepository;
import sqliteeventstore.model.PayAsYouGo.Money;
import sqliteeventstore.model.PayAsYouGo.PayAsYouGoAccount;

import java.util.UUID;

public class CreateAccount {

    private final IPayAsYouGoAccountRepository payAsYouGoAccountRepository;
    private final Object unitOfWork;

    public CreateAccount(IPayAsYouGoAccountRepository payAsYouGoAccountRepository, Object unitOfWork) {
        this.payAsYouGoAccountRepository = payAsYouGoAccountRepository;
        this.unitOfWork = unitOfWork;
    }

    public void execute(UUID id) {
        var payAsYouGoAccount = new PayAsYouGoAccount(id, new Money(10));

        payAsYouGoAccountRepository.add(payAsYouGoAccount);

        // unitOfWork.saveChanges();
    }
}
