# 1. List API
curl --location 'http://localhost:8080/sortedData/all' 

Response - 

{
    "january": [
        "Enrollment{month=january, score='1', name='vakeel'}",
        "Enrollment{month=january, score='56', name='vakeel'}",
        "Enrollment{month=january, score='100', name='vakeel'}"
    ],
    "february": [],
    "march": [],
    "april": [],
    "may": [],
    "june": [],
    "july": [],
    "august": [
        "Enrollment{month=august, score='1', name='vakeel'}",
        "Enrollment{month=august, score='7', name='vakeel'}"
    ],
    "september": [],
    "october": [],
    "november": [],
    "december": [
        "Enrollment{month=december, score='1', name='vakeel'}",
        "Enrollment{month=december, score='7', name='vakeel'}",
        "Enrollment{month=december, score='56', name='vakeel'}",
        "Enrollment{month=december, score='100', name='vakeel'}",
        "Enrollment{month=december, score='200', name='vakeel'}"
    ]
}

To obtain comprehensive data: Determine the enrollment figures for each month, organized according to the corresponding scores.

# 2. Upload Data
curl --location 'http://localhost:8080/postData' \
--header 'Content-Type: application/json' \
--data '{
    "month":"august",
    "score":"1",
    "name":"vakeel"
}'

Add enrollment data for each month.

# 3. Get Data By Month
curl --location 'http://localhost:8080/sortedData/january'
Get Data for supplied month



Provision and Configure Redis - https://app.redislabs.com/ from here, its free for dev

Copy credentials from https://app.redislabs.com/#/subscriptions/subscription/<yourid>/bdb-view/12004108/configuration and add to your config file.

Sorted Map logic in - io.redis.jedis.jedisdemo.controllers.SortedSetController

---------------------------------------------------------------------------------
# Some Important Links for Redis -

https://www.youtube.com/watch?v=h30k7YixrMo

https://www.youtube.com/watch?v=5TRFpFBccQM

https://singhneeruk.medium.com/how-redis-internally-works-in-progress-e2aea2841427

https://medium.com/@rrakshith007/io-multiplexing-and-its-advantages-8c75584079d1#:~:text=IO%20Multiplexing%20is%20a%20way,involve%20processing%20by%20the%20CPU.

EvenLoopp - https://udemy.com/course/nodejs-express-mongodb-bootcamp/learn/lecture/15064746#overview


