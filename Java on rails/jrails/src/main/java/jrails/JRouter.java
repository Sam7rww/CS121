package jrails;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JRouter {

    private List<Route> routes = new ArrayList<>();

    public void addRoute(String verb, String path, Class clazz, String method) {
        // Implement me!
        Route r = new Route(verb,path,clazz,method);
        routes.add(r);
    }

    // Returns "clazz#method" corresponding to verb+URN
    // Null if no such route
    public String getRoute(String verb, String path) {
        for(Route r:routes){
            if(r.matchVerbPath(verb,path)){
                String name="";
                String[] classnames = r.getClazz().getName().split("\\.");
                if(classnames.length>1){
                    name = classnames[classnames.length-1];
                }else{
                    name = classnames[0];
                }
                String result = r.getClazz().getName()+"#"+r.getMethod();
                return result;
            }
        }
        return null;
    }

    // Call the appropriate controller method and
    // return the result
    public Html route(String verb, String path, Map<String, String> params) {
        String route = this.getRoute(verb,path);
        if(route == null) throw new UnsupportedOperationException();

        for(Route r:routes){
            if(r.matchVerbPath(verb,path)){
                try {
                    Class c = r.getClazz();
                    Object o = c.getConstructor().newInstance();
                    Method m = c.getMethod(r.getMethod(),Map.class);
                    Html html = (Html) m.invoke(o,params);
                    System.out.println(html);
                    return html;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
