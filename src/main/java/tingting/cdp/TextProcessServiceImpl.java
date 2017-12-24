package tingting.cdp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cdp on 2017/12/23.
 */
@Component
@Slf4j
@ConfigurationProperties("tingting.textprocess")
public class TextProcessServiceImpl implements TextProcessService {

    private final TextProcessProperties textProcessProperties;

    public TextProcessServiceImpl(TextProcessProperties textProcessProperties){
        this.textProcessProperties = textProcessProperties;
    }

    public static final Set<Character> punctuations =
        new HashSet<>(Arrays.asList(
                ' ','\n','\t','\r','\f',
                ',','.','?','!',';','(',')','"','\'',':', //English punctuation
                '，','。','？','！','；','（','）','“','”','‘','’','：' //Chinese punctuation
        ));

    /**
     *  Split the String, the return String encode bytes size is less then maxSplitLength;
     * @param str
     * @return
     */
    private List<String> splitString(String str)
        throws UnsupportedEncodingException{
        if(textProcessProperties.getMaxSplitByteLength() < 2) {
            log.error(String.format("maxSplitLength=%d, should >= 2.",textProcessProperties.getMaxSplitByteLength()));
            return null;
        }
        if(Objects.isNull(str)){
            log.error("string is null.");
            return null;
        }

        if(str.getBytes(textProcessProperties.getEncoder()).length <
                textProcessProperties.getMaxSplitByteLength()){
            return new LinkedList<>(Arrays.asList(str));
        }
        else{
            List<String> retList = new LinkedList<>();
            retList.addAll(splitString(str.substring(0,str.length()/2)));
            retList.addAll(splitString(str.substring(str.length()/2,str.length())));
            return retList;
        }
    }

    private List<String> splitStringWithPunctuation(String str, Set<Character> puncSet){

        if(Objects.isNull(str) || Objects.isNull(puncSet)){
            return null;
        }

        String puncSplitWord = "@@@";

        StringBuilder strBuilder = new StringBuilder();
        for(char ch : str.toCharArray()){
            if(puncSet.contains(ch)){
                strBuilder.append(ch);
                strBuilder.append(puncSplitWord);
            }
            else{
                strBuilder.append(ch);
            }
        }
        String newStr = strBuilder.toString();
        return Arrays.asList(newStr.split(puncSplitWord));

    }

    @Override
    public String urlEncode(String text) throws UnsupportedEncodingException{
        if(Objects.isNull(text)){
            log.error("text is null");
            return null;
        }
        char[] replaceChar = {'\n','\t','\r','\f'};
        for(char c : replaceChar){
            text = text.replace(c,' ');
        }

        return URLEncoder.encode(text.trim(),textProcessProperties.getEncoder());

    }

    @Override
    public String textToReadable(String str){

        if(Objects.isNull(str)){
            log.error("str is null");
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder(str);

        //Delete duplicate '\n'
        int i = 1;
        while(i < stringBuilder.length()){
            if(stringBuilder.charAt(i) == '\n' && stringBuilder.charAt(i-1) == '\n'){
                stringBuilder.deleteCharAt(i);
            }
            else{
                i++;
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public List<String> removeUnVoiceText(List<String> text){
        if(text == null){
            log.error("text is null");
            return null;
        }
        //Remove text only contain punctuations.
        return text.stream().filter(t -> {
            String temp = t;
            for(char c : punctuations){
                temp = temp.replace(c,' ');
            }
            temp = temp.trim();
            if(temp.length() == 0){
                return false;
            }
            else{
                return true;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> textSplit(String str)
            throws UnsupportedEncodingException{

        log.debug("text process properties:");
        log.debug(textProcessProperties.toString());

        if(Objects.isNull(str)){
            return new ArrayList<>();
        }
        if(textProcessProperties.getMaxSplitByteLength() <= 2){
            String log_error = String.format("maxSplitLength=%d, should >= 2."
                    ,textProcessProperties.getMaxSplitByteLength());
            log.error(log_error);
            throw new IllegalArgumentException(log_error);
        }

        if(str.getBytes(textProcessProperties.getEncoder()).length
                < textProcessProperties.getMaxSplitByteLength()){
            return Arrays.asList(str);
        }

        //Split the text by punctuation.
        List<String> puncSplitList = splitStringWithPunctuation(str,punctuations);

        //Make sure encode byte size less then maxSplitLength
        List<String> strAfterLengthSplit = new LinkedList<>();
        for(String s : puncSplitList){
            strAfterLengthSplit.addAll(splitString(s));
        }

        //Combine strings to reduce return list size.
        List<String> ret = new LinkedList<>();
        StringBuilder strBuilder = new StringBuilder();
        for(String s : strAfterLengthSplit){
            String temp = strBuilder.toString();
            strBuilder.append(s);

            if(strBuilder.toString().getBytes(textProcessProperties.getEncoder()).length
                    >= textProcessProperties.getMaxSplitByteLength()){
                ret.add(temp);
                strBuilder.setLength(0);
                strBuilder.append(s);
            }
        }

        if(strBuilder.toString().length() > 0){
            ret.add(strBuilder.toString());
        }

        ret = ret.stream().map(s -> s.trim()).collect(Collectors.toList());

        return ret;
    }
}
