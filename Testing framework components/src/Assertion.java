public class Assertion {
    /* You'll need to change the return type of the assertThat methods */
    static ObjectAssertion assertThat(Object o) {
        ObjectAssertion oa = new ObjectAssertion(o);
        return oa;
    }
    static StringAssertion assertThat(String s) {
	    StringAssertion sa = new StringAssertion(s);
	    return sa;
    }
    static BoolAssertion assertThat(boolean b) {
	    BoolAssertion ba = new BoolAssertion(b);
	    return ba;
    }
    static IntAssertion assertThat(int i) {
	    IntAssertion ia = new IntAssertion(i);
	    return ia;
    }
}