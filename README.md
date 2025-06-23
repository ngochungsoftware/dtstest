# User Management REST API

REST API quản lý user với authentication, authorization và soft delete sử dụng Java Spring Boot và MySQL.

## Tính năng

- ✅ Quản lý user với các thông tin: name, username, password, email, phone, avatar, status
- ✅ Soft delete (xóa mềm)
- ✅ Authentication và Authorization với JWT
- ✅ Role-based access control (USER, ADMIN, MODERATOR)
- ✅ MySQL database
- ✅ Docker support
- ✅ RESTful API endpoints
- ✅ Validation và error handling
- ✅ Pagination và sorting
- ✅ Search functionality

## Yêu cầu môi trường

- **Java 17** hoặc cao hơn
- **Maven 3.6+**
- **MySQL 8.0+** (nếu chạy local, không dùng Docker)
- **Docker & Docker Compose** (nếu muốn chạy nhanh toàn bộ bằng Docker)
- **Postman** hoặc công cụ test API tương tự để kiểm thử

## Hướng dẫn cài đặt & chạy project

### Cách 1: Chạy với Docker (Khuyến nghị cho người mới)

1. **Clone repository**
   ```bash
   git clone <repository-url>
   cd user-management-api
   ```
2. **Build và chạy với Docker Compose**
   ```bash
   docker-compose up --build
   ```
3. **Truy cập API**
   - API: http://localhost:8080/api
   - Health check: http://localhost:8080/api/auth/health

### Cách 2: Chạy local (Tùy chỉnh cấu hình DB nếu cần)

1. **Cài đặt MySQL**
   - Cài đặt MySQL 8.0
   - Tạo database: `user_management`
   - Cập nhật thông tin kết nối trong `src/main/resources/application.yml` nếu khác mặc định
2. **Build project**
   ```bash
   ./mvnw clean install
   ```
3. **Chạy ứng dụng**
   ```bash
   ./mvnw spring-boot:run
   ```
   hoặc
   ```bash
   java -jar target/dts-0.0.1-SNAPSHOT.jar
   ```
4. **Truy cập API**
   - API: http://localhost:8080/api

## Tài khoản mẫu để test

| Vai trò     | Username   | Password      | Email                | Role      |
|-------------|------------|--------------|----------------------|-----------|
| Admin       | admin      | admin123     | admin@example.com    | ADMIN     |
| Moderator   | moderator  | moderator123 | moderator@example.com| MODERATOR |
| User        | user       | user123      | user@example.com     | USER      |

Các tài khoản này sẽ được tự động tạo khi ứng dụng khởi động (nếu chưa có trong database).

## Hướng dẫn test API với Postman

1. **Đăng nhập lấy token**
   - URL: `POST http://localhost:8080/api/auth/login`
   - Body:
     ```json
     {
       "usernameOrEmail": "admin",
       "password": "admin123"
     }
     ```
   - Lấy giá trị `token` trong response để test các API khác.

2. **Gọi các API khác**
   - Thêm header:
     ```
     Authorization: Bearer <token>
     ```
   - Ví dụ lấy danh sách user:
     ```bash
     curl -X GET http://localhost:8080/api/users \
       -H "Authorization: Bearer <token>"
     ```

3. **Tạo user mới**
   - URL: `POST /api/users`
   - Body:
     ```json
     {
       "name": "Test User",
       "username": "testuser",
       "password": "test123",
       "email": "testuser@example.com",
       "phone": "+84123456789",
       "avatar": "https://via.placeholder.com/150"
     }
     ```

4. **Cập nhật user**
   - URL: `PUT /api/users/{id}`
   - Body:
     ```json
     {
       "name": "Updated Name",
       "username": "testuser",
       "email": "testuser@example.com",
       "phone": "+84123456789",
       "avatar": "https://via.placeholder.com/150"
     }
     ```

5. **Xóa user**
   - URL: `DELETE /api/users/{id}`

6. **Khôi phục user đã xóa**
   - URL: `POST /api/users/{id}/restore`

7. **Đổi quyền hoặc trạng thái user**
   - URL: `PUT /api/users/{id}/role?role=MODERATOR`
   - URL: `PUT /api/users/{id}/status?status=INACTIVE`

## Lưu ý
- Các API (trừ `/api/auth/**`) đều cần gửi header `Authorization: Bearer <token>`.
- Nếu gặp lỗi kết nối DB, kiểm tra lại cấu hình MySQL và đảm bảo DB đã chạy.
- Nếu dùng Docker, mọi thứ sẽ tự động cấu hình.

## Tham khảo thêm
- Xem chi tiết các endpoint và ví dụ trong phần API Endpoints phía dưới README này.
- Nếu gặp lỗi, xem log server để biết nguyên nhân và gửi log khi cần hỗ trợ.

## Cấu hình Database

### MySQL Configuration
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/user_management?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

## API Endpoints

### Authentication
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/refresh` - Refresh token
- `GET /api/auth/health` - Health check

### User Management (Yêu cầu ADMIN role)
- `POST /api/users` - Tạo user mới
- `GET /api/users` - Lấy danh sách users (có pagination)
- `GET /api/users/{id}` - Lấy user theo ID
- `PUT /api/users/{id}` - Cập nhật user
- `DELETE /api/users/{id}` - Xóa user (soft delete)
- `POST /api/users/{id}/restore` - Khôi phục user đã xóa
- `PUT /api/users/{id}/status` - Thay đổi status
- `PUT /api/users/{id}/role` - Thay đổi role
- `GET /api/users/search` - Tìm kiếm users
- `GET /api/users/status/{status}` - Lấy users theo status
- `GET /api/users/role/{role}` - Lấy users theo role
- `GET /api/users/count/active` - Đếm số user active
- `GET /api/users/deleted` - Lấy danh sách user đã xóa

## Authentication

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin",
    "password": "admin123"
  }'
```

### Sử dụng JWT Token
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## User Roles

- **USER**: Quyền cơ bản, chỉ có thể xem thông tin cá nhân
- **ADMIN**: Quyền quản trị, có thể quản lý tất cả users
- **MODERATOR**: Quyền trung gian, có thể quản lý một số chức năng

## User Status

- **ACTIVE**: User hoạt động bình thường
- **INACTIVE**: User không hoạt động
- **SUSPENDED**: User bị tạm khóa
- **PENDING**: User chờ xác nhận

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    avatar VARCHAR(255),
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED', 'PENDING') DEFAULT 'ACTIVE',
    role ENUM('USER', 'ADMIN', 'MODERATOR') DEFAULT 'USER',
    deleted BOOLEAN DEFAULT FALSE,
    deleted_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Development

### Cấu trúc project
```
src/main/java/com/dts/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── exception/      # Custom exceptions
├── repository/     # Data repositories
├── security/       # Security components
└── service/        # Business logic services
```

### Build và Test
```bash
# Build project
mvn clean compile

# Run tests
mvn test

# Build JAR file
mvn clean package

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Monitoring

### Health Check
- Endpoint: `GET /actuator/health`
- Endpoint: `GET /api/auth/health`

### Metrics
- Endpoint: `GET /actuator/metrics`

## Troubleshooting

### Common Issues

1. **Database Connection Error**
    - Kiểm tra MySQL service đang chạy
    - Kiểm tra thông tin kết nối trong `application.yml`

2. **Port Already in Use**
    - Thay đổi port trong `application.yml`
    - Hoặc kill process đang sử dụng port 8080

3. **JWT Token Expired**
    - Sử dụng refresh token để lấy token mới
    - Hoặc đăng nhập lại

### Logs
```bash
# View application logs
docker-compose logs app

# View database logs
docker-compose logs mysql
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.

## Contact

- Email: hungit2301@gmail.com
- GitHub: [ngochungsoftware](https://github.com/ngochungsoftware) 