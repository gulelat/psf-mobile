package br.unifesp.psf.cassandra.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;

public class CassandraDaoSuport {

	private Cluster c;
	private Keyspace ko;

	public CassandraDaoSuport(String clusterName, String host, String port,
			String keyspace) {
		c = HFactory.getOrCreateCluster(clusterName, host + ":" + port);
		ko = HFactory.createKeyspace(keyspace, c);
	}

	public Object save(Object objeto) throws IllegalArgumentException,
			IllegalAccessException, SecurityException, NoSuchMethodException,
			InvocationTargetException {

		Mutator<String> mutator = HFactory.createMutator(ko, StringSerializer
				.get());

		Object key = getMethod(objeto, "getKey").invoke(objeto);

		Long id = null;

		if (key == null) {
			id = getUUID(objeto);
		} else {
			id = Long.parseLong(key.toString());
		}

		getMethod(objeto, "setKey", Long.class).invoke(objeto, id);

		for (Field f : objeto.getClass().getDeclaredFields()) {
			if (!f.getName().equals("key")) {
				Object result = getMethod(objeto,
						"get" + toUpperFirst(f.getName())).invoke(objeto);

				if (result != null) {
					HColumn<String, String> column = HFactory.createColumn(f
							.getName(), result.toString(), StringSerializer
							.get(), StringSerializer.get());
					mutator.addInsertion(String.valueOf(id), objeto.getClass()
							.getSimpleName(), column);
				}
			}
		}
		MutationResult mutationResult = mutator.execute();

		return objeto;
	}

	public Object get(Class classe, Long uuid) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			SecurityException, InvocationTargetException, NoSuchMethodException {
		Object resultado = classe.newInstance();

		for (Annotation a : classe.getDeclaredAnnotations()) {
			System.out.println(a.toString());

		}

		for (Field f : resultado.getClass().getDeclaredFields()) {
			ColumnQuery<String, String, String> columnQuery = HFactory
					.createColumnQuery(ko, StringSerializer.get(),
							StringSerializer.get(), StringSerializer.get());

			columnQuery.setColumnFamily(resultado.getClass().getSimpleName());
			columnQuery.setKey(Long.toString(uuid));
			columnQuery.setName(f.getName());
			QueryResult<HColumn<String, String>> queryResult = columnQuery
					.execute();

			String value = null;

			if (!f.getName().equals("key")) {
				if (queryResult != null && queryResult.get() != null) {
					value = queryResult.get().getValue();
				}
			} else {
				value = Long.toString(uuid);
			}

			if (value != null) {
				setValue(resultado, f, value);
			}
		}

		return resultado;
	}

	public void delete(Object objeto) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Mutator<String> mutator = HFactory.createMutator(ko, StringSerializer
				.get());

		mutator.addDeletion(getMethod(objeto, "getKey").invoke(objeto)
				.toString(), objeto.getClass().getSimpleName());
		MutationResult mutationResult = mutator.execute();

	}

	public List<Object> cQuery(Class classe, String campo, String valor)
			throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			NoSuchFieldException {

		List<Object> resultado = new ArrayList<Object>();

		IndexedSlicesQuery<String, String, String> indexedSlicesQuery = HFactory
				.createIndexedSlicesQuery(ko, StringSerializer.get(),
						StringSerializer.get(), StringSerializer.get());
		indexedSlicesQuery.addEqualsExpression(campo, valor);
		indexedSlicesQuery.setColumnNames(getNameFields(classe));
		indexedSlicesQuery.setColumnFamily(classe.getSimpleName());

		QueryResult<OrderedRows<String, String, String>> result = indexedSlicesQuery
				.execute();

		for (Row<String, String, String> linha : result.get().getList()) {
			Object obj = classe.newInstance();
			getMethod(obj, "setKey", Long.class).invoke(obj,
					Long.valueOf(linha.getKey()));
			for (HColumn<String, String> coluna : linha.getColumnSlice()
					.getColumns()) {
				setValue(obj, classe.getDeclaredField(coluna.getName()), coluna
						.getValue());
			}

			resultado.add(obj);
		}

		return resultado;
	}

	public List<Object> listAll(Class classe)
			throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			NoSuchFieldException {

		List<Object> resultado = new ArrayList<Object>();

		IndexedSlicesQuery<String, String, String> indexedSlicesQuery = HFactory
				.createIndexedSlicesQuery(ko, StringSerializer.get(),
						StringSerializer.get(), StringSerializer.get());
		indexedSlicesQuery.setColumnNames(getNameFields(classe));
		indexedSlicesQuery.setColumnFamily(classe.getSimpleName());

		QueryResult<OrderedRows<String, String, String>> result = indexedSlicesQuery
				.execute();

		for (Row<String, String, String> linha : result.get().getList()) {
			Object obj = classe.newInstance();
			getMethod(obj, "setKey", Long.class).invoke(obj,
					Long.valueOf(linha.getKey()));
			for (HColumn<String, String> coluna : linha.getColumnSlice()
					.getColumns()) {
				setValue(obj, classe.getDeclaredField(coluna.getName()), coluna
						.getValue());
			}

			resultado.add(obj);
		}

		return resultado;
	}

	private Collection<String> getNameFields(Class classe) {
		List<String> resultado = new ArrayList<String>();

		for (Field field : classe.getDeclaredFields()) {
			if (!field.getName().equals("key")) {
				resultado.add(field.getName());
			}
		}

		return resultado;
	}

	private Method getMethod(Object objeto, String methodName,
			Class... parameter) throws SecurityException, NoSuchMethodException {
		return objeto.getClass().getDeclaredMethod(methodName, parameter);
	}

	private void setValue(Object resultado, Field f, String value)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Method method = getMethod(resultado, "set" + toUpperFirst(f.getName()),
				f.getType());

		if (f.getType() == String.class)
			method.invoke(resultado, value);

		else if (f.getType() == Long.class)
			method.invoke(resultado, Long.parseLong(value));

		else if (f.getType() == Integer.class)
			method.invoke(resultado, Integer.parseInt(value));

		else if (f.getType() == Double.class)
			method.invoke(resultado, Double.parseDouble(value));

		else if (f.getType() == Float.class)
			method.invoke(resultado, Float.parseFloat(value));

		else if (f.getType() == BigDecimal.class)
			method.invoke(resultado, new BigDecimal(value));

		else if (f.getType() == Boolean.class)
			method.invoke(resultado, new Boolean(value));
	}

	private String toUpperFirst(String valor) {
		StringBuilder resultado = new StringBuilder(valor);
		resultado.setCharAt(0, new String(Character.toString(resultado
				.charAt(0))).toUpperCase().charAt(0));

		return resultado.toString();
	}

	private Long getUUID(Object objeto) {
		return System.currentTimeMillis();
	}

	public void addHost(String host, int port) {
		CassandraHost cassandraHost = new CassandraHost(host, port);
		c.addHost(cassandraHost, true);
	}
}
