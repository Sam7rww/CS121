import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class Unit {
    public static HashMap<String, Throwable> testClass(String name) throws Exception {
        HashMap<String,Throwable> result = new HashMap<>();
        Class temp = Class.forName(name);
        Object testObject = temp.getConstructor().newInstance();
        List<String> beforeClassAnno = new ArrayList<>();
        List<String> afterClassAnno = new ArrayList<>();
        List<String> beforeAnno = new ArrayList<>();
        List<String> afterAnno = new ArrayList<>();
        List<String> testAnno = new ArrayList<>();

        //store exception threw by before and after
        List<Exception> exceptions = new ArrayList<>();

        //scan all the method in designated class, allocate each annotation to List
        for (Method m : Class.forName(name).getMethods()) {
            //each method only has one annotation
            if(m.getAnnotations().length>1) throw new RuntimeException();

            if (m.isAnnotationPresent(Test.class)) {
                testAnno.add(m.getName());
            }else if(m.isAnnotationPresent(BeforeClass.class)){
                //before-class annotation should appear on static method
                if(!Modifier.isStatic(m.getModifiers())){
                    throw new RuntimeException();
                }else{
                    beforeClassAnno.add(m.getName());
                }
            }else if(m.isAnnotationPresent(AfterClass.class)){
                //after-class annotation should appear on static method
                if(!Modifier.isStatic(m.getModifiers())){
                    throw new RuntimeException();
                }else{
                    afterClassAnno.add(m.getName());
                }
            }else if(m.isAnnotationPresent(Before.class)){
                beforeAnno.add(m.getName());
            }else if(m.isAnnotationPresent(After.class)){
                afterAnno.add(m.getName());
            }
        }

        //sort method name in alphabetic order in each Arraylist
        beforeClassAnno.sort((o1, o2) -> o1.compareTo(o2));
        afterClassAnno.sort((o1, o2) -> o1.compareTo(o2));
        beforeAnno.sort((o1, o2) -> o1.compareTo(o2));
        afterAnno.sort((o1, o2) -> o1.compareTo(o2));
        testAnno.sort(((o1, o2) -> o1.compareTo(o2)));

        //start to run test(including before and after)
        //@before class
        if(!beforeClassAnno.isEmpty()){
            for(String bcn : beforeClassAnno){
                try {
                    temp.getMethod(bcn).invoke(null);
                }catch (Exception e){
                    exceptions.add(e);
                }
            }
        }

        for(String testname:testAnno){
            //@before
            if(!beforeAnno.isEmpty()){
                for(String bn:beforeAnno){
                    try{
                        temp.getMethod(bn).invoke(testObject);
                    }catch (Exception e){
                        exceptions.add(e);
                    }
                }
            }

            //@Test
            try {
                temp.getMethod(testname).invoke(testObject);
            }catch (Throwable t){
                if(t instanceof InvocationTargetException){
                    t = t.getCause();
                }
                result.put(testname,t);
            }finally {
                //check whether throwable is exist, if not, add null to result
                if(!result.containsKey(testname)) result.put(testname,null);
            }

            //@after
            if(!afterAnno.isEmpty()){
                for(String an:afterAnno){
                    try {
                        temp.getMethod(an).invoke(testObject);
                    }catch (Exception e){
                        exceptions.add(e);
                    }
                }
            }
        }

        //@after class
        if(!afterClassAnno.isEmpty()){
            for(String acn:afterClassAnno){
                try {
                    temp.getMethod(acn).invoke(null);
                }catch (Exception e){
                    exceptions.add(e);
                }
            }
        }

        //wrap all the Exceptions threw by before and after
        if(!exceptions.isEmpty()){
            for(Exception e:exceptions){
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    public static HashMap<String, Object[]> quickCheckClass(String name) throws Exception {
	    HashMap<String,Object[]> result = new HashMap<>();
	    QuickCheckHandle handle = new QuickCheckHandle();
        Class temp = Class.forName(name);
        Object testObject = temp.getConstructor().newInstance();

	    for(Method m : Class.forName(name).getMethods()){
            if(m.isAnnotationPresent(Property.class)){
                //check whether it is a property
                Annotation[][] paramter_Anno = m.getParameterAnnotations();
                //store all argument List
                List<List<?>> all_arg_lists = new ArrayList<>();
                int argument_num = paramter_Anno.length;

                for(int i=0;i<argument_num;i++){
                    if(paramter_Anno[i][0].annotationType().equals(IntRange.class)){
                        //parameter annotation is IntRange
                        List<?> res = handle.handleInt(paramter_Anno[i][0]);
                        all_arg_lists.add(res);
                    }else if(paramter_Anno[i][0].annotationType().equals(StringSet.class)){
                        //parameter annotation is StringSet
//                        Object res = handle.handleString(paramter_Anno[0][0],testObject,m);
//                        if(res != null){
//                            Object[] oblist = {res};
//                            result.put(m.getName(),oblist);
//                        }else{
//                            result.put(m.getName(),null);
//                        }
                        List<?> res = handle.handleString(paramter_Anno[i][0]);
                        all_arg_lists.add(res);
                    }else if(paramter_Anno[i][0].annotationType().equals(ListLength.class)){
                        //parameter annotation is ListLength
                        Parameter parameter = m.getParameters()[0];
                        AnnotatedType para_type = parameter.getAnnotatedType(); //superclass annotatedType,e.g @ListLength java.util.List<java.lang.Integer>
                        Annotation pa;
                        AnnotatedType nestedAnno;
                        if(para_type instanceof AnnotatedParameterizedType){
                            //AnnotatedParameterizedType represent the nested annotation type, eg. @ListLength java.util.List<@IntRange java.lang.Integer>
                            AnnotatedParameterizedType at = (AnnotatedParameterizedType) para_type;
                            nestedAnno = at.getAnnotatedActualTypeArguments()[0];   //nestedAnno represent the nested annotation,eg.@IntRange java.lang.Integer
                            pa = nestedAnno.getAnnotations()[0];    // the annotation of temporary AnnotatedType, eg.@IntRange
//                        System.out.println("type2:"+at.getAnnotatedActualTypeArguments()[0].getType());
                        }else{
                            throw new RuntimeException();
                        }
//                        Object res = handle.handleList(paramter_Anno[0][0],pa,testObject,m, nestedAnno);
//                        if(res != null){
//                            Object[] oblist = {res};
//                            result.put(m.getName(),oblist);
//                        }else{
//                            result.put(m.getName(),null);
//                        }
                        List<?> res = handle.handleList(paramter_Anno[i][0],pa,nestedAnno);
                        all_arg_lists.add(res);
                    }else if(paramter_Anno[i][0].annotationType().equals(ForAll.class)){
//                        Object res = handle.handleForall(paramter_Anno[0][0],temp,testObject,m);
//                        if(res != null){
//                            Object[] oblist = {res};
//                            result.put(m.getName(),oblist);
//                        }else{
//                            result.put(m.getName(),null);
//                        }
                        List<?> res = handle.handleForall(paramter_Anno[i][0],temp,testObject);
                        all_arg_lists.add(res);
                    }else{
                        throw new RuntimeException();
                    }
                }

                //invoke method using designated argument
                int count=0;
                List<Object[]> all_arg = handle.all_arg(all_arg_lists);
                for(Object[] o:all_arg){
                    if(count>=100) {
                        result.put(m.getName(),null);
                        break;
                    }
                    boolean func_res = true;
                    try {
                        func_res = (boolean) m.invoke(testObject,o);
                    }catch (Throwable t){
                        if(t instanceof InvocationTargetException){
                            t = t.getCause();
                        }
                        if(t != null) {
                            result.put(m.getName(),o);
                            break;
                        }
                    }
                    if(!func_res){
                        result.put(m.getName(),o);
                        break;
                    }
                    count++;
                }
                if(!result.containsKey(m.getName())) result.put(m.getName(),null);

            }
        }



	    return result;
    }




    public static void main(String[] args) throws Exception {
        String name = "qqqqqq";
        Unit.testClass(name);
        String s = "cs";
        Assertion.assertThat(s).isNotNull().startsWith("cs");
        Unit.quickCheckClass(name);
//        Unit.quickCheckClass(name);
//        for(Method m : Class.forName(name).getMethods()){
//            if(m.isAnnotationPresent(Property.class)){
//                //check whether it is a property
//                Annotation[][] paramter_Anno = m.getParameterAnnotations();
//                if(paramter_Anno[0][0].annotationType().equals(IntRange.class)){
//                    System.out.println("correct");
//                }else{
//                    System.out.println("wrong");
//                }
//                IntRange intRange = (IntRange)paramter_Anno[0][0];
//                System.out.println(intRange.min());
//            }
//        }
        //Unit.testClass(name);
    }
}