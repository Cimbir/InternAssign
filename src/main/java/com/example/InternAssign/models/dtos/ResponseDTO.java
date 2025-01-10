package com.example.InternAssign.models.dtos;

import com.example.InternAssign.models.Transfer;

import java.util.ArrayList;

public class ResponseDTO {
    private ArrayList<Transfer> selectedTransfers;
    private int totalCost;
    private int totalWeight;



    public ArrayList<Transfer> getSelectedTransfers() {
        return selectedTransfers;
    }

    public void setSelectedTransfers(ArrayList<Transfer> selectedTransfers) {
        this.selectedTransfers = selectedTransfers;
    }


    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }


    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

}
