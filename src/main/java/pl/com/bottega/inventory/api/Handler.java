package pl.com.bottega.inventory.api;


import pl.com.bottega.inventory.domain.commands.Command;

public interface Handler<C extends Command, R> {

    R handle(C command);

    Class<? extends Command> getSupportedCommandClass();

    default boolean canHandle(Command command) {
        return command.getClass().equals(getSupportedCommandClass());
    }

}