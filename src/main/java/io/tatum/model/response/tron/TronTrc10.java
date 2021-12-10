package io.tatum.model.response.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TronTrc10 {

    private long precision;
    private String description;
    private long id;
    private String abbr;
    private String url;
    private String name;
    private BigDecimal totalSupply;
    private String ownerAddress;
}
