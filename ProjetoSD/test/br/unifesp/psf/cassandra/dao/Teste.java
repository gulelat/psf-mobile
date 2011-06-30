package br.unifesp.psf.cassandra.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Teste {
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchMethodException, InstantiationException, InvocationTargetException {
			
			CassandraDaoSuport dao=new CassandraDaoSuport("Test Cluster", "localhost", "9160", "unifespApresentacao");
			dao.addHost("localhost", 9160);
			
	
				Base base = new Base();
				base.setID(1);
				base.setXml("<oi>op 01</oi><tchau>op dois</tchau>");
			
				User usuarioSalvo = (User) dao.save(base);
				try{
					
					usuarioSalvo.setFirst(usuarioSalvo.getFirst()+"123");
					dao.save(usuarioSalvo);
					
					User user=(User) dao.get(User.class, usuarioSalvo.getKey());
					
					List<Object> users=dao.cQuery(User.class, "first", "Gustavo123");
					
					dao.delete(user);
				}catch(Exception e){
					e.printStackTrace();
				}
			//}
			
			System.exit(0);
		}
}

