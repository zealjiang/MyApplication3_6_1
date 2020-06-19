package com.example.lizhijiang.myapplication.util;

import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.lizhijiang.myapplication.AppApplication;

public class FontUtil {

    public static int getTextHeight(TextView textView){
        if(textView == null)return -1;
        Paint.FontMetricsInt fm = textView.getPaint().getFontMetricsInt();
        //return ((int) Math.ceil(fm.descent - fm.top) + 2) /2 ;//大于或等于给定数字的最小整数。
        //textView.getPaint().set
        float height = fm.bottom - fm.top;


/*        Ln.e("font bottom:" + getPaint().getFontMetricsInt().bottom +
                "  \ndescent:" + getPaint().getFontMetricsInt().descent +
                " \nascent:" + getPaint().getFontMetricsInt().ascent +
                " \ntop:" + getPaint().getFontMetricsInt().top +
                " \nbaseline:" + getBaseline());*/
        return (int) Math.ceil(height);
    }

    /**
     * 获取纯文字的高度
     * @param textView
     * @return
     */
    public static int getTextHeightNoPadding(TextView textView){
        if(textView == null)return -1;
        Paint.FontMetricsInt fm = textView.getPaint().getFontMetricsInt();
        float height = fm.descent - fm.ascent;

        return (int) Math.ceil(height);
    }

    public static int getTextWidth(TextView textView){
        if(textView == null)return -1;
        int textWidth = (int) android.text.Layout.getDesiredWidth(textView.getText(), textView.getPaint());
        return textWidth;
    }

    public static int getTextLines(CharSequence charSequence){
        if(TextUtils.isEmpty(charSequence))return -1;

        int lineNum = 0;
        if(charSequence.length() > 0){
            lineNum = 1;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if(charSequence.charAt(i) == '\n'){
                lineNum ++;
            }
        }

        return lineNum;
    }

    /**
     * 根据自动换行宽度和内容本身的换行符 计算内容行数
     * @param textView
     * @param content
     * @param lineFeedWidth
     * @return
     */
    public static int getTextLines(TextView textView, String content, int lineFeedWidth){
        if(textView == null || TextUtils.isEmpty(content) || lineFeedWidth < 10){
            return getTextLines(content);
        }
        int lineFeedWidthPx = ScreenUtils.dipConvertPx(AppApplication.getContext(),lineFeedWidth);

        int lineNum = 0;

        String[] arrays = content.split("\n");

        for (int i = 0; i < arrays.length; i++) {
            String temp = arrays[i];

            //计算内容宽度
            int textWidth = (int) android.text.Layout.getDesiredWidth(temp, textView.getPaint());
            if(textWidth > lineFeedWidthPx){
                int num = textWidth / lineFeedWidthPx;
                lineNum += num;
                if(textWidth % lineFeedWidthPx > 0){
                    lineNum ++;
                }
            }else{
                lineNum ++;
            }

        }


        return lineNum;
    }


    /**
     * 设置最多显示的行数  如果tv为空或自动换行宽度小于10个单位，则不考虑自动换行，只考虑手动换行
     * @param tv 显示内容的TextView 用来计算自动换行宽度
     * @param lineFeedWidth 换行宽度  不可小于10单位
     * @param maxLines 最多显示行数
     * @param sourceContent 最多显示行数可显示的内容
     * @return 最多显示行数可显示的内容
     */
    public static String setMaxLines(TextView tv, int lineFeedWidth, int maxLines, String sourceContent) {
        String content = sourceContent.substring(0);
        if (TextUtils.isEmpty(content)) {
            return content;
        }
        if (tv != null) {
            if (maxLines <= 1) {
                maxLines = 1;
                tv.setMaxLines(1);
            } else {
                tv.setMaxLines(maxLines);
            }
        }

        //超过换行宽度插入换行符
        content = handleTextWidthLines(tv,content,lineFeedWidth);

        int lineNum;
        if(tv == null || lineFeedWidth < 10){
            lineNum = FontUtil.getTextLines(content);
        }else{
            lineNum = getTextLines(tv,content,lineFeedWidth);
        }

        if(lineNum <= maxLines){
            return content;
        }else{
            //删除超过的行的内容
            String newContent = filterLine(content,maxLines);

            return newContent;
        }
    }

    /**
     * 根据最大换行宽度插入换行符，如果行数超过了最大行数，后面的内容不再插入换行符，返回插入换行符后的内容
     * 如果自动换行宽度小于10个单位，返回原内容
     *
     * @param tv 显示内容的TextView 用来计算自动换行宽度
     * @param lineFeedWidth 换行宽度  不可小于10单位  单位dp
     * @param maxLines 最多显示行数
     * @param exceedMaxLineIsLineFeed 超过最大行数后面的内容是否换行，true换行，false不换行
     * @param exceedMaxLineRemove 超过最大行数后面的内容是否删除，true删除，false不删除
     * @param sourceContent 最多显示行数可显示的内容
     * @return 最多显示行数可显示的内容
     */
    public static String autoInsertLinefeed(TextView tv, int lineFeedWidth, int maxLines,
                                            boolean exceedMaxLineIsLineFeed,boolean exceedMaxLineRemove, String sourceContent) {
        String content = sourceContent;
        if (TextUtils.isEmpty(content)) {
            return content;
        }
        if (tv != null) {
            if (maxLines <= 1) {
                maxLines = 1;
                tv.setMaxLines(1);
            } else {
                //tv.setMaxLines(maxLines);
            }
        }

        int lineNum=0;//当前行数
        StringBuilder stringBuilder = new StringBuilder();
        String[] arrays = content.split("\n");
        for (int i = 0; i < arrays.length; i++) {
            String temp = arrays[i];

            if(exceedMaxLineIsLineFeed){
                //所有内容都根据最大换行宽度插入换行符
                String feedline = handleTextWidthLines(tv,temp,lineFeedWidth);
                stringBuilder.append(feedline);
                stringBuilder.append("\n");
                lineNum++;
            }else{
                //没超过最大行数的内容插入换行符，超过最大行数的内容不再插入换行符
                if(lineNum> maxLines -1){
                    stringBuilder.append(temp);
                    stringBuilder.append("\n");
                }else{
                    //超过换行宽度插入换行符
                    String[] feedline = handleTextWidthLines(tv,temp,lineFeedWidth,maxLines - lineNum);
                    Log.d("mtest","autoInsertLinefeed feedline:"+feedline[0]);
                    stringBuilder.append(feedline[0]);
                    stringBuilder.append("\n");
                    lineNum++;
                    lineNum += Integer.valueOf(feedline[1])-1;
                }
            }

/*            //超过换行宽度插入换行符
            //String feedline = handleTextWidthLines(tv,temp,lineFeedWidth);
            String feedline = handleTextWidthLines(tv,temp,lineFeedWidth,maxLines);
            stringBuilder.append(feedline);
            stringBuilder.append("\n");
            lineNum++;*/
        }

        //删除掉最后的"\n"换行符
        content = stringBuilder.deleteCharAt(stringBuilder.toString().length()-1).toString();
        Log.d("mtest","autoInsertLinefeed lineNum: "+lineNum);
        Log.d("mtest","autoInsertLinefeed content: "+content);

        //删除超过最大行数后的行内容
        if(exceedMaxLineRemove){
            //删除超过的行的内容
            int lineNumDelete;
            if(tv == null || lineFeedWidth < 10){
                lineNumDelete = getTextLines(content);
            }else{
                lineNumDelete = getTextLines(tv,content,lineFeedWidth);
            }

            if(lineNumDelete <= maxLines){
                return content;
            }else{
                //删除超过的行的内容
                String newContent = filterLine(content,maxLines);

                return newContent;
            }
        }else{
            return content;
        }

    }

    /**
     * 设置最多显示的行数
     * @param maxLines
     */
    public static String setMaxLines(int maxLines, String content){
        if(maxLines <=1 || TextUtils.isEmpty(content)){
            return content;
        }
        int lineNum = FontUtil.getTextLines(content);
        int lines = maxLines;
        if(lines <= 1){
            lines = 1;
        }
        if(lineNum <= lines){
            return content;
        }else{
            //删除超过的行的内容
            String newContent = filterLine(content,lines);
            return newContent;
        }
    }

    public static String filterLine(String content, int maxlines){
        if(TextUtils.isEmpty(content) || maxlines < 1){
            return content;
        }

        StringBuilder sb = new StringBuilder();

        String[] arrays = content.split("\n");
        if(arrays.length < maxlines){
            return content;
        }else{
            for (int i = 0; i < maxlines; i++) {
                sb.append(arrays[i]);
                sb.append("\n");
            }
            //去掉最后的"\n"，换行符
            sb.delete(sb.toString().length()-1,sb.toString().length());
        }


        return sb.toString();
    }


    /**
     *
     * @param textView  tv
     * @param content  内容
     * @param maxlines  最大行数
     * @param lineFeedWidth  最大换行宽度  单位dp
     * @param lastLineWidthRatio  如果超过最大行数  最后一行内容的宽度为最大行宽的比例 0 到 1
     * @return
     */
    public static String getMaxLineContent(TextView textView, String content, int maxlines, int lineFeedWidth, float lastLineWidthRatio){
        if(textView == null || TextUtils.isEmpty(content) || maxlines < 0 || lineFeedWidth < 10){
            return "";
        }
        StringBuilder sb = new StringBuilder();

        String[] arrays = content.split("\n");
        int forSize = maxlines;
        if(arrays.length < maxlines){
            forSize = arrays.length;
        }

        int lineFeedWidthPx = ScreenUtils.dipConvertPx(AppApplication.getContext(),lineFeedWidth);
        int curLineNum = 0;
        int n = 0;
        for (int i = 0; i < forSize; i++) {
            int lines = getTextLines(textView,arrays[i],lineFeedWidth);
            curLineNum =+ lines;
            if(curLineNum <= maxlines){
                sb.append(arrays[i]);
                sb.append("\n");
            }/*else if(curLineNum == maxlines){
                int textWidth = (int) android.text.Layout.getDesiredWidth(arrays[i], textView.getPaint());
                float charWidth = textWidth * 1.0f/arrays[i].length();
                float charNum = lineFeedWidthPx * lastLineWidthRatio/charWidth;
                if(charNum > arrays[i].length()){
                    sb.append(arrays[i].subSequence(0,arrays[i].length()));
                }else{
                    sb.append(arrays[i].subSequence(0,(int)charNum));
                }
                sb.append("\n");
            }*/else{
                break;
            }
            n++;
        }
        if(sb.toString().endsWith("\n")){
            //去掉最后的"\n"，换行符
            sb.delete(sb.toString().length()-1,sb.toString().length());
        }

        //如果最后一行超过一行，单独处理最后一行
        if(curLineNum > maxlines){
            String lastLine = arrays[n];
            float validLineNum = maxlines - n - 1 + lastLineWidthRatio;
            //计算内容宽度
            int textWidth = (int) android.text.Layout.getDesiredWidth(lastLine, textView.getPaint());
            float charWidth = textWidth * 1.0f/lastLine.length();
            float charNum = lineFeedWidthPx * validLineNum/charWidth;
            if(charNum > lastLine.length()){
                sb.append(lastLine.subSequence(0,lastLine.length()));
            }else{
                sb.append(lastLine.subSequence(0,(int)charNum));
            }
        }

        return sb.toString();

    }


    /**
     * 自动换行处理
     * @param textView 显示此内容的view
     * @param content 原内容
     * @param lineFeedWidth  最大单行宽度 单位dp
     * @return 换行后的内容
     */
    public static String autoLineFeedWidth(TextView textView, String content, int lineFeedWidth){
        if(textView == null || TextUtils.isEmpty(content) || lineFeedWidth < 10){
            return content;
        }
        int lineFeedWidthPx = ScreenUtils.dipConvertPx(AppApplication.getContext(),lineFeedWidth);

        StringBuilder sb = new StringBuilder();
        String[] arrays = content.split("\n");

        for (int i = 0; i < arrays.length; i++) {
            String temp = arrays[i];

            //计算内容宽度
            int textWidth = (int) android.text.Layout.getDesiredWidth(temp, textView.getPaint());
            if(textWidth > lineFeedWidthPx){

            }

        }

        return "";
    }


    /**
     * 根据换行宽度加入换行符  (todo 特殊字符可能会被截断成乱码)
     * @param textView
     * @param content
     * @param lineFeedWidth
     * @return
     */
    public static String handleTextWidthLines(TextView textView, String content, int lineFeedWidth){


        if(lineFeedWidth < 10 || textView == null || TextUtils.isEmpty(content)){
            return content;
        }


        int lineFeedWidthPx = ScreenUtils.dipConvertPx(AppApplication.getContext(),lineFeedWidth);
/*        //获取当前播放器的宽度
        int videoWidth = VideoSettingController.getInstance().getResolutionWidth();
        if(lineFeedWidthPx > videoWidth){
            lineFeedWidthPx = videoWidth;
        }*/

        int contentWidth = (int) android.text.Layout.getDesiredWidth(content, textView.getPaint());
        if(contentWidth > lineFeedWidthPx){
            StringBuilder sb = new StringBuilder();
            float singleTextWidth = textView.getTextSize();
            float num = lineFeedWidthPx/singleTextWidth;
            if(num > content.length()){
                return content;
            }
            int start = 0;
            while (true){
                //计算num个字的宽度
                int end = start + (int)num;
                if(content.length() == 0){
                    break;
                }
                if(end > content.length()){
                    end = content.length();
                }
                String tempStr = content.substring(start,end);
                int textWidth = (int) android.text.Layout.getDesiredWidth(tempStr, textView.getPaint());
                if(textWidth > lineFeedWidthPx){
                    num--;
                    continue;
                }

                if(end + 1 > content.length()){

                    if(textWidth <= lineFeedWidthPx){
                        sb.append(tempStr);
                        sb.append("\n");
                        break;
                    }else if(textWidth > lineFeedWidthPx){
                        num--;
                    }
                }else{
                    //计算num+1个字的宽度
                    String tempStr2 = content.substring(start,end + 1);
                    int textWidth2 = (int) android.text.Layout.getDesiredWidth(tempStr2, textView.getPaint());

                    if(textWidth <= lineFeedWidthPx && textWidth2 > lineFeedWidthPx){
                        sb.append(tempStr);
                        sb.append("\n");
                        content = content.substring(end);
                        if(content.length() == 0){
                            break;
                        }
                    }else if(textWidth < lineFeedWidthPx){
                        num++;
                    }else if(textWidth > lineFeedWidthPx){
                        num--;
                    }
                }

            }

            sb.deleteCharAt(sb.toString().length()-1);
            return sb.toString();
        }else{
            return content;
        }
    }

    /**
     * 如果content内容是单行，对超过最大行宽的内容插入换行符，如果插入的换行符个数为(maxLines-1)不再插入换行符
     * 根据换行宽度加入换行符，超过最大的行数后面的内容不再插入换行符
     * (todo 特殊字符可能会被截断成乱码)
     * @param textView
     * @param content
     * @param lineFeedWidth
     * @param maxLines 超过最大的行数后面的内容不再插入换行符,如果maxLines为-1，这个参数不生效，表示整个内容处理加入换行符的操作
     * @return
     */
    public static String[] handleTextWidthLines(TextView textView, String content, int lineFeedWidth,int maxLines){

        String[] contentAndLinefeedNum = new String[2];
        if(lineFeedWidth < 10 || textView == null || TextUtils.isEmpty(content)){
            contentAndLinefeedNum[0] = content;
            contentAndLinefeedNum[1] = "1";
            return contentAndLinefeedNum;
        }


        int lineFeedWidthPx = ScreenUtils.dipConvertPx(AppApplication.getContext(),lineFeedWidth);
/*        //获取当前播放器的宽度
        int videoWidth = VideoSettingController.getInstance().getResolutionWidth();
        if(lineFeedWidthPx > videoWidth){
            lineFeedWidthPx = videoWidth;
        }*/

        int contentWidth = (int) android.text.Layout.getDesiredWidth(content, textView.getPaint());
        if(contentWidth > lineFeedWidthPx){
            int curLineNum = 0;
            StringBuilder sb = new StringBuilder();
            float singleTextWidth = textView.getTextSize();
            float num = lineFeedWidthPx/singleTextWidth;
            if(num > content.length()){
                contentAndLinefeedNum[0] = content;
                contentAndLinefeedNum[1] = (curLineNum+1)+"";
                return contentAndLinefeedNum;
            }
            int start = 0;
            while (true){
                //插入2个换行符能将内容折成3行
                if(curLineNum >= maxLines-1 && maxLines > -1){
                    sb.append(content);
                    sb.append("\n");
                    break;
                }

                //计算num个字的宽度
                int end = start + (int)num;
                if(content.length() == 0){
                    break;
                }
                if(end > content.length()){
                    end = content.length();
                }
                String tempStr = content.substring(start,end);
                int textWidth = (int) android.text.Layout.getDesiredWidth(tempStr, textView.getPaint());
                if(textWidth > lineFeedWidthPx){
                    num--;
                    continue;
                }

                if(end + 1 > content.length()){

                    if(textWidth <= lineFeedWidthPx){
                        sb.append(tempStr);
                        sb.append("\n");
                        break;
                    }else if(textWidth > lineFeedWidthPx){
                        num--;
                    }
                }else{
                    //计算num+1个字的宽度
                    String tempStr2 = content.substring(start,end + 1);
                    int textWidth2 = (int) android.text.Layout.getDesiredWidth(tempStr2, textView.getPaint());

                    if(textWidth <= lineFeedWidthPx && textWidth2 > lineFeedWidthPx){
                        sb.append(tempStr);
                        sb.append("\n");
                        content = content.substring(end);
                        if(content.length() == 0){
                            break;
                        }
                        curLineNum++;//记录当前插入的换行符个数
                    }else if(textWidth < lineFeedWidthPx){
                        num++;
                    }else if(textWidth > lineFeedWidthPx){
                        num--;
                    }
                }

            }

            sb.deleteCharAt(sb.toString().length()-1);
            contentAndLinefeedNum[0] = sb.toString();
            contentAndLinefeedNum[1] = (curLineNum+1)+"";
            return contentAndLinefeedNum;
        }else{
            contentAndLinefeedNum[0] = content;
            contentAndLinefeedNum[1] = "1";
            return contentAndLinefeedNum;
        }
    }



    /**
     * 获取文字宽度
     * @param textView
     * @param lineFeedWidth
     * @param minEms
     * @param content
     * @return
     */
    public static int getTextWidth(TextView textView, int lineFeedWidth, int minEms, String content) {
        if(textView == null)return -1;
        //如果设置了换行宽度，则文字的宽度最大为换行宽度
        String newContent = handleTextWidthLines(textView,content,lineFeedWidth);

        //考虑多行(有换行符的情况)，计算多行中宽度最宽的行为标准
        String str = newContent;
        String[] arrays = str.split("\n");
        int max = 0;
        for (int i = 0; i < arrays.length; i++) {
            int width = (int) android.text.Layout.getDesiredWidth(arrays[i], textView.getPaint());
            if(width > max){
                max = width;
            }
        }

        int curTextWidth = max;

        if (minEms <= 0) {//以中文字符为标准
            return curTextWidth;
        }

        //准备工作
        String standard = "汉";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < minEms; i++) {
            sb.append(standard);
        }

        int standardTextWidth = (int) android.text.Layout.getDesiredWidth(sb.toString(), textView.getPaint());

        //当前内容的长度与最小个数标准文字的长度比较
        if (curTextWidth < standardTextWidth) {
            //XLog.debug(TAG, " return textWidth: " + textWidth);
            return standardTextWidth;
        } else {
            //XLog.debug(TAG, " return curTextWidth: " + curTextWidth);
            return curTextWidth;
        }

    }

    /**
     * 计算TextView的行高，如果textView为空，返回-1
     * @param textView
     * @param lineFeedWidth
     * @param maxLines
     * @param paddingTop
     * @param paddingBottom
     * @return
     */
    public static int calculateHeight(TextView textView, int lineFeedWidth, int maxLines, int paddingTop, int paddingBottom){
        if(textView == null){
            return -1;
        }
        if(maxLines > 0){
            if(textView != null){
                textView.setMaxLines(maxLines);
            }
        }

        //FontUtil.containsEmoji(textView.getText().toString());

        //计算当前view的高度
        int singleHeight = getTextHeight(textView);
        int singleHeightNoPadding = getTextHeightNoPadding(textView);
        int paddingHeight = 0;


        //获取当前行数
        int lineHeight = textView.getLineHeight();
        int textSize = (int)textView.getTextSize();
        if(textSize > lineHeight){
            lineHeight = textSize;
        }
        int lineNum ;
        lineNum = FontUtil.getTextLines(textView,textView.getText().toString(),lineFeedWidth);
        if(lineNum < 1){
            lineNum = 1;
        }
        //不能超过最多行数
        int maxNum = maxLines;
        if(maxNum < 1){
            maxNum = 1;
            textView.setLines(1);
        }
        if(lineNum > maxNum){
            lineNum = maxNum;
            paddingHeight = Math.abs(singleHeight - singleHeightNoPadding);
        }

        int height = lineNum * lineHeight + paddingTop + paddingBottom + paddingHeight;

        return height;
    }


    public static boolean containsEmoji(String str) {//真为不含有表情
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * 获取字符长度  一个汉字占两个字符，一个英文和一个数字占一个字符
     * @param str
     * @return
     */
    public static int getCharacterLength(String str){
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char item = str.charAt(i);
            if (item < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
        }
        return count;
    }

    /**
     * 根据设置的最大长度返回有效的字符
     * @param str
     * @param maxLen 以汉字占两个，字母、数字占一个来计算；例如：限制5个汉字，maxLen 应该是10
     * @return
     */
    public static String getStringByMaxCharacterLength(String str, int maxLen){
        if (TextUtils.isEmpty(str) || maxLen <= 0) {
            return str;
        }

        int count = 0;
        int endIndex=0;
        boolean isExceedLimit = false;
        for (int i = 0; i < str.length(); i++) {
            char item = str.charAt(i);
            if (item < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
            if(maxLen==count || (item>=128 && maxLen+1==count)){
                endIndex=i;
                isExceedLimit = true;
                break;
            }
        }

        if(isExceedLimit){
            return str.substring(0, endIndex+1);//包含最后一个字符
        }

        if (count <= maxLen) {
            return str;
        }

        return str;
    }


    public static String getStringByMaxCharacterLength2(String str, int maxLen){
        if (TextUtils.isEmpty(str) || maxLen <= 0) {
            return str;
        }

        int count = 0;
        int endIndex=0;
        for (int i = 0; i < str.length(); i++) {
            char item = str.charAt(i);
            if (item < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
            if(maxLen==count || (item>=128 && maxLen+1==count)){
                endIndex=i;
                break;
            }
        }

        if(endIndex >= 0){
            return str.substring(0, endIndex+1);//包含最后一个字符
        }

        if (count <= maxLen) {
            return str;
        }

        return str;
    }
}
