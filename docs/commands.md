**Rebuild and Restart the Containers**
   After applying the above changes, rebuild and restart the containers:

   ```bash
   docker-compose down
   docker-compose up --build
   ```
Verify that MongoDB and Mongo Express are working by accessing the Mongo Express UI at http://localhost:8081.
**Build the custom MongoDB image**:
   ```bash
   docker compose build
   ```
   **Start the container**:
   ```bash
   docker compose up -d
   ```
   **Rebuild and restart the container**:
   ```bash
   docker compose build && docker compose up -d
   ```
   **Check the logs** to confirm the dump and restore process:
   ```bash
   docker logs mongodb-container
   ```
   **Verify the containers are running**:
   ```bash
   docker ps
   ```
### **Stop and Remove Containers**

1. **Verify the containers are running**:
   ```bash
   docker ps
   ```

2. **Stop the running containers**:
   ```bash
   docker compose down
   ```

3. **Remove any orphaned volumes**:
   ```bash
   docker volume prune -f
   ```

4. **Remove Docker images (optional)**:
   ```bash
   docker image prune -a
   ```
   
5. **Check Logs for Issues**:
    ```bash
    docker logs mongodb-container
    ```

6. **Remove Orphaned Containers (if any)**:
   ```bash
   docker-compose down --remove-orphans
   ```

7. **Restart the Services**:
   ```bash
   docker-compose restart
   ```
---










### **Verify Replica Set Status**

```bash
docker exec -it mongodb-container mongosh -u admin -p admin --authenticationDatabase admin --eval "rs.status()"
```
---

#### To **kill the process using port 8080** on macOS with **one command**, you can use the following:
  ```bash
  kill -9 $(lsof -t -i :8080)
  ```

#### **Explanation:**
- **`lsof -t -i :8080`**: Finds the **Process ID (PID)** using port 8080.
- **`kill -9`**: Forces the process to terminate.



#### **Verify the Process is Killed:**
```bash
lsof -n -i4TCP:8080
```

If no output is shown, the process has been successfully terminated.

---