# Display submission feedback

| Test                                                           | Expected Result | Actual Result |
|----------------------------------------------------------------|--------------------------------------------------------------------------|---------------|
| Manually add job to job queue and run server | Server marks exercise with score being 0 and all crosses on feedback  | Pass          |
| Complete exercise and click submit button | Server marks exercise with expected feedback output  | Pass          |
| (With no exercises attempted) click on submit button and view attempts tab after marking successful | One exercise should be displayed with feedback card shown | Pass | 
| (With 5 exercises attempted) click on submit button and view attempts tab after marking successful | 6 exercises should be displayed with latest attempt also showing feedback card. All other attempts' feedback card is collapsed | Pass |
| Manually add attempt with all span colours in feedback e.g. `<span style="color:#8be9fd;">Cyan</span>` etc | Colours are displayed correctly and output is legible | Pass | 
| Click on an attempt multiple times | Drop down icon toggles between up and down state (up when feedback expanded, down when feedback collapsed) | Pass |
| Click on another attempt | Feedback card for current open attempt collapses and feedback card, for the attempt clicked on, opens | Pass |
| Check icons displayed correctly | Cross, tick, up arrow and down arrow display as intended | Pass |
| Manually add attempt with feedback containing escape characters | Escape characters are printed out | Pass |
| Manually add attempt with feedback of size 65kb | Successfully adds row to attempt table | Pass |
| Manually add attempt with feedback of size 70kb | Fails to add row to attempt table | Pass |
| Manually add attempt with feedback that is empty | Successfully adds row to attempt table (shows empty feedback card in attempts tab) | Pass |
| Manually add attempt with feedback containing UTF-8 3 byte characters (e.g. ä) | Successfully adds row to attempt table | Pass |
| Manually add attempt with feedback containing UTF-8 4 byte characters (e.g. ☃) | Successfully adds row to attempt table | Fail - Does not add row to attempt table (mySQL ERROR 1366) |
| Check padding around the feedback card | Padding around feedback card is as intended | Pass |