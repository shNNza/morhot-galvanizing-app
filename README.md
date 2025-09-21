# Morhot Galvanizing Android App

A modern Android application for digitally capturing job cards and syncing them with Pastel accounting system.

## Features

- **Digital Job Card Capture**: Create, edit, and manage job cards on tablets
- **Offline Support**: Work offline and sync when connectivity is restored
- **Pastel Integration**: Automatic synchronization with Pastel accounting system
- **Secure Authentication**: Encrypted user credentials and secure API communication
- **Modern UI**: Built with Jetpack Compose for a smooth user experience

## Architecture

The app follows Clean Architecture principles with:

- **UI Layer**: Jetpack Compose screens and ViewModels
- **Domain Layer**: Use cases and repository interfaces
- **Data Layer**: Room database, network APIs, and repository implementations
- **Dependency Injection**: Hilt for managing dependencies

## Key Components

### Data Models
- `JobCard`: Main entity for job information
- `JobCardItem`: Line items for detailed job breakdown
- `SyncQueue`: Background synchronization management

### Database
- Room database with DAOs for local storage
- Type converters for Date handling
- Foreign key relationships

### Network
- Retrofit for Pastel API communication
- DTOs for API request/response mapping
- Authentication and token management

### Background Sync
- WorkManager for reliable background synchronization
- Retry logic for failed sync operations
- Queue-based sync management

## Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd morhot-galvanizing-app
   ```

2. **Configure Pastel API**
   - Update the base URL in `NetworkModule.kt`
   - Configure authentication endpoints as per your Pastel setup

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Install on device/emulator**
   ```bash
   ./gradlew installDebug
   ```

## Configuration

### Pastel API Integration
Update the following in `NetworkModule.kt`:
```kotlin
.baseUrl("https://your-pastel-api.com/v1/")
```

### Authentication
The app uses encrypted SharedPreferences for secure credential storage. No additional configuration needed.

### Database
Room database will be created automatically on first app launch.

## Usage

1. **Login**: Enter your Pastel credentials
2. **Create Job Cards**: Fill in client and job details
3. **Offline Work**: Continue working without internet
4. **Automatic Sync**: Data syncs automatically when online

## Security Features

- Encrypted SharedPreferences for sensitive data
- Secure network communication with HTTPS
- Token-based authentication
- Credential exclusion from backups

## Dependencies

- **UI**: Jetpack Compose, Material Design 3
- **Architecture**: Hilt, ViewModels, Navigation
- **Database**: Room
- **Network**: Retrofit, OkHttp
- **Background**: WorkManager
- **Security**: EncryptedSharedPreferences

## Development

### Adding New Features
1. Create data models in `data/entity`
2. Add DAOs in `data/dao`
3. Update database schema
4. Create repository interfaces and implementations
5. Build UI screens with Compose
6. Add navigation routes

### Testing
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## Build Variants

- **Debug**: Development build with logging
- **Release**: Production build with optimizations

## Support

For technical support or feature requests, contact the development team.

## License

Proprietary - Morhot Galvanizing