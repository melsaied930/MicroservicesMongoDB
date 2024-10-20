To clean up all **Docker builds** and **reclaim disk space**, you can use a few key Docker commands. Here’s how to safely remove Docker builds, containers, volumes, and networks:

---

### **1. Remove Unused Docker Builds and Images**

```bash
docker builder prune -a --force
```

- **`-a`**: Remove all unused build cache, not just dangling ones.
- **`--force`**: Automatically confirm the operation.

---

### **2. Remove Dangling Images**

Sometimes, intermediate images (dangling images) remain after builds. Use this command to remove them:

```bash
docker image prune -a --force
```

---

### **3. Remove All Containers (Stopped and Running)**

```bash
docker rm -f $(docker ps -aq)
```

- **`$(docker ps -aq)`**: This part lists all container IDs.
- **`-f`**: Forcefully removes running containers.

---

### **4. Remove All Docker Volumes**

```bash
docker volume prune --force
```

- Removes all unused volumes.

---

### **5. Remove All Docker Networks**

```bash
docker network prune --force
```

- Deletes any unused networks.

---

### **6. Clean Up Everything with One Command**

If you want to remove **all builds, images, containers, networks, and volumes** at once, use:

```bash
docker system prune -a --volumes --force
```

- **`-a`**: Removes all unused images, including ones not tagged as dangling.
- **`--volumes`**: Removes all unused volumes.
- **`--force`**: Skips confirmation prompts.

---

If you encounter **"Permission Denied"** errors, use `sudo` in Terminal to delete the folder:

```bash
rm -rf ../data
rm -rf ../dump
```
---

### **Example Workflow for Cleaning Up Docker:**

```bash
# Stop all running containers
docker stop $(docker ps -q)

# Remove Unused Docker Builds and Images
docker builder prune -a --force

# Remove Dangling Images
docker image prune -a --force

# Remove All Containers (Stopped and Running)
docker rm -f $(docker ps -aq)

# Remove All Docker Volumes
docker volume prune --force

# Remove All Docker Networks
docker network prune --force

# Remove all containers, images, networks, and volumes
docker system prune -a --volumes --force
```

---

### **Conclusion**

These commands help you clean up old and unused Docker artifacts, freeing up disk space. Use them with caution, as this will delete all Docker data that’s not actively used.
