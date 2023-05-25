#!/bin/bash
java DatabaseNode -tcpport 1 -record 1:$Random &
java DatabaseNode -tcpport 2 -record 2:$Random -connect localhost:1 &
java DatabaseNode -tcpport 3 -record 3:$Random -connect localhost:1 -connect localhost:2 &
java DatabaseNode -tcpport 4 -record 4:$Random -connect localhost:3 -connect localhost:2 &
java DatabaseNode -tcpport 5 -record 5:$Random -connect localhost:3 -connect localhost:4 -connect localhost:1 &
java DatabaseNode -tcpport 6 -record 6:$Random -connect localhost:4 -connect localhost:2 &
java DatabaseNode -tcpport 7 -record 7:$Random -connect localhost:6 &
java DatabaseNode -tcpport 8 -record 8:$Random -connect localhost:7 &
java DatabaseNode -tcpport 9 -record 9:$Random -connect localhost:8 &
java DatabaseNode -tcpport 10 -record 10:$Random -connect localhost:8 -connect localhost:9 &
java DatabaseNode -tcpport 11 -record 11:$Random -connect localhost:9 -connect localhost:10 &
java DatabaseNode -tcpport 12 -record 12:$Random -connect localhost:9 -connect localhost:10 -connect localhost:11 &
java DatabaseNode -tcpport 13 -record 13:$Random -connect localhost:4 -connect localhost:6 -connect localhost:7 -connect localhost:8 -connect localhost:10 &
java DatabaseNode -tcpport 14 -record 14:$Random -connect localhost:13 &
java DatabaseNode -tcpport 15 -record 15:$Random -connect localhost:14 &
java DatabaseNode -tcpport 16 -record 16:$Random -connect localhost:14 &
java DatabaseNode -tcpport 17 -record 17:$Random -connect localhost:14 &
java DatabaseNode -tcpport 18 -record 18:$Random -connect localhost:14 &
java DatabaseNode -tcpport 19 -record 19:$Random -connect localhost:14 &
java DatabaseNode -tcpport 20 -record 20:$Random -connect localhost:14 &
java DatabaseNode -tcpport 21 -record 21:$Random -connect localhost:14 &
java DatabaseNode -tcpport 22 -record 22:$Random -connect localhost:14 &
java DatabaseNode -tcpport 23 -record 23:$Random -connect localhost:14 &
java DatabaseNode -tcpport 24 -record 24:$Random -connect localhost:14 &
java DatabaseNode -tcpport 25 -record 25:$Random -connect localhost:19 -connect localhost:20 &
