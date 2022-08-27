package jrails;

public class Html {
    private String htmls = "";
    public String toString() {
        return htmls;
    }

    public Html seq(Html h) {
        htmls += h.toString();
        return this;
    }

    public Html br() {
        htmls += "<br/>";
        return this;
    }

    public Html t(Object o) {
        // Use o.toString() to get the text for this
        htmls = htmls+o.toString();
        return this;
    }

    public Html p(Html child) {
        htmls += "<p>"+child.toString()+"</p>";
        return this;
    }

    public Html div(Html child) {
        htmls += "<div>"+child.toString()+"</div>";
        return this;
    }

    public Html strong(Html child) {
        htmls += "<strong>"+child.toString()+"</strong>";
        return this;
    }

    public Html h1(Html child) {
        htmls += "<h1>"+child.toString()+"</h1>";
        return this;
    }

    public Html tr(Html child) {
        htmls += "<tr>"+child.toString()+"</tr>";
        return this;
    }

    public Html th(Html child) {
        htmls += "<th>"+child.toString()+"</th>";
        return this;
    }

    public Html td(Html child) {
        htmls += "<td>"+child.toString()+"</td>";
        return this;
    }

    public Html table(Html child) {
        htmls += "<table>"+child.toString()+"</table>";
        return this;
    }

    public Html thead(Html child) {
        htmls += "<thead>"+child.toString()+"</thead>";
        return this;
    }

    public Html tbody(Html child) {
        htmls += "<tbody>"+child.toString()+"</tbody>";
        return this;
    }

    public Html textarea(String name, Html child) {
        htmls += "<textarea name=\""+name+"\">"+child.toString()+"</textarea>";
        return this;
    }

    public Html link_to(String text, String url) {
        htmls += "<a href=\""+url+"\">"+text+"</a>";
        return this;
    }

    public Html form(String action, Html child) {
        htmls += "<form action=\""+action+"\" accept-charset=\"UTF-8\" method=\"post\">"+child.toString()+"</form>";
        return this;
    }

    public Html submit(String value) {
        htmls += "<input type=\"submit\" value=\""+value+"\"/>";
        return this;
    }
}