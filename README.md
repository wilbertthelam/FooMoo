# FooMoo
Created by Linsen, Jane, Wilbert 2015

Android application to provide an easier time to find friends with similar food interests.

TEST ACCOUNTS WITH LOTS OF PREBUILT DATA:
login into app with: <br>
email: troy_qkajhkt_barnes@tfbnw.net <br>
password: troybarnes<br>

REST URL: http://ec2-52-88-203-84.us-west-2.compute.amazonaws.com:3333/api<br>
Endpoints:<br>
/users -> returns all the user's info (user_id, fname, lname, current_craving, fav_1, fav_2, fav_3. craving_timestamp)<br>
/user/[insert user_id] -> returns the users info for given user_id<br>
/events -> returns all the event's info (event_id, event_name, time, location, when_created)<br>
/events/[insert event_id] -> return the events info for given event_id<br>
/eventsByUserId/[insert user_id] -> returns the events info for a given user_id<br>
/userByEventId/[insert event_id] -> returns the users info for a given event_id
