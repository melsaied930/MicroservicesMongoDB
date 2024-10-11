Here’s the list of **Java records** for your MongoDB collections, with UUID auto-generation.

### 1. **User Record**

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.time.Instant;
import java.util.List;

@Document(collection = "users")
public record User(
    @Id UUID id,
    String username,
    String email,
    String password, // should be hashed
    List<String> roles, // E.g., ['customer', 'admin']
    UUID profileId, // Reference to user_profiles._id
    Instant createdAt,
    Instant updatedAt
) {}
```

### 2. **UserProfile Record** (now a separate collection)

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "user_profiles")
public record UserProfile(
    @Id UUID id,
    UUID userId, // Reference to users._id
    String firstName,
    String lastName,
    UUID addressId, // Reference to addresses._id
    String phoneNumber,
    UUID shippingAddressId // Reference to addresses._id
) {}
```

### 3. **Address Record** (separated and reusable)

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "addresses")
public record Address(
    @Id UUID id,
    String street,
    String city,
    String state,
    String postalCode,
    String country
) {}
```

### 4. **Product Record** (now referencing `Category`)

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "products")
public record Product(
    @Id UUID id,
    String name,
    String description,
    UUID categoryId, // Reference to categories._id
    BigDecimal price,
    int stock,
    String imageUrl,
    Instant createdAt,
    Instant updatedAt
) {}
```

### 5. **Category Record** (now a separate collection)

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "categories")
public record Category(
    @Id UUID id,
    String name,
    String description
) {}
```

### 6. **Order Record** (separating `Address` for shipping details)

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "orders")
public record Order(
    @Id UUID id,
    UUID userId, // Reference to users._id
    String status, // E.g., "pending", "shipped", "delivered"
    List<OrderItem> items,
    BigDecimal totalPrice,
    UUID shippingAddressId, // Reference to addresses._id
    Instant createdAt,
    Instant updatedAt
) {}

public record OrderItem(
    UUID productId, // Reference to products._id
    int quantity,
    BigDecimal price // Price when the order was placed
) {}
```

### 7. **RestockOrder Record** (unchanged from previous)

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.time.Instant;

@Document(collection = "restock_orders")
public record RestockOrder(
    @Id UUID id,
    UUID productId, // Reference to products._id
    UUID supplierId, // Reference to suppliers._id
    int quantity,
    String status, // E.g., "pending", "received"
    Instant restockDate,
    Instant createdAt,
    Instant updatedAt
) {}
```

### 8. **Supplier Record** (unchanged from previous)

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "suppliers")
public record Supplier(
    @Id UUID id,
    String name,
    ContactPerson contactPerson,
    UUID addressId, // Reference to addresses._id
    Instant createdAt,
    Instant updatedAt
) {}

public record ContactPerson(
    String name,
    String email,
    String phoneNumber
) {}
```

### 9. **SalesReport Record**

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "sales_reports")
public record SalesReport(
    @Id UUID id,
    DateRange dateRange,
    BigDecimal totalSales,
    List<BestSellingProduct> bestSellingProducts,
    List<WorstSellingProduct> worstSellingProducts
) {}

public record DateRange(
    Instant startDate,
    Instant endDate
) {}

public record BestSellingProduct(
    UUID productId, // Reference to products._id
    int unitsSold
) {}

public record WorstSellingProduct(
    UUID productId, // Reference to products._id
    int unitsSold
) {}
```
