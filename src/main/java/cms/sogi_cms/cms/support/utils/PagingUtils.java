package cms.sogi_cms.cms.support.utils;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PagingUtils {

    public static <T> String getQueryString(StringBuilder sb, T t) throws IllegalAccessException {
        List<Field> fields = getAllFields(t);

        // 쿼리 스트링용 StringBuilder에 값이 비지 않은 필드 값 주입
        for (Field field : fields) {
            field.setAccessible(true); // 외부에서 private 필드에 접근할 수 있도록 해주는 설정. oop의 추상화를 깨는 것이기 때문에 필요한 곳에서만 사용이 필요하다.
            if (field.get(t) != null) {
                if (StringUtils.hasText(String.valueOf(field.get(t)))) {
                    sb.append("&").append(field.getName()).append("=")
                            .append(field.get(t));
                }
            }
        }

        return sb.toString();
    }

    public static <T> List<Field> getAllFields(T t) {
        Class<?> clazz = t.getClass(); // 리플렉션으로 클래스 정보 추출

        List<Field> fields = new ArrayList<>();
        while (clazz != null) { // 상위 클래스가 null 이 아닐때까지 모든 필드를 list 에 담는다.
            fields.addAll(Arrays.asList(clazz.getDeclaredFields())); // 클래스의 필드 정보 추출
            clazz = clazz.getSuperclass();
        }

        return fields;
    }
}
