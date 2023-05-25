cd ..
start cmd /k java DatabaseNode -tcpport 9989 -record 17:25622
start cmd /k java DatabaseNode -tcpport 9997 -record 15:2564 -connect localhost:9989
start cmd /k java DatabaseNode -tcpport 9990 -record 17:2563 -connect localhost:9997 -connect localhost:9989
start cmd /k java DatabaseNode -tcpport 9991 -record 12:2562 -connect localhost:9990 -connect localhost:9997 -connect localhost:9989

 

start cmd /k java DatabaseClient -gateway localhost:9991 -operation get-value 17
start cmd /k java DatabaseClient -gateway localhost:9990 -operation get-value 17
start cmd /k java DatabaseClient -gateway localhost:9991 -operation get-value 17
start cmd /k java DatabaseClient -gateway localhost:9991 -operation get-value 17

 

 

start cmd /k java DatabaseNode -tcpport 9989 -record 1:22
start cmd /k java DatabaseNode -tcpport 9997 -record 2:4 -connect localhost:9989
start cmd /k java DatabaseNode -tcpport 9990 -record 3:3 -connect localhost:9997 -connect localhost:9989
start cmd /k java DatabaseNode -tcpport 9991 -record 1:2 -connect localhost:9990 -connect localhost:9997 -connect localhost:9989

 

start cmd /k java DatabaseClient -gateway localhost:9991 -operation set-value 3:90

start cmd /k java DatabaseClient -gateway localhost:9991 -operation get-value 17
start cmd /k java DatabaseClient -gateway localhost:9991 -operation get-value 17
start cmd /k java DatabaseClient -gateway localhost:9991 -operation get-value 17
start cmd /k java DatabaseClient -gateway localhost:9991 -operation get-value 17
