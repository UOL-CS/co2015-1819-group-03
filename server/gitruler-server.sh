#!/bin/bash

cloneDir=mark

while true; do
    while [ "$(mysql -h 127.0.0.1 -P 3307 -pPASSWORD_HERE -u USERNAME USERNAME -e "SELECT COUNT(*) FROM queue;" -sN)" -gt "0" ]; do

        mysql -h 127.0.0.1 -P 3307 -pPASSWORD_HERE -u USERNAME USERNAME -e "SELECT * FROM queue;" -sN | while read id exId link userId; do
            echo "id: $id, exid: $exId, link: $link, userid: $userId"
            
            rm -rf "$cloneDir"

            git clone --quiet "$link" "$cloneDir"
            output=$(java -jar gitruler.jar -w -r $cloneDir)

            score=$(echo "$output" | tail -1)

            mysql -h 127.0.0.1 -P 3307 -pPASSWORD_HERE -u USERNAME USERNAME -e "DELETE FROM queue WHERE id=$id;" -sN
            mysql -h 127.0.0.1 -P 3307 -pPASSWORD_HERE -u USERNAME USERNAME -e "INSERT INTO attempt VALUES (0, $score, $exId, $userId);"
        done

    done
    sleep 60
done




