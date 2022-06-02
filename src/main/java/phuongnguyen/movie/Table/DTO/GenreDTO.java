package phuongnguyen.movie.Table.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GenreDTO {

    private Integer id;
    private String name;
    private Boolean isActive;
    private Boolean isDelete;
    @JsonFormat(timezone = "GMT+07:00")
    private LocalDateTime createdDate;
    @JsonFormat(timezone = "GMT+07:00")
    private LocalDateTime updatedDate;
    private String createdUserId;
    private String updatedUserId;
}
