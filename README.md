# 🐦 TFollowers – Twitter-Like Kotlin App

**TFollowers** is a sleek, modern Kotlin-based Android app that mimics core functionality of Twitter, allowing users to add, manage, and customize their own followers and following all locally. Designed to showcase UI/UX, Room database use, and image handling with Glide.


## ✨ Features

- 👤 Add & manage followers/following locally
- 📸 Pick profile pictures using ImagePicker & Glide
- 🧠 Offline data storage with Room DB
- 🔄 Edit, delete, or update profiles
- 📱 Material Design components with a clean interface
- 🧩 Circle Image Views for profile aesthetics


## 📸 Screenshots

| Add Profile | Followers List |
|-------------|----------------|
| <img src="assets/screenshots/add_profile.png" width="300"/> | <img src="assets/screenshots/followers_list.png" width="300"/> |



## 🚀 Getting Started

git clone https://github.com/Fasih45100/TFollowers.git
cd TFollowers
Open the project in Android Studio and click Run.

🛠️ Tech Stack
Kotlin – Primary development language
Room – For local database operations
Glide – Efficient image loading
ImagePicker – User profile photo selection
Material Design – Beautiful UI components

📦 Dependencies
// Core
implementation 'androidx.core:core-ktx:1.16.0'
implementation 'androidx.appcompat:appcompat:1.7.1'
implementation 'com.google.android.material:material:1.12.0'
implementation 'androidx.constraintlayout:constraintlayout:2.2.1'

// Image Handling
implementation 'com.github.Drjacky:ImagePicker:2.3.22'
implementation 'de.hdodenhof:circleimageview:3.1.0'
implementation 'com.github.bumptech.glide:glide:4.15.1'

// Database
implementation "androidx.room:room-runtime:2.7.2"
kapt "androidx.room:room-compiler:2.7.2"
implementation "androidx.room:room-ktx:2.7.2"

// Misc
implementation 'com.facebook.fresco:fresco:2.5.0'

📄 License
This project is open-source and available under the MIT License.

👨‍💻 Developed by Fasih Ullah
⚡ Want a clean and custom app like this? Feel free to reach out or fork this repo!
