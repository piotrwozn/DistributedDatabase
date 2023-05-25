#!/bin/bash

cd ..
start cmd /k java DatabaseClient -gateway localhost:1 -operation new-record 1:$Random &
start cmd /k java DatabaseClient -gateway localhost:1 -operation get-max &
start cmd /k java DatabaseClient -gateway localhost:1 -operation new-record 1:$Random &
start cmd /k java DatabaseClient -gateway localhost:4 -operation new-record 4:$Random &
start cmd /k java DatabaseClient -gateway localhost:1 -operation get-max &
start cmd /k java DatabaseClient -gateway localhost:5 -operation new-record 5:$Random &
start cmd /k java DatabaseClient -gateway localhost:1 -operation get-min &
start cmd /k java DatabaseClient -gateway localhost:2 -operation get-min &
start cmd /k java DatabaseClient -gateway localhost:1 -operation find-key 3 &
start cmd /k java DatabaseClient -gateway localhost:5 -operation find-key 4 &
start cmd /k java DatabaseClient -gateway localhost:7 -operation find-key 13 &
start cmd /k java DatabaseClient -gateway localhost:6 -operation find-key 11 &
start cmd /k java DatabaseClient -gateway localhost:7 -operation get-min &
start cmd /k java DatabaseClient -gateway localhost:7 -operation get-value 14 &
start cmd /k java DatabaseClient -gateway localhost:24 -operation find-key 1 &
start cmd /k java DatabaseClient -gateway localhost:25 -operation get-value 1 &
start cmd /k java DatabaseClient -gateway localhost:1 -operation terminate-all &

wait