package com.subcrii.subcrii.global.exception;

import jakarta.validation.constraints.Email;

public class EmailDuplicatedException extends IllegalArgumentException {
    public EmailDuplicatedException(@Email String email) {
        super("Email already exists: " + email);
    }
}
