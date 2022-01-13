# Movie Budgets Application

## About the project
This repository contains a Kotlin Multiplatform Mobile (KMM) project. The Android and iOS app calls The Movie DB API and displays the response for the user. The task was to display the budget for each movie in the list view.

## Technologies
- **Kotlin Multiplatform Mobile** - https://kotlinlang.org/lp/mobile/
- **Jetpack Compose** for Android UI development - https://developer.android.com/jetpack/compose
- **SwiftUI** for iOS UI development - https://developer.apple.com/xcode/swiftui/
- **Ktor** for networking in the common code - https://ktor.io/
- **KODEIN** for dependency injection - https://kodein.org/di/

## Architecture
The MVI (Model-View-Intent) pattern is used in the project. It fits very well with the concept of KMM, because of the Model and View is decoupled. 

##### Overview
![Architecture](/architecture.png)

##### Business layer
The business layer has diferrent modules, such as the Domain, Data, Features, Core (and Resources) module.

- **Domain module**: data classes for the models of the application.
- **Data module**: responsible for getting the data that needs to be presented. It can happen locally or remotely (through internet). The use cases are defined here.
- **Core module**: the core of the business logic and the MVI pattern is declared here. (*the repository does not contain the implemetation of this module*)
- **Features module**: the ViewModels are implemented here using the BaseViewModel declared in the Core module. An intermediate abstract class wedged for every ViewModel, an AbstractMviView implementation named *ClassName*View. The iOS UI can access the ViewModel through this abstract class which is implemented on the iOS side as a proxy.
- **Resources**: common resources are declared here such as dimensions and font sizes (*this module is not used in this project*).

## Applications
Both application respects the phones dark or light theme.

##### Android
![Android light](/android_light.gif) 
![Android light search](/android_light_search.gif)

<p align="center">
  <img src="android_dark.gif" alt="animated" />
</p>

##### iOS

![iOS light](/ios_light.gif)
![iOS dark](/ios_dark.gif)
