package ch.application.framework;

import ch.application.commands.IBusinessRequest;
import ch.application.handlers.ICommandHandler;

import java.util.function.Consumer;

public interface ICommandHandlerRegistry {

    <T extends IBusinessRequest, H extends ICommandHandler<T>> void register_handler_for(Class<T> businessCaseType, H handler);

    <T extends IBusinessRequest> Consumer<T> find_handler_for(T businessCase);
}
