package cc.llcon.youshanxunche.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Pos {
    private BigInteger id;
    private BigDecimal latitude;
    private String latitudeDir;
    private BigDecimal longitude;
    private String longitudeDir;
    private String deviceId;
    private String sats;
    private LocalDateTime createTime;
}
