# InfiniteLevelMessagingBackend

## Overview
The **InfiniteLevelMessagingBackend** is a Java-based web application that implements a tree-structured message board with infinite nesting capabilities. This project serves as an exercise to demonstrate proficiency in backend development, database design, RESTful API creation, and user authentication. The system allows users to register, log in, post messages, and comment on messages with unlimited nesting levels. The backend is built using Java, while the frontend can be implemented using any modern framework (e.g., React, Vue, Angular, or jQuery).

---

## Features

### User Registration
- Users can register by providing a username, password, and email.
- **Username Requirements**:
  - Must be unique.
  - Length between 5-20 characters.
  - Can only contain letters and numbers.
- **Password Requirements**:
  - Length between 8-20 characters.
  - Must include at least one uppercase letter, one lowercase letter, one number, and one special character.
- **Email Requirements**:
  - Must be unique and in a valid format.
  - No email confirmation is required for simplicity.

### User Login
- Users can log in using either their username or email along with their password.
- **"Remember Me" Feature**:
  - If enabled, the user remains logged in for a month.
  - If disabled, the session expires when the browser is closed.
- After logging in, the username and email are displayed at the top of the page.

### Message Posting
- Logged-in users can post messages.
- **Message Requirements**:
  - Length between 3-200 characters.
  - Supports Chinese characters.
  - Dynamic feedback on the remaining character count is provided while typing.
  - The posting time is recorded.

### Commenting and Replying
- Users can comment on any message.
- **Comment Requirements**:
  - Same as message requirements (3-200 characters).
- Users can reply to comments, allowing for infinite nesting of replies.

### Viewing Messages
- All messages and nested comments are displayed in a tree structure on a single page.
- The backend generates the complete tree structure and sends it to the frontend in one response.
- Messages are sorted in reverse chronological order, with the newest messages at the top.
- Each message displays the poster's username and posting time.
- Viewing messages does not require logging in.

---

## Technical Requirements

### Backend
- Built using Java with any framework (e.g., Spring Boot, Jakarta EE).
- Provides RESTful APIs for all functionalities.
- Proper permission checks and HTTP status codes are implemented.
- Passwords are stored using irreversible encryption (e.g., bcrypt).

### Database
- A database (relational or NoSQL) is used to store user data, messages, and comments.
- Recommended to use a file-based database like SQLite for simplicity.
- Tables are created and managed using SQL, NoSQL, or ORM (e.g., Hibernate).

### Frontend
- Can be implemented using any frontend framework (e.g., React, Vue, Angular, jQuery).
- Communicates with the backend via RESTful APIs.
- Displays the tree-structured messages and comments dynamically.

### Initialization and Startup
- A command is provided to initialize and start the website.
- The homepage opens automatically in a browser after startup.

---

## Database Design

### Tables
1. **Users**:
   - `id` (Primary Key)
   - `username` (Unique)
   - `password` (Encrypted)
   - `email` (Unique)
   - `created_at` (Timestamp)

2. **Messages**:
   - `id` (Primary Key)
   - `user_id` (Foreign Key to Users)
   - `content` (Text)
   - `created_at` (Timestamp)

3. **Comments**:
   - `id` (Primary Key)
   - `user_id` (Foreign Key to Users)
   - `message_id` (Foreign Key to Messages)
   - `parent_comment_id` (Foreign Key to Comments, for nesting)
   - `content` (Text)
   - `created_at` (Timestamp)

---

## RESTful API Endpoints

### Authentication
- **POST /api/register**: Register a new user.
- **POST /api/login**: Log in a user.
- **POST /api/logout**: Log out a user.

### Messages
- **GET /api/messages**: Fetch all messages with nested comments.
- **POST /api/messages**: Post a new message (requires authentication).

### Comments
- **POST /api/comments**: Post a comment on a message or reply to another comment (requires authentication).

---

## Setup and Running the Project

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/InfiniteLevelMessagingBackend.git
   cd InfiniteLevelMessagingBackend
   ```

2. **Initialize the Database**:
   - Run the SQL scripts to create the necessary tables.
   - Alternatively, configure the ORM to auto-generate the schema.

3. **Build and Run the Backend**:
   - Use Maven or Gradle to build the project.
   - Start the application using the provided command:
     ```bash
     ./start.sh
     ```

4. **Check if the application is running**:
   - Run:
     ```bash
     netstat -tulnp | grep 8080
     ```
   - Or check logs:
     ```bash
     tail -f logs/app.log
     ```

5. **Access the Application**:
   - Open your browser and navigate to:
     ```
     http://localhost:8080
     ```
   - Or, modify the script to open automatically:
     ```bash
     ./start.sh && xdg-open http://localhost:8080
     ```
     *(MacOS users replace `xdg-open` with `open`.)*

---

## Troubleshooting
- If the server doesn't start, ensure you have Java and Maven/Gradle installed.
- Check logs for any database connection issues.
- If port 8080 is in use, change the port in `application.properties`:
  ```properties
  server.port=9090
  ```

