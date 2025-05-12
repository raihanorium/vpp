# Virtual Power Plant (VPP)

## Instructions to run the project

1. Clone the repository:
   ```bash
   git clone https://github.com/raihanorium/vpp.git
   cd vpp
   ```
2. Run the database (make sure 5433 port is free):
   ```bash
   docker-compose up -d
   ```

3. Run the tests:
   ```bash
   ./gradlew test
   ```

4. Run the application:
   ```bash
    ./gradlew bootRun
    ```

## API Docs

### GET http://localhost:8080/api/v1/batteries

Accepts a JSON object with the following structure:

  ```json
{
    "postcodes": [
      "6733",
      "6525",
      "6107",
      "6076"
    ],
    "minimumCapacity": 10000,
    "maximumCapacity": 20000
}
  ```

Parameters:

- `postcodes`: An array of strings representing the postcodes to filter the batteries by.
- `minimumCapacity`: [Optional] An integer representing the minimum capacity of the batteries to be included in the
  response.
- `maximumCapacity`: [Optional] An integer representing the maximum capacity of the batteries to be included in the
  response.

Response:

- `400 Bad Request`: If the request body is not valid JSON or if the postcodes are not provided.
- `500 Internal Server Error`: If there is an error processing the request.
- `200 OK`: A JSON object containing the filtered batteries.
```json
{
  "batteryNames": [
    "Cannington",
    "Carmel",
    "Kalamunda",
    "Koolan Island",
    "Lesmurdie",
    "Mount Adams"
  ],
  "totalWattage": 98500,
  "averageWattage": 16416.666666666668
}
  ```

### POST http://localhost:8080/api/v1/batteries

Accepts a JSON object with the following structure:

  ```json
{
    "batteries": [
      {
        "name": "Test 1",
        "postcode": "1001",
        "capacity": 1001
      },
      {
        "name": "Test 2",
        "postcode": "1002",
        "capacity": 1002
      }
    ]
}
```

## Extra Config
In the ./src/main/resources/application.yaml file, you can configure the following properties:
```yaml
app:
  maxPostcodesInQuery: 50

  # database or stream
  # database performs well for large dataset
  # stream processes the collection in 3 streams
  batteryQueryStrategy: database
```