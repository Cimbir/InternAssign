package com.example.InternAssign.controllers;

import com.example.InternAssign.models.Transfer;
import com.example.InternAssign.models.dtos.RequestDTO;
import com.example.InternAssign.models.dtos.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.InternAssign.services.TransferService;

import java.util.ArrayList;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/cheapest-route")
    public ResponseEntity<ResponseDTO> cheapestRoute(@RequestBody RequestDTO requestdto) {
        if(requestdto.getAvailableTransfers() == null || requestdto.getMaxWeight() <= 0) {
            // Check request body
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO());
        }

        ArrayList<Transfer> result = transferService.getCheapestRoute(requestdto.getAvailableTransfers(), requestdto.getMaxWeight());

        return ResponseEntity.status(HttpStatus.OK).body(getResponseDTO(result));
    }

    private static ResponseDTO getResponseDTO(ArrayList<Transfer> result) {
        ResponseDTO response = new ResponseDTO();
        int totalWeight = 0;
        int totalCost = 0;

        for(Transfer transfer : result) {
            totalWeight += transfer.getWeight();
            totalCost += transfer.getCost();
        }

        response.setSelectedTransfers(result);
        response.setTotalWeight(totalWeight);
        response.setTotalCost(totalCost);
        return response;
    }
}
