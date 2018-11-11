package services.flight_booking;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FlightBooking {

  @WebMethod
  int bookFlight(
          @WebParam(name = "user_number") int user_number,
          @WebParam(name = "flight_number") int flight_number
  );

  @WebMethod
  void cancelFlight(
          @WebParam(name = "user_number") int user_number,
          @WebParam(name = "flight_number") int flight_number
  );

  @WebMethod
  void rescheduleFlight(
          @WebParam(name = "user_number") int user_number,
          @WebParam(name = "old_flight_number") int old_flight_number,
          @WebParam(name = "new_flight_number") int new_flight_number
  );

}
