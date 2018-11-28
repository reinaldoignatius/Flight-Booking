package services.flight_booking;

import javax.xml.ws.Endpoint;

public class FlightBookingPublisher {
  public static void main(String[] args) {
    System.out.println("Publishing services");
      Endpoint.publish("http://localhost:8888/services/flightBooking", new FlightBookingImpl());
    }
  }
