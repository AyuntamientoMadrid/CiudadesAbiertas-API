package org.ciudadesAbiertas.madrid.utils;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
/*
 * Clase para renombrar las tablas añadiendo el {h-schema}, que se utiliza 
 * para controlar el esquema de los usuarios en SQLServer
 * */
public class TableNameReplacer {
	
	public static final String H_SCHEMA = "{h-schema}";
	
	private static final Logger log = LoggerFactory.getLogger(TableNameReplacer.class);
	

	private static TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
	
	
	public static String addAliases(String sql)  {
		

		log.debug("Query before add aliases: "+sql);
		
		
		if (sql.startsWith(DynamicQueryUtils.SELECT_FROM))
		{
			return sql;
		}
		
        Select select=null;
		try {
			select = (Select) CCJSqlParserUtil.parse(sql);
		} catch (JSQLParserException e) {
			log.error("Error parsinga data",e);
		}        
       
		if (select!=null)
		{
			PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
			List selectItems = plainSelect.getSelectItems();
			Iterator it=selectItems.iterator();
			int functions=1;
			while (it.hasNext())
			{
				try
				{
					Object expressionObj=it.next();
					if (expressionObj instanceof SelectExpressionItem)
					{
						SelectExpressionItem next = (SelectExpressionItem) it.next();
						if (next.getAlias()==null)
						{
							Expression expression = next.getExpression();
							
							if (expression instanceof Function)
							{	
								next.setAlias(new Alias("f"+functions++));
							}
							else if (expression instanceof Column)
							{
								String myAlias=((Column)next.getExpression()).getColumnName();
								next.setAlias(new Alias(myAlias));
							}						
						}
					}
				}
				catch (Exception e)
				{
					log.error("Error adding alias",e);
				}
			}	
		}
		
		

        StringBuilder buffer = new StringBuilder();
        ExpressionDeParser expressionDeParser = new ExpressionDeParser() {
            @Override
            public void visit(Column tableColumn) {
            	
            	super.visit(tableColumn);
            }
        };
        
        SelectDeParser deparser = new SelectDeParser(expressionDeParser, buffer) {
            @Override
            public void visit(Table tableName) {
            	
                super.visit(tableName);
            }
        };
        
        expressionDeParser.setSelectVisitor(deparser);
        expressionDeParser.setBuffer(buffer);
        select.getSelectBody().accept(deparser);

        String sqlWithAliases=buffer.toString();
        
        log.debug("Query after add aliases: "+sqlWithAliases);
        
        return (sqlWithAliases);
		
	}
	

	public static String addSchemaToQuery(String sql) throws JSQLParserException {
		

		log.debug("Query before add schema: "+sql);
		
        Select select = (Select) CCJSqlParserUtil.parse(sql);        
		
		List<String> tableList = tablesNamesFinder.getTableList(select);

        StringBuilder buffer = new StringBuilder();
        ExpressionDeParser expressionDeParser = new ExpressionDeParser() {
            @Override
            public void visit(Column tableColumn) {
            	
            	if (tableColumn.getTable() != null) 
            	{            	
            		if (tableList.contains(tableColumn.getTable().getName()))
            		{
            			tableColumn.getTable().setName(H_SCHEMA+tableColumn.getTable().getName());
            		}
            	}
                super.visit(tableColumn);
            }
        };
        
        SelectDeParser deparser = new SelectDeParser(expressionDeParser, buffer) {
            @Override
            public void visit(Table tableName) {
            	
            	if (tableList.contains(tableName.getName()))
            	{
            		 tableName.setName(H_SCHEMA+tableName.getName() );
            	}           	
               
                super.visit(tableName);
            }
        };
        
        expressionDeParser.setSelectVisitor(deparser);
        expressionDeParser.setBuffer(buffer);
        select.getSelectBody().accept(deparser);

        String sqlWithSchema=buffer.toString();
        
        log.debug("Query after add schema: "+sqlWithSchema);
        
        return (sqlWithSchema);
    }
	
	
	public static void main(String[] args) {
		
		String sql="select * from agenda a, agenda_rol b where a.id = b.id";
		
		try {
			System.out.println(addSchemaToQuery(sql));
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
