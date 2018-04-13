Android LiveData & ViewModel Demo
---------------------------------------
[![CircleCI](https://circleci.com/gh/kioko/android-liveData-viewModel.svg)](https://circleci.com/gh/kioko/android-liveData-viewModel)

A simple android project that demonstrates how to implement Android Architecture Components.

 <table>
  <td>
    <p align="center">
  <img src="https://github.com/kioko/android-liveData-viewModel/blob/master/art/HomeScreen.png?raw=true" alt="Home Page" width="250"/>
</p>
</td>
<td>
    <p align="center">
  <img src="https://github.com/kioko/android-liveData-viewModel/blob/master/art/MovieDetails.png?raw=true" alt="Movie Details" width="250"/>
    </p>
  </td>

</table>

### Architecture
The app uses ViewModel to abstract the data from UI and TmdbRepository as single source of truth for data. TmdbRepository first fetch the data from database if exist than display data to the user and at the same time it also fetches data from the webservice and update the result in database and reflect the changes to UI from database.

![](https://github.com/kioko/android-liveData-viewModel/blob/master/art/archtiture.png)

### Requirements

* JDK Version 1.7 & above
* Android Studio Preview Version 3.0

### Prerequisites
For the app to make requests you require a [TMDB API key](https://developers.themoviedb.org/3/getting-started ).

If you don’t already have an account, you will need to [create one](https://www.themoviedb.org/account/signup)
in order to request an API Key.

Once you have it, open `gradle.properties` file and paste your API key in `TMDB_API_KEY` variable.

### Libraries


* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [OkHttp][OkHttp] for adding interceptors to Retrofit
* [Glide][glide] for image loading
* [Timber][timber] for logging
* [espresso][espresso] for UI tests
* [mockito][mockito] for mocking in tests


[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[support-lib]: https://developer.android.com/topic/libraries/support-library/index.html
[arch]: https://developer.android.com/arch
[OkHttp]: http://square.github.io/okhttp/
[espresso]: https://google.github.io/android-testing-support-library/docs/espresso/
[dagger2]: https://google.github.io/dagger
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide
[timber]: https://github.com/JakeWharton/timber
[mockito]: http://site.mockito.org


### License

    Copyright 2017 Thomas Kioko


    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
