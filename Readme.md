# HiDraw
![appicon](https://github.com/oguz-sahin/HiDraw/blob/develop/app/src/main/res/mipmap-xhdpi/ic_launcher_foreground.png)

HiDraw is a reference application for HMS Kits to phones running with the android based
HMS Service. The app obviously is draw application which it provides attend and create draw from [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home).
You can attend draw created by someone or you can create new draw according to [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home).
The draw result computation according to comments in forum link.
## Screenshots

<table>
<tr>
<td>

![Explore Landmarks](art/01Login/01AuthScreen.png)</td>

<td>

![Explore Landmarks](art/01Login/02Signup&Credentials.png)</td>

<td>

![Explore Landmarks](art/01Login/03SwipeToDelete.png)</td>

<td>

![Explore Landmarks](art/01Login/04BiometricAuth.png)</td></tr>

<tr>
<td>

![Explore Landmarks](art/02LandmarkRecognition/LandmarkRecognition.png)</td>

<td>

![Explore Landmarks](art/03LandmarkDetail/LandmarkDetail.png)</td>

<td>

![Explore Landmarks](art/04PanoramaScreen/PanoramaScreen.png)</td>

<td>

![Explore Landmarks](art/05ImageGallery/ImageGallery.png)</td></tr>

</table>


# 📱 Features

### Sign in

-----
Thanks to [Account Kit](https://developer.huawei.com/consumer/en/hms/huawei-accountkit/) you
can sign in the app. Also, you can see the profile information in profile page.

https://user-images.githubusercontent.com/28221219/153600393-b7cbb8b4-f5c8-4d14-8fa0-85dd13ccd1b0.mp4

### Create Draw

-----
Thanks to [Huawei Machine Learning](https://developer.huawei.com/consumer/en/hms/huawei-mlkit/) you
can recognize landmarks into images by this app. You have an image related to a landmark and do you
forget the name of the landmark? Okay, no problem just take a photo of the image and learn the
landmark information. Are you on travel and do you want to learn more information about the landmark
of you visiting? Okay just take a picture of the landmark and learn the landmark information. Also,
you can learn the landmark information by choosing an image that contains a landmark from the
gallery.

https://user-images.githubusercontent.com/28221219/153600544-e226e046-7bc1-441c-ac73-7b4bc4f7efe1.mp4

### 📄 Landmark Detail - Information

-----
You can see the recognized landmark information on this screen. And you can navigate to some screens
related to the landmark. You can see details of these landmarks in this repo: The Eiffel Tower, The
Sydney Opera House, The Burj Khalifa, The Kabah. When you try the repo please attention to that. Try
these landmarks if you want to learn detail about the landmarks.

<img src='/art/03LandmarkDetail/LandmarkDetail.png' width='358'>

### 🖼 📹 Panoramic Image and Video

-----
Thanks to [Huawei Panorama Kit](https://developer.huawei.com/consumer/en/hms/huawei-panoramakit/)
you can view panoramic images and panoramic videos of the landmarks in this app. There are 2
panoramic images each of The Eiffel Tower, The Sydney Opera House, The Burj Khalifa, The Kabah in
this repo.

https://user-images.githubusercontent.com/28221219/153600727-82f95d3d-a42c-4810-aca8-2b7ab8849664.mp4

There is 1 panoramic video each of The Eiffel Tower and The Burj Khalifa in this repo.

https://user-images.githubusercontent.com/28221219/153600784-adfa0865-bcfa-4be5-8cd7-bb001452ccf5.mp4

### 🖼 Image Gallery

-----
Thanks to [Huawei Search Kit](https://developer.huawei.com/consumer/en/hms/huawei-searchkit/) you
can view more images of the recognized landmark in this app. No any landmark limitations such as
Panorama Screen or Detail Screen in this feature. You can view more images (with nice animations) of
any recognized landmark on this screen.

https://user-images.githubusercontent.com/28221219/153601390-276fc684-f17f-4de7-8d81-c3cc0b946c91.mp4

## Tech Stack & Used Kits

This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture to have a unidirectional flow of data, separation of concern, and a lot more which is recommended by Google itself.

![Architecture](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

### Used Kits
✅ [HUAWEI ACCOUNT KIT](https://developer.huawei.com/consumer/en/hms/huawei-accountkit/) \
✅ [HUAWEI VIDEO EDITOR KIT](https://developer.huawei.com/consumer/en/hms/huawei-video-editor/) \
✅ [HUAWEI PUSH KIT](https://developer.huawei.com/consumer/en/hms/huawei-pushkit/)
✅ [HUAWEI CRASH SERVICE](https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-crash-introduction-0000001055732708)     \
✅ [HUAWEI ANALYTICS KIT](https://developer.huawei.com/consumer/en/hms/huawei-analyticskit/)     \


### Tech Stack

✅ [Hilt (DI)](https://developer.android.com/training/dependency-injection/hilt-android)     \
✅ [Architecture Components (DataBinding, ViewModel, LiveData)](https://developer.android.com/topic/libraries/architecture/viewmodel)     \
✅ [Coroutines](https://developer.android.com/kotlin/coroutines)     \
✅ [Jetpack Navigation component](https://developer.android.com/guide/navigation)     \
✅ [Airbnb Lottie](https://github.com/airbnb/lottie-android)     \
✅ [Glide](https://github.com/bumptech/glide)     \
✅ [SDP](https://github.com/intuit/sdp) \
✅ [HBRecorder](https://github.com/HBiSoft/HBRecorder) \


## Licence

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
