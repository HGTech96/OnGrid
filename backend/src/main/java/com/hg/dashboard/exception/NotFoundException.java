package com.hg.dashboard.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private String entityName;
    private Long entityId;

    @Override
    public String getMessage() {
        return String.format("%s %s not found", entityName, getIdentifier());
    }

    protected String getIdentifier() {
        return entityId.toString();
    }
}
