cd..
start cmd /k java DatabaseNode -tcpport 1 -record 1:%random%
start cmd /k java DatabaseNode -tcpport 2 -record 2:%random% -connect localhost:1
start cmd /k java DatabaseNode -tcpport 3 -record 3:%random% -connect localhost:1 -connect localhost:2
start cmd /k java DatabaseNode -tcpport 4 -record 4:%random% -connect localhost:3 -connect localhost:2
start cmd /k java DatabaseNode -tcpport 5 -record 5:%random% -connect localhost:3 -connect localhost:4 -connect localhost:1
start cmd /k java DatabaseNode -tcpport 6 -record 6:%random% -connect localhost:4 -connect localhost:2
start cmd /k java DatabaseNode -tcpport 7 -record 7:%random% -connect localhost:6
start cmd /k java DatabaseNode -tcpport 8 -record 8:%random% -connect localhost:7
start cmd /k java DatabaseNode -tcpport 9 -record 9:%random% -connect localhost:8
start cmd /k java DatabaseNode -tcpport 10 -record 10:%random% -connect localhost:8 -connect localhost:9
start cmd /k java DatabaseNode -tcpport 11 -record 11:%random% -connect localhost:9 -connect localhost:10
start cmd /k java DatabaseNode -tcpport 12 -record 12:%random% -connect localhost:9 -connect localhost:10 -connect localhost:11
start cmd /k java DatabaseNode -tcpport 13 -record 13:%random% -connect localhost:4 -connect localhost:6 -connect localhost:7 -connect localhost:8 -connect localhost:10
start cmd /k java DatabaseNode -tcpport 14 -record 14:%random% -connect localhost:13
start cmd /k java DatabaseNode -tcpport 15 -record 15:%random% -connect localhost:14
start cmd /k java DatabaseNode -tcpport 16 -record 16:%random% -connect localhost:14
start cmd /k java DatabaseNode -tcpport 17 -record 17:%random% -connect localhost:14
start cmd /k java DatabaseNode -tcpport 18 -record 18:%random% -connect localhost:14
start cmd /k java DatabaseNode -tcpport 19 -record 19:%random% -connect localhost:14
start cmd /k java DatabaseNode -tcpport 20 -record 20:%random% -connect localhost:14
start cmd /k java DatabaseNode -tcpport 21 -record 21:%random% -connect localhost:14
start cmd /k java DatabaseNode -tcpport 22 -record 22:%random% -connect localhost:14
start cmd /k java DatabaseNode -tcpport 23 -record 23:%random% -connect localhost:14
start cmd /k java DatabaseNode -tcpport 24 -record 24:%random% -connect localhost:14
start cmd /k java DatabaseNode -tcpport 25 -record 25:%random% -connect localhost:19 -connect localhost:20