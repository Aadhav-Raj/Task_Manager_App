# Task_Manager_App


This is a Task Manager application built for Android that allows users to manage their tasks efficiently. The app provides user authentication, CRUD operations, and communicates with a Django backend server using Retrofit.

Features:

User Authentication: Secure login and registration with backend validation.
Token Authorization: Implements token-based authentication for secure API access
Email Verification: Email verification in Django using Gmail SMTP integration or Gmail email backend to send verification links.
Task Management: Users can create, view, update, and delete tasks.
Networking: Uses Retrofit to communicate with a Django backend.
Data Persistence: Handles data seamlessly between the client and server.
Technologies Used
Kotlin: Primary language for Android development.
Retrofit: For network requests and data parsing.
Django: Backend server for handling data and user authentication.
REST API: Provides endpoints for user authentication and task CRUD operations.

The app communicates with a custom Django backend under the link https://github.com/Aadhav-Raj/task_manager_web

Prerequisites:
Android Studio
Kotlin 1.5+
Django 3.0+
Django REST Framework
