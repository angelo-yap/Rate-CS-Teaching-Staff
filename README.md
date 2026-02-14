# How to run locally
Prerequisites:
- Java 17+
- Git
- Render PostgreSQL database (or local)

### Clone and build
- Clone the repo 
```bash
git clone https://github.com/angelo-yap/Rate-CS-Teaching-Staff.git
```
- Build by using 
```bash
./mvnw clean package
```

### Create .env file
- Add variables
```bash
DB_URL = jdbc:postgresql://dbg (paste external url after @dbg)
DB_USER = (database username)
DB_PASS = (database password)
```

> [!WARNING] 
> Add .env to .gitignore to prevent your database information being committed

### Load environment variables & run
```bash
set -a
source .env
set +a
./mvnw spring-boot:run
```

# How to deploy (Render)

### Create Render PostgreSQL database
- Dashboard → New service → Postgres
  - Make sure PostgreSQL version is set to the latest version 
- edit .env variables accordingly to match database credentials using **internal URL instead of external**

### Create Render web service
- Dashboard → New service → Web Service
  - Link your repository to the Web Service
  - Make sure runtime environment is set to Docker
  - Set environment variables to the same values as your .env file

## Follow the given Render URL and enjoy your new webapp!









