# Description:
Users can create an account and log in using their username and password. Each customer will have a unique ID, called CustomerId. Users can create a ride by providing the source and destination, along with their CustomerId. In response, we will receive ride details, including the maximum speed limit.

To get the current car speed, we have a fake SpeedService API that returns the current speed every 5 seconds, which can be customized. Once we receive the current speed, we can compare it with the maximum speed limit. If the current speed exceeds the maximum speed limit, a warning alert dialog will be displayed to the user.

#### I followed a clean MVVM architecture pattern with clearly defined domain, data, and presentation layers. The UI is designed using Jetpack Compose.

# Demo:
https://github.com/user-attachments/assets/c759f22b-1dfb-4477-9a83-4e3a757c9c52

