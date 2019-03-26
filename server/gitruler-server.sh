#!/bin/bash

cloneDir=mark
mysqlcommand="mysql --defaults-file=$1 -e"

while true; do
    while [[ $($mysqlcommand "SELECT COUNT(*) FROM queue;" -sN) -gt 0 ]]; do
        eval "$mysqlcommand 'SELECT * FROM queue;' -sN" | while read -r id exId link userId; do
          rm -rf "$cloneDir"
          git clone --quiet "$link" "$cloneDir"

          output=$(java -jar gitruler.jar -w -r $cloneDir)

          score=$(echo "$output" | tail -1)

          eval "$mysqlcommand 'INSERT INTO attempt VALUES (0, $score, $exId, $userId);'"
          eval "$mysqlcommand 'DELETE FROM queue WHERE id=$id;' -sN";
        done
    done
    sleep 60
done




