package io.tatum.model.response.vet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private String[] topics;
}
