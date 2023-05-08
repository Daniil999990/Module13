package Task2;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Task2 {

    public static void main(String[] args) throws IOException {
        int userId = 1; // id користувача
        URL url = new URL("https://jsonplaceholder.typicode.com/users/" + userId + "/posts");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        InputStream responseStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        reader.close();
        responseStream.close();

        String responseString = responseBuilder.toString();

        int lastPostId = -1;
        String lastPostTitle = null;
        String lastPostBody = null;

        String[] postsArray = responseString.substring(1, responseString.length() - 1).split("\\},\\{");
        for (String postString : postsArray) {
            String[] fields = postString.split(",");
            int postId = Integer.parseInt(fields[0].substring(7));
            if (postId > lastPostId) {
                lastPostId = postId;
                lastPostTitle = fields[1].substring(8, fields[1].length() - 1);
                lastPostBody = fields[2].substring(9, fields[2].length() - 1);
            }
        }

        if (lastPostId != -1) {
            url = new URL("https://jsonplaceholder.typicode.com/posts/" + lastPostId + "/comments");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            responseStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(responseStream));
            responseBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
            responseStream.close();

            responseString = responseBuilder.toString();

            String fileName = "user-" + userId + "-post-" + lastPostId + "-comments.json";
            FileWriter writer = new FileWriter(fileName);
            writer.write("[\n");

            String[] commentsArray = responseString.substring(1, responseString.length() - 1).split("\\},\\{");
            for (int i = 0; i < commentsArray.length; i++) {
                String[] fields = commentsArray[i].split(",");
                int postId = Integer.parseInt(fields[0].substring(10));
                int id = Integer.parseInt(fields[1].substring(5));
                String name = fields[2].substring(8, fields[2].length() - 1);
                String email = fields[3].substring(9, fields[3].length() - 1);
                String body = fields[4].substring(8, fields[4].length() - 1);
                writer.write("\t{\n");
                writer.write("\t\t\"postId\": " + postId + ",\n");
                writer.write("\t\t");
                        writer.write("\t\t\"body\": \"" + body + "\"\n");
                writer.write("\t}");
                if (i != commentsArray.length - 1) {
                    writer.write(",\n");
                }
                else {
                    writer.write("\n");
                }
            }
            writer.write("]");
            writer.close();
            System.out.println("Comments saved to file: " + fileName);
        }
    }
}
