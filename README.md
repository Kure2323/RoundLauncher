# RoundLauncher
Launcher minimalista para dispositivos _Android_ enfocado en la velocidad, simplicidad y productividad
Escrito en _Kotlin_ con _Jetpack Compose_
Han sido usadas las siguientes librerías:
- _LauncherApps_
- _Navigation for Compose_
- _Room_
- _SharedPreferences_
- _BroadcastReceiver_

## Imágenes
### | _Home_ |
<img width="250" alt="home1" src="https://github.com/user-attachments/assets/5f5ef422-7b89-4d03-8193-b9aa86d0744e" />
<img width="250" alt="home2" src="https://github.com/user-attachments/assets/3f63c84f-64c0-4627-a9a6-1b517ce2e05d" />

### | _Drawer_  |  _Settings_ |
<img width="250" alt="drawer" src="https://github.com/user-attachments/assets/dd8643b1-13b3-4651-9644-c0c9c2a68df8" />
<img width="250" alt="settings" src="https://github.com/user-attachments/assets/e62524b5-fd6f-45fe-9b8e-c2145e7d4542" />

## Instalar última versión
<img width="250" alt="qr" src="https://github.com/user-attachments/assets/328059ee-0d3c-4f79-a188-9313ad027162" />

## Instalar proyecto
Ejecuta: 
```bash
git clone https://github.com/Kure2323/RoundLauncher.git
cd RoundLauncher
```

## Estructura de carpetas
```bash
roundlauncher
├── Container.kt
├── data
│   ├── local
│   │   ├── dao
│   │   │   └── AppKeyDao.kt
│   │   ├── db
│   │   │   └── AppDatabase.kt
│   │   └── entity
│   │       └── AppKey.kt
│   ├── repo
│   │   └── AppKeyRepo.kt
│   └── UApp.kt
├── MainActivity.kt
├── MainViewModel.kt
├── navigation
│   ├── AppNavigation.kt
│   └── Screens.kt
├── system
│   ├── cache
│   │   ├── AppCache.kt
│   │   └── IconCache.kt
│   ├── RLBroadcastReceiver.kt
│   └── Util.kt
└── ui
    ├── components
    |   └── SmallItems.kt
    ├── drawpage
    |   └── DrawerPage.kt
    ├── homepage
    │   └── HomePage.kt
    ├── settingspage
    │   ├── SettingsK.kt
    │   └── SettingsPage.kt
    └── theme
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```
