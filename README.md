# Counters
Application for store counters with names.

## Usage

### Create counter 
curl -X POST http://localhost:8080/counter/create -H "Content-Type: application/json" -d '{"name": "name1"}'

### Increment counter
curl -X POST http://localhost:8080/counter/increment -H "Content-Type: application/json" -d '{"name": "name1"}'

### Get counter value
curl -X POST http://localhost:8080/counter/value -H "Content-Type: application/json" -d '{"name": "name1"}'

### Delete counter
curl -X DELETE http://localhost:8080/counter/delete -H "Content-Type: application/json" -d '{"name": "name1"}'

### Get sum of all counters 
curl -X GET http://localhost:8080/group/sum

### Get names of all counters 
curl -X GET http://localhost:8080/group/names
