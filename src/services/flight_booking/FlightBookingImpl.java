package services.flight_booking;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@WebService(endpointInterface = "services.flight_booking.FlightBooking")
public class FlightBookingImpl implements FlightBooking {
  public static final String HOST = "http://167.205.35.219:8080";

  public String bookFlight(String username, String flightNumber, Passenger[] passengers, String eventId) {
    try {
      HashMap<String, Object> variables = new HashMap<>();
      variables.put("username", new CamundaItem(username, "string"));
      variables.put("flight_number", new CamundaItem(flightNumber, "string"));
      variables.put("passengers", new CamundaItem(passengers, "string"));
      variables.put("event_id", new CamundaItem(eventId, "string");

      HashMap<String, HashMap<String, Object>> requestObject = new HashMap<>();
      requestObject.put("variables", variables);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

      String serialized = objectMapper.writeValueAsString(requestObject);

      HttpResponse<JsonNode> response = Unirest.post(String.format("%s/engine-rest/process-definition/key/BookFlight/start", HOST))
              .header("Content-Type", "application/json")
              .body(serialized).asJson();
      System.out.println(response.getBody());
      return String.format("Booking succeed with number: %s", response.getBody().getObject().getString("id"));
    } catch(Exception e) {
      e.printStackTrace();
    }
    return "Booking failed";
  }

  public String cancelBooking(String bookingNumber) {
    try {
      HashMap<String, Object> variables = new HashMap<>();
      variables.put("booking_number", new CamundaItem(bookingNumber, "string"));

      HashMap<String, Object> requestObject = new HashMap<>();
      requestObject.put("variables", variables);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

      String serialized = objectMapper.writeValueAsString(requestObject);

      HttpResponse<JsonNode> response = Unirest.post(String.format(
              "%s/engine-rest/process-definition/key/CancelBooking/start", HOST))
              .header("Content-Type", "application/json")
              .body(serialized).asJson();

      return String.format("Cancel success with id: %s", response.getBody().getObject().getString("id"));
    } catch(Exception e) {
      e.printStackTrace();
    }
    return "Booking cancellation failed";
  }

  public String rescheduleFlight(String bookingNumber, String flightNumber) {
    try {
      HashMap<String, Object> variables = new HashMap<>();
      variables.put("booking_number", new CamundaItem(bookingNumber, "string"));
      variables.put("flight_number", new CamundaItem(flightNumber, "string"));

      HashMap<String, Object> requestObject = new HashMap<>();
      requestObject.put("variables", variables);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
      String serialized = objectMapper.writeValueAsString(requestObject);

      HttpResponse<JsonNode> response = Unirest.post(String.format(
              "%s/engine-rest/process-definition/key/RescheduleBooking/start", HOST))
              .header("Content-Type", "application/json")
              .body(serialized).asJson();

      return String.format("Booking rescheduled with id: %s", response.getBody().getObject().getString("id"));
    } catch(Exception e) {
      e.printStackTrace();
    }
    return "Booking reschedule failed";
  }

}
