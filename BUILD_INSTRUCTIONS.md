# Building the Morhot Galvanizing Android App

## Prerequisites
- Android Studio (recommended) OR
- Android SDK + Java JDK 8 or higher

## Method 1: Using Android Studio (Easiest)

1. **Download and Install Android Studio**
   - Go to: https://developer.android.com/studio
   - Download and install Android Studio
   - Follow the setup wizard to install Android SDK

2. **Open the Project**
   - Launch Android Studio
   - Choose "Open an existing project"
   - Navigate to: `C:\Users\krwhi\OneDrive\Documents\Development\morhot-galvanizing-app`
   - Click "OK"

3. **Wait for Sync**
   - Android Studio will automatically sync Gradle dependencies
   - This may take a few minutes on first run

4. **Build APK**
   - Click "Build" in the menu bar
   - Select "Build Bundle(s) / APK(s)"
   - Choose "Build APK(s)"
   - Wait for build to complete

5. **Find Your APK**
   - APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`
   - Click "locate" in the build notification to open the folder

## Method 2: Command Line

If you have Android SDK configured:

```bash
cd morhot-galvanizing-app
gradlew.bat assembleDebug
```

## Installing on Device

1. **Enable Developer Options on your Android device:**
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Go back to Settings → Developer Options
   - Enable "USB Debugging" and "Install unknown apps"

2. **Install the APK:**
   - Copy `app-debug.apk` to your device
   - Open file manager and tap the APK
   - Allow installation from unknown sources if prompted
   - Tap "Install"

## Troubleshooting

- **Gradle sync fails**: Check internet connection, Android Studio will download dependencies
- **Build errors**: Make sure you have the latest Android SDK and build tools
- **Installation fails**: Ensure "Unknown sources" is enabled in device settings

## App Features to Test

1. **Login Screen**: See the Morhot logo and red branding
2. **Job Cards List**: Create and view job cards
3. **Job Card Creation**: Add client details and job information
4. **Offline Storage**: Data saves locally even without internet

The app is currently configured for development/testing and includes placeholder authentication (any username/password will work for demo purposes).