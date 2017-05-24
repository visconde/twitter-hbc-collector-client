package twitterTrackerInvoker;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import twitter4j.JSONException;
import twitter4j.JSONObject;
import main.java.com.twitter.hbc.example.SampleStreamExample;

public class TwitterTrackerMain {

	public static void main(String[] args) throws IOException  {

		String consumerKey = "rjGn4uBECAwrOK5TpDilQ97Kz";
		String consumerSecret = "lGz0zQZQp5fJVs11MK3o4WFi3CHIwRYvmaBqCtqkCRUvDYl6hl";
		String accessToken = "63850197-pcJvniIvgdq2zQCavubcWUz99nsRHq8BLde3k8rLT"; 
		String accessTokenSecret = "NH3hngVK1HL1lShf3SKAyfiGYme26Rivx7C5iQuObbYtr";		
		String consumerKey2 		= "eVT2QIr8bfqfeP4254SzjqTWw";
		String consumerSecret2		= "gZgYBmTqWNEHmuDjUmqsasLTYq9nnhwmPR0emnLVSZKF0mTAm3";
		String accessToken2			= "63850197-Rvbl8xvP2V3G5HxTIixH6TwKqFBqHf0YCo1gluqlU"; 
		String accessTokenSecret2 	= "x3UOKtwEy42eRRDfiToIHCkfXc1D8X43AmLtbkpNjT4K0";
		
		SampleStreamExample sse = new SampleStreamExample(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		Long[] ids = new Long[1];
		ids[0] =  new Long(134745857);
		
		String[] words = new String[2];
		words [0] =  "timvision";
		words [1] =  "#timvision";
		
		sse.setKeywords(words);
		sse.setUserIds(ids);
		
		sse.run();
		
		BlockingQueue bq = sse.getBlockingQueue();

		while (true){
			try{
				String message = bq.take().toString();
				System.out.println("got!"  + message);
				processJsonEntry(message);

			}catch (InterruptedException ex){
				System.out.println("IOException. Exiting!"  + ex.getMessage());
				sse.close();
				break;

			}
		}
	}

	public static void processJsonEntry (String jsonEntry) {
		JSONObject entry;
		try {
			entry = new JSONObject(jsonEntry);
			String id = entry.getString("id");
			String text = entry.getString("text");
			String createdAt = entry.getString("created_at");		
			JSONObject user = entry.getJSONObject("user");
			String userId = user.getString("id");
			String name = user.getString("name");
			String screen_name = user.getString("screen_name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
