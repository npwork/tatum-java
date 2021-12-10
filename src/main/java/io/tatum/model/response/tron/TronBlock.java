package io.tatum.model.response.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TronBlock {

    private String hash;
    private long blockNumber;
    private long timestamp;
    private String parentHash;
    private String witnessAddress;
    private String witnessSignature;
    private TronTransaction[] transactions;
}
