package Task2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Task2 {

    public static void main(String[] args) throws IOException {
        int userId = 1; // id користувача

        String postsString = getPostsString(userId);
        int lastPostId = getLastPostId(postsString);
        String commentsString = getCommentsString(lastPostId);
        saveCommentsToFile(userId, lastPostId, commentsString);
    }

    private static String getPostsString(int userId) throws IOException {
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

        return responseBuilder.toString();
    }

    private static int getLastPostId(String postsString) {
        int lastPostId = -1;

        String[] postsArray = postsString.substring(1, postsString.length() - 1).split("\\},\\{");
        for (String postString : postsArray) {
            String[] fields = postString.split(",");
            int postId = Integer.parseInt(fields[0].substring(7));
            if (postId > lastPostId) {
                lastPostId = postId;
            }
        }

        return lastPostId;
    }

    private static String getCommentsString(int postId) throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts/" + postId + "/comments");
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

        return responseBuilder.toString();
    }

    private static void saveCommentsToFile(int userId, int postId, String commentsString) throws IOException {
        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";
        FileWriter writer = new FileWriter(fileName);
        writer.write("[\n");

        String[] commentsArray = commentsString.substring(1, commentsString.length() - 1).split("\\},\\{");
        for (int i = 0; i < commentsArray.length; i++) {
            String[] fields = commentsArray[i].split(",");
            int commentPostId = Integer.parseInt(fields[0].substring(10));
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
            } else {
                writer.write("\n");
            }
        }
        writer.write("]");
        writer.close();
        System.out.println("Comments saved to file: " + fileName);
    }
}
