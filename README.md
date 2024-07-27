# SpaceX Launch Tracker App

## Objective

Create an Android application to display information about SpaceX launches. The app includes navigation between Home, Store, and Search tabs using a bottom navigation bar. Users can view a list of launches, detailed information about each launch, and search for specific launches. The Store tab should load a specific URL and should not reload when navigated back from other tabs.

## Features

### Home Screen
- **Display a list of SpaceX launches**: 
  - Each list item shows mission name, launch year, and rocket name.
  - Implement endless smooth scrolling to load launches seamlessly.

### Launch Detail Screen
- **Detailed Information Screen**:
  - When a user clicks on a launch item, navigate to a detail screen.
  - Display detailed information about the selected launch, including mission name, launch date, rocket details, payload details, launch site, and links to media and articles.

### Search Tab
- **Search Functionality**:
  - Allow users to search for launches by mission name, launch year, and rocket name.
  - Display the search results.

### Store Tab
- **WebView**:
  - Load the URL [SpaceX Vehicles](https://www.spacex.com/vehicles/falcon-9/).
  - Ensure the WebView does not reload when navigating back from other tabs (Home or Search).

## JSON Data Structure

The JSON response contains information about various SpaceX launches. Key fields include:
- **Url to load**: [SpaceX Vehicles](https://www.spacex.com/vehicles/falcon-9/)
- **API**: [SpaceX Launches API](https://api.spacexdata.com/v3/launches)
- **flight_number**: The flight number of the launch.
- **mission_name**: The name of the mission.
- **launch_year**: The year of the launch.
- **rocket**: Details about the rocket used in the launch, including rocket_name and rocket_type.
- **launch_site**: Information about the launch site.
- **links**: Links to media and articles related to the launch.

## Project Structure

- **Database & Repository**:
  - `AppDatabase.kt`
  - `AppModule.kt`
  - `Dao.kt`
  - `DataModel.kt`
  - `LaunchRepository.kt`

- **Network**:
  - `SpaceXApiService.kt`
  - `NetworkConnectivityObserver.kt`
  - `NetworkUtils.kt`

- **UI Components**:
  - `BottomNavigationBar.kt`
  - `FavoriteScreen.kt`
  - `HomeScreen.kt`
  - `LaunchDetailsScreen.kt`
  - `OfflineScreen.kt`
  - `SearchScreen.kt`
  - `Store.kt`
  - `StoreScreen.kt`
  - `TopBar.kt`

- **Main Components**:
  - `MainActivity.kt`
  - `MyApplication.kt`
  - `LaunchViewModel.kt`
 
## Dependencies
- **Jetpack Compose: For building the UI.
- **Dagger Hilt: For dependency injection.
- **Retrofit: For network operations.
- **Room: For local database operations.
- **Coil: For image loading.
- **Accompanist: For swipe-to-refresh functionality.


Feel free to adjust any sections based on your specific needs or preferences and also you can reachout to me at themihirbajpai@gmail.com
