plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.sr_wedding"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sr_wedding"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.generativeai)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    //ViewModel
    implementation("androidx.activity:activity-ktx:1.4.0")

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.google.code.gson:gson:2.9.1")
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.google.guava:guava:32.1.2-jre")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")


}