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
import br.unifesp.psf.utils.PSFUtils;

/**
 * Servlet implementation class TestServlet
 */
public class GetXML extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetXML() {
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
		
		try{
			String valor = request.getParameter("id");
			
			Questionario questionario = (Questionario)PSFUtils.getCassandraDaoSuport().get(Questionario.class, Long.parseLong(valor));
			
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.print(questionario.getXml());

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
