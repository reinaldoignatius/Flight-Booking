package services.flight_booking;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONString;

import javax.jws.WebService;

@WebService(endpointInterface = "services.flight_booking.FlightBooking")
public class FlightBookingImpl implements FlightBooking {

  public int bookFlight(String username, String flight_number, String[] passangers) {
    try {
      String booking_number = "TEST123";

      HttpResponse<JsonNode> createBookingResponse = Unirest.post("http://localhost:8000/bookings/")
              .field("number", booking_number)
              .field("user", username)
              .field("flight", flight_number)
              .field("passengers", passangers)
              .asJson();
      System.out.println(createBookingResponse.getBody());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public void cancelBooking(String username, String booking_number) {

  }

  public void rescheduleFlight(String username, String old_flight_number, String new_flight_number) {
    cancelBooking(username, old_flight_number);
//    bookFlight(username, new_flight_number, );
  }

}
