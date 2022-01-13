plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val composeVersion = "1.0.5"

dependencies {
    implementation(project(":core"))
    implementation(project(":features"))
    implementation(project(":domain"))
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    implementation("org.kodein.di:kodein-di:7.7.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-graphics:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.navigation:navigation-compose:2.4.0-beta01")

    implementation(platform("com.google.firebase:firebase-bom:28.3.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    implementation("com.google.accompanist:accompanist-insets:0.16.1")
    // If using insets-ui
    implementation("com.google.accompanist:accompanist-insets-ui:0.16.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.16.1")

    implementation("com.google.accompanist:accompanist-appcompat-theme:0.21.3-beta")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")

    implementation("org.kodein.di:kodein-di-framework-compose:7.6.0")
    implementation("androidx.core:core-splashscreen:1.0.0-alpha01")

    implementation("io.coil-kt:coil-compose:1.4.0")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "hu.benefanlabs.moviebudgets.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        create("release") {
            keyAlias = "key0"
            storeFile = file("keystore/release.jks")
            storePassword = "snapsofthw"
            keyPassword = "snapsofthw"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
}
