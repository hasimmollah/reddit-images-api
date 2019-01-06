# reddit-images-api
We can start up the application in following ways.
1. Double click on the jar present in the "output" directory.
2. Open command prompt in output directory execute following command:
     java -jar redditimages-api-0.0.1-SNAPSHOT.jar
3. Execute maven command : mvn clean package
   In target folder redditimages-api-0.0.1-SNAPSHOT.jar would be generated.
   execute command mentioned in option 2.
   
Request: 

Port : 9080
context root: reddit-images-api/v1.0
request path: /images	 
Header "topic" is mandatory.


Request url:
  http://localhost:9080/reddit-images-api/v1.0/images
  
  header:
    topic:Sun
	
