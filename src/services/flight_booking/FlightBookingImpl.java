package services.flight_booking;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.jws.WebService;
import java.util.Date;

@WebService(endpointInterface = "services.flight_booking.FlightBooking")
public class FlightBookingImpl implements FlightBooking {
  public static final String HOST = "http://167.205.35.219:8080";

  public String bookFlight(String username, String flightNumber, Passenger[] passengers) {
    try {
      JSONObject variables = new JSONObject();
      variables.append("username", new JSONObject());
      variables.getJSONObject("username").append("value", username);
      variables.getJSONObject("username").append("type", "string");

      variables.append("flight_number", new JSONObject());
      variables.getJSONObject("flight_number").append("value", flightNumber);
      variables.getJSONObject("flight_number").append("type", "string");

      variables.append("passengers",new JSONObject());
      variables.getJSONObject("passengers").append("value", new JSONArray());
      variables.getJSONObject("passengers").append("type", "string");

      for (Passenger passenger: passengers) {
        variables.getJSONObject("passengers").getJSONObject("value").append("first_name", passenger.getFirstName());
        variables.getJSONObject("passengers").getJSONObject("value").append("last_name", passenger.getLastName());
      }

      JSONObject requestObject = new JSONObject();
      requestObject.append("variables", variables);
      HttpResponse<JsonNode> response = Unirest.post(String.format("%s/engine-rest/process-definition/key/BookFlight/start", HOST))
              .body(requestObject).asJson();

      return String.format("Booking succeed with number: %s", response.getBody().getObject().getString("id"));
    } catch(Exception e) {
      e.printStackTrace();
    }
    return "Booking failed";
  }

  public String cancelBooking(String bookingNumber) {
    try {
      JSONObject variables = new JSONObject();

      variables.append("booking_number", new JSONObject());
      variables.getJSONObject("booking_number").append("value", bookingNumber);
      variables.getJSONObject("booking_number").append("type", "string");

      JSONObject requestObject = new JSONObject();
      HttpResponse<JsonNode> response = Unirest.post(String.format(
              "%s/engine-rest/process-definition/key/CancelBooking/start", HOST)).body(requestObject).asJson();

      return String.format("Booking success with id: %s", response.getBody().getObject().getString("id"));
    } catch(Exception e) {
      e.printStackTrace();
    }
    return "Booking cancellation failed";
  }

  public String rescheduleFlight(String bookingNumber, String flightNumber) {
    try {
      JSONObject variables = new JSONObject();

      variables.append("booking_number", new JSONObject());
      variables.getJSONObject("booking_number").append("value", bookingNumber);
      variables.getJSONObject("booking_number").append("type", "string");

      variables.append("flight_number", new JSONObject());
      variables.getJSONObject("flight_number").append("value", flightNumber);
      variables.getJSONObject("flight_number").append("type", "string");

      JSONObject requestObject = new JSONObject();
      HttpResponse<JsonNode> response = Unirest.post(String.format(
              "%s/engine-rest/process-definition/key/RescheduleBooking/start", HOST)).body(requestObject).asJson();

      return String.format("Booking success with id: %s", response.getBody().getObject().getString("id"));
    } catch(Exception e) {
      e.printStackTrace();
    }
    return "Booking reschedule failed";
  }

}
