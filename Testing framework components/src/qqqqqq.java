import java.util.List;

public class qqqqqq {
//    @Property
//    public boolean testss(@IntRange(min=-10, max=10) Integer i){
//        System.out.println("aaaa!");
//        return true;
//    }

    @BeforeClass
    public static boolean beforecc(){
        System.out.println("this is before class;");
        return true;
    }

    @Before
    public boolean before1(){
        System.out.println("this is before");
        return true;
    }

    @Test
    public boolean bbb(){
        System.out.println("test bbb");
        return true;
    }

    @Test
    public boolean aaa(){
        System.out.println("test aaa");
        return true;
    }

    @Test
    public boolean ccc(){
        System.out.println("test ccc");
        throw new RuntimeException();
    }



    @Property
    public boolean testLength(@ListLength(min=1, max=2) List<@ListLength(min=1, max=3)List< @IntRange(min=5, max=7) Integer> > l){
        System.out.println(l.toString());
        return true;
    }

    @Property
    public boolean test2arg(@IntRange(min=2,max=6)int i,@StringSet(strings={"s1", "s2","s3"}) String s){
        System.out.println("i is:"+i+" ; string is:"+s);
        return true;
    }
}
