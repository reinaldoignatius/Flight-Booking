package services.flight_booking;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FlightBooking {

  @WebMethod
  void bookFlight(
          @WebParam(name = "user_id") int user_id,
          @WebParam(name = "flight_id") int flight_id
  );

  @WebMethod
  void cancelFlight(
          @WebParam(name = "user_id") int user_id,
          @WebParam(name = "flight_id") int flight_id
  );

  @WebMethod
  void rescheduleFlight(
          @WebParam(name = "user_id") int user_id,
          @WebParam(name = "old_flight_id") int old_flight_id,
          @WebParam(name = "new_flight_id") int new_flight_id
  );

}
