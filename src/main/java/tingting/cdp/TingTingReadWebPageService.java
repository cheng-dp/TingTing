package tingting.cdp;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestParam;
import tingting.cdp.Vo.AudioMessageVo;
import tingting.cdp.Vo.ParseResultVo;
import tingting.cdp.Vo.TingTingGetAudioVo;

import java.util.List;

/**
 * Created by cdp on 2018/1/10.
 */
public class TingTingReadWebPageService {

    @Autowired
    ParserService parserService;

    @Autowired
    @Qualifier("baiduTTS")
    TTSService ttsService;

    public TingTingGetAudioVo readWebPage(@RequestParam String url){
        ParseResultVo parseResultVo = parserService.parseText(url);
        if(parseResultVo.getCode() != MessageCode.SUCCESS){
            return TingTingGetAudioVo.getError("parse article error:" + parseResultVo.getMessage());
        }
        AudioMessageVo audioMessageVo = ttsService.getAudioForText(parseResultVo.getText());
        if(audioMessageVo.getCode() != MessageCode.SUCCESS){
            return TingTingGetAudioVo.getError("TTS error:" + audioMessageVo.getMessage());
        }
        List<Byte> audioByte = audioMessageVo.getAudioByte();
        byte[] retAudio = ArrayUtils.toPrimitive(audioByte.toArray(new Byte[audioByte.size()]));

        return new TingTingGetAudioVo(MessageCode.SUCCESS,"get audio success",retAudio);
    }
}
