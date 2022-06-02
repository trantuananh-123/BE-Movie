package phuongnguyen.movie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Service.SmsService;
import phuongnguyen.movie.Table.Request.SmsRequest;

import java.util.Random;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/v1/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public Response sendSMS(@RequestBody SmsRequest smsRequest) {
        Random random = new Random();
        smsRequest.setText(smsRequest.getText() + String.valueOf(100000 + random.nextInt(900000)));
        return smsService.sendMessage(smsRequest);
    }
}
