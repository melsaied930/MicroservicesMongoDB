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

