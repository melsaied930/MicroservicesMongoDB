### Steps to Set Up Mongo Repositories:

1. **Define Repository Interfaces**: Each repository will correspond to a collection in MongoDB, and Spring Data MongoDB will automatically provide basic CRUD operations (create, read, update, delete) for each entity (record).

2. **Spring Data MongoDB Annotations**: The `@Document` annotation marks a class as a document that is mapped to a MongoDB collection. Each record type that has this annotation can be used in a Mongo repository.

3. **Spring Data Repository Interface**: The repository should extend `MongoRepository` or `ReactiveMongoRepository` depending on your needs (synchronous or reactive programming).

Here are examples of repository interfaces for each of your models:

### 1. **UserRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    // Custom query methods (if needed)
    Optional<User> findByEmail(String email);
}
```

### 2. **ProductRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface ProductRepository extends MongoRepository<Product, UUID> {
    // Custom query methods (if needed)
    List<Product> findByCategoryName(String categoryName);
}
```

### 3. **CartRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface CartRepository extends MongoRepository<Cart, UUID> {
    // Custom query methods (if needed)
    Optional<Cart> findByUserId(UUID userId);
}
```

### 4. **OrderRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, UUID> {
    // Custom query methods (if needed)
    List<Order> findByUserId(UUID userId);
}
```

### 5. **RestockOrderRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface RestockOrderRepository extends MongoRepository<RestockOrder, UUID> {
    // Custom query methods (if needed)
    List<RestockOrder> findByProductId(UUID productId);
}
```

### 6. **SupplierRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface SupplierRepository extends MongoRepository<Supplier, UUID> {
    // Custom query methods (if needed)
    Optional<Supplier> findByName(String name);
}
```

### 7. **SalesReportRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface SalesReportRepository extends MongoRepository<SalesReport, UUID> {
    // Custom query methods (if needed)
    List<SalesReport> findByDateRangeStartDateGreaterThanEqualAndDateRangeEndDateLessThanEqual(Instant startDate, Instant endDate);
}
```

### How It Works:
- **CRUD Operations**: By extending `MongoRepository`, you automatically get basic CRUD methods like `save()`, `findById()`, `findAll()`, `deleteById()`, and so on.
- **Custom Queries**: You can define custom query methods by following Spring Data’s **method query conventions** (like `findByUserId`, `findByCategoryName`), and Spring will automatically implement them for you.
- **Generics**: Each repository interface takes two parameters:
    - The first is the entity type (e.g., `User`, `Product`).
    - The second is the type of the `_id` field (e.g., `UUID`).

### Full Example of a Repository Implementation (Service Layer):

Let’s say you want to retrieve all products in a specific category:

```java
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductsByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
```

### Application Configuration:
Ensure that your `application.properties` or `application.yml` is properly configured for MongoDB:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=mydatabase
```

### Summary:
- **MongoRepository** is fully compatible with all the Java records you’ve defined.
- You can easily implement repositories for each entity, allowing you to interact with MongoDB in a declarative, type-safe manner.
- Repositories offer built-in CRUD functionality, and you can add custom query methods as needed.

Let me know if you'd like help implementing specific queries or further details on other components, like custom query methods or service-layer integration!


*******************************************************************************************************************************************************************************

To regenerate all repositories with **Spring Data REST** and integrate them with **Spring HATEOAS** (`spring-boot-starter-hateoas`), you’ll expose your MongoDB repositories as RESTful endpoints while ensuring that the HATEOAS principles are followed, allowing you to navigate through API relationships.

Here’s how you can regenerate all repositories:

### Steps:

1. **Add Dependencies**:
   You need the following dependencies in your `pom.xml` for Spring Data MongoDB, Spring Data REST, and HATEOAS:

   ```xml
   <!-- Spring Data REST -->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-rest</artifactId>
   </dependency>

   <!-- Spring Data MongoDB -->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-mongodb</artifactId>
   </dependency>

   <!-- Spring HATEOAS -->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-hateoas</artifactId>
   </dependency>
   ```

2. **Repository Definitions**:
   Now regenerate the repositories for all your records. Each repository will be automatically exposed as a REST API with **HATEOAS** links included.

### Regenerated Repositories:

#### 1. **UserRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends MongoRepository<User, UUID> {
    // Custom query methods if needed
    Optional<User> findByEmail(String email);
}
```

#### 2. **UserProfileRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "userProfiles", path = "userProfiles")
public interface UserProfileRepository extends MongoRepository<UserProfile, UUID> {
    // Custom query methods if needed
}
```

#### 3. **AddressRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "addresses", path = "addresses")
public interface AddressRepository extends MongoRepository<Address, UUID> {
    // Custom query methods if needed
}
```

#### 4. **ProductRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends MongoRepository<Product, UUID> {
    // Custom query methods if needed
    List<Product> findByCategoryId(UUID categoryId);
}
```

#### 5. **CategoryRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends MongoRepository<Category, UUID> {
    // Custom query methods if needed
}
```

#### 6. **OrderRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends MongoRepository<Order, UUID> {
    // Custom query methods if needed
    List<Order> findByUserId(UUID userId);
}
```

#### 7. **RestockOrderRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "restockOrders", path = "restockOrders")
public interface RestockOrderRepository extends MongoRepository<UUID, RestockOrder> {
    // Custom query methods if needed
}
```

#### 8. **SupplierRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "suppliers", path = "suppliers")
public interface SupplierRepository extends MongoRepository<Supplier, UUID> {
    // Custom query methods if needed
}
```

#### 9. **SalesReportRepository**

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "salesReports", path = "salesReports")
public interface SalesReportRepository extends MongoRepository<SalesReport, UUID> {
    // Custom query methods if needed
}
```

### Key Points:
- **@RepositoryRestResource**: This annotation automatically exposes the repository as a REST resource. You can customize the `collectionResourceRel` (how the collection will appear in HAL format) and `path` (the URL path for the resource).

  For example:
  - The `UserRepository` is exposed at `/users` and its collection will appear as `users` in the HAL response.

### HATEOAS Links:
With **Spring Data REST** and **HATEOAS**, each resource will automatically include HATEOAS links. For example, when you access `/users`, you will receive a response like this (HAL format):

```json
{
  "_embedded": {
    "users": [
      {
        "username": "johndoe",
        "email": "johndoe@example.com",
        "_links": {
          "self": {
            "href": "http://localhost:8080/users/1"
          },
          "profile": {
            "href": "http://localhost:8080/userProfiles/1"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/users"
    }
  }
}
```

### HAL Explorer:
To make your API exploration easier, you can use the **HAL Explorer** as mentioned earlier. The **HAL Explorer** will give you a navigable UI for interacting with your RESTful API resources:

- URL to access HAL Explorer: `http://localhost:8080/browser/index.html`

### Customizing Projections (Optional):
If you want to customize the data returned by your REST repositories, you can use **Spring Data REST projections**:

```java
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineCategory", types = { Product.class })
public interface ProductProjection {
    String getName();
    BigDecimal getPrice();
    Category getCategory(); // Category will be automatically linked via HATEOAS
}
```

This projection will give you a custom view of `Product` entities. You can use it in URLs like: `/products?projection=inlineCategory`.

### Summary:
- **Spring Data REST** automatically exposes your MongoDB repositories as RESTful endpoints, including HATEOAS links for navigating relationships.
- **HAL Explorer** provides a user-friendly UI for navigating and interacting with the API.
- Custom query methods can be added to the repositories as needed, and projections can be used to control the output format of entities.

Would you like help implementing custom queries or projections, or adding security (like OAuth 2.0 or JWT) to your API?


