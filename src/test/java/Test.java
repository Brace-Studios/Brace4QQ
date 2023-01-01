import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method method = Gson.class.getMethod("fromJson", String.class, Class.class);
        Class clazz = Gson.class;
        Object obj = clazz.getDeclaredConstructor().newInstance();
        System.out.println(method.getName());
        TestClass a = (TestClass) method.invoke(obj, "{\"a\":\"Hello\"}", TestClass.class);
        System.out.println(a.a);
    }

    public static class TestClass {
        String a = "b";
    }
}
