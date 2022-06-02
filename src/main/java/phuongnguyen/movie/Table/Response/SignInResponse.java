package phuongnguyen.movie.Table.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import phuongnguyen.movie.Table.Model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignInResponse {

    private String accessToken;
    private String type = "Bearer";
    private User user;
    private LocalDateTime expiredAt;

}
