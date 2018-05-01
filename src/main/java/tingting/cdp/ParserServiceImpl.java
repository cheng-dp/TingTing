package tingting.cdp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tingting.cdp.Vo.ParseRequestVo;
import tingting.cdp.Vo.ParseResultVo;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by cdp on 2017/12/19.
 */
@Component
@Slf4j
public class ParserServiceImpl implements ParserService{

    private static final RestTemplate rest = new RestTemplate();

    private final ParseServerProperties parseServerProperties;

    public ParserServiceImpl(ParseServerProperties parseServerProperties){
        this.parseServerProperties = parseServerProperties;
    }

    @Override
    public boolean isUrl(String urlStr){
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(urlStr);
    }

    @Override
    public ParseResultVo parseText(String urlStr){
        ParseResultVo parseResultVo_1 = parseText(urlStr,LanCode.ZH);
        if(Objects.isNull(parseResultVo_1) || parseResultVo_1.getCode() == MessageCode.ERROR){
            return parseResultVo_1;
        }
        ParseResultVo parseResultVo_2 = parseText(urlStr,LanCode.EN);
        if(Objects.isNull(parseResultVo_2) || parseResultVo_2.getCode() == MessageCode.ERROR){
            return parseResultVo_1;
        }
        if(parseResultVo_1.getText().trim().length()
                > parseResultVo_2.getText().trim().length()){
            return parseResultVo_1;
        }
        else{
            return parseResultVo_2;
        }
    }

    @Override
    public ParseResultVo parseText(String urlStr, String lanCode){
        if(!isUrl(urlStr)){
            return new ParseResultVo(MessageCode.ERROR,urlStr + " not valid url.","");
        }
        Map<String,String> uriVariables = new HashMap<String, String>();

        ParseRequestVo parseRequestVo = new ParseRequestVo(urlStr,lanCode);

        String parseServerUrl = parseServerProperties.getHost() + ":" + parseServerProperties.getPort()
                + RestEndPoint.TEXT_PARSER_END_POINT;
        try{
            ParseResultVo ret =
                    rest.postForObject(parseServerUrl,parseRequestVo,ParseResultVo.class,uriVariables);
            return ret;
        }
        catch(Exception e){
            log.error(e.toString());
            return new ParseResultVo(MessageCode.ERROR,e.toString(),"");
        }
    }

}
