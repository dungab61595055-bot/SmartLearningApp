# Smart Learning App

Một ứng dụng Android để hỗ trợ quản lý học tập và đánh giá kiến thức thông qua các bài trắc nghiệm.

## Tính năng chính

- 📱 **Đăng ký / Đăng nhập**: Hệ thống xác thực người dùng
- 📚 **Quản lý môn học**: Tạo, chỉnh sửa, xóa các môn học
- 📅 **Quản lý lịch học**: Lên kế hoạch thời gian học tập
- ✓ **Quản lý bài tập**: Tạo và theo dõi tiến độ bài tập
- 🎯 **Trắc nghiệm**: Làm bài kiểm tra trắc nghiệm
- 📊 **Thống kê**: Xem kết quả học tập và thống kê
- 👤 **Hồ sơ người dùng**: Quản lý thông tin cá nhân

## Cấu trúc dự án

```
SmartLearningApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/smartlearning/app/
│   │   │   │   ├── activity/          # Activities
│   │   │   │   ├── adapter/           # RecyclerView Adapters
│   │   │   │   ├── dao/               # Data Access Objects
│   │   │   │   ├── database/          # Database Helper
│   │   │   │   ├── model/             # Data Models
│   │   │   │   └── utils/             # Utilities
│   │   │   ├── res/
│   │   │   │   ├── layout/            # XML Layouts
│   │   │   │   ├── values/            # Strings, Colors, Styles
│   │   │   │   ├── drawable/          # Drawables
│   │   │   │   └── menu/              # Menu Resources
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle
├── build.gradle
├── gradle.properties
└── settings.gradle
```

## Công nghệ sử dụng

- **Language**: Java
- **Database**: SQLite
- **UI Framework**: Android AppCompat + Material Design
- **Build Tool**: Gradle
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 34 (Android 14)

## Hướng dẫn cài đặt

### Yêu cầu
- Android Studio Arctic Fox (2020.3.1) hoặc cao hơn
- JDK 8 hoặc cao hơn
- Android SDK 34

### Cài đặt

1. Clone repository:
```bash
git clone https://github.com/dungab61595055-bot/SmartLearningApp.git
```

2. Mở trong Android Studio:
   - File > Open > Chọn thư mục dự án

3. Chạy ứng dụng:
   - Click Run > Run 'app'
   - Hoặc nhấn Shift + F10

## Hướng dẫn sử dụng

### 1. Đăng ký tài khoản
- Nhập thông tin: Tên, Email, Mật khẩu, Số điện thoại
- Click "Đăng ký"

### 2. Đăng nhập
- Nhập Email và Mật khẩu
- Click "Đăng nhập"

### 3. Quản lý môn học
- Vào mục "Môn học"
- Click "+Thêm môn học" để tạo môn học mới
- Nhập tên, mô tả, và chọn màu

### 4. Lên kế hoạch học tập
- Vào mục "Lịch"
- Tạo các buổi học theo lịch trình
- Theo dõi và quản lý thời gian học tập

### 5. Làm bài trắc nghiệm
- Vào mục "Trắc nghiệm"
- Chọn bài kiểm tra muốn làm
- Trả lời các câu hỏi
- Xem kết quả

### 6. Theo dõi tiến độ
- Vào mục "Thống kê"
- Xem điểm trung bình, số bài hoàn thành, v.v.

## Cấu trúc Database

### Bảng Users
```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    phone TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)
```

### Bảng Subjects
```sql
CREATE TABLE subjects (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    description TEXT,
    color TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
)
```

### Bảng Schedules
```sql
CREATE TABLE schedules (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    subject_id INTEGER NOT NULL,
    date TEXT NOT NULL,
    start_time TEXT NOT NULL,
    end_time TEXT NOT NULL,
    title TEXT NOT NULL,
    type TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (subject_id) REFERENCES subjects(id)
)
```

### Bảng Tasks
```sql
CREATE TABLE tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    subject_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    due_date TEXT NOT NULL,
    is_completed INTEGER DEFAULT 0,
    completed_date TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (subject_id) REFERENCES subjects(id)
)
```

### Bảng Quizzes
```sql
CREATE TABLE quizzes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    subject_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    total_questions INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (subject_id) REFERENCES subjects(id)
)
```

### Bảng Questions
```sql
CREATE TABLE questions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    quiz_id INTEGER NOT NULL,
    content TEXT NOT NULL,
    option_a TEXT NOT NULL,
    option_b TEXT NOT NULL,
    option_c TEXT NOT NULL,
    option_d TEXT NOT NULL,
    correct_answer TEXT NOT NULL,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
)
```

### Bảng Quiz Results
```sql
CREATE TABLE quiz_results (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    quiz_id INTEGER NOT NULL,
    score REAL,
    total_questions INTEGER,
    correct_answers INTEGER,
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
)
```

## Đóng góp

Nếu bạn muốn đóng góp cho dự án này, vui lòng:
1. Fork repository
2. Tạo branch mới (`git checkout -b feature/AmazingFeature`)
3. Commit thay đổi (`git commit -m 'Add some AmazingFeature'`)
4. Push đến branch (`git push origin feature/AmazingFeature`)
5. Mở Pull Request

## License

Dự án này được cấp phép theo MIT License - xem file [LICENSE](LICENSE) để biết chi tiết.

## Liên hệ

- Email: dungab61595055@gmail.com
- GitHub: [@dungab61595055-bot](https://github.com/dungab61595055-bot)

---

**Phát triển bởi**: Smart Learning Team
**Phiên bản**: 1.0.0
**Cập nhật lần cuối**: 08/06/2026
