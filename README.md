# REST API Quản Lý Người Dùng

Đây là một REST API được xây dựng bằng **Spring Boot** và **MySQL** để quản lý người dùng, bao gồm các chức năng xác thực (authentication), phân quyền (authorization), và xóa mềm (soft delete) cho người dùng và vai trò. API hỗ trợ đăng ký, đăng nhập, quản lý vai trò, và quản lý hồ sơ người dùng.

## Mục Lục
- [Tính Năng](#tính-năng)
- [Công Nghệ](#công-nghệ)
- [Yêu Cầu Cần Có](#yêu-cầu-cần-có)
- [Hướng Dẫn Cài Đặt](#hướng-dẫn-cài-đặt)
- [Khởi Tạo Dịch Vụ](#khởi-tạo-dịch-vụ)
- [Các API Endpoint](#các-api-endpoint)
- [Chạy Với Docker](#chạy-với-docker)
- [Kiểm Thử](#kiểm-thử)
- [Đóng Góp](#đóng-góp)
- [Giấy Phép](#giấy-phép)

## Tính Năng
- Quản lý người dùng (CRUD) với soft delete
- Xác thực và phân quyền dựa trên vai trò
- Đăng ký và đăng nhập người dùng
- Lấy thông tin hồ sơ của người dùng đã xác thực
- Khởi tạo vai trò admin và người dùng admin mặc định
- Xác thực đầu vào và xử lý lỗi
- Tích hợp cơ sở dữ liệu MySQL
- Hỗ trợ đóng gói bằng Docker

## Công Nghệ
- **Java 17**
- **Spring Boot 3.5.0**
- **MySQL 8.0**
- **Spring Data JPA**
- **Spring Security**
- **MapStruct 1.6.3** (cho ánh xạ DTO)
- **Lombok** (giảm mã boilerplate)
- **Maven** (quản lý phụ thuộc)
- **Docker** và **Docker Compose** (đóng gói dịch vụ)

## Yêu Cầu Cần Có
Trước khi cài đặt dự án, hãy đảm bảo bạn đã cài đặt:
- **Java 17** (JDK): Đảm bảo biến môi trường `JAVA_HOME` được thiết lập.
- **Maven 3.9.10** (hoặc sử dụng Maven Wrapper đi kèm với `./mvnw`).
- **MySQL 8.0+**: Cài đặt và chạy MySQL server cục bộ.
- **Docker** và **Docker Compose** (tùy chọn, để chạy dịch vụ trong container).
- **Git**: Để clone repository.
- Công cụ kiểm thử API như **Postman** hoặc **cURL** (khuyến nghị).

## Hướng Dẫn Cài Đặt
Thực hiện các bước sau để cài đặt môi trường và chạy ứng dụng cục bộ.

### 1. Clone Repository
Clone mã nguồn từ GitHub:
```bash
git clone https://github.com/HieuT3/User-Management.git
cd User-Management
```

### 2. Cấu Hình MySQL
Tạo một cơ sở dữ liệu mới trong MySQL:
```sql
CREATE DATABASE user_management;
```
Cấu hình thông tin kết nối MySQL trong file `src/main/resources/application.yaml`:
```yaml
spring:
  datasource:
    username: root
    password: your_mysql_password
    url: jdbc:mysql://localhost:3306/user_management
```
Thay `your_mysql_password` bằng mật khẩu MySQL của bạn.  
**Lưu ý:**
- Nếu chạy trên **localhost** thì **url** sẽ là: **jdbc:mysql://localhost:3306/user_management**
- Nếu sử dụng **Docker** thì **url** sẽ là **jdbc:mysql://db:3306/user_management** (xem phần [Chạy Với Docker](#chạy-với-docker)).

### 3. Cài Đặt Phụ Thuộc
Sử dụng Maven để tải và cài đặt các phụ thuộc:
```bash
./mvnw clean install
```
Lệnh này sẽ tải tất cả các thư viện cần thiết được khai báo trong `pom.xml`.

### 4. Chạy Ứng Dụng
Chạy ứng dụng Spring Boot bằng Maven:
```bash
./mvnw spring-boot:run
```
Ứng dụng sẽ khởi động trên cổng `8080`. Bạn có thể truy cập API tại `http://localhost:8080`.

## Khởi Tạo Dịch Vụ
Khi ứng dụng khởi động lần đầu, dịch vụ sẽ tự động thực hiện các bước khởi tạo sau thông qua lớp `AdminAndRoleSeeder`:
- **Tạo Vai Trò**: Hai vai trò mặc định (`admin` và `user`) sẽ được tạo trong cơ sở dữ liệu nếu chưa tồn tại:
  - Vai trò `admin`: Có toàn bộ quyền, mô tả: "This is the admin role, which has all permissions."
  - Vai trò `user`: Quyền hạn chế, mô tả: "This is the user role, which has limited permissions."
- **Tạo Người Dùng Admin**: Một tài khoản admin mặc định sẽ được tạo nếu chưa tồn tại và bạn có thể sử dụng tài khoản này để sử dụng API:
  - Username: `admin`
  - Password: `Admin123456`
  - Email: `admin@gmail.com`
  - Phone: `0123456789`
  - FullName: `ADMIN`
  - Avatar URL: `URL`
  - Vai trò: Cả `admin` và `user`
- **Cơ Chế Soft Delete**: Người dùng và vai trò được đánh dấu `isDeleted = true` thay vì xóa vật lý khỏi cơ sở dữ liệu.

Để kiểm tra, đăng nhập bằng tài khoản admin sử dụng endpoint `/api/v1/auth/login` và tiếp tục sử dụng các endpoint khác (xem [Các API Endpoint](#các-api-endpoint)).

## Các API Endpoint
Dưới đây là danh sách các endpoint chính của API. Sử dụng công cụ như Postman để kiểm thử.

### Xác Thực (Auth)
- **POST /api/v1/auth/login**
  - Đăng nhập người dùng.
  - Body: `{"username": "admin", "password": "Admin123456"}`
  - Phản hồi: `{"success": true, "message": "Login successful"}`
- **POST /api/v1/auth/register**
  - Đăng ký người dùng mới (mặc định gán vai trò `user`).
  - Body: `{"fullName": "John Doe", "username": "john", "password": "Password123", "email": "john@example.com", "phone": "0987654321"}`
  - Phản hồi: Thông tin người dùng đã đăng ký.
- **GET /api/v1/auth/profile**
  - Lấy thông tin hồ sơ người dùng đã xác thực.
  - Yêu cầu: Header `Authorization` với token hoặc session.
  - Phản hồi: Thông tin người dùng.

### Quản Lý Vai Trò (Roles) - Yêu cầu vai trò ADMIN
- **GET /api/v1/roles**
  - Lấy danh sách tất cả vai trò.
- **GET /api/v1/roles/{roleId}**
  - Lấy thông tin vai trò theo ID.
- **GET /api/v1/roles/name/{roleName}**
  - Lấy thông tin vai trò theo tên (admin/user).
- **POST /api/v1/roles**
  - Thêm vai trò mới.
  - Body: `{"roleName": "user", "description": "New user role"}`
- **DELETE /api/v1/roles/{roleId}**
  - Xóa mềm vai trò theo ID.
- **DELETE /api/v1/roles/name/{roleName}**
  - Xóa mềm vai trò theo tên.

### Quản Lý Người Dùng (Users) - Yêu cầu vai trò ADMIN
- **GET /api/v1/users**
  - Lấy danh sách tất cả người dùng.
- **GET /api/v1/users/{userId}**
  - Lấy thông tin người dùng theo ID.
- **POST /api/v1/users**
  - Thêm người dùng mới.
  - Body: `{"fullName": "Jane Doe", "username": "jane", "password": "Password123", "email": "jane@example.com", "phone": "0987654321", "roles": ["user"]}`
- **PUT /api/v1/users/{userId}**
  - Cập nhật thông tin người dùng.
  - Body: `{"fullName": "Jane Updated", "email": "jane.updated@example.com"}`
- **PUT /api/v1/users/{userId}/status**
  - Cập nhật trạng thái người dùng (active/nonactive).
  - Body: `{"status": "nonactive"}`
- **DELETE /api/v1/users/{userId}**
  - Xóa mềm người dùng theo ID.

## Chạy Với Docker
Docker được sử dụng để đóng gói ứng dụng và cơ sở dữ liệu MySQL, giúp triển khai dễ dàng và nhất quán.

### 1. Cài Đặt Docker
Cài đặt **Docker** và **Docker Compose** trên máy của bạn:
- [Docker Installation Guide](https://docs.docker.com/get-docker/)
- [Docker Compose Installation Guide](https://docs.docker.com/compose/install/)
Đảm bảo Docker đang chạy:
```bash
docker -v
docker compose version
```

### 2. Chạy Ứng Dụng Với Docker
Từ thư mục gốc của dự án, chạy lệnh:
```bash
docker compose up -d
```
Lệnh này sẽ:
- Build image cho ứng dụng Spring Boot.
- Khởi động container MySQL và tạo cơ sở dữ liệu `user_management`.
- Khởi động ứng dụng và kết nối với MySQL.
Truy cập API tại `http://localhost:8080`.

Để dừng các container:
```bash
docker compose down
```
Để xóa dữ liệu MySQL (nếu cần reset):
```bash
docker compose down -v
```