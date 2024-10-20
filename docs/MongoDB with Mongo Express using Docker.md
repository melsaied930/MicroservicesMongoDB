## Here’s a **step-by-step summary** to install, configure, run, and manage **MongoDB with Mongo Express** using Docker on macOS (Intel).

---

## **1. Install Docker Desktop**
1. **Install Docker via Homebrew**:
   ```bash
   brew install --cask docker
   ```
2. **Start Docker Desktop**:
   ```bash
   open /Applications/Docker.app
   ```
3. **Verify Docker Installation**:
   ```bash
   docker --version
   docker compose version
   ```

---

## **2. Create the Docker Compose Configuration**

1. **Navigate to your project directory**:
   ```bash
   mkdir ~/MicroservicesMongoDB && cd ~/MicroservicesMongoDB
   ```

2. **Create `docker-compose.yml`**:
   ```bash
   nano docker-compose.yml
   ```

3. **Paste the following content** into the file:

   ```yaml
   version: '3.8'

   services:
     mongodb:
       image: mongo:latest
       container_name: mongodb-container
       restart: always
       environment:
         MONGO_INITDB_ROOT_USERNAME: admin
         MONGO_INITDB_ROOT_PASSWORD: admin
       ports:
         - "27017:27017"

     mongo-express:
       image: mongo-express:latest
       container_name: mongo-express-container
       restart: always
       environment:
         ME_CONFIG_MONGODB_ADMINUSERNAME: admin
         ME_CONFIG_MONGODB_ADMINPASSWORD: admin
         ME_CONFIG_MONGODB_SERVER: mongodb-container
       ports:
         - "8081:8081"
       depends_on:
         - mongodb

   networks:
     default:
       driver: bridge
   ```

4. **Save and exit** (`Ctrl+O` to save, `Ctrl+X` to exit).

---

## **3. Run the Containers**

1. **Start the MongoDB and Mongo Express services**:
   ```bash
   docker compose up -d
   ```

2. **Verify the containers are running**:
   ```bash
   docker ps
   ```

---

## **4. Access the Mongo Express Web Interface**

1. Open your browser and navigate to:  
   [http://localhost:8081](http://localhost:8081)

2. **Login Credentials**:
    - **Username**: `admin`
    - **Password**: `pass`

---

## **5. Validate MongoDB Connectivity**

1. **Check MongoDB logs**:
   ```bash
   docker logs mongodb-container
   ```

2. **Check Mongo Express logs**:
   ```bash
   docker logs mongo-express-container
   ```

3. **Test MongoDB via CLI**:
    - Open a shell in Mongo Express:
      ```bash
      docker exec -it mongo-express-container sh
      ```
    - Install MongoDB client (inside the container):
      ```sh
      apk add --no-cache mongodb-tools
      ```
    - Connect to MongoDB:
      ```bash
      mongo --host mongodb-container -u admin -p admin --authenticationDatabase admin
      ```
   - Exit the MongoDB shell:
      ```bash
      exit;
      ```

---

## **6. Stop and Remove Containers**

1. **Stop the running containers**:
   ```bash
   docker compose down
   ```

2. **Remove any orphaned volumes**:
   ```bash
   docker volume prune -f
   ```

3. **Remove Docker images (optional)**:
   ```bash
   docker image prune -a
   ```

---

## **7. Troubleshooting**

1. **Reset Docker to Factory Defaults** if issues persist:
    - Open Docker Desktop > **Preferences** > **Troubleshoot** > **Reset to Factory Defaults**.

2. **Rebuild containers if needed**:
   ```bash
   docker compose up --build -d
   ```

3. **Ensure MongoDB and Mongo Express are on the same network**:
   ```bash
   docker network inspect bridge
   ```

---

## **8. Optional Cleanup**

1. **Uninstall Docker Desktop**:
   ```bash
   brew uninstall --cask docker
   ```

2. **Remove Docker-related files**:
   ```bash
   rm -rf ~/.docker /Library/Group\ Containers/group.com.docker /Applications/Docker.app
   ```

3 **Remove Partial or Broken Docker Files (if any)**:
Run the following commands to clean up any residual Docker files that may interfere with a new installation:
   ```bash
   rm -rf ~/.docker
   rm -rf /Library/Group\ Containers/group.com.docker
   rm -rf /usr/local/bin/docker
   rm -rf /Applications/Docker.app
   ```
---

## **Summary**

This process ensures you:
1. **Installed Docker Desktop** and configured Docker Compose.
2. **Created a Docker Compose file** with MongoDB and Mongo Express.
3. **Started and validated the services** through logs and web interface.
4. **Stopped and cleaned up** the containers and images properly.

This guide provides a complete workflow from installation to cleanup for MongoDB and Mongo Express in Docker.
