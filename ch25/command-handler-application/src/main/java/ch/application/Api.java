package ch.application;

import ch.application.commands.IBusinessRequest;
import ch.application.framework.ICommandHandlerRegistry;

public class Api {

    private final ICommandHandlerRegistry commandHandlerRegistry;

    public Api(ICommandHandlerRegistry commandHandlerRegistry) {
        this.commandHandlerRegistry = commandHandlerRegistry;
    }

    public <T extends IBusinessRequest> void actionRequestTo(T business_case) {
        commandHandlerRegistry.find_handler_for(business_case).accept(business_case);
    }
}
