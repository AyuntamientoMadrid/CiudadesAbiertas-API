package org.ciudadesAbiertas.madrid.utils;



import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.OrNode;

public class RSQLUtil {

	private List<Condition> conditions= new ArrayList<Condition>();
	
	private Node rootNode;
	
	private Map<String, String> map;
	
	private String databaseType="";
	
	private String databaseKey="";
	
	
	public void setDatabaseType(String databaseType) {
		this.databaseType=databaseType;		
	}
	
	public String getDatabaseType() {
		return databaseType;		
	}
		
	public String getDatabaseKey() {
	  return databaseKey;
	}

	public void setDatabaseKey(String databaseKey) {
	  this.databaseKey = databaseKey;
	}

	private String addaptFieldToDatabase(String field, String database)
	{
		String finalField=field;
		if (database.equals(Constants.ORACLE))
        {	        	
			finalField= DifferentSQLforDatabases.TRANSLATE_ORACLE +finalField + DifferentSQLforDatabases.TRANSLATE_END;
        		
        }
        else if (database.equals(Constants.SQLSERVER))
        {	
        	finalField=  DifferentSQLforDatabases.getTranslateSQLServer(databaseKey)+finalField + DifferentSQLforDatabases.TRANSLATE_END;
        }
        else if (database.equals(Constants.MYSQL))
        {
        	finalField= finalField;
        }
		return finalField;
	}
	
	public void checkNode(Node node, int level, String fatherOperator) throws Exception
	{
		List<Node> children=new ArrayList<Node>();
		
		String operator="";	
		
		if (node instanceof OrNode)
		{
			OrNode or=(OrNode) node;			
			operator=or.getOperator().name();				
			children = or.getChildren();			
		}
		else if (node instanceof AndNode) 
		{
			AndNode and=(AndNode) node;			
			operator=and.getOperator().name();			
			children = and.getChildren();			
		}
		else 
		{
			String field="";
			String cNodeOperator="";
			String argument="";	
						
			ComparisonNode cnode=(ComparisonNode) node;	
			field=cnode.getSelector();	
			String fieldType= map.get(field);
			if (fieldType==null)
			{
				throw new Exception("wrong field");
			}
			
			cNodeOperator=cnode.getOperator().getSymbol();
			List<String> arguments = cnode.getArguments();
			if (arguments.size()==1)
			{
				argument=arguments.get(0);
				if (cNodeOperator.equals("=in=")||cNodeOperator.equals("=out="))
				{
					if (fieldType.equals(Constants.JAVA_LANG_STRING))
					{
						argument="'"+argument+"'";
					}					
					argument="("+argument+")";
				}
					
				
			}
			else if (arguments.size()>1)
			{
				argument="(";
				Iterator<String> iterator = arguments.iterator();
				while (iterator.hasNext())
				{
					String actualArgument=iterator.next();					
					if (fieldType.equals(Constants.JAVA_LANG_STRING))
					{
						actualArgument="'"+actualArgument+"'";
						argument+=actualArgument+",";	
					}
					else if (fieldType.equals(Constants.JAVA_SQL_TIMESTAMP))
					{
						Date date=StringToDateConverter.convertStringToDate(actualArgument);		    
						actualArgument=Util.dateTimeFormatter.format(date);		    	
						actualArgument="'"+actualArgument+"'";
				    	argument+=actualArgument+",";	
					}
					else if (fieldType.equals(Constants.JAVA_SQL_DATE))
					{
						Date date=StringToDateConverter.convertStringToDate(actualArgument);		    
						actualArgument=Util.dateFormatter.format(date);		    	
						actualArgument="'"+actualArgument+"'";
				    	argument+=actualArgument+",";	
					}	
					else if (fieldType.equals(Constants.JAVA_SQL_TIME))
					{
						Time time=StringToDateConverter.convertStringToTime(actualArgument);		    
						actualArgument=Util.timeFormatter.format(time);		    	
						actualArgument="'"+actualArgument+"'";
				    	argument+=actualArgument+",";	
					}
					else {		
						actualArgument = actualArgument.replace(",", ".");
						argument+=actualArgument+",";	
					}
				}
				argument=StringUtils.chop(argument)+")";
			}		
			
			Condition c=new Condition();
			c.level=level;
			c.operator=fatherOperator;
			c.text=node.toString();
			
			if (map!=null)
			{
				String rsqlCondition=node.toString();
				if (fieldType.equals(Constants.JAVA_LANG_STRING)||fieldType.equals(Constants.JAVA_LANG_BOOLEAN))
				{
					field=addaptFieldToDatabase(field, databaseType);
					cNodeOperator=cNodeOperator.replace("==", "like");
					cNodeOperator=cNodeOperator.replace("!=", "not like");
					cNodeOperator=cNodeOperator.replace("==", "=");
					cNodeOperator=cNodeOperator.replace("!=", "<>");
					cNodeOperator=commonOperators(cNodeOperator);
					
					argument = argument.replace("*", "%");
					argument = Util.stripAccents(argument.toUpperCase());
					
					if ((arguments.size()==1)&&((argument.startsWith("(")==false)&&(argument.endsWith(")")==false)))
					{
						argument="'"+argument+"'";
					}
					
					rsqlCondition=field+" "+cNodeOperator+" "+argument;
				}
				else if (fieldType.equals(Constants.JAVA_SQL_TIMESTAMP))
			    {
					if (arguments.size()==1)
					{
						Date date=StringToDateConverter.convertStringToDate(argument);		    
						argument=Util.dateTimeFormatter.format(date);		    	
						argument="'"+argument+"'";
					}
			    	
			    	cNodeOperator=cNodeOperator.replace("==", "=");
					cNodeOperator=cNodeOperator.replace("!=", "<>");
					cNodeOperator=commonOperators(cNodeOperator);
													
					rsqlCondition=field+" "+cNodeOperator+" "+argument;
			    }
				else if (fieldType.equals(Constants.JAVA_SQL_DATE))
			    {
					if (arguments.size()==1)
					{
						Date date=StringToDateConverter.convertStringToDate(argument);		    
						argument=Util.dateFormatter.format(date);		    	
						argument="'"+argument+"'";
					}
			    	
			    	cNodeOperator=cNodeOperator.replace("==", "=");
					cNodeOperator=cNodeOperator.replace("!=", "<>");
					cNodeOperator=commonOperators(cNodeOperator);
													
					rsqlCondition=field+" "+cNodeOperator+" "+argument;
			    }
				else if (fieldType.equals(Constants.JAVA_SQL_TIME))
			    {
					if (arguments.size()==1)
					{
						Time time=StringToDateConverter.convertStringToTime(argument);		    
						argument=Util.timeFormatter.format(time);		    	
						argument="'"+argument+"'";
					}
			    	
			    	cNodeOperator=cNodeOperator.replace("==", "=");
					cNodeOperator=cNodeOperator.replace("!=", "<>");
					cNodeOperator=commonOperators(cNodeOperator);
													
					rsqlCondition=field+" "+cNodeOperator+" "+argument;
			    }
			    else if (fieldType.equals(Constants.JAVA_MATH_BIGDECIMAL)
			    		||fieldType.equals(Constants.JAVA_LANG_INTEGER)
			    		||fieldType.equals(Constants.JAVA_LANG_SHORT)
			    		||fieldType.equals(Constants.JAVA_LANG_LONG) 
			    		||fieldType.equals(Constants.JAVA_LANG_FLOAT)
			    		||fieldType.equals(Constants.JAVA_LANG_DOUBLE) )
			    {
			    	if (arguments.size()==1)
					{
				    	if (Util.validValue(argument)) {
				    		argument = argument.replace(",", ".");
						}
					}
			    	cNodeOperator=cNodeOperator.replace("==", "=");
					cNodeOperator=cNodeOperator.replace("!=", "<>");
					cNodeOperator=commonOperators(cNodeOperator);
					
			    	rsqlCondition=field+" "+cNodeOperator+" "+argument;
			    }
				c.text=rsqlCondition;
			}
			conditions.add(c);
		}
		
		
		Iterator<Node> iterator = children.iterator();		
		while (iterator.hasNext())
		{
			Node thisNode=iterator.next();
			checkNode(thisNode,level+1, operator);
		}
		
		
	}

	private String commonOperators(String cNodeOperator) {
		String operator=cNodeOperator;
		operator=operator.replace("=gt=", ">");
		operator=operator.replace("=lt=", "<");
		operator=operator.replace("=le=", "<=");
		operator=operator.replace("=ge=", ">=");
		operator=operator.replace("=in=", "in");
		operator=operator.replace("=out=", "not in");
		return operator;
	}
	
	public String generateSingleRSQL()
	{
		String query="";
		if (conditions.size()>0)
		{
			Collections.sort(conditions); 
		
			int maxLevel=0;
			for (Condition c:conditions)
			{	
				if (c.level>maxLevel)
				{
					maxLevel=c.level;
				}
			}
		
			
			while (maxLevel>=1)
			{				
				for (Condition c:conditions)
				{	
					if (c.level==maxLevel)
					{
						query=query+" " + c.operator+ " "+"(" +c.text + ")" ; 
					}
						
				}
				query=query.substring(query.indexOf("("));
				query="("+query+")";
				maxLevel--;
			}			
		}
		return query;
	}
	
	public void parseQuery(String query)
	{
		rootNode = new RSQLParser().parse(query);
	}
	
	public void checkNode() throws Exception
	{
		checkNode(rootNode, 1, "");
	}
	
	
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public static void main(String[] args) throws Exception {
		
		String rsqlQuery="genres=in=(sci-fi,action);genres=out=(romance,animated,horror),director==Que*Tarantino";
		
//		rsqlQuery="name==\"Kill Bill\",year=gt=2003";
//		
//		rsqlQuery="year=gt=2003";
//		
//		rsqlQuery="genres=in=(sci-fi,action) or (director=='Christopher Nolan' or actor==*Bale) and year>=2000";
		
		//rsqlQuery="(director=='Christopher Nolan' or actor==*Bale) and year>=2000";
		
		System.out.println("Query: "+rsqlQuery);
		
		
		RSQLUtil rUtil=new RSQLUtil();
		rUtil.parseQuery(rsqlQuery);
		rUtil.checkNode();
		String queryTranslated=rUtil.generateSingleRSQL();	
		
		System.out.println(queryTranslated);
		
	}
	
	
	public static class Condition implements Comparable<Condition>
	{
		int level;
		String text;
		String operator;
		
		@Override
		public String toString() {
			return "Condition [level=" + level + ", text=" + text + ", operator=" + operator + "]";
		}

		@Override
		public int compareTo(Condition o) {
			return level-o.level;			
		}
		
		
			
	}


	


	
	
}
