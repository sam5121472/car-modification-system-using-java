package com.cms.exception;

public class BudgetExceededException extends Exception {
    public BudgetExceededException(String message) {
        super(message);
    }
}
