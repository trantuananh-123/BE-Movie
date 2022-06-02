package phuongnguyen.movie.Table.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleDTO {

    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
    private Boolean isDelete;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
