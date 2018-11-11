package services.flight_booking;

import javax.jws.WebService;

@WebService(endpointInterface = "services.flight_booking.FlightBooking")
public class FlightBookingImpl implements FlightBooking {

  public int bookFlight(int user_number, int flight_number) {
    return 0;
  }

  public void cancelFlight(int user_number, int flight_number) {

  }

  public void rescheduleFlight(int user_number, int old_flight_number, int new_flight_number) {
    cancelFlight(user_number, old_flight_number);
    bookFlight(user_number, new_flight_number);
  }

}
