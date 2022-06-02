package phuongnguyen.movie.Table.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {

    private String userId;
    private String phoneNumber;
    private String text;

}
