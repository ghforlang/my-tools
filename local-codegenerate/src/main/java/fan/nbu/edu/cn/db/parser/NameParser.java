package fan.nbu.edu.cn.db.parser;


import fan.nbu.edu.cn.exception.IllegalNameException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/22-3:50 下午
 * @since 1.0
 */
public abstract class NameParser implements Parser<String>{

    private static final String WORD_SEPARATOR = "_";

    @Override
    public String parse(String s) {
        return null;
    }


    public static String parseUnderlineName(String s) {
        if(StringUtils.isBlank(s) || s.startsWith(WORD_SEPARATOR) || s.endsWith(WORD_SEPARATOR)){
            throw new IllegalNameException("illegal name [" + s +  "]!");
        }
        StringBuffer sb = new StringBuffer();

        if(s.contains(WORD_SEPARATOR)){
            String[] words = StringUtils.split(s,WORD_SEPARATOR);
            sb.append(words[0]);
            for(int i=1;i<words.length;i++){
                if(!Character.isLowerCase(words[i].charAt(0)) && !Character.isDigit(words[i].charAt(0))){
                    throw new IllegalNameException("illegal character " + words[i].charAt(0) + "in name [" + s +  "]!");
                }else if (Character.isDigit(words[i].charAt(0))){
                    sb.append(words[i]);
                    continue;
                }
                sb.append(words[i].substring(0,1).toUpperCase() + words[i].substring(1));
            }
        }else{
            sb.append(s);
        }
        return sb.toString();
    }
}
