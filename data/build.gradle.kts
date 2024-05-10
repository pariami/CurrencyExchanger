plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "msi.paria.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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

    packaging {
        resources {
            resources.excludes.add("META-INF/*")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    flavorDimensions.add("type")
    productFlavors {
        create("api") {
            namespace = "msi.paria.currencyexchanger.data"
            dimension = "type"

            buildConfigField ("String", "BASE_URL", "\"https://developers.paysera.com/tasks/api/\"")
            buildConfigField ("String", "API_KEY", "\"9f9fb073b991c6fa7e25b6f5b1182af4\"")
            buildConfigField ("String", "API_HASH", "\"fc343222b79729980c2868355e5dddf395a17dc3\"")

            buildConfigField("boolean", "IS_MOCKED", false.toString())
        }

        create("mock") {
            namespace = "msi.paria.currencyexchanger.data"
            dimension = "type"

            buildConfigField("boolean", "IS_MOCKED", true.toString())
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":domain")))

    implementation(libs.retrofit.gson)
    implementation(libs.retrofit)

    implementation(libs.room.runtime)
    implementation(libs.room.paging)
    implementation(libs.room.common)

    kapt(libs.room.compiler)

    implementation(libs.kotlin.coroutines)

    implementation(libs.compose.activity)

    implementation(libs.paging)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    testImplementation(libs.test.mockk)

    implementation(libs.test.junit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.compose.ui.test)
    testImplementation(libs.test.coroutines)

    testImplementation(libs.test.turbine)

    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(libs.test.coroutines)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.core.ktx)
    androidTestImplementation(libs.test.junit.ktx)
    androidTestImplementation(libs.test.room)
    androidTestImplementation(libs.test.mockk.android)
}