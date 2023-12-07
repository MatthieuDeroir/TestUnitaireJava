package fr.epsi.App.Services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TranslationService {

    private static final String apiKey = "062f8be081msh26559d4a94a4eb0p1b2f1ajsn1972aefd2af6"; // Remplacer par votre clé API
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    private TranslationService() {
        // Constructeur privé pour empêcher l'instanciation
    }

    public static String translate(String textToTranslate, String targetLanguage) throws IOException, InterruptedException {
        String encodedText = URLEncoder.encode(textToTranslate, StandardCharsets.UTF_8);
        String requestBody = "{\"text\": \"" + encodedText + "\", \"source\": \"EN\", \"target\": \"" + targetLanguage.toUpperCase() + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://deepl-translator.p.rapidapi.com/translate"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "deepl-translator.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


        Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);


        // Extraction du texte traduit de la réponse
        if (responseMap.containsKey("text")) {
            return (String) responseMap.get("text");
        } else {
            throw new IOException("Failed to extract translated text from the response");
        }
    }
}
