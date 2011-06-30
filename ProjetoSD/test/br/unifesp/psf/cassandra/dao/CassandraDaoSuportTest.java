package br.unifesp.psf.cassandra.dao;

import java.lang.reflect.InvocationTargetException;
//import java.util.List;

public class CassandraDaoSuportTest {
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchMethodException, InstantiationException, InvocationTargetException {
			
			CassandraDaoSuport dao=new CassandraDaoSuport("Test Cluster", "localhost", "9160", "unifespApresentacao");
			dao.addHost("localhost", 9160);
			
	
				User user2=new User();
				user2.setAge(35);
				user2.setFirst("Gustavo");
				user2.setLast("Konish");
			
			
				User usuarioSalvo = (User) dao.save(user2);
				try{
					
					usuarioSalvo.setFirst(usuarioSalvo.getFirst()+"123");
					dao.save(usuarioSalvo);
					
					User user=(User) dao.get(User.class, usuarioSalvo.getKey());
					
					//List<Object> users=dao.cQuery(User.class, "first", "Gustavo123");
					dao.cQuery(User.class, "first", "Gustavo123");
					
					dao.delete(user);
				}catch(Exception e){
					e.printStackTrace();
				}
			//}
			
			System.exit(0);
		}
}

