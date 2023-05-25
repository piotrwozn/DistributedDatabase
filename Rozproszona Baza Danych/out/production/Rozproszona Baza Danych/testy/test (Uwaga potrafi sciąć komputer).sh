#!/bin/bash

java DatabaseNode -tcpport 1 -record 1:$RANDOM &
java DatabaseNode -tcpport 2 -record 2:$RANDOM -connect localhost:1 &
java DatabaseNode -tcpport 3 -record 3:$RANDOM -connect localhost:1 -connect localhost:2 &
java DatabaseNode -tcpport 4 -record 4:$RANDOM -connect localhost:3 -connect localhost:2 &
java DatabaseNode -tcpport 5 -record 5:$RANDOM -connect localhost:3 -connect localhost:4 -connect localhost:1 &
java DatabaseNode -tcpport 6 -record 6:$RANDOM -connect localhost:4 -connect localhost:2 &

java DatabaseClient -gateway localhost:1 -operation new-record 1:$RANDOM &
java DatabaseClient -gateway localhost:1 -operation get-max &
java DatabaseClient -gateway localhost:1 -operation new-record 1:$RANDOM &
java DatabaseClient -gateway localhost:4 -operation new-record 4:$RANDOM &
java DatabaseClient -gateway localhost:1 -operation get-max &
java DatabaseClient -gateway localhost:5 -operation new-record 5:$RANDOM &

java DatabaseNode -tcpport 7 -record 7:$RANDOM -connect localhost:6 &
java DatabaseNode -tcpport 8 -record 8:$RANDOM -connect localhost:7 &
java DatabaseNode -tcpport 9 -record 9:$RANDOM -connect localhost:8 &
java DatabaseNode -tcpport 10 -record 10:$RANDOM -connect localhost:8 -connect localhost:9 &
java DatabaseNode -tcpport 11 -record 11:$RANDOM -connect localhost:9 -connect localhost:10 &
java DatabaseNode -tcpport 12 -record 12:$RANDOM -connect localhost:9 -connect localhost:10 -connect localhost:11 &

java DatabaseClient -gateway localhost:1 -operation get-min &
java DatabaseClient -gateway localhost:2 -operation get-min &
java DatabaseClient -gateway localhost:1 -operation find-key 3 &
java DatabaseClient -gateway localhost:5 -operation find-key 4 &

java DatabaseNode -tcpport 13 -record 13:$RANDOM -connect localhost:4 -connect localhost:6 -connect localhost:7 -connect localhost:8 -connect localhost:10 &
java DatabaseNode -tcpport 14 -record 14:$RANDOM -connect localhost:13 &
java DatabaseNode -tcpport 15 -record 15:$RANDOM -connect localhost:14 &
java DatabaseNode -tcpport 16 -record 16:$RANDOM -connect localhost:14 &
java DatabaseNode -tcpport 17 -record 17:$RANDOM -connect localhost:14 &
java DatabaseNode -tcpport 18 -record 18:$RANDOM -connect localhost:14 &
java DatabaseNode -tcpport 19 -record 19:$RANDOM -connect localhost:14 &
java DatabaseNode -tcpport 20 -record 20:$RANDOM -connect localhost:14 &
java DatabaseNode -tcpport 21 -record 21:$RANDOM -connect localhost:14 &
java DatabaseNode -tcpport 22 -record 22:$RANDOM -connect localhost:14 &
java DatabaseNode -tcpport 23 -record 23:$RANDOM -connect localhost:14 &
java DatabaseNode -tcpport 24 -record 24:$RANDOM -connect localhost:14 &
java DatabaseNode -tcpport 25 -record 25:$RANDOM -connect localhost:19 -connect localhost:20 &

java DatabaseClient -gateway localhost:7 -operation find-key 13 &
java DatabaseClient -gateway localhost:6 -operation find-key 25 &
java DatabaseClient -gateway localhost:7 -operation get-min &
java DatabaseClient -gateway localhost:7 -operation get-value 14 &
java DatabaseClient -gateway localhost:24 -operation find-key 1
java DatabaseClient -gateway localhost:25 -operation get-value 1

