import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class GithubUserActivity {
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.print("Username argument is missing!");
            return ;
        }
        
        String userName = args[0];
        
        try {
            fetchUserActivity(userName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


    }

    public static void fetchUserActivity(String username) {
        String apiUrl = "https://api.github.com/users/" + username + "/events";

        try {
            // Create a URL object
            URL url = new URI(apiUrl).toURL();

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response, only list 10 latest PushEvent as user recent activity
                JSONArray events = new JSONArray(response.toString());
                int totalCommits = 0;
                for (int i = 0; i < Math.min(10, events.length()); i++) { 
                    JSONObject event = events.getJSONObject(i);

                    if(event.getString("type").equals("PushEvent")){
                        String eventType = event.getString("type");
                        String repoName = event.getJSONObject("repo").getString("name");
                        String createdAt = event.getString("created_at");
                        JSONObject payload = event.getJSONObject("payload");


                        System.out.println("Event Type: " + eventType);
                        System.out.println("Repository: " + repoName);
                        if (payload.has("commits")) {
                            JSONArray commits = payload.getJSONArray("commits");
                            totalCommits += commits.length();
                            for (int j = 0; j < commits.length(); j++) {
                                JSONObject commit = commits.getJSONObject(j);

                                // Extract and print commit details
                                String message = commit.getString("message");
                                JSONObject author = commit.getJSONObject("author");
                                String authorName = author.getString("name");
                                String commitUrl = commit.getString("url");

                                System.out.println("  Commit " + (j + 1) + ":");
                                System.out.println("    Message: " + message);
                                System.out.println("    Author: " + authorName);
                                System.out.println("    Commit URL: " + commitUrl);
                            }
                        }
                        System.out.println("Created At: " + createdAt);
                        System.out.println("--------------------------------------");
                    } 
                }

                System.out.println("Total number of commits: " + totalCommits);
                System.out.println("--------------------------------------");
            } else {
                System.out.println("Error: Unable to fetch user activity. Response Code: " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}