package eventstoredb.application.BusinessUseCases;

import eventstoredb.model.PayAsYouGo.IPayAsYouGoAccountRepository;
import eventstoredb.model.PayAsYouGo.Money;
import eventstoredb.model.PayAsYouGo.PayAsYouGoAccount;

import java.util.UUID;

public class CreateAccount {

    private final IPayAsYouGoAccountRepository payAsYouGoAccountRepository;

    public CreateAccount(IPayAsYouGoAccountRepository payAsYouGoAccountRepository) {
        this.payAsYouGoAccountRepository = payAsYouGoAccountRepository;
    }

    public void execute(UUID id) {
        var payAsYouGoAccount = new PayAsYouGoAccount(id, new Money(10));

        payAsYouGoAccountRepository.add(payAsYouGoAccount);

    }
}
