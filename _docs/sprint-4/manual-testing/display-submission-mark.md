# Display submission mark 

| Test                                                           | Expected Result | Actual Result |
|----------------------------------------------------------------|--------------------------------------------------------------------------|---------------|
| Click on attempts tab when attempts list is empty | Message "No attempts available." is displayed  | Pass          |
| Click on attempts tab when attempts list is not empty | All attempts of the exercise for the user are displayed    | Pass          |
| Click on attempts tab | Switches view to new tab     | Pass          |
| Click on attempts tab, after job has been marked | Attempt number and score are displayed with the correct values   | Fail - Attempt number is the id of the row instead of the actual attempt number for the exercise          |
| When submit button is clicked check if server marks submission | Server adds new row to attempt table and removes marked job from job queue     | Pass          |
| When submit button is clicked without completing the exercise | Score 0 is displayed for that attempt   | Pass          |
| When multiple exercises are submitted, the appropriate number of jobs are added to the server job queue | Same number of jobs are marked (rows added to attempt table and removed from job queue)    | Pass          |
| When exercise is completed and submit button is clicked, the correct values are displayed | Server adds new row to attempt table and removes marked job from job queue    | Pass          |
| Manually add a job to the job queue when the server is in sleep state | Server wakes up after around a minute and marks the new job in the queue    | Pass          |
| Manually add a job to the job queue when the server is in wake state and another job is in the queue | Server marks the manually added job after it finishes marking the previous one  | Pass          |
| Manually add 25 jobs to the job queue | Server marks all 25 jobs consecutively and goes into sleep state    | Pass          |
| Manually add job with incorrect repository link | Server fails to mark repository and removes row from job queue    | Pass          |
| Manually add job to the job queue, but delete the forked repository before starting the server | Server fails to mark repository and removes row from job queue    | Pass          |
| Multiple users using the service click on submit button for different exercises at different times | Server successfully marks the given exercise for the correct user    | Pass          |