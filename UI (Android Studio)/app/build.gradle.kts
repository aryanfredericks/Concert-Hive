plugins {
    id("com.android.application")
}

android {
    namespace = "com.aryan.javaminiproject.TicketBooking"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aryan.javaminiproject.TicketBooking"
        minSdk = 24
        targetSdk = 33
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

    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Blur View
    implementation("com.github.Dimezis:BlurView:version-2.0.3")

    //ssp/sdp
    implementation ("com.intuit.ssp:ssp-android:1.0.5")
    implementation("com.intuit.sdp:sdp-android:1.1.0")

    //Lottie
    implementation("com.airbnb.android:lottie:6.1.0")

    //Volley
    implementation("com.android.volley:volley:1.2.1")

    //Meow Navigation
    implementation ("com.etebarian:meow-bottom-navigation:1.2.0")

    //Swipeable button
    implementation("com.ebanx:swipe-button:0.8.3")

    //Circular Image View
    implementation("de.hdodenhof:circleimageview:3.1.0")
}