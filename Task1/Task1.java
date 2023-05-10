package Task1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

class JsonPlaceholderApi {
    private static final String API_BASE_URL = "https://jsonplaceholder.typicode.com";
    private final Gson gson = new Gson();

    public User createUser(String userJson) throws IOException {
        URL url = new URL(API_BASE_URL + "/users");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = userJson.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();
        if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                String responseJson = br.readLine();
                return gson.fromJson(responseJson, User.class);
            }
        } else {
            throw new IOException("POST request failed with error code " + responseCode);
        }
    }

    public User updateUser(int userId, String userJson) throws IOException {
        URL url = new URL(API_BASE_URL + "/users/" + userId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = userJson.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();
        if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                String responseJson = br.readLine();
                return gson.fromJson(responseJson, User.class);
            }
        } else {
            throw new IOException("PUT request failed with error code " + responseCode);
        }
    }

    public void deleteUser(int userId) throws IOException {
        URL url = new URL(API_BASE_URL + "/users/" + userId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");

        int responseCode = con.getResponseCode();
        if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
            return;
        } else {
            throw new IOException("DELETE request failed with error code " + responseCode);
        }
    }

    public List<Post> getPostsByUserId(int userId) throws IOException {
        URL url = new URL(API_BASE_URL + "/posts?userId=" + userId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                Type listType = new TypeToken<List<Post>>() {
                }.getType();
                return gson.fromJson(br, listType);
            }
        } else {
            throw new IOException("GET request failed with error code " + responseCode);
        }
    }
    public class User {
        private int id;
        private String name;
        private String username;
        private String email;
        private Address address;
        private String phone;
        private String website;
        private Company company;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public Company getCompany() {
            return company;
        }

        public void setCompany(Company company) {
            this.company = company;
        }
    }

    public class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getSuite() {
            return suite;
        }

        public void setSuite(String suite) {
            this.suite = suite;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public Geo getGeo() {
            return geo;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
        }
    }

    public class Geo {
        private String lat;
        private String lng;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }
    }

    public class Company {
        private String name;
        private String catchPhrase;
        private String bs;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public String getBs() {
            return bs;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }
    }

}
