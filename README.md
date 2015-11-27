# GAE_CalendarAPI_CloudSQL_OAuth2
Google App Engine that uses Google Calendar Api, stores events into CloudSQL and uses OAuth2

#Before running the project

1. Download your own credentials (client_secrets.json) from GAE and place them to src\main\resources.
2. Modify project name in src\main\webapp\WEB-INF\appengine-web.xml.
3. Use your own CloudSQL instance, and connect to it.

#To RUN the project on GAE

 > cd "project_location"
 > mvn clean install && appcfg.cmd -A <gae_project_id> update target/war
 
#Check project (replace gappsassignment with <your_gae_project_id>)
link: https://gappsassignment.appspot.com/
