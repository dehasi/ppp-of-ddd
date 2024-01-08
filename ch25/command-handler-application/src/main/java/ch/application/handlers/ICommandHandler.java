package ch.application.handlers;

import ch.application.commands.IBusinessRequest;

@FunctionalInterface
public interface ICommandHandler<T extends IBusinessRequest> {
    void action(T businessRequest);
}
