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

![Explore Landmarks](art/screenshoots/auth_kit_sign_.jpg)</td>

<td>

![Explore Landmarks](art/screenshoots/active_draw.jpg)</td>

<td>

![Explore Landmarks](art/screenshoots/attend_draw.jpg)</td>

<td>

![Explore Landmarks](art/screenshoots/my_attended.jpg)</td></tr>

<tr>
<td>

![Explore Landmarks](art/screenshoots/my_created.jpg)</td>

<td>

![Explore Landmarks](art/screenshoots/edit_draw_screenrecord.jpg)</td>

<td>

![Explore Landmarks](art/screenshoots/result_draw.jpg)</td>

<td>

![Explore Landmarks](art/screenshoots/push_notification.jpg)</td></tr>

</table>


# 📱 Features

### Sign in

-----
Thanks to [Account Kit](https://developer.huawei.com/consumer/en/hms/huawei-accountkit/) you
can sign in the app. Also, you can see the profile information in profile page.

https://user-images.githubusercontent.com/28221219/153600393-b7cbb8b4-f5c8-4d14-8fa0-85dd13ccd1b0.mp4

### Create Draw

-----
You can create a draw from [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home) link. You have to fill necessary fields and click create.
Url field should be link from huawei developer topic. Winners is selected this topic comments.  

https://user-images.githubusercontent.com/28221219/153600544-e226e046-7bc1-441c-ac73-7b4bc4f7efe1.mp4

### Attend Draw

-----
You can attend draw that is created by other users and you can obtain the chance to win.

<img src='/art/03LandmarkDetail/LandmarkDetail.png' width='358'>

### Profile Management

-----
You can see the attended and created draw with profile page and manage the draw state.


### Edit Draw

-----
Thanks to [HUAWEI VIDEO EDITOR KIT](https://developer.huawei.com/consumer/en/hms/huawei-video-editor/) You can edit the screen record and share with participants.


### Learn Winners

-----
Thanks to [HUAWEI PUSH KIT](https://developer.huawei.com/consumer/en/hms/huawei-pushkit/) You can learn the winners that is already your attended draws with notification.



## Tech Stack & Used Kits

This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture to have a unidirectional flow of data, separation of concern, and a lot more which is recommended by Google itself.

![Architecture](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

### Used Kits
✅ [HUAWEI ACCOUNT KIT](https://developer.huawei.com/consumer/en/hms/huawei-accountkit/) \
✅ [HUAWEI VIDEO EDITOR KIT](https://developer.huawei.com/consumer/en/hms/huawei-video-editor/) \
✅ [HUAWEI PUSH KIT](https://developer.huawei.com/consumer/en/hms/huawei-pushkit/) \
✅ [HUAWEI CRASH SERVICE](https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-crash-introduction-0000001055732708)     \
✅ [HUAWEI ANALYTICS KIT](https://developer.huawei.com/consumer/en/hms/huawei-analyticskit/)     


### Tech Stack

✅ [Hilt (DI)](https://developer.android.com/training/dependency-injection/hilt-android)     \
✅ [Architecture Components (DataBinding, ViewModel, LiveData)](https://developer.android.com/topic/libraries/architecture/viewmodel)     \
✅ [Coroutines](https://developer.android.com/kotlin/coroutines)     \
✅ [Jetpack Navigation component](https://developer.android.com/guide/navigation)     \
✅ [Airbnb Lottie](https://github.com/airbnb/lottie-android)     \
✅ [Glide](https://github.com/bumptech/glide)     \
✅ [SDP](https://github.com/intuit/sdp) \
✅ [HBRecorder](https://github.com/HBiSoft/HBRecorder) \

## Support
Thanks Berkhan Güngör, Dogan Burak Ziyanak, Cengiz Torufor their supports during preparing this repo.

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
