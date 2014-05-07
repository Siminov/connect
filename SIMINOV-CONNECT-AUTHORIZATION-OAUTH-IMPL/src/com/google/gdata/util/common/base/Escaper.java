package com.google.gdata.util.common.base;

public abstract interface Escaper
{
  public abstract String escape(String paramString);
  
  public abstract Appendable escape(Appendable paramAppendable);
}
