# Display Exercises List 

| Test                                                           | Expected Result                                                          | Actual Result |
|----------------------------------------------------------------|--------------------------------------------------------------------------|---------------|
| /exercises displays Exercise list page when authenticated      | Exercise List page is displayed                                          | Pass          |
| /exercises redirects to / when not authenticated               | Exercise List page is not be displayed, redirects to index page                                   | Pass          |
| /exercises goes to /exercises when authenticated               | Exercise List page is displayed                          | Pass          |
| / redirects to /exercises when already authenticated               | Exercise List page is displayed                          | Pass          |
| /exercises when no exercises in the database        | The "No exercises available." message is displayed         | Pass          |
| Clicking on a exercise redirects you to that exercise url e.g. clicking on exercise 1 redirects to /exercise/1              | Redirected to the url of that exercise page | Pass          |
| Typing the url of the exercise page redirects to that page e.g. typing /exercise/1, /exercise/2              | Redirected to the url of that exercise page                                                   | Pass          |
| /exercises when exercises are present in the database: check card layout        | List of all the available exercises are displayed as cards       | Pass          |
| /exercises when exercises are present in the database: check icons                  | Exercise Icons are displayed inside card                                           | Pass          |
| /exercises when exercises are present in the database: check exercise name                  | Exercise name is displayed inside card                                                     | Pass          |
| /exercises when exercises are present in the database: check description                 | Exercise description is displayed inside card                                                     | Pass          |
| /exercises when exercises are present in the database: check points badge                  | Exercise points are displayed inside card                                                    | Pass          |
| Check Layout of page is as intended on multiple browsers (Google Chrome, Firefox, Edge and Safari) e.g. Fonts, colours, positioning                | The page is displayed in the layout intended in different browsers                                              | Fail: Tab bar alignment was out of place |        





