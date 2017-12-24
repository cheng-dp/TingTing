package tingting.cdp;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by cdp on 2017/12/23.
 */
public interface TextProcessService {

    /**
     *
     * Split the text if length is larger then maxSplitLength, punctuation considered.
     *
     * @param str original text
     * @return String list after split.
     */
    List<String> textSplit(String str) throws UnsupportedEncodingException;

    /**
     * UnVoice text is the text return no voice if call TTS,
     * here means text only contain punctuations.
     * @param text
     * @return
     */
    List<String> removeUnVoiceText(List<String> text);

    /**
     *
     * Translates a string into {@code application/x-www-form-urlencoded}
     * format using a specific encoding scheme.
     * @param text
     * @return
     */
    String urlEncode(String text) throws UnsupportedEncodingException;

    /**
     * Improve the readability of text
     * @param str
     * @return
     */
    String textToReadable(String str);
}
