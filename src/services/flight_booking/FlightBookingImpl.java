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
  public String bookFlight(String username, String flightNumber, Passenger[] passengers) {

    String booking_number = String.format("%d%s%s", new Date().getTime(), username, flightNumber);
    Unirest.get(String.format("http://localhost:8000/flights/%s/", flightNumber))
            .asJsonAsync(new Callback<JsonNode>() {
              @Override
              public void completed(HttpResponse<JsonNode> httpResponse) {
                int newAvailableSeats = httpResponse.getBody().getObject().getInt("available_seats") - passengers.length;
                Unirest.patch(String.format("http://localhost:8000/flights/%s/", flightNumber))
                        .field("available_seats", newAvailableSeats).asJsonAsync(new Callback<JsonNode>() {
                          @Override
                            public void completed(HttpResponse<JsonNode> httpResponse) {
                              if (httpResponse.getStatus() == 200) {
                                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                                try {
                                  Unirest.post("http://localhost:8000/bookings/")
                                          .field("number", booking_number)
                                          .field("user", username)
                                          .field("flight", flightNumber)
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
              }

              @Override
              public void failed(UnirestException e) {
               e.printStackTrace();
              }

              @Override
              public void cancelled() {

              }
            });

    return booking_number;
  }

  public void cancelBooking(String bookingNumber) {
    Unirest.get(String.format("http://localhost:8000/bookings/%s/", bookingNumber)).asJsonAsync(new Callback<JsonNode>() {
      @Override
      public void completed(HttpResponse<JsonNode> httpResponse) {
        JSONObject booking = httpResponse.getBody().getObject();
        int passengerCount = booking.getJSONArray("passengers").length();
        String flightNumber = booking.getString("flight");
        Unirest.get(String.format("Http://localhost:8000/flights/%s/", flightNumber)).asJsonAsync(new Callback<JsonNode>() {
          @Override
          public void completed(HttpResponse<JsonNode> httpResponse) {
            int newAvailableSeats = httpResponse.getBody().getObject().getInt("available_seats") + passengerCount;
            Unirest.patch(String.format("Http://localhost:8000/flights/%s/", flightNumber))
                    .field("available_seats", newAvailableSeats).asJsonAsync(new Callback<JsonNode>() {
              @Override
              public void completed(HttpResponse<JsonNode> httpResponse) {
                try {
                  Unirest.delete(String.format("http://localhost:8000/bookings/%s/", bookingNumber)).asJson();
                } catch(Exception e) {
                  e.printStackTrace();
                }
              }

              @Override
              public void failed(UnirestException e) {
                e.printStackTrace();
                System.out.println("get2");
              }

              @Override
              public void cancelled() {

              }
            });
          }

          @Override
          public void failed(UnirestException e) {
            e.printStackTrace();
            System.out.println("delete");
          }

          @Override
          public void cancelled() {

          }
        });
      }

      @Override
      public void failed(UnirestException e) {
        e.printStackTrace();
      }

      @Override
      public void cancelled() {

      }
    });
  }

  public void rescheduleFlight(String bookingNumber, String newFlightNumber) {
    Unirest.get(String.format("http://localhost:8000/bookings/%s/", bookingNumber))
            .header("Connection", "keep-alive").asJsonAsync(new Callback<JsonNode>() {
      @Override
      public void completed(HttpResponse<JsonNode> httpResponse) {
        JSONObject booking = httpResponse.getBody().getObject();
        String oldFlightNumber = booking.getString("flight");
        int passengerCount = booking.getJSONArray("passengers").length();
        try {
          Unirest.patch(String.format("http://localhost:8000/bookings/%s/", bookingNumber))
                  .field("flight", newFlightNumber).asJson();
        } catch(Exception e) {
          e.printStackTrace();
        }

        Unirest.get(String.format("http://localhost:8000/flights/%s/", oldFlightNumber)).asJsonAsync(new Callback<JsonNode>() {
          @Override
          public void completed(HttpResponse<JsonNode> httpResponse) {
            int oldFlightNewAvailableSeats = httpResponse.getBody().getObject().getInt("available_seats") + passengerCount;
            try {
              Unirest.patch(String.format("http://localhost:8000/flights/%s/", oldFlightNumber))
                      .field("available_seats", oldFlightNewAvailableSeats).asJsonAsync(new Callback<JsonNode>() {
                @Override
                public void completed(HttpResponse<JsonNode> httpResponse) {

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

          @Override
          public void failed(UnirestException e) {
            e.printStackTrace();
          }

          @Override
          public void cancelled() {

          }
        });

        Unirest.get(String.format("http://localhost:8000/flights/%s/", newFlightNumber)).asJsonAsync(new Callback<JsonNode>() {
          @Override
          public void completed(HttpResponse<JsonNode> httpResponse) {
            int newFlightNewAvailableSeats = httpResponse.getBody().getObject().getInt("available_seats") - passengerCount;
            try {
              Unirest.patch(String.format("http://localhost:8000/flights/%s/", newFlightNumber))
                      .field("available_seats", newFlightNewAvailableSeats).asJsonAsync(new Callback<JsonNode>() {
                @Override
                public void completed(HttpResponse<JsonNode> httpResponse) {

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

          @Override
          public void failed(UnirestException e) {
            e.printStackTrace();
          }

          @Override
          public void cancelled() {

          }
        });
      }

      @Override
      public void failed(UnirestException e) {
        e.printStackTrace();
      }

      @Override
      public void cancelled() {

      }
    });
  }

}
