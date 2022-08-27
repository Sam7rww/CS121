public class BoolAssertion implements AssertInterface {
    private boolean target;
    public BoolAssertion(boolean b){
        target = b;
    }

    public BoolAssertion isEqualTo(boolean b2){
        if(target != b2){
            throw new RuntimeException();
        }else {
            return this;
        }
    }

    public BoolAssertion isTrue(){
        if(!target){
            throw new RuntimeException();
        }else {
            return this;
        }
    }

    public BoolAssertion isFalse(){
        if(target){
            throw new RuntimeException();
        }else{
            return this;
        }
    }
}
