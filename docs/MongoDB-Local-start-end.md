## **How to Start and Stop MongoDB on macOS**

### **Using Homebrew** (if MongoDB was installed via Homebrew)

- **Start MongoDB**:
   ```bash
   brew services start mongodb-community
   ```

- **Stop MongoDB**:
   ```bash
   brew services stop mongodb-community
   ```

- **Restart MongoDB**:
   ```bash
   brew services restart mongodb-community
   ```

- **Check MongoDB Status**:
   ```bash
   brew services list
   ```

---

### **Using the Terminal (Manual Start)**

1. **Start MongoDB (Standalone Instance)**:
   ```bash
   mongod --dbpath /usr/local/var/mongodb
   ```

2. **Stop MongoDB**:
    - Open another terminal and **find the MongoDB process**:
      ```bash
      ps aux | grep mongod
      ```
    - **Kill the process** using its PID:
      ```bash
      kill <pid>
      ```

---

This guide provides two approaches for managing MongoDB on macOS, depending on whether it's installed through **Homebrew** or **manually launched**.

**Start MongoDB Replica Set with `run-rs`**
```bash
run-rs --keep
```
---
**Kill the process** using ports [ 27017, 27018, 27019 ]:
```bash
kill -9 `lsof -i TCP:27017 | awk '/LISTEN/{print $2}'`
kill -9 `lsof -i TCP:27018 | awk '/LISTEN/{print $2}'`
kill -9 `lsof -i TCP:27019 | awk '/LISTEN/{print $2}'`
```

