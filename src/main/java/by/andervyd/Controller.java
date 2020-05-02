package by.andervyd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {
    @FXML
    private Label nameCity;
    @FXML
    private TextField enterCity;
    @FXML
    private Text temperatureFX;
    @FXML
    private Button buttonSearch;
    @FXML
    private RadioButton metric;
    @FXML
    private RadioButton imperial;
/*
    @FXML
    private ImageView changeBG;
    @FXML
    private Text maxFx;
    @FXML
    private Text fetlFX;
    @FXML
    private Text minFX;
    @FXML
    private Text pressureFX;
*/

    private String temperature;
    private String icon;

    @FXML
    void initialize() {

        ToggleGroup groupTG = new ToggleGroup();

        groupTG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (groupTG.getSelectedToggle() != null) {
                    RadioButton button = (RadioButton) groupTG.getSelectedToggle();
                    temperature = button.getId();
                    icon = button.getText();
                }
            }
        });

        buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String getUserCity = enterCity.getText().trim();
                if(!getUserCity.equals("")) {
                    String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=c7d90c67450ed7d8990c1553a9cb5d57&units=" + temperature);
                    if (!output.isEmpty()) {
                        JSONObject obj = new JSONObject(output);
                        temperatureFX.setText(obj.getJSONObject("main").getInt("temp") + icon);
                        nameCity.setText(obj.getString("name"));
                    }
                }
            }
        });

        metric.setToggleGroup(groupTG);
        metric.setSelected(true);
        imperial.setToggleGroup(groupTG);
    }

    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }

            bufferedReader.close();

        } catch (Exception e) {
            System.out.println("This city not fained!");
        }
        return content.toString();
    }
}