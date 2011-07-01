package servlets.tomcat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Entrada extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public Entrada()
    {
        super();
    }

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
	{
		String nome = arg0.getParameter("xml");
		
		//Pega o xml e grava no banco
		//Ver se precisa retornar ID
		
		//super.service(arg0, arg1);
	}
}