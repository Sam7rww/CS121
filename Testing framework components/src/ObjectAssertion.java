public class ObjectAssertion implements AssertInterface {
    private Object target;
    public ObjectAssertion(Object o){
        target = o;
    }
    public ObjectAssertion isNull(){
        if(target != null){
            throw new RuntimeException();
        }else{
            return this;
        }
    }
    public ObjectAssertion isNotNull(){
        if(target == null){
            throw new RuntimeException();
        }else{
            return this;
        }
    }
    public ObjectAssertion isEqualTo(Object o2){
        if(target == null){
            if(o2 == null){
                return this;
            }else{
                throw new RuntimeException();
            }
        }
        if(!target.equals(o2)){
            throw new RuntimeException();
        }else{
            return this;
        }
    }
    public ObjectAssertion isNotEqualTo(Object o2){
        if(target == null){
            if(o2 != null){
                return this;
            }else{
                throw new RuntimeException();
            }
        }
        if(target.equals(o2)){
            throw new RuntimeException();
        }else{
            return this;
        }
    }
    public ObjectAssertion isInstanceOf(Class c){
        if(!c.isInstance(target)){
            throw new RuntimeException();
        }else {
            return this;
        }
    }
}
