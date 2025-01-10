# Cheapest Transfer Route

## Running the project

Clone the project and navigate to the project root directory.

### Run Directly (No need to build JAR)

```bash
./mvnw spring-boot:run
```

### Build JAR

```bash
./mvnw clean package
```

### Run JAR

```bash
java -jar target/SOME_SNAPSHOT.jar
```

## API

### Get the Cheapest Transfer Route

**Endpoint:** `/transfer/cheapest-route`

**Method:** `POST`

**Description:** Retrieves the cheapest transfer route based on the provided transfers and the max weight.

**Content-Type:** application/json

**Request Body:**


- `maxWeight` (number): The maximum weight allowed for transfers. Must be positive

- `availableTransfers` (array): An array of transfer objects, each containing:
  - `weight` (number): The weight of the transfer.
  - `cost` (number): The cost associated with the transfer.

**Response:**

- `200 OK`: Returns the details of the cheapest transfer route.
- `400 Bad Request`: If any required parameter is missing or invalid.
- `415 Unsupported Media Type`: If the input data is in the wrong format
- `500 Internal Server Error`: If there is an issue processing the request.

**Response Body:**

- `selectedTransfers` (array) : An array of the cheapest cost transfers, each containing:
  - `weight` (number): The weight of the transfer.
  - `cost` (number): The cost associated with the transfer.

- `totalCost` (number): The total cost of the selected transfers

- `totalWeight` (number): The total weight of the selected transfers

### Examples

---

#### Valid

**Request:**

```bash
curl -X POST http://localhost:8080/transfer/cheapest-route -H "Content-Type: application/json" -d '{
									"maxWeight": 15,
									"availableTransfers": [
										{
											"weight": 5,
											"cost": 10
										},
										{
											"weight": 10,
											"cost": 20
										},
										{
											"weight": 3,
											"cost": 5
										},
										{
											"weight": 8,
											"cost": 15
										}
									]
								}'
```

**Response:**

```json
{"selectedTransfers":[{"weight":10,"cost":20},{"weight":5,"cost":10}],"totalCost":30,"totalWeight":15}
```

---

#### Invalid

**Request:**

```bash
curl -X POST http://localhost:8080/transfer/cheapest-route -H "Content-Type: application/json" -d '{}'                                      
```

**Response:**

```json
{"selectedTransfers":null,"totalCost":0,"totalWeight":0}
```

---

#### Wrong Format

**Request**

```bash
curl -X POST http://localhost:8080/transfer/cheapest-route -d '{"maxWeight": 10, "availableTransfers": []}' 
```

**Response**

```json
{"timestamp":"2025-01-09T23:17:53.421+00:00","status":415,"error":"Unsupported Media Type","path":"/transfer/cheapest-route"}
```

## File Test Format

```
[transfer amount = n]
[weight 1] [cost 1] 
...
[weight n] [cost n]
[max weight]
[result transfer amount] [result total weight] [result total cost]
```