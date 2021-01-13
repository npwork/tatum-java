package io.tatum.model.response.bch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * The type Bch info.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BchInfo {

    private String chain;
    private long blocks;
    private String bestblockhash;
    private BigInteger difficulty;
}
