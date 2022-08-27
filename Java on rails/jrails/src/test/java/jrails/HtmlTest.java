package jrails;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.*;

public class HtmlTest {

    private Html html;

    @Before
    public void setUp() throws Exception {
        html = new Html();
    }

    @Test
    public void empty() {
        assertThat(View.empty().toString(), isEmptyString());
    }

    @Test
    public void htmltest(){
        String title = "books";
        String name = "Programming Languages";
        String writer = "Norman Ramsey";
        Html test1 = View.h1(new Html().t(title)).div(new Html().p(new Html().t(name)).p(new Html().t(writer)));
        assertThat(test1.toString(),is("<h1>books</h1><div><p>Programming Languages</p><p>Norman Ramsey</p></div>"));
    }
}
