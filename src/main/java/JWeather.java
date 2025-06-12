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

    private static double parseFeelsLikeFromJson(String json) {
        JsonObject jsonParser = JsonParser.parseString(json).getAsJsonObject();
        return jsonParser.getAsJsonObject("main").get("feels_like").getAsDouble();
    }

    public static void main(String[] args) {

        String apiKey = System.getenv("OPENWEATHER_API_KEY");
        
        if (apiKey == null || apiKey.isEmpty()) {
        System.err.println("ERROR: OPENWEATHER_API_KEY environment variable not set.");
        System.exit(1);
        apiKey = "";
        }
        
        String city = args[0];
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        try (Response response = client.newCall(request).execute()){
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseBody = response.body().string();
                double temperature = parseTemperatureFromJson(responseBody);
                double feels_like = parseFeelsLikeFromJson(responseBody);
                System.out.println("The temperature in " + city + " is " + temperature + "°C." + " Feels like " + feels_like + "°C.");
            } else {
                System.out.println("Error: " + response.code() + " - " + response.message());
            }
        }
        catch (Exception ignored) {}
    }
}
