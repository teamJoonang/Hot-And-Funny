package com.choongang.concert.exception.ticket;

public class CashNotSufficientException extends RuntimeException {
    public CashNotSufficientException() {
        super("Cash is not sufficient for this operation.");
    }

    public CashNotSufficientException(String message) {
        super(message);
    }
}
