package tingting.cdp;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tingting.cdp.Vo.AudioMessageVo;

import java.util.*;

/**
 * Created by cdp on 2017/12/23.
 */
@Slf4j
@Component
@Qualifier("baiduTTS")
public class TTSServiceBaiduImpl implements TTSService{

    @Autowired
    TextProcessService textProcessService;

    private final BaiduTTSProperties baiduTTSProperties;

    public TTSServiceBaiduImpl(BaiduTTSProperties baiduTTSProperties){
        this.baiduTTSProperties = baiduTTSProperties;
    }

    @Override
    public AudioMessageVo getAudioForText(String text){

        log.debug("baiduTTSProperties:");
        log.debug(baiduTTSProperties.toString());

        if(Objects.isNull(text)){
            return AudioMessageVo.errorAudioMessage;
        }

        String readableText = textProcessService.textToReadable(text);
        List<String> splitedText;

        try{
            splitedText = textProcessService.textSplit(readableText);
        }
        catch(Exception e){
            log.error(e.toString());
            return AudioMessageVo.errorAudioMessage;
        }

        List<String> voiceText = textProcessService.removeUnVoiceText(splitedText);

        AudioMessageVo audioMessageVo = new AudioMessageVo(MessageCode.SUCCESS,"",new LinkedList<>());
        for(String t : voiceText){
            AipSpeech client = new AipSpeech(baiduTTSProperties.getAppId(), baiduTTSProperties.getApiKey(), baiduTTSProperties.getSecretKey());

            HashMap<String, Object> options = new HashMap<>();
            options.put("per", baiduTTSProperties.getPer());

            TtsResponse res = client.synthesis(t, baiduTTSProperties.getLanguage(), baiduTTSProperties.getCtp(), options);
            byte[] data = res.getData();
            JSONObject res1 = res.getResult();
            if(data != null){
                for(byte d : data){
                    audioMessageVo.getAudioByte().add(d);
                }
            }
            if(res1 != null){
                log.error(res1.toString(4));
                audioMessageVo.setMessage(res1.getString("err_msg"));
                audioMessageVo.setCode(MessageCode.ERROR);
                return audioMessageVo;
            }
        }

        return audioMessageVo;
    }
}
