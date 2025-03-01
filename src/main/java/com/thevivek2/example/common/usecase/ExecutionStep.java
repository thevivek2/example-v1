package com.thevivek2.example.common.usecase;

import lombok.Data;

@Data
public class ExecutionStep {
    private String name;
    private String description;
    private String status;

    public static ExecutionStep of(String name, String description, String status) {
        var executionStep = new ExecutionStep();
        executionStep.setName(name);
        executionStep.setDescription(description);
        executionStep.setStatus(status);
        return executionStep;
    }

    public static ExecutionStep success(String name, String description) {
        return of(name, description, "SUCCESS");
    }
}
