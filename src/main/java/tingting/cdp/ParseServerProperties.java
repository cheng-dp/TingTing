package tingting.cdp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by cdp on 2017/12/28.
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties("tingting.parseserver")
public class ParseServerProperties {
    String host;
    String port;
}
