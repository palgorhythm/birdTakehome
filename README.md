# 🐦bird takehome 🐦

### test it remotely

- ##### GET all the birds currently in a ride
    http://ec2-18-233-7-248.compute-1.amazonaws.com:3000/api/birds?state=in_ride

- ##### GET a bird with its events
    http://ec2-18-233-7-248.compute-1.amazonaws.com:3000/api/bird/46ff9fb2-4517-4450-a12a-4bcc18b04965

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
- option 1: add LOCAL_DB_URL as an environment variable in a .env file in the project root,
with its value set to the url of your local postgres database
- option 2: to use an in-memory repository (so you don't have to create a local postgres db),
and change EventsBirdsDatabase on line 16 in Application.kt to MockDatabase.

- after either option, run ```./gradlew run``` in the project root and the server will run on port 3000.