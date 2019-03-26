#!/bin/bash

if [ "$#" -ne 1  ]; then
    echo "Usage: ./gitruler-server.sh /path/to/config.cnf"
    exit 1
fi

if [ ! -f "$1" ]; then
    echo "Usage: ./gitruler-server.sh /path/to/config.cnf"
    exit 1
fi

cloneDir=mark
mysqlcommand="mysql --defaults-file=$1 -e"

echo "[Server starting]"

while true; do
    while [[ $($mysqlcommand "SELECT COUNT(*) FROM queue;" -sN) -gt 0 ]]; do
        eval "$mysqlcommand 'SELECT * FROM queue;' -sN" | while read -r id exId link userId; do
            echo "[Starting Job $id]"
            rm -rf "$cloneDir"
            if git clone --quiet "$link" "$cloneDir"; then
                echo "[Clone successful]"

                output=$(java -jar gitruler.jar -w -r $cloneDir)

                score=$(echo "$output" | tail -1)

                if eval "$mysqlcommand 'INSERT INTO attempt VALUES (0, $score, $exId, $userId);'"; then
                    echo "[Row insertion successful]"
                else 
                    echo "[Row insertion failed]"
                fi

            else 
                echo "[Clone failed]"
            fi

            if eval "$mysqlcommand 'DELETE FROM queue WHERE id=$id;' -sN"; then
                echo "[Row deletion successful]"
            else
                echo "[Row deletion failed]"
            fi

        done
    done
    echo "[Server sleeping]"
    sleep 60
    echo "[Server wake]"
done




