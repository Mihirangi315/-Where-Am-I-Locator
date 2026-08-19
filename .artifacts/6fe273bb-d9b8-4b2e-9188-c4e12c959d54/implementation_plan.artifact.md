# Implementation Plan - Where Am I? Locator

Build a simple Android app that fetches and displays the device's current location once when a button is tapped.

## User Review Required

> [!IMPORTANT]
> The app will use `play-services-location` for the Fused Location Provider. I will add this to the version catalog and project dependencies.

## Proposed Changes

### Project Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/rajap_rbgcbku/AndroidStudioProjects/WhereAmI/gradle/libs.versions.toml)
- Add `play-services-location` version and library definition.

#### [MODIFY] [build.gradle.kts](file:///C:/Users/rajap_rbgcbku/AndroidStudioProjects/WhereAmI/app/build.gradle.kts)
- Add `implementation(libs.play.services.location)` to the dependencies block.

#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/rajap_rbgcbku/AndroidStudioProjects/WhereAmI/app/src/main/AndroidManifest.xml)
- Add `android.permission.ACCESS_FINE_LOCATION` and `android.permission.ACCESS_COARSE_LOCATION`.

---

### UI Layer

#### [MODIFY] [activity_main.xml](file:///C:/Users/rajap_rbgcbku/AndroidStudioProjects/WhereAmI/app/src/main/res/layout/activity_main.xml)
- Replace the "Hello World" TextView with a layout containing:
    - App title TextView
    - "Get My Location" Button
    - TextViews for Latitude, Longitude, Accuracy, and Timestamp.
    - Status TextView for errors/messages.

---

### Logic Layer

#### [MODIFY] [MainActivity.kt](file:///C:/Users/rajap_rbgcbku/AndroidStudioProjects/WhereAmI/app/src/main/java/com/example/whereami/MainActivity.kt)
- Implement `FusedLocationProviderClient`.
- Add permission request logic using `ActivityResultLauncher`.
- Implement `getCurrentLocation()` call.
- Update UI components with location data or error messages.

## Verification Plan

### Automated Tests
- N/A for this simple UI-focused task, manual verification is primary.

### Manual Verification
1. Deploy the app to an Android emulator.
2. Set a mock location in the emulator's Extended Controls.
3. Open the app and tap "Get My Location".
4. Accept the location permission request.
5. Verify that the correct Latitude, Longitude, Accuracy, and Timestamp are displayed.
6. Verify that denying permission shows the appropriate status message.
