package services.flight_booking;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.jws.WebService;
import java.util.Date;

@WebService(endpointInterface = "services.flight_booking.FlightBooking")
public class FlightBookingImpl implements FlightBooking {
  public String bookFlight(String username, String flight_number, Passenger[] passengers) {
    Unirest.setTimeouts(10000, 600000);

    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

    String booking_number = String.format("%d%s%s", new Date().getTime(), username, flight_number);
    try {
      Unirest.get(String.format("http://localhost:8000/flights/%s/", flight_number))
              .asJsonAsync(new Callback<JsonNode>() {
                @Override
                public void completed(HttpResponse<JsonNode> httpResponse) {
                  int newAvailableSeats = httpResponse.getBody().getObject().getInt("available_seats") - passengers.length;
                  try {
                    Unirest.patch(String.format("http://localhost:8000/flights/%s/", flight_number))
                            .field("available_seats", newAvailableSeats).asJsonAsync(new Callback<JsonNode>() {
                              @Override
                                public void completed(HttpResponse<JsonNode> httpResponse) {
                                  if (httpResponse.getStatus() == 200) {
                                    try {
                                      Unirest.post("http://localhost:8000/bookings/")
                                              .field("number", booking_number)
                                              .field("user", username)
                                              .field("flight", flight_number)
                                              .field("passengers", mapper.writeValueAsString(passengers))
                                              .asJsonAsync(new Callback<JsonNode>() {
                                                @Override
                                                public void completed(HttpResponse<JsonNode> httpResponse) {
//                                                  Issue Invoice
                                                }

                                                @Override
                                                public void failed(UnirestException e) {

                                                }

                                                @Override
                                                public void cancelled() {

                                                }
                                              });
                                    } catch(Exception e) {
                                      e.printStackTrace();
                                    }
                                  }
                                }

                              @Override
                              public void failed(UnirestException e) {
                                e.printStackTrace();
                              }

                              @Override
                              public void cancelled() {

                              }
                            });
                  } catch(Exception e) {
                    e.printStackTrace();
                  }
                }

                @Override
                public void failed(UnirestException e) {
                 e.printStackTrace();
                }

                @Override
                public void cancelled() {

                }
              });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return booking_number;
  }

  public void cancelBooking(String username, String booking_number) {

  }

  public void rescheduleFlight(String username, String old_flight_number, String new_flight_number) {
    cancelBooking(username, old_flight_number);
//    bookFlight(username, new_flight_number, );
  }

}
