FROM mongo:latest

# Install MongoDB tools and cron
RUN apt-get update && apt-get install -y \
    mongodb-database-tools cron \
    && rm -rf /var/lib/apt/lists/*

# Copy the initialization script
COPY init-script.sh /docker-entrypoint-initdb.d/init-script.sh

# Ensure the script is executable
RUN chmod +x /docker-entrypoint-initdb.d/init-script.sh