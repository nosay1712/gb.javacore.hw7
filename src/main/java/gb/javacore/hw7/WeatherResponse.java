package gb.javacore.hw7;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.ZonedDateTime;

public class WeatherResponse {
    public String jsonString;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherResponse(String sourceString)
    {
        jsonString= sourceString;
    }

    public String getFormattedResponse() throws JsonProcessingException {

        String temperature = objectMapper.readTree(jsonString).get(0).at("/Temperature/Metric/Value").asText();
        String weatherText = objectMapper.readTree(jsonString).get(0).at("/WeatherText").asText();

        return "temperature " + temperature + " C, Weather " + weatherText;
    }

    public String getFormattedResponseForPeriod() throws JsonProcessingException {

        JsonNode jsonNode = objectMapper.readTree(jsonString).at("/DailyForecasts");
        String response =" Weather for the next "+jsonNode.size()+ " days\n";

        for (int i=0; i<jsonNode.size();i++)
        {

            String globalDate =jsonNode.get(i).at("/Date").asText();

            ZonedDateTime dateTime = ZonedDateTime.parse(globalDate);
            String simpleData = dateTime.toLocalDate().toString();

            String minTemperature = jsonNode.get(i).at("/Temperature/Minimum/Value").asText();
            String maxTemperature = jsonNode.get(i).at("/Temperature/Maximum/Value").asText();

            String weatherText = jsonNode.get(i).at("/Day/IconPhrase").asText();

            response +="On "+simpleData+" Temperature from "+minTemperature+
                    " to "+maxTemperature+" C, in the afternoon "+weatherText+"\n";
        }

        return response;
    }
}
