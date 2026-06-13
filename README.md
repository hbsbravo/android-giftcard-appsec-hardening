# Android Gift Card AppSec Hardening

## Overview

This project is a mobile application security hardening exercise for an Android gift card application. The original app contained several insecure design and implementation patterns, including unsafe Intent usage, overly exposed Activities, insecure HTTP communication, unnecessary permissions, and privacy-invasive telemetry behavior.

The goal of this project was to review the Android codebase, identify risky behavior, and apply practical mobile AppSec remediations.

## Security Focus Areas

- Android Intent security
- Explicit vs implicit Intents
- Activity exposure and `android:exported`
- AndroidManifest hardening
- HTTPS migration
- REST API access control analysis
- Privacy and permission minimization
- Secure mobile development practices

## Tools Used

- Android Studio
- Kotlin
- Gradle
- Android Emulator
- Logcat
- GitHub Actions
- AndroidManifest.xml review
- curl / API testing

## Key Security Improvements

### 1. Secure Intent Handling

The app used Intents to move between components. Insecure or overly broad Intent usage can allow other apps to intercept or trigger unintended behavior.

**Remediation:**

- Reviewed Intent usage in app fragments.
- Preferred explicit Intents for internal app navigation.
- Reduced unnecessary exposure to external apps.

### 2. Activity Exposure Hardening

The application did not require external applications to launch its internal Activities.

**Remediation:**

- Reviewed `AndroidManifest.xml`.
- Restricted exported Activities.
- Removed unnecessary intent filters where external access was not required.
- Set Activities to non-exported where appropriate.

### 3. HTTPS Migration

The app used insecure HTTP communication for backend API calls.

**Remediation:**

- Replaced HTTP URLs with HTTPS URLs across app components.
- Updated API-related files and string resources.
- Reduced risk of plaintext credential/session/token exposure.

### 4. Broken Access Control Analysis

The app included behavior where a user could potentially use a gift card that did not belong to them.

**Finding:**

This issue could not be fully fixed client-side because ownership validation must be enforced by the backend API.

**Security Lesson:**

Client-side checks can improve user experience, but authorization decisions must be enforced server-side.

### 5. Privacy and Permission Cleanup

The app requested or used functionality that was not strictly necessary for buying, browsing, or using gift cards.

**Remediation:**

- Reviewed app permissions.
- Removed unnecessary data collection behavior.
- Reduced privacy-invasive functionality.
- Minimized access to sensors and user data.

## Project Structure

```text
.
├── .github/
├── GiftcardSite/
├── .gitignore
├── .gitattributes
└── README.md