import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QuickCheckHandle<T> {

//    private List<Integer> templist;
//    private List<String> stringlist;
//    private List<List<T>> listlist;

    public List<Integer> handleInt(Annotation a){
        IntRange intRange = (IntRange) a;
        int min = intRange.min();
        int max = intRange.max();
        List<Integer> result = new ArrayList<>();
        for(int i=min;i<=max;i++){
            result.add(i);
        }

        return result;
    }

    public List<String> handleString(Annotation a){
        StringSet stringSet = (StringSet) a;
        String[] list = stringSet.strings();
        List<String> result = new ArrayList<>();
        for(String s : list){
            result.add(s);
        }

        return result;
    }

    public List<Object> handleForall(Annotation a,Class c, Object testObject) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ForAll forAll = (ForAll) a;
        String name = forAll.name();
        int times = forAll.times();

        //called to generate times values for the argument
        List<Object> result = new ArrayList<>();
        for(int i=0;i<times;i++){
            Object o = c.getMethod(name).invoke(testObject);
            result.add(o);
        }

        return result;
    }

    public List<?> handleList(Annotation la, Annotation pa,  AnnotatedType type){
        ListLength listLength = (ListLength) la;
        int min = listLength.min();
        int max = listLength.max();
//        templist = new ArrayList<>();
//        stringlist = new ArrayList<>();
//        listlist = new ArrayList<>();
        //run time count; maximum 100
        int count = 0;
        if(pa.annotationType().equals(IntRange.class)){
            List<List<Integer>> allList = this.constructIntRange(min,max,pa);
//            for(List<Integer> l:allList){
//                if(count>=100) return null;
//                boolean temp = true;
//                try{
//                    temp = (boolean) m.invoke(testObject,l);
//                }catch (InvocationTargetException | IllegalAccessException ie){
//                    Throwable e = ie.getCause();
//                    if(e != null) return l;
//                }
//                if(!temp) return l;
//                count++;
//            }
            return allList;
        }else if(pa.annotationType().equals(StringSet.class)){
            List<List<String>> allList = this.constructStringSet(min,max,pa);
//            for(List<String> s:allList){
//                if(count>=100) return null;
//                boolean temp = true;
//                try {
//                    temp = (boolean) m.invoke(testObject,s);
//                }catch (InvocationTargetException | IllegalAccessException ie){
//                    Throwable e = ie.getCause();
//                    if(e != null) return s;
//                }
//                if(!temp) return s;
//                count++;
//            }
            return allList;
        }else if(pa.annotationType().equals(ListLength.class)){
            List<List<?>> allList = this.constructList(min,max,type);
//            for(List<?> s:allList){
//                if(count>=100) return null;
//                boolean temp = true;
//                try {
//                    temp = (boolean) m.invoke(testObject,s);
//                }catch (InvocationTargetException | IllegalAccessException ie){
//                    Throwable e = ie.getCause();
//                    if(e != null) return s;
//                }
//                if(!temp) return s;
//                count++;
//            }
            return allList;
        }

        return null;
    }

    //@list & @IntRange
    private List<List<Integer>> constructIntRange(int min, int max, Annotation a){
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        IntRange intRange = (IntRange) a;
        int intmin = intRange.min();
        int intmax = intRange.max();
        int[] numbers = new int[intmax-intmin+1];
        for(int i =0;i<(intmax-intmin+1);i++){
            numbers[i] = intmin+i;
        }

        for(int i= min;i<=max;i++){
            if(i == 0){
                result.add(new ArrayList<Integer>());
                continue;
            }
            List<Integer> il = new ArrayList<>();
            this.f(result,numbers,i,0,il);
        }

        return result;
    }

    private void f(List<List<Integer>> lists, int[] numbers, int tar, int cur,List<Integer> templist){
        //tar: target length of Array, cur:current length of Array; recursively construct target length Array
        if(tar == cur){
//            System.out.println(templist);
            List<Integer> l = new ArrayList<>(templist);
            lists.add(l);
            return;
        }

        for(int i=0;i<numbers.length;i++){
            templist.add(numbers[i]);
            f(lists,numbers,tar,cur+1,templist);
            templist.remove(templist.size()-1);
        }

    }

    //@list & @StringSet
    private List<List<String>> constructStringSet(int min,int max, Annotation a){
        List<List<String>> result = new ArrayList<>();
        StringSet stringSet = (StringSet) a;
        String[] strings = stringSet.strings();

        for(int i = min;i<=max;i++){
            if(i == 0){
                result.add(new ArrayList<String>());
                continue;
            }
            List<String> sl = new ArrayList<>();
            this.fs(result,strings,i,0,sl);
        }

        return result;
    }

    private void fs(List<List<String>> lists, String[] s, int tar,int cur,List<String> stringlist){
        //tar: target length of Array, cur:current length of Array; recursively construct target length Array
        if(tar == cur){
//            System.out.println(stringlist);
            List<String> l = new ArrayList<>(stringlist);
            lists.add(l);
            return;
        }

        for(int i=0;i<s.length;i++){
            stringlist.add(s[i]);
            fs(lists,s,tar,cur+1,stringlist);
            stringlist.remove(stringlist.size()-1);
        }

    }

    //@list & @list
    private List<List<?>> constructList(int min,int max, AnnotatedType at){
        List<List<?>> result = new ArrayList<>();
        ListLength ll = (ListLength)at.getAnnotations()[0];
        int min_next = ll.min();
        int max_next = ll.max();

        //nested annotation
        AnnotatedParameterizedType temp = (AnnotatedParameterizedType) at;
        AnnotatedType new_at = temp.getAnnotatedActualTypeArguments()[0];
        Annotation typeArg = new_at.getDeclaredAnnotations()[0];
        List<List<?>> list_res = new ArrayList<>();
        if(typeArg.annotationType().equals(IntRange.class)){
            List<List<Integer>> list_int = this.constructIntRange(min_next,max_next,typeArg);
            list_res.addAll(list_int);
        }else if(typeArg.annotationType().equals(StringSet.class)){
            List<List<String>> list_str = this.constructStringSet(min_next,max_next,typeArg);
            list_res.addAll(list_str);
        }else if(typeArg.annotationType().equals(ListLength.class)){
            List<List<?>> list_list = this.constructList(min_next,max_next,new_at);
            list_res.addAll(list_list);
        }

        for(int i = min;i<=max;i++){
            List<List<?>> listlist = new ArrayList<>();
            this.fl(result,list_res,i,0,listlist);
        }

        return result;
    }

    private void fl(List<List<?>> lists,List<List<?>> l,int tar, int cur,List<List<?>> listlist){
        //tar: target length of Array, cur:current length of Array; recursively construct target length Array
        if(tar == cur){
            //System.out.println(listlist);
            List<?> temp = new ArrayList<>(listlist);
            lists.add(temp);
            return;
        }

        for(int i=0;i<l.size();i++){
            listlist.add(l.get(i));
            fl(lists,l,tar,cur+1,listlist);
            listlist.remove(listlist.size()-1);
        }
    }

    //for multi argument in QuickCheck, find all the combination of arguments.
    public List<Object[]> all_arg(List<List<?>> all_arg_lists){
        List<Object[]> result = new ArrayList<>();
        Object[] templist = new Object[all_arg_lists.size()];
        this.f_arg(result,all_arg_lists,all_arg_lists.size(),0,templist);
        return result;
    }

    private void f_arg(List<Object[]> result,List<List<?>> all_arg_lists,int tar,int cur,Object[] templist){
        if(tar == cur){
            Object[] temp = templist.clone();
            result.add(temp);
            return;
        }

        List<?> temp_scan_list = all_arg_lists.get(cur);
        for(int i=0;i<temp_scan_list.size();i++){
            templist[cur] = temp_scan_list.get(i);
            f_arg(result,all_arg_lists,tar,cur+1,templist);
        }

    }

}
