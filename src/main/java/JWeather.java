import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JWeather {

    private static double parseTemperatureFromJson(String json) {

        JsonObject jsonParser = JsonParser.parseString(json).getAsJsonObject();

        return jsonParser.getAsJsonObject("main").get("temp").getAsDouble();

    }

    public static void main(String[] args) {

        String apiKey = "e8afda2e61d4d6272f9313ee6637c5e5";
        String city = "Cologne";
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();
        /*try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                double temperature = parseTemperatureFromJson(responseBody);
                System.out.println("Temperature in " + city + ": " + temperature + "°C");
            } else {
                System.out.println("Error: " + response.code() + " - " + response.message());
            }
        } */

        try (Response response = client.newCall(request).execute()){

            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseBody = response.body().string();
                double temperature = parseTemperatureFromJson(responseBody);
                System.out.println("Temperature in " + city + ": " + temperature + "°C");
            } else {
                System.out.println("Error: " + response.code() + " - " + response.message());
            }
        }

        catch (Exception ignored) {

        }
    }
}