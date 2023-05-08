package Task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Task3 {

    public static void main(String[] args) throws IOException {

        int userId = 1; // ідентифікатор користувача
        String apiUrl = "https://jsonplaceholder.typicode.com/users/" + userId + "/todos";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Список відкритих задач для користувача з ідентифікатором " + userId + ":");

        String[] lines = response.toString().split("\n");

        for (String line : lines) {
            if (line.contains("\"completed\":false,")) {
                String title = line.substring(line.indexOf("\"title\":") + 9, line.indexOf(",\"completed\""));
                System.out.println("- " + title);
            }
        }

    }
}
