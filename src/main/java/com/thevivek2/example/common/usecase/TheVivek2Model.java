package com.thevivek2.example.common.usecase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TheVivek2Model implements TheVivek2Event {

    private String message;
    @Transient
    @JsonIgnore
    private Long executionTime;
    @Transient
    @JsonIgnore
    private int executeStatus;
    @Transient
    @JsonIgnore
    private List<ExecutionStep> executions = new ArrayList<>();

    @JsonIgnore
    private String source;
    private String event;
    private String destinationChannel;

    private String createdBy;

    /**
     * don't expose everytime outside if needed
     **/
    public Object resource() {
        return this;
    }

    public void addExecutionStep(ExecutionStep executionStep) {
        executions.add(executionStep);
    }

}

