package services.flight_booking;

import javafx.util.Pair;
import org.json.JSONString;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FlightBooking {

  @WebMethod
  int bookFlight(
          @WebParam(name = "username")String username,
          @WebParam(name = "flight_number")String flight_number,
          @WebParam(name = "passengers")String[] passengers
  );

  @WebMethod
  void cancelBooking(
          @WebParam(name = "username")String username,
          @WebParam(name = "flight_number")String booking_number
  );

  @WebMethod
  void rescheduleFlight(
          @WebParam(name = "username")String username,
          @WebParam(name = "old_flight_number")String old_flight_number,
          @WebParam(name = "new_flight_number")String new_flight_number
  );

}
