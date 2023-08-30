# usman-springboot-test
# Some sample curl to test this API

curl --location 'http://localhost:8085/api/v1/call/getapi' \
--header 'x-api-key: dummy'

curl --location 'http://localhost:8085/api/v1/call/postapi' \
--header 'x-api-key: dummy' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email" : "eve.holt@reqres.in",
    "password" : "pistol"
}'
