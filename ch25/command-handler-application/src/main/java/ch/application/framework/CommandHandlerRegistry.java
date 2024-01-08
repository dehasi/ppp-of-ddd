package ch.application.framework;

import ch.application.commands.IBusinessRequest;
import ch.application.handlers.ICommandHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CommandHandlerRegistry implements ICommandHandlerRegistry {
    private final Map<Class<? extends IBusinessRequest>, ICommandHandler<?>> handlers = new HashMap<>();

    @Override public <T extends IBusinessRequest, H extends ICommandHandler<T>> void register_handler_for(Class<T> businessCaseType, H handler) {
        // TODO: what if we have a few handlers?
        handlers.put(businessCaseType, handler);
    }

    @Override public <T extends IBusinessRequest> Consumer<T> find_handler_for(T businessCase) {

        var handler = (ICommandHandler<T>) handlers.get(businessCase.getClass());//ObjectFactory.TryGetInstance < ICommandHandler < TCommand >> ();

        var transactional_handler = new TransactionHandler<T>(); // ObjectFactory.GetInstance < TransactionHandler > ();

        Consumer<T> method_to_handle_command = cmd -> transactional_handler.action(cmd, handler);

        return method_to_handle_command;
    }
}
