plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.project.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}
kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(project(":domain"))

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    val accompanist_version = "0.28.0"
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanist_version")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.5.0")
    implementation("androidx.navigation:navigation-runtime-ktx:2.6.0")
    implementation("androidx.navigation:navigation-compose:2.7.1")

    implementation("com.airbnb.android:lottie-compose:6.0.0")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")
    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")
    implementation("androidx.compose.ui:ui-util:1.5.4")
    implementation("androidx.compose.material:material-icons-extended-android:1.6.0-alpha08")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("com.wdullaer:materialdatetimepicker:4.2.3")

    implementation("com.github.tehras:charts:0.2.4-alpha")
}