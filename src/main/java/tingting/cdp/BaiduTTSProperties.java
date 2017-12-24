package tingting.cdp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by cdp on 2017/12/26.
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties("tingting.baidutts")
public class BaiduTTSProperties {
    private String appId;
    private String apiKey;
    private String secretKey;
    private String language;
    private String per;
    private int ctp;
}
