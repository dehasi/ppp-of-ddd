package ravendbeventstore.application.BusinessUseCases;

import net.ravendb.client.documents.session.IDocumentSession;
import net.ravendb.client.exceptions.ConcurrencyException;
import ravendbeventstore.infrastructure.Clock;
import ravendbeventstore.model.PayAsYouGo.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class RecordPhoneCall {

    private final IPayAsYouGoAccountRepository payAsYouGoAccountRepository;
    private final IDocumentSession unitOfWork;
    private final Clock clock;

    public RecordPhoneCall(IPayAsYouGoAccountRepository payAsYouGoAccountRepository, IDocumentSession unitOfWork, Clock clock) {
        this.payAsYouGoAccountRepository = payAsYouGoAccountRepository;
        this.unitOfWork = unitOfWork;
        this.clock = clock;
    }

    public void Execute(UUID id, String phoneNumber, LocalDateTime callStart, int callLengthInMinutes) {
        try {
            var payAsYouGoAccount = payAsYouGoAccountRepository.findBy(id);
            var numberDialled = new PhoneNumber(phoneNumber);

            var phoneCall = new PhoneCall(numberDialled, callStart, new Minutes(callLengthInMinutes));
            payAsYouGoAccount.record(phoneCall, new PhoneCallCosting(), clock);

            payAsYouGoAccountRepository.save(payAsYouGoAccount);

            unitOfWork.saveChanges();
        } catch (ConcurrencyException e) {
            unitOfWork.advanced().clear();
            // maybe retry, but for demo we can just throw an exception
            throw new RuntimeException(e);
        }
    }
}
