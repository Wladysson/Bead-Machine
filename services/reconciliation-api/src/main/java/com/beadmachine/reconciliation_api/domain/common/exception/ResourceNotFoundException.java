package com.beadmachine.reconciliation.api.domain.common.exception;

public class ResourceNotFoundException extends DomainException {

    public ResourceNotFoundException(String resource, Object identifier) {
        super(resource + " was not found for identifier: " + identifier);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}