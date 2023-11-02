package me.dehasi.ch05.transactionScript.domain;

@FunctionalInterface
public interface ICommand {

    void execute();
}
