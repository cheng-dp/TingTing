package tingting.cdp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by cdp on 2017/12/26.
 */
@Component
@Getter
@Setter
@ToString
@ConfigurationProperties("tingting.textprocess")
public class TextProcessProperties {

    int maxSplitByteLength;
    String encoder;
}
