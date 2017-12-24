package tingting.cdp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tingting.cdp.Vo.ParseResultVo;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by cdp on 2017/12/20.
 */
public interface ParserService {

    boolean isUrl(String urlStr);

    ParseResultVo parseText(String urlStr);

    ParseResultVo parseText(String urlStr, String lanCode);

}
