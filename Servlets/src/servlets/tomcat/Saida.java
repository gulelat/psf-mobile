package servlets.tomcat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Saida extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public Saida()
    {
        super();
    }

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
	{
		String nome = arg0.getParameter("data");
		
		//Pega o xml do banco pelo ID e devolve pros caras
		//Precisa ver se tem que montar pagina
		
		//super.service(arg0, arg1);
	}
}