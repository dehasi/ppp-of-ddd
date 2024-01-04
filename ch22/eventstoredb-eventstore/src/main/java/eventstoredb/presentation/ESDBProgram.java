package eventstoredb.presentation;

import eventstoredb.Bootstrapper;
import eventstoredb.application.BusinessUseCases.CreateAccount;
import eventstoredb.application.BusinessUseCases.RecordPhoneCall;
import eventstoredb.application.BusinessUseCases.TopUpCredit;

import java.time.LocalDateTime;
import java.util.UUID;

class ESDBProgram {

    public static void main(String[] args) {
        var objectFactory = Bootstrapper.startup();

        var id = UUID.randomUUID();

        System.out.println("Create account");
        System.out.println();
        var createAccount = objectFactory.getInstance(CreateAccount.class);
        createAccount.execute(id);

        System.out.println("Record call");
        System.out.println();
        var recordPhoneCall = objectFactory.getInstance(RecordPhoneCall.class);
        recordPhoneCall.Execute(id, "07789923557", LocalDateTime.now(), 8);

        System.out.println("Top up credit");
        System.out.println();
        var topUpCredit = objectFactory.getInstance(TopUpCredit.class);
        topUpCredit.execute(id, 20);

        System.out.println("Press any key to continue...");
        readLine();

        System.out.println("Record call");
        System.out.println();
        recordPhoneCall.Execute(id, "07789923557", LocalDateTime.now(), 100);
    }

    private static void readLine() {
        try {
            System.console().readLine();
        } catch (Exception ignore) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
