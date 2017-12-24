package tingting.cdp.Vo;

import lombok.*;

/**
 * Created by cdp on 2017/12/24.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ParseRequestVo {
    private String url;
    private String language;
}
