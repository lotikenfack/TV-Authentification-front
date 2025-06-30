# TV Authentification Frontend (Android TV)

## Description
Application Android TV native (Kotlin) servant de portail d'authentification, débloquant l'accès à la TV après connexion, avec bouton de déconnexion flottant.

## Technologies
- Kotlin (API 21+)
- Jetpack Leanback
- Retrofit
- Coroutines
- AndroidX Security (EncryptedSharedPreferences)
- WorkManager

## Fonctionnalités principales
- Application launcher par défaut (HOME/LEANBACK)
- Écran de connexion optimisé D-pad
- Authentification sécurisée (JWT)
- Heartbeat session (WorkManager)
- Déconnexion automatique après inactivité
- Bouton de déconnexion flottant (Service)

## Installation
1. Ouvrez ce dossier dans Android Studio.
2. Ajoutez les dépendances suivantes dans `build.gradle` :
   - `implementation "androidx.leanback:leanback:1.0.0"`
   - `implementation "androidx.security:security-crypto:1.1.0-alpha06"`
   - `implementation "androidx.work:work-runtime-ktx:2.7.1"`
   - `implementation "com.squareup.retrofit2:retrofit:2.9.0"`
   - `implementation "com.squareup.retrofit2:converter-gson:2.9.0"`
   - `implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0"`
3. Ajoutez les icônes nécessaires (`ic_logout`, `rounded_bg`) dans `res/drawable`.
4. Modifiez l'URL du backend dans `ApiClient.kt`.
5. Définissez l'application comme launcher par défaut sur l'appareil de test.
6. Pour le bouton flottant, Android TV peut nécessiter une autorisation manuelle pour SYSTEM_ALERT_WINDOW.

## Structure du code
- `AndroidManifest.xml` : Permissions, launcher, service flottant
- `MainApplication.kt` : Initialisation, heartbeat
- `MainActivity.kt` : Navigation, timer inactivité
- `LoginFragment.kt` + `fragment_login.xml` : UI connexion
- `FloatingLogoutService.kt` + `floating_logout_button.xml` : Bouton flottant
- `AuthManager.kt` : Gestion token sécurisé
- `ApiService.kt`, `ApiClient.kt` : Réseau
- `SessionHeartbeatWorker.kt` : Vérification session

## Notes
- Le bouton flottant reste visible tant que la session est valide.
- En cas d'invalidation, l'app revient au premier plan et affiche l'écran de connexion avec le message d'erreur.
- Le bouton retour fonctionne normalement après authentification.