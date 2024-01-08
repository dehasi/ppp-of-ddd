package ch.application.framework;

import ch.application.commands.IBusinessRequest;
import ch.application.handlers.ICommandHandler;

public class TransactionHandler<Command extends IBusinessRequest> {
    void action(Command command, ICommandHandler<Command> handler) {
        // run command transitionally
        handler.action(command);
    }
}
