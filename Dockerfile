FROM mongo:latest

# Install MongoDB tools and cron
RUN apt-get update && apt-get install -y \
    mongodb-database-tools cron \
    && rm -rf /var/lib/apt/lists/*

# Copy the initialization script
COPY init-script.sh /docker-entrypoint-initdb.d/init-script.sh

# Ensure the script is executable
RUN chmod +x /docker-entrypoint-initdb.d/init-script.sh

# Copy the cron job (optional, if using cron)
COPY cronjob /etc/cron.d/mongo-cronjob
RUN chmod 0644 /etc/cron.d/mongo-cronjob && crontab /etc/cron.d/mongo-cronjob