package services.flight_booking;

import javax.xml.ws.Endpoint;

public class FlightBookingPublisher {
  public static void main(String[] args) {
    Endpoint.publish("http://localhost:8080/services/flightBooking", new FlightBookingImpl());
  }
}
