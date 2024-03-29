package br.unifesp.psf.servlet;

import java.io.IOException;

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
public class SaveAnswers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveAnswers() {
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
			Resposta resposta = new Resposta();
			resposta.setXml(request.getParameter("xml"));
			resposta.setIdQuestionario(Long.parseLong(request.getParameter("idForm")));
		
			Resposta respostaSalva = (Resposta) PSFUtils.getCassandraDaoSuport().save(resposta);
			
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.print("http://"+request.getLocalName()+":"+request.getLocalPort()+request.getContextPath()+"/GetAnswer?id="+respostaSalva.getKey());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
