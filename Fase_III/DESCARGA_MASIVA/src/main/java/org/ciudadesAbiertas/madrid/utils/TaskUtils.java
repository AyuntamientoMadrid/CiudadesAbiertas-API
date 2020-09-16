package org.ciudadesAbiertas.madrid.utils;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*Clase utilizada para sincronizar procesos*/
public class TaskUtils
{
  private static final Logger log = LoggerFactory.getLogger(TaskUtils.class);

  private static Set<String> processCode = new HashSet<String>();

  public static synchronized boolean addCode(String code)
  {
    if (processCode.contains(code) == false)
    {
      processCode.add(code);
      return true;
    }
    else
    {
      log.info("tarea ignorada, el proceso " + code + " ya se esta ejecutando");
      return false;
    }
  }
  
  public static synchronized boolean checkCode(String code)
  {
    return processCode.contains(code);   
  }

  public static synchronized void finishCode(String code)
  {
    processCode.remove(code);
  }
}
