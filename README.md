# Intelligent Task Manager (ML-Powered)

A task management backend that uses machine learning to automatically set task priority and estimate how long a task will take.

## Architecture
- Spring Boot REST API
- Python FastAPI ML microservice
- scikit-learn models (TF-IDF + RandomForest)

![intelligent-task-manger-architecture.jpg](src/main/resources/images/intelligent-task-manger-architecture.jpg)
## Features
- Create and manage tasks
- Automatic task priority prediction
- Automatic time estimation
- A safe fallback system if the ML service is not available

## Tech stack
- Java 17, Spring Boot
- Python, FastAPI
- scikit-learn, pandas
- H2 / PostgreSQL

## How it works
1. Client sends task title & description
2. Spring Boot calls Python ML service
3. ML service predicts priority and time
4. Task is saved with predictions
