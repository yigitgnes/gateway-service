package com.atech.calculator.rest.client.task.model;

public class Task {

    public Long id;
    public String description;
    public TaskCategory category;
    public boolean completed;
}

enum TaskCategory {
    MARKETING,
    INVENTORY_MANAGEMENT,
    CUSTOMER_ENGAGEMENT
}

