
### **Backup Command:**
   ```bash
   mongodump --uri="mongodb+srv://admin:admin@cluster0.xxkqp.mongodb.net/sample_mflix?retryWrites=true&w=majority" \
   --out="backup"
   ```

---

### **Restore Command:**
   ```bash
   mongorestore --db sample "backup/sample_mflix"
   ```