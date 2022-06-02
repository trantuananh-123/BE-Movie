package phuongnguyen.movie.Service.Impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import phuongnguyen.movie.Repository.UserRepository;
import phuongnguyen.movie.Table.Model.User;
import phuongnguyen.movie.Table.Model.VerifyToken;
import phuongnguyen.movie.Table.Response.GenerateResponse;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.Response.StatusCode;
import phuongnguyen.movie.Service.SmsService;
import phuongnguyen.movie.Service.VerifyTokenService;
import phuongnguyen.movie.Table.Request.SmsRequest;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SmsServiceImpl implements SmsService {

    private final Logger logger = LogManager.getLogger(SmsServiceImpl.class);

    @Value(value = "${account.sid}")
    private String accountSID;

    @Value(value = "${auth.token}")
    private String authToken;

    @Value(value = "${phone.number}")
    private String phoneNumber;

    @Autowired
    private VerifyTokenService verifyTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response sendMessage(SmsRequest smsRequest) {
        try {
            User user;
            Optional<User> optionalUser = userRepository.findById(smsRequest.getUserId());
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                return GenerateResponse.generateErrorResponse("USER NOT FOUND", StatusCode.ERROR);
            }
            Twilio.init(accountSID, authToken);

            VerifyToken verifyToken = verifyTokenService.createVerifyToken(user);
            String text = smsRequest.getText();

            PhoneNumber phoneNumberReceiver = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber phoneNumberSender = new PhoneNumber(phoneNumber);

            MessageCreator
                creator = Message.creator(phoneNumberReceiver, phoneNumberSender, text);
            Message create = creator.create();

            BigDecimal billingAmount = create.getPrice();
            Status status = create.getStatus();

            logger.info(billingAmount);
            logger.info(status);
            return GenerateResponse.generateSuccessResponse("Message send successfully to the number: "
                + smsRequest.getPhoneNumber(), StatusCode.SUCCESS, verifyToken);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse("ERROR", StatusCode.ERROR);
        }
    }
}
