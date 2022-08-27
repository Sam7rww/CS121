public class StringAssertion implements AssertInterface {
    private String target;
    public StringAssertion(String a){
        target = a;
    }

    public StringAssertion isNull(){
        if(target != null){
            throw new RuntimeException();
        }else{
            return this;
        }
    }
    public StringAssertion isNotNull(){
        if(target == null){
            throw new RuntimeException();
        }else{
            return this;
        }
    }
    public StringAssertion isEqualTo(Object o2){
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
    public StringAssertion isNotEqualTo(Object o2){
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

    public StringAssertion startsWith(String s2){
        int length = s2.length();
        for(int i = 0;i<length;i++){
            char target_c = target.charAt(i);
            char start_c = s2.charAt(i);
            if(target_c != start_c) throw new RuntimeException();
        }
        return this;
    }

    public StringAssertion isEmpty(){
        if(target.length() != 0){
            throw new RuntimeException();
        }else{
            return this;
        }
    }

    public StringAssertion contains(String s2){
        if(!target.contains(s2)){
            throw new RuntimeException();
        }else{
            return this;
        }
    }
}
