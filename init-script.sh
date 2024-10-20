#!/bin/bash

# Variables
REMOTE_URI="mongodb+srv://admin:admin@cluster0.xxkqp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
LOCAL_URI="mongodb://admin:admin@localhost:27017"
DUMP_PATH="/dump"

echo "Starting MongoDB dump from remote server..."

# Step 1: Dump the remote MongoDB database
mongodump --uri="$REMOTE_URI" --out="$DUMP_PATH"

if [ $? -ne 0 ]; then
    echo "MongoDB dump failed!"
    exit 1
fi

echo "Remote MongoDB dump completed successfully."

# Step 2: Restore the dump to the local MongoDB container
echo "Restoring dump to local MongoDB..."

mongorestore --uri="$LOCAL_URI" --drop "$DUMP_PATH"

if [ $? -ne 0 ]; then
    echo "MongoDB restore failed!"
    exit 1
fi

echo "MongoDB restore completed successfully."

# Cleanup dump files
rm -rf "$DUMP_PATH"
echo "Temporary dump files removed."