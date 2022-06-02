package phuongnguyen.movie.Table.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GenerateResponse {

    public static Response generateSuccessResponse(String message, int status, Object data) {
        Response response = new Response();
        response.setMessage(message);
        response.setData(data);
        response.setStatus(status);
        return response;
    }

    public static Response generateErrorResponse(String message, int status) {
        Response response = new Response();
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }

}
