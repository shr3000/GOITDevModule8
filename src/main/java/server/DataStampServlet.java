package server;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@WebServlet(urlPatterns = "/time")
public class DataStampServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int zone = getZone(req);

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("E, dd MMMM yyyy zzzz");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        String stringDateFormat = format.format(date);

       if (zone >= -12 && zone <= 12) {
           date = new Date(date.getTime() + zone * 3600000);
           stringDateFormat = format.format(date);
       }

       if (zone > 0) {
           stringDateFormat = stringDateFormat + "+" + zone;
       } else {
           stringDateFormat = stringDateFormat + zone;
       }

        resp.setHeader("Content-Type", "text/html; charset=utf-8");
        resp.getWriter().write("<a href=\"/\">Поточна дата</a>");
        resp.getWriter().write(stringDateFormat + "<br>");
        resp.getWriter().write("_____________________________________________<br>");
        resp.getWriter().flush();
        resp.getWriter().close();

    }

    private Integer getZone(HttpServletRequest req) {
        String timezone = req.getParameter("timezone");
        if (timezone != null) {
            String substring = timezone.substring(3);
            if (substring.startsWith(" ")) {
                substring = substring.substring(1);
            }
            return Integer.parseInt(substring);
        }
        return 0;
    }

}
