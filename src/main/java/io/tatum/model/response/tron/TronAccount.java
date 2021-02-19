package io.tatum.model.response.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TronAccount {
    private String address;
    private long freeNetUsage;
    private long balance;
    private Trc10[] trc10;
    private Map<String, String>[] trc20;
    private String assetIssuedId;
    private String assetIssuedName;
    private long createTime;
}
