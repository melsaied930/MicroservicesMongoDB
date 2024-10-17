## **Complete Steps to Install**
- **Xcode Command Line Tools**
- **MongoDB Community Edition**
- **MongoDB Compass**
- **Node.js, npm, and MongoDB Replica Set**
- on **macOS** using **Homebrew**
---

### **Xcode Command Line Tools**
```bash
xcode-select --install
xcode-select -p
```

### **Homebrew**
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
brew --version
```

### **MongoDB Community Edition**
```bash
brew tap mongodb/brew
brew install mongodb-community
```

### **MongoDB Compass**
```bash
brew install --cask mongodb-compass
open -a "MongoDB Compass"
```

### **mongosh**
```bash
brew install mongosh
mongosh --version
mongosh
```

### **MongoDB Replica Set with `run-rs`**
```bash
brew install node
npm install -g run-rs
run-rs --keep
mongosh mongodb://localhost:27017
rs.status()
show dbs
```

### **Test MongoDB**
```javascript
use testDB
db.testCollection.insertOne({ name: "Test Document", value: 123 })
db.testCollection.find()
```

### **Stop and Clean Up**
```bash
rm -rf .db
```