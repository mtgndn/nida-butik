package org.example.taskmanager.dto;

import org.example.taskmanager.entity.Gender;

public interface CustomerPurchaseSummary {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    Gender getGender();
    Long getPurchasedQuantity();
}
