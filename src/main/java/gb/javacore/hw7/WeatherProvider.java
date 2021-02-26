package gb.javacore.hw7;

import gb.javacore.hw7.enums.Periods;

import java.io.IOException;

public interface WeatherProvider {

    void getWeather(Periods periods) throws IOException;

}
