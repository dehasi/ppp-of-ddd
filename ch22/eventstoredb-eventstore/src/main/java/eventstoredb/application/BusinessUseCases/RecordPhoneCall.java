package eventstoredb.application.BusinessUseCases;

import eventstoredb.infrastructure.Clock;
import eventstoredb.model.PayAsYouGo.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class RecordPhoneCall {

    private final IPayAsYouGoAccountRepository payAsYouGoAccountRepository;
    private final Clock clock;

    public RecordPhoneCall(IPayAsYouGoAccountRepository payAsYouGoAccountRepository, Clock clock) {
        this.payAsYouGoAccountRepository = payAsYouGoAccountRepository;
        this.clock = clock;
    }

    public void Execute(UUID id, String phoneNumber, LocalDateTime callStart, int callLengthInMinutes) {
        try {
            var payAsYouGoAccount = payAsYouGoAccountRepository.findBy(id);
            var numberDialled = new PhoneNumber(phoneNumber);

            var phoneCall = new PhoneCall(numberDialled, callStart.toString(), new Minutes(callLengthInMinutes));
            payAsYouGoAccount.record(phoneCall, new PhoneCallCosting(), clock);

            payAsYouGoAccountRepository.save(payAsYouGoAccount);

        } catch (RuntimeException e) {
            // maybe retry, but for demo we can just throw an exception
            throw new RuntimeException(e);
        }
    }
}
