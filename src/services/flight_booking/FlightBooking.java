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
          @WebParam(name = "username") String username,
          @WebParam(name = "flight_number") int flight_number
  );

  @WebMethod
  void cancelFlight(
          @WebParam(name = "username") String username,
          @WebParam(name = "flight_number") int flight_number
  );

  @WebMethod
  void rescheduleFlight(
          @WebParam(name = "username") String username,
          @WebParam(name = "old_flight_number") int old_flight_number,
          @WebParam(name = "new_flight_number") int new_flight_number
  );

}
