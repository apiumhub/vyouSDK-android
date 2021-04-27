
# VYou SDK for Android

The VYou SDK provides two modules, the core module that provides an API to interact directly with the backend and authenticate your users; and the UI module, in which you will have developed the interface to interact with dynamic forms, such as registration or profile. Those modules are structured as follows

## VYou-Core
### Install
In order to install the VYou-Core SDK in your Android application you'll need to add the following dependency inside your application-level build.gradle file
`TODO("Gradle dependency + repository")`

### Setup
  You'll need to include to your AndroidManifest the following permissions, to allow the SDK to perform network operations.
  ```xml
<uses-permission android:name="android.permission.INTERNET" />
```

There are a few parameters that you need to provide to the SDK in order to make it work properly.
They must be provided via AndroidManifest's `<meta-data>`, and are read autommatically by the SDK when needed.
Those parameters are the following:
```xml
<meta-data
android:name="vyou_client_id"
android:resource="@string/VYOU_CLIENT_ID"
android:value="string" />
```
```xml
<meta-data
android:name="vyou_redirect_uri"
android:resource="@string/VYOU_REDIRECT_URI"
android:value="string" />
```
```xml
<meta-data
android:name="vyou_url"
android:resource="@string/VYOU_URL"
android:value="string" />
```
```xml
<meta-data
android:name="google_client_id"
android:resource="@string/GOOGLE_CLIENT_ID"
android:value="string" />
```
```xml
<meta-data
android:name="facebook_app_id"
android:resource="@string/FACEBOOK_APP_ID"
android:value="string" />
```

Our proposed approach for including all of this data is adding them via build.gradle such as:
```groovy
resValue "string", "VYOU_CLIENT_ID", "8XxbGzbBAAbapQ9t*******KDw9h0KBWQYvT2P67T42lAlrZrG7KWg"
resValue "string", "VYOU_REDIRECT_URI", "vyouapp://com.vyouapp.auth"
resValue "string", "VYOU_URL", "https://test.vyou-app.com:8380"
resValue "string", "GOOGLE_CLIENT_ID", "975381680954-klti62p4c***ti9tikbglf.apps.googleusercontent.com"
resValue "string", "FACEBOOK_APP_ID", "83431****8440"
resValue "string", "FACEBOOK_PROTOCOL_SCHEME", "fb83431****40"
```

This allows you to setup those values by environment read them from any external file (such as local.properties) in case you need to.

Once you have all of those values in place, the last thing you need to do is initialize the SDK along with your application.
In order to do that, you need to call the following method in your Application class

```kotlin
VYou.initialize(this)
```

### API
Every public method inside the SDK return a `VYouResult` which basically is a wrapper of the result of an action. This is represented by a `sealed class` whose possible values are
- `VYouResult.Success` which holds a `value: T` with the result of the action
- `VYouResult.Failure` which holds an `error: Throwable` with the reason for the failure

The VYou-Core SDK is divided in three main components that allows the developers to interact with the SDK depending on their needs, those are the following

#### VYouLoginManager
This component allows you to interact with the three possible login platforms that the SDK provides. The API for that is the following
```kotlin
suspend fun signInWithAuth(): VyouResult<VyouSession>
suspend fun signInWithGoogle(): VyouResult<VyouSession>
suspend fun signInWithFacebook(fragment: Fragment): VyouResult<VyouSession>
```
Those three methods will start a new `Activity` inside your application, gather the necessary information from the user, perform an API request to the VYou backend and return the proper result inside the VYouSession object.

To be able to interact with the VYouLoginManager, it's mandatory to pass an ActivityResultCaller as a parameter from your application.
```kotlin
fun getLogin(actResultCaller: ActivityResultCaller) = VYouLoginManager(actResultCaller)
```
The activityResultCaller can be an activity or a fragment, this class can call APIs of type Activity#startActivityForResult without having to manage the request codes, and converting the request/response to an Intent.
It's necessary to call this function before the onCreate of your Activity or Fragment. You can do it like this:
```kotlin
private val vyouLogin = VYou.getLogin(this)
```
Last but not least, if you are using Facebook to authenticate your users, you need to override
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
```
in the Fragment or Activity that is calling the `signInWithFacebook(...)` method and call
```kotlin
VyouLoginManager.onActivityResult(requestCode, resultCode, data)
```
inside it.


#### VYouTenantManager
This manager allows the developer to interact with all of the API that doesn't require the user to be authenticated. Those methods are the one for obtaining the Tenant information and the one for registering a new user in the platform:
```kotlin
suspend fun tenant(): VYouResult<VYouTenant>
suspend fun register(customer: RegisterDto): VYouResult<Unit>
```
Any of those two methods will perform an API request and return the proper result wrapped inside a `VYouResult` object.

#### VYouSession
The last one of the components inside the `VYou-Core SDK` is the session.
This session is accessible statically via the `val session: VYouSession?` property inside the `VYou` object.
As you might have realized, this is a nullable property which will only hold a value if the user has been previously authenticated in your application.
All of this session data is stored inside Android's EncryptedSharedPreferences, so all of this data is properly secured by the OS.
The `VYouSession` class holds the user's credentials (`VYouCredentials`) which are accessible to the developer in case it's needed. It also holds the necessary methods to interact with a logged-in user, which are the following:

```kotlin
suspend fun signOut(): VYouResult<Unit>
suspend fun tenantProfile(): VYouResult<VYouProfile>
suspend fun editProfile(editProfileDto: EditProfileDto): VYouResult<Unit>
```

#### HttpClient
This SDK also provides you with an `OkHttpClient` with the proper configuration so that you can make authenticated requests with any server that needs it. This client has three interceptors which are responsible for adding the `Client-Credentials` header which authenticated your application against the VYou servers, the `Authorization` header, which authenticates your user if it's logged in, and a last one responsible for refreshing the user's token if it's expired or invalid.
This client is accessible via a static property `val httpClient: OkHttpClient` inside the VYou object.

## VYou-UI
Along with the Core SDK, we provide another library that provides built-in forms for registering a user and editing it's profile. Those forms are dynamically built with the information configured in the back office.
### Install
In order to install the VYou-UI SDK in your Android application you'll need to add the following dependency inside your application-level build.gradle file
`TODO("Gradle dependency + repository")`
**Important note** Notice that VYou-Core is a dependency inside VYou-UI, so if you add VYou-UI to your project, you don't need to add the `Core` dependency anymore.
### Setup
Please refer to the [VYou-Core setup](#setup), as you'll have to follow all of the steps described on it.
**Important note** To make VYou-UI SDK work you must replace this line
```kotlin
VYou.initialize(this)
```
with the following
```kotlin
VYouUI.initialize(this)
```
As in the VYou-Core setup, you will need to call this function before the onCreate of your Activity or Fragment:
```kotlin
private val vyouUi = VYouUI(this)
```
### API
As VYou-UI holds a dependency to VYou-Core, all of it's API will still be available, so please take a [look at it](#api) if you haven't done so yet.
The VYou-UI SDK provides methods to start the register & edit profile flows from your application

```kotlin
suspend fun startRegister(): VYouResult<Unit>
suspend fun startProfile(credentials: VYouCredentials): VYouResult<Unit>
```
Those two methods will start a new `Activity` inside your application, and return the result of any of the flows inside a `VYouResult`
In order to launch the edit profile activity, you must provide the user's credentials which are easily accessible through
```kotlin
Vyou.session?.credentials
```

### Styling
In order to style the forms shown to the user, the SDK is integrated with the built-in theme system inside Android, so everything that's configurable by the theme should also work inside the SDK. We provide the following example in the sample application
```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
  <style name="Theme.VYou" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
        <!-- Primary brand color. -->
  <item name="colorPrimary">@color/purple_500</item>
        <item name="colorPrimaryVariant">@color/purple_700</item>
        <item name="colorOnPrimary">@color/white</item>
        <!-- Secondary brand color. -->
  <item name="colorSecondary">@color/teal_200</item>
        <item name="colorSecondaryVariant">@color/teal_700</item>
        <item name="colorOnSecondary">@color/black</item>
        <!-- Status bar color. -->
  <item name="android:statusBarColor" tools:targetApi="l">?attr/colorPrimaryVariant</item>
        <!-- Customize your theme here. -->
  </style>
</resources>
```
but everything that's configurable via theme.xml will also change the display inside the SDK.

### Sample App
We provide a sample application for developers to see how VYou is integrated inside a real android app.
To be able to login with google, you must register the project in Firebase, add the keystore and the google-services.json file to the sample application.
Here you can find the Firebase [documentation](https://firebase.google.com/docs/auth/android/google-signin?hl=es) for adding the Google login.
