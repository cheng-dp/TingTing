package tingting.cdp.Vo;

import lombok.*;
import sun.plugin2.message.Message;
import tingting.cdp.MessageCode;

import java.util.List;

/**
 * Created by cdp on 2017/12/20.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AudioMessageVo {
    private int messageCode;
    private String message;
    private List<Byte> audioByte;

    public static final AudioMessageVo errorAudioMessage =
            new AudioMessageVo(MessageCode.ERROR, "TTS ERROR", null);
}
