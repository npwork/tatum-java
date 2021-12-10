package io.tatum.model.response.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TronAccount {
    private BigDecimal balance;
    private Trc10[] trc10;
    private Map<String, BigDecimal>[] trc20;
    private long createTime;
}
