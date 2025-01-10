package com.example.InternAssign;

import com.example.InternAssign.models.Transfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.InternAssign.services.TransferService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransferServiceTests {

    private TransferService transferService;

    @BeforeEach
    void setUp() { transferService = new TransferService(); }

    ArrayList<Transfer> getResult(int maxWeight, int[] weights, int[] costs){
        ArrayList<Transfer> transfers = new ArrayList<>();
        for(int i = 0; i < weights.length; i++) {
            transfers.add(new Transfer(weights[i], costs[i]));
        }
        return transferService.getCheapestRoute(transfers, maxWeight);
    }

    void testResult(ArrayList<Transfer> result, int am, int totalCost, int totalWeight) {
        assertEquals(am, result.size());
        int cost = 0;
        int weight = 0;
        for(Transfer i : result) {
            weight += i.getWeight();
            cost += i.getCost();
        }
        assertEquals(totalCost, cost);
        assertEquals(totalWeight, weight);
    }

    @Test
    void testGetCheapestRouteSample() {
        int[] weights = {5, 10, 3, 8};
        int[] costs = {10, 20, 5, 15};
        ArrayList<Transfer> result = getResult(15, weights, costs);
        testResult(result, 2, 30, 15);
    }

    @Test
    void testGetCheapestRouteEmpty() {
        int[] weights = {};
        int[] costs = {};
        ArrayList<Transfer> result = getResult(15, weights, costs);
        testResult(result, 0, 0, 0);
    }

    @Test
    void testGetCheapestRouteOverweight() {
        int[] weights = {100};
        int[] costs = {10};
        ArrayList<Transfer> result = getResult(15, weights, costs);
        testResult(result, 0, 0, 0);
    }

    @Test
    void testGetCheapestRouteNotFillWeight() {
        int[] weights = {5, 10, 3};
        int[] costs = {10, 20, 30};
        ArrayList<Transfer> result = getResult(15, weights, costs);
        testResult(result, 2, 50, 13);
    }

    @Test
    void testGetCheapestRouteComplex10() {
        int[] weights = {95,4,60,32,23,72,80,62,65,46};
        int[] costs = {55,10,47,5,4,50,8,61,85,87};
        ArrayList<Transfer> result = getResult(269, weights, costs);
        testResult(result, 6, 295, 269);
    }

    @Test
    void testGetCheapestRouteComplex20() {
        int[] weights = {92,4,43,83,84,68,92,82,6,44,32,18,56,83,25,96,70,48,14,58};
        int[] costs = {44,46,90,72,91,40,75,35,8,54,78,40,77,15,61,17,75,29,75,63};
        ArrayList<Transfer> result = getResult(878, weights, costs);
        testResult(result, 17, 1024, 871);
    }

    @Test
    void testGetCheapestRouteInvalidMaxWeight() {
        int[] weights = {1,2,3};
        int[] costs = {1,2,3};
        ArrayList<Transfer> result = getResult(-1, weights, costs);
        assertNull(result);
    }

    @Test
    void testGetCheapestRouteInvalidTransfer() {
        int[] weights = {1,2,-3};
        int[] costs = {1,2,3};
        ArrayList<Transfer> result = getResult(10, weights, costs);
        assertNull(result);
    }

    // File tests

    void fileTest(String filename) {
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String nStr = br.readLine();
            int n = Integer.parseInt(nStr);
            int[] weights = new int[n];
            int[] costs = new int[n];
            for(int i = 0; i < n; i++){
                String transferStr = br.readLine();
                String[] transfer = transferStr.split(" ");
                weights[i] = Integer.parseInt(transfer[0]);
                costs[i] = Integer.parseInt(transfer[1]);
            }
            String maxWeightStr = br.readLine();
            int maxWeight = Integer.parseInt(maxWeightStr);

            String resultStr = br.readLine();
            String[] resultSplit = resultStr.split(" ");
            int resAm = Integer.parseInt(resultSplit[0]);
            int resWeight = Integer.parseInt(resultSplit[1]);
            int resCost = Integer.parseInt(resultSplit[2]);
            ArrayList<Transfer> result = getResult(maxWeight, weights, costs);
            testResult(result, resAm, resCost, resWeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void directoryTest(String dir) {
        try(Stream<Path> paths = Files.walk(Paths.get(dir))) {
            paths.filter(Files::isRegularFile).forEach(path -> fileTest(path.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetCheapestRouteBigTests() {
        String big_tests_dir = "./src/test/java/com/example/InternAssign/test_files/big/";
        directoryTest(big_tests_dir);
    }

    @Test
    void testGetCheapestRouteCustomTests() {
        String small_tests_dir = "./src/test/java/com/example/InternAssign/test_files/custom/";
        directoryTest(small_tests_dir);
    }

}
