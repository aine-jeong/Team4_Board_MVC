package kr.or.bit.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class StringUtils {
   public static String titleSubString(String title) {
	   return title.substring(0, 10);
   }
   
   public <T extends Object> String listParseToJavascriptArray(List<T> list, T object) {
       
       // 객체 변수명 문자열로 담기
       List<String> fieldNames = new ArrayList<String>();
       
       for(Field field : object.getClass().getDeclaredFields()) {
           String str = field.toString();
           fieldNames.add(str.substring(str.lastIndexOf('.') + 1));
       }
       
       // 파싱?
       StringBuilder parsedString = new StringBuilder();
       parsedString.append("[");
       
       try {
           
           for(int i = 0; i < list.size(); i++) {
               parsedString.append("{");
               for(int j = 0; j < fieldNames.size(); j++) {
                   Field obj = list.get(i).getClass().getDeclaredField(fieldNames.get(j));
                   Object key = obj.getName();
                   // Object value = obj.get(list.get(i));
                   obj.trySetAccessible();
                   Object value = obj.get(list.get(i));
                   
                   parsedString.append("\"");
                   parsedString.append(key);
                   parsedString.append("\"");
                   parsedString.append(":");
                   parsedString.append("\"");
                   parsedString.append(value);
                   parsedString.append("\"");
                   
                   
                   if(j == fieldNames.size() - 1) {
                       // nothing
                       
                   } else {
                       parsedString.append(",");
                   }
                   
               } // j for end
               
               if(i == list.size() - 1) {
                   parsedString.append("}");
               } else {
                   parsedString.append("},");
               }
               
           }// i for end
       
       } catch(Exception e) {
           e.printStackTrace();
       }
       
       parsedString.append("]");
       
       return parsedString.toString();
   }
   
   
}
