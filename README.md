# 🐦bird takehome 🐦

### test it remotely

- ##### GET all the birds currently in a ride
    http://ec2-18-233-7-248.compute-1.amazonaws.com:3000/api/birds?state=in_ride

- ##### GET a bird with its events
    - some bird: http://ec2-18-233-7-248.compute-1.amazonaws.com:3000/api/bird/46ff9fb2-4517-4450-a12a-4bcc18b04965
    - some other bird: http://ec2-18-233-7-248.compute-1.amazonaws.com:3000/api/bird/46ff9fb2-4517-4450-b12b-4bcc18b04965
    
- ##### POST new event
    http://ec2-18-233-7-248.compute-1.amazonaws.com:3000/api/event
    - include a JSON request body. example:
    ```json
    {
      "id": "905cb84e-d69f-11e9-b057-7b9802c1ecf1",
      "kind": "ride_start",
      "bird_id": "77d010ce-d699-11e9-912d-0783d6ca1a7f",
      "lat": 39.74127,
      "lng": -105.50419,
      "timestamp": 1568431036
    }
    ```

### test it locally
- clone this repo, then...
- option 1 (in-memory db) 
    1. run ```./gradlew run``` in the project root and the server will run on port 3000.
- option 2 (postgres): 
    1. change MockDatabase on line 16 in Application.kt to EventsBirdsDatabase
    2. create a local or remote postgres db
    3. add DB_URL as an environment variable in the .env file in the project root, with its value set to the url of the postgres database
    4. run ```./gradlew run``` in the project root and the server will run on port 3000.