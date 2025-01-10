package com.example.InternAssign.services;

import com.example.InternAssign.models.Transfer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TransferService {

    public ArrayList<Transfer> getCheapestRoute(ArrayList<Transfer> transfers, int maxWeight) {
        // Knapsack with costs problem solution
        int n = transfers.size();
        int[][] dp = new int[n + 1][maxWeight + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= maxWeight; j++) {
                int w = transfers.get(i - 1).getWeight();
                int c = transfers.get(i - 1).getCost();

                dp[i][j] = dp[i - 1][j];
                if (w <= j) {
                    dp[i][j] = Math.max(dp[i][j], c + dp[i - 1][j - w]);
                }
            }
        }

        // Get the transfers of that solution
        ArrayList<Transfer> result = new ArrayList<>();
        int j = maxWeight;
        for(int i = n; i > 0; i--) {
            if(dp[i][j] != dp[i - 1][j]) {
                result.add(transfers.get(i - 1));
                j -= transfers.get(i - 1).getWeight();
            }
        }

        return result;
    }

}
