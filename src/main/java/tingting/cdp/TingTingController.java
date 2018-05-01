
package tingting.cdp;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingting.cdp.Vo.AudioMessageVo;
import tingting.cdp.Vo.ParseResultVo;

import java.util.List;

/**
 * Created by cdp on 2017/12/14.
 */
/*
@RestController
public class TingTingController {



    @RequestMapping(
        value="/getAudioForUrl",
        method= RequestMethod.POST
    )
    public ResponseEntity<byte[]> getAudioForUrl(@RequestParam String url){
        ParseResultVo parseResultVo = parserService.parseText(url);
        if(parseResultVo.getCode() != MessageCode.SUCCESS){
            throw new TextTTSException("parse article error:" + parseResultVo.getMessage());
        }

        AudioMessageVo audioMessageVo = ttsService.getAudioForText(parseResultVo.getText());
        if(audioMessageVo.getMessageCode() != MessageCode.SUCCESS){
            throw new TextTTSException("TTS error:" + audioMessageVo.getMessage());
        }

        List<Byte> audioByte = audioMessageVo.getAudioByte();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/mp3"));

        byte[] retAudio = ArrayUtils.toPrimitive(audioByte.toArray(new Byte[audioByte.size()]));

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(retAudio,headers,HttpStatus.OK);
        return responseEntity;
    }

    @ExceptionHandler(TextTTSException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String textTTSError(TextTTSException e){
        return e.toString();
    }

}
*/