package com.example.InternAssign.models.dtos;

import com.example.InternAssign.models.Transfer;

import java.util.ArrayList;

public class RequestDTO {
    private int maxWeight;
    private ArrayList<Transfer> availableTransfers;


    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }


    public ArrayList<Transfer> getAvailableTransfers() {
        return availableTransfers;
    }

    public void setAvailableTransfers(ArrayList<Transfer> availableTransfers) {
        this.availableTransfers = availableTransfers;
    }

}
