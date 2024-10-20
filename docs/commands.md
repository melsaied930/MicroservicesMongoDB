To **kill the process using port 8080** on macOS with **one command**, you can use the following:

```bash
kill -9 $(lsof -t -i :8080)
```

### **Explanation:**
- **`lsof -t -i :8080`**: Finds the **Process ID (PID)** using port 8080.
- **`kill -9`**: Forces the process to terminate.

---

### **Verify the Process is Killed:**

After running the command, confirm that **port 8080** is no longer in use:

```bash
lsof -n -i4TCP:8080
```

If no output is shown, the process has been successfully terminated.

---

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