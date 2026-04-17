package com.cms.exception;

public class ModificationNotAllowedException extends Exception {
    public ModificationNotAllowedException(String message) {
        super(message);
    }
}
