package sqliteeventstore.application.BusinessUseCases;

import sqliteeventstore.infrastructure.Clock;
import sqliteeventstore.model.PayAsYouGo.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class RecordPhoneCall {

    private final IPayAsYouGoAccountRepository payAsYouGoAccountRepository;
    private final Object unitOfWork;
    private final Clock clock;

    public RecordPhoneCall(IPayAsYouGoAccountRepository payAsYouGoAccountRepository, Object unitOfWork, Clock clock) {
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

            // unitOfWork.saveChanges();
        } catch (RuntimeException e) {
            // unitOfWork.advanced().clear();
            // maybe retry, but for demo we can just throw an exception
            throw new RuntimeException(e);
        }
    }
}
