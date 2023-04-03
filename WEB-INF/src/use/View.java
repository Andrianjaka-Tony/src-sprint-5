package use;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import etu1765.framework.Mapping;

public class View {
  
  public static String loadView(String url, HashMap<String, Mapping> mappingUrls) throws Exception {

    Set<String> set = mappingUrls.keySet();
    if (!set.contains(url)) {
      throw new Exception("404 not found!");
    }

    String className = mappingUrls.get(url).getClassName();
    String methodName = mappingUrls.get(url).getMethod();
    Class<?> classe = Class.forName(className);
    Method method = classe.getDeclaredMethod(methodName);

    Constructor<?> constructor = classe.getDeclaredConstructor();
    Object object = constructor.newInstance();

    String view = String.valueOf(method.invoke(object));
    
    return view;

  }
  
}
