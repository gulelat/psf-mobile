package br.unifesp.psf.servlet;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unifesp.psf.cassandra.dao.CassandraDaoSuport;
import br.unifesp.psf.cassandra.model.*;

/**
 * Servlet implementation class TestServlet
 */
public class ListAnswers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListAnswers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CassandraDaoSuport dao=new CassandraDaoSuport("Test Cluster", "172.20.9.144", "9160", "unifespApresentacao");
		//dao.addHost("localhost", 9160);
		
		try{
			List<Resposta> list = null;
			List<Object> lista = dao.cQuery(Resposta.class, "key", "");
			
			ServletOutputStream outputStream = response.getOutputStream();
			for(int i = 0; i < list.size(); i++)
			{
				outputStream.print("ID: " + list.get(i).getKey() + "\n");
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
