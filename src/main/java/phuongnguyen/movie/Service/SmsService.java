package phuongnguyen.movie.Service;

import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.Request.SmsRequest;

public interface SmsService {

    Response sendMessage(SmsRequest smsRequest);
}
