#!/bin/bash

# Define log file path
LOG_FILE="/path/to/logs/mongodb_logs_$(date +%F_%T).log"

# Capture logs from the MongoDB container
docker logs mongodb-container > "$LOG_FILE"

# Optional: Keep only the latest 5 logs to avoid disk space issues
ls -1t /path/to/logs/mongodb_logs_*.log | tail -n +6 | xargs rm -f
