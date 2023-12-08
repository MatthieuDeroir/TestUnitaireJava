package fr.epsi.App.Services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LanguageTranslationService {

    private static final String apiKey = ResourceBundle.getBundle("config").getString("API_KEY");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    private LanguageTranslationService() {
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
            try {
                Thread.sleep(500); // Attendre 500ms pour éviter de dépasser la limite de requêtes par minute
                return (String) responseMap.get("text");
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            throw new IOException("Failed to extract translated text from the response");
        }
    }


    public static List<String> getSupportedLanguages() throws IOException, InterruptedException {
        // return all supported languages by DeepL
        return List.of(
                "bg", "zh", "cs", "da", "nl", "en", "et", "fi", "fr", "de", "el", "hu", "id", "it", "ja", "lv", "lt", "pl", "pt-PT", "pt-BR", "ro", "ru", "sk", "sl", "es", "sv", "tr", "uk", "ko", "nb"
        );
    }
}
