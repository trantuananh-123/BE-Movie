package phuongnguyen.movie.Table.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String username;
    private String accessToken;
    private LocalDateTime expiredAt;
}
