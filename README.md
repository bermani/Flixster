# Project 2 - *Flixster*

**Flixster** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user, as well as to allow the user to vote on movies.

Time spent: **20** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* [x] For each movie displayed, user can see the following details:
* [x] Title, Poster Image, Overview (Portrait mode)
* [x] Title, Backdrop Image, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings and popularity within a separate activity

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring.
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations)
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen.

The following **additional** features are implemented:

* [x] Implement multiple tabs to allow users to not only see [Now Playing](https://developers.themoviedb.org/3/movies/get-now-playing) movies, but [Top Rated](https://developers.themoviedb.org/3/movies/get-top-rated-movies) and [Upcoming](https://developers.themoviedb.org/3/movies/get-upcoming) movies as well.
* [x] Add the movie's release date and genres to the details view
* [x] Allow the user to [vote on movies](https://developers.themoviedb.org/3/movies/rate-movie) through the details view

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='./walkthrough.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [Kap](https://getkap.co/).

## Notes

The biggest challenge I encountered was the difficulty in learning the large volume of information required to complete the project. I had to learn about Android features like View Binding, ScrollView, TabLayout, and advanced styling techniques. I had to consult the documentation on the movie API to implement new routes and properly send GET and POST requests with the CodePath HTTP library. I had to learn how to properly organize and debug my project to improve code quality and reduce bugs. It was challenging to get through all of the content and learn the material in order to implement all of the features I did, but I'm very proud of what I managed to accomplish. I also ran into some minor configuration and network issues, but resolved those with relative ease.

## Open-source libraries used

- [CPAsyncHttpClient](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android
- [Parceler](https://github.com/johncarl81/parceler) - Library to make passing objects between Contexts easy

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
