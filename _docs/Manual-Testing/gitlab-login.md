# gitlab-login

| Test                                                           | Expected Result                                                          | Actual Result |
|----------------------------------------------------------------|--------------------------------------------------------------------------|---------------|
| Click continue with Gitlab button (while not logged in Gitlab) | Go to Gitlab login page.                                                 | Pass          |
| Sign in into Gitlab through Gitlab’s login page                | Show deny or authorize application page.                                 | Pass          |
| Authorized button is clicked in Gitlab                         | Go to exercise page.                                                     | Pass          |
| Not authorized and deny button is clicked in Gitlab            | Return to index page with an error message displayed                     | Pass          |
| Not authorized and type /exercises in address bar              | Go back to login page, exercises page should not be able to be accessed. | Pass          |
| Authorized and type /exercises in address bar.                 | Go to exercises page.                                                    | Pass          |
| Not authorized, type / in address bar                          | Stay on login page.                                                      | Pass          |
| Authorized, type / in address bar                              | Redirect to exercises page.                                              | Pass          |