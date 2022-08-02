package com.hg.dashboard.exception;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long entityId) {
        super("User", entityId);
    }
}
