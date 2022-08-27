public class IntAssertion implements AssertInterface {
    private int target;
    public IntAssertion(int a){
        target = a;
    }

    public IntAssertion isEqualTo(int i2){
        if(target != i2){
            throw new RuntimeException();
        }else{
            return this;
        }
    }

    public IntAssertion isLessThan(int i2){
        if(target >= i2){
            throw new RuntimeException();
        }else{
            return this;
        }
    }

    public IntAssertion isGreaterThan(int i2){
        if(target <= i2){
            throw new RuntimeException();
        }else {
            return this;
        }
    }
}
