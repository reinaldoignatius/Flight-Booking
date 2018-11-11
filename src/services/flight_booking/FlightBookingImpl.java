package services.flight_booking;

import javax.jws.WebService;

@WebService(endpointInterface = "services.flight_booking.FlightBooking")
public class FlightBookingImpl implements FlightBooking {

  public void bookFlight(int user_id, int flight_id) {

  }

  public void cancelFlight(int user_id, int flight_id) {

  }

  public void rescheduleFlight(int user_id, int old_flight_id, int new_flight_id) {
    cancelFlight(user_id, old_flight_id);
    bookFlight(user_id, new_flight_id);
  }

}
