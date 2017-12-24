package tingting.cdp;

import tingting.cdp.Vo.AudioMessageVo;

/**
 * Created by cdp on 2017/12/21.
 */

public interface TTSService {

    AudioMessageVo getAudioForText(String text);
}
