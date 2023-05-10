package Task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Task3 {

    public static void main(String[] args) throws IOException {

        int userId = 1;
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

        List<Todo> openTodos = new ArrayList<>();

        Todo[] todos = new Gson().fromJson(response.toString(), Todo[].class);
        for (Todo todo : todos) {
            if (!todo.isCompleted()) {
                openTodos.add(todo);
            }
        }

        System.out.println("Список відкритих задач для користувача з ідентифікатором " + userId + ":");
        for (Todo todo : openTodos) {
            System.out.println("- " + todo.getTitle());
        }

    }

    private static class Todo {
        private int id;
        private int userId;
        private String title;
        private boolean completed;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
