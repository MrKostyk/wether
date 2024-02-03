package com.example.demo11;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.io.IOException;




public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onTodayButtonClick() {
        try {
            String weatherInfo = getWeatherInfo("Kyiv");
            welcomeText.setText(weatherInfo);
        } catch (IOException e) {
            e.printStackTrace();
            welcomeText.setText("Помилка отримання інформації про погоду");
        }
    }

    private String getWeatherInfo(String city) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String apiKey = "667258058fd4729b7da09d8fd407965e";
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Неочікуваний код " + response);

            JsonObject jsonObject = JsonParser.parseString(response.body().string()).getAsJsonObject();
            String weatherDescription = jsonObject.getAsJsonArray("weather")
                    .get(0).getAsJsonObject().get("description").getAsString();
            return "Погода у місті " + city + ": " + weatherDescription;
        }
    }

    public void onOKButtonClick(ActionEvent actionEvent) {
    }
}
