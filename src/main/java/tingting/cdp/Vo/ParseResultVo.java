package tingting.cdp.Vo;

import lombok.*;
import sun.plugin2.message.Message;
import tingting.cdp.MessageCode;

/**
 * Created by cdp on 2017/12/20.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ParseResultVo {
    private int code;
    private String message;
    private String text;

    public static final ParseResultVo errorParseResult =
            new ParseResultVo(MessageCode.ERROR,"Parse webpage error",null);
}
