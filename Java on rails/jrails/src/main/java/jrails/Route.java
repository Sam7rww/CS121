package jrails;

public class Route {

    private String verb;

    private String path;

    private Class clazz;

    private String method;
    public Route(String v,String p, Class c, String m){
        verb = v;
        path = p;
        clazz = c;
        method = m;
    }

    public boolean matchVerbPath(String v,String p){
        if(verb.equals(v) && path.equals(p)){
            return true;
        }else{
            return false;
        }
    }

    public String getVerb() {
        return verb;
    }

    public String getPath() {
        return path;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getMethod() {
        return method;
    }
}
