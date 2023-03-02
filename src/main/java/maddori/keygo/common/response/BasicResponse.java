package maddori.keygo.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class BasicResponse {
    private final boolean success;
    private final String message;
}
