package fr.epsi.App.Managers;

import java.util.*;


// SentenceSelector is a class that get a ResourceBundle from the LanguageManager and put it in a Map in order to be used by the IOManager
// The Map will be used to create a list of messages that will be used by the IOManager
public class MessageManager {
    private Map<String, String> messages;
    private Set<String> keys;
    private ResourceBundle resourceBundle;

    public String PROMPT = "input.prompt";
    public String INVALID = "input.invalid";
    public String CONTINUE = "input.continue";
    public String AUTOMATIC = "input.automatic";
    public String LANGUAGE = "input.language";
    public String MORNING = "greeting.morning";
    public String AFTERNOON = "greeting.afternoon";
    public String EVENING = "greeting.evening";
    public String GOODBYE = "greeting.goodbye";
    public String CONGRATULATIONS = "greeting.congratulations";


    public MessageManager(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        extractDataFromResourceBundle();
    }

    private void extractDataFromResourceBundle() {
        messages = new HashMap<>();
        keys = new HashSet<>();

        for (String key : resourceBundle.keySet()) {
            messages.put(key, resourceBundle.getString(key));
            keys.add(key);
        }
    }

    private void updateResourceBundle() {
        for (String key : keys) {
            resourceBundle.getString(key);
        }
    }

    public String getSentence(String key) {
        return messages.get(key);
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

}
