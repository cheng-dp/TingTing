package tingting.cdp.Vo;

import lombok.*;
import tingting.cdp.MessageCode;

/**
 * Created by cdp on 2018/1/10.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TingTingGetAudioVo {
    private int code;
    private String message;
    private byte[] audio;

    public static final TingTingGetAudioVo getError(String errorMessage){
        return new TingTingGetAudioVo(MessageCode.ERROR,errorMessage, null);
    }
}
