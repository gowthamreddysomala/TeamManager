# 🚀 Team Manager Application - Database Schema

Welcome to the database schema documentation for the Team Manager application! This guide provides a detailed overview of the underlying data structure that powers our project and task management system.

The database schema is intelligently managed by **Spring Data JPA** and **Hibernate**, meaning it's automatically generated and updated based on our Java entity definitions located in the `com.task.TeamManager.model` package.

---

## 📋 1. Schema Overview

Our database is structured around five core entities, designed to manage users, their roles, various projects, and the tasks within them:

* **👥 `users`**: Where all user authentication and profile information resides.
* **🔑 `roles`**: Defines the different access levels and responsibilities within the application.
* **🤝 `user_roles`**: The crucial link that connects users to their assigned roles.
* **🏗️ `projects`**: Details about all the exciting projects being managed.
* **✅ `tasks`**: The actionable items, linked to projects and assigned to team members.

---

## 📊 2. Table Details

Let's dive into the specifics of each table:

### 2.1. 👥 `users` Table

* **Purpose**: Manages user accounts, profiles, and login credentials.
* **Columns**:
    * `id` (`BIGINT`): Primary Key, auto-generated unique identifier.
    * `username` (`VARCHAR(255)`): User's unique login name. **`NOT NULL`, `UNIQUE`**.
    * `email` (`VARCHAR(255)`): User's email address. **`NOT NULL`, `UNIQUE`**.
    * `password` (`VARCHAR(255)`): Hashed password for security. **`NOT NULL`**.
    * `created_at` (`DATETIME` / `TIMESTAMP`): Timestamp of account creation. **`NOT NULL`**, auto-generated on insert.

### 2.2. 🔑 `roles` Table

* **Purpose**: Defines system-wide roles that can be assigned to users.
* **Columns**:
    * `id` (`BIGINT`): Primary Key, auto-generated unique identifier.
    * `name` (`VARCHAR(255)`): Unique name of the role (e.g., `'ROLE_ADMIN'`, `'ROLE_USER'`). **`NOT NULL`, `UNIQUE`**.

### 2.3. 🤝 `user_roles` Table

* **Purpose**: A dedicated join table to handle the **Many-to-Many** relationship between `users` and `roles`. This allows a user to have multiple roles, and a role to be assigned to multiple users.
* **Columns**:
    * `user_id` (`BIGINT`): Part of composite Primary Key. **`NOT NULL`**.
    * `role_id` (`BIGINT`): Part of composite Primary Key. **`NOT NULL`**.
* **Primary Key**: (`user_id`, `role_id`) - ensures unique user-role combinations.
* **Foreign Keys**:
    * `user_id` ➡️ references `users.id`
    * `role_id` ➡️ references `roles.id`

### 2.4. 🏗️ `projects` Table

* **Purpose**: Stores details and metadata for each project.
* **Columns**:
    * `id` (`BIGINT`): Primary Key, auto-generated unique identifier.
    * `name` (`VARCHAR(255)`): Name of the project. **`NOT NULL`**.
    * `description` (`TEXT`): Detailed project description. (Optional).
    * `start_date` (`DATE`): The project's official start date. **`NOT NULL`**.
    * `end_date` (`DATE`): The project's planned completion date. (Optional, allows for ongoing projects).
    * `project_manager_id` (`BIGINT`): **Foreign Key**, links to the `User` managing this project. **`NOT NULL`**.
* **Foreign Keys**:
    * `project_manager_id` ➡️ references `users.id`

### 2.5. ✅ `tasks` Table

* **Purpose**: Manages individual tasks, linking them to projects and assigning them to team members.
* **Columns**:
    * `id` (`BIGINT`): Primary Key, auto-generated unique identifier.
    * `title` (`TEXT`): Short, descriptive title of the task. **`NOT NULL`**.
    * `description` (`TEXT`): Detailed description of the task. (Optional).
    * `status` (`VARCHAR(255)`): Current state of the task (e.g., `'TO_DO'`, `'IN_PROGRESS'`, `'DONE'`). **`NOT NULL`**.
    * `priority` (`VARCHAR(255)`): Importance level of the task (e.g., `'LOW'`, `'MEDIUM'`, `'HIGH'`, `'CRITICAL'`). **`NOT NULL`**.
    * `due_date` (`DATE`): The target completion date for the task. (Optional).
    * `project_id` (`BIGINT`): **Foreign Key**, links to the parent project. **`NOT NULL`**.
    * `assigned_to_id` (`BIGINT`): **Foreign Key**, links to the `User` assigned to the task. (Optional, allows unassigned tasks).
    * `created_at` (`DATETIME` / `TIMESTAMP`): Timestamp when the task was created. **`NOT NULL`**, auto-generated on insert.
* **Foreign Keys**:
    * `project_id` ➡️ references `projects.id`
    * `assigned_to_id` ➡️ references `users.id`

---

## 🔗 3. Relationships at a Glance

Our schema establishes the following crucial connections:

* **👥 User 🤝 Role**:
    * **Type**: Many-to-Many
    * **How**: Explicitly managed by the `user_roles` join table.
        * `User` has many `User_Roles`.
        * `Role` has many `User_Roles`.
        * Each `User_Roles` entry connects one `User` to one `Role`.
* **👥 User ➡️ 🏗️ Project (Manager)**:
    * **Type**: One-to-Many (One `User` can manage multiple `Projects`).
    * **How**: `projects.project_manager_id` column points to `users.id`.
* **👥 User ➡️ ✅ Task (Assigned To)**:
    * **Type**: One-to-Many (One `User` can be assigned multiple `Tasks`).
    * **How**: `tasks.assigned_to_id` column points to `users.id`.
* **🏗️ Project ➡️ ✅ Task**:
    * **Type**: One-to-Many (One `Project` can have multiple `Tasks`).
    * **How**: `tasks.project_id` column points to `projects.id`.

---

## 💡 4. Enum Representation

We utilize Java enums (`ETaskStatus` and `ETaskPriority`) for type safety and readability in our application code. In the database, these enums are persisted as `VARCHAR` (String) values. This means you'll see the exact enum names (e.g., `"TO_DO"`, `"HIGH"`) stored in the `status` and `priority` columns of the `tasks` table.

---

## ⚙️ 5. Database Setup & Access

This schema is designed to be effortlessly managed by Spring Data JPA and Hibernate.

* **Automatic Schema Generation**: For convenience during development, ensure your `application.properties` (or `application.yml`) includes a `spring.jpa.hibernate.ddl-auto` setting (e.g., `update`, `create`, `create-drop`). Hibernate will then automatically create or update your database schema based on the entity definitions.
* **Local Development**: If you're running with an embedded H2 database, you can usually access its web console at `http://localhost:8080/h2-console` (adjust port if necessary) once the application is running, provided `spring.h2.console.enabled=true` is set.
* **External Databases**: For production-grade databases like PostgreSQL or MySQL, configure your connection details in `application.properties` and use your preferred database management tool to connect and inspect the schema.

---