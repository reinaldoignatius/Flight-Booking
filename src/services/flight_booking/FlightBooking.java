package services.flight_booking;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FlightBooking {

  @WebMethod
  String bookFlight(
          @WebParam(name = "username")String username,
          @WebParam(name = "flight_number")String flightNumber,
          @WebParam(name = "passengers")Passenger[] passengers
  );

  @WebMethod
  void cancelBooking(
          @WebParam(name = "booking_number")String bookingNumber
  );

  @WebMethod
  void rescheduleFlight(
          @WebParam(name = "booking_number")String bookingNumber,
          @WebParam(name = "new_flight_number")String newFlightNumber
  );

}
