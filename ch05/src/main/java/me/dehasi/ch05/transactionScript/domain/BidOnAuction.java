package me.dehasi.ch05.transactionScript.domain;

import me.dehasi.replacements.exception.NotImplementedException;

class BidOnAuction implements ICommand {

    @Override public void execute() {
        throw new NotImplementedException();
    }
}
