package services.flight_booking;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import javax.jws.WebService;

@WebService(endpointInterface = "services.flight_booking.FlightBooking")
public class FlightBookingImpl implements FlightBooking {

  public int bookFlight(String username, int flight_number) {
    try {
      HttpResponse<JsonNode> createBookingResponse = Unirest.post("localhost:8000/bookings/")
              .field("username", username)
              .field("flight_number", flight_number)
              .asJson();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public void cancelFlight(String username, int flight_number) {

  }

  public void rescheduleFlight(String username, int old_flight_number, int new_flight_number) {
    cancelFlight(username, old_flight_number);
    bookFlight(username, new_flight_number);
  }

}
