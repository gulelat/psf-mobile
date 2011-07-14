package br.unifesp.psf.utils;

import br.unifesp.psf.cassandra.dao.CassandraDaoSuport;

public class PSFUtils {

	public static CassandraDaoSuport getCassandraDaoSuport(){
		CassandraDaoSuport dao=new CassandraDaoSuport("Test Cluster", "172.20.9.144", "9160", "unifespApresentacao");
		dao.addHost("172.20.9.143", 9160);
		
		return dao;
	}
	
}
