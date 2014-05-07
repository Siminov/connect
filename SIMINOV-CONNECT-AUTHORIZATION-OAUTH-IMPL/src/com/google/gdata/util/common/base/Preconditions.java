package com.google.gdata.util.common.base;

import java.util.Collection;

public final class Preconditions
{
  public static void checkArgument(boolean expression)
  {
    if (!expression) {
      throw new IllegalArgumentException();
    }
  }
  
  public static void checkArgument(boolean expression, Object errorMessage)
  {
    if (!expression) {
      throw new IllegalArgumentException(String.valueOf(errorMessage));
    }
  }
  
  public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs)
  {
    if (!expression) {
      throw new IllegalArgumentException(
        format(errorMessageTemplate, errorMessageArgs));
    }
  }
  
  public static void checkState(boolean expression)
  {
    if (!expression) {
      throw new IllegalStateException();
    }
  }
  
  public static void checkState(boolean expression, Object errorMessage)
  {
    if (!expression) {
      throw new IllegalStateException(String.valueOf(errorMessage));
    }
  }
  
  public static void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageArgs)
  {
    if (!expression) {
      throw new IllegalStateException(
        format(errorMessageTemplate, errorMessageArgs));
    }
  }
  
  public static <T> T checkNotNull(T reference)
  {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }
  
  public static <T> T checkNotNull(T reference, Object errorMessage)
  {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    }
    return reference;
  }
  
  public static <T> T checkNotNull(T reference, String errorMessageTemplate, Object... errorMessageArgs)
  {
    if (reference == null) {
      throw new NullPointerException(
        format(errorMessageTemplate, errorMessageArgs));
    }
    return reference;
  }
  
  public static <T extends Iterable<?>> T checkContentsNotNull(T iterable)
  {
    if (containsOrIsNull(iterable)) {
      throw new NullPointerException();
    }
    return iterable;
  }
  
  public static <T extends Iterable<?>> T checkContentsNotNull(T iterable, Object errorMessage)
  {
    if (containsOrIsNull(iterable)) {
      throw new NullPointerException(String.valueOf(errorMessage));
    }
    return iterable;
  }
  
  public static <T extends Iterable<?>> T checkContentsNotNull(T iterable, String errorMessageTemplate, Object... errorMessageArgs)
  {
    if (containsOrIsNull(iterable)) {
      throw new NullPointerException(
        format(errorMessageTemplate, errorMessageArgs));
    }
    return iterable;
  }
  
  private static boolean containsOrIsNull(Iterable<?> iterable)
  {
    if (iterable == null) {
      return true;
    }
    if ((iterable instanceof Collection))
    {
      Collection<?> collection = (Collection)iterable;
      try
      {
        return collection.contains(null);
      }
      catch (NullPointerException e)
      {
        return false;
      }
    }
    for (Object element : iterable) {
      if (element == null) {
        return true;
      }
    }
    return false;
  }
  
  public static void checkElementIndex(int index, int size)
  {
    checkElementIndex(index, size, "index");
  }
  
  public static void checkElementIndex(int index, int size, String desc)
  {
    checkArgument(size >= 0, "negative size: %s", new Object[] { Integer.valueOf(size) });
    if (index < 0) {
      throw new IndexOutOfBoundsException(
        format("%s (%s) must not be negative", new Object[] { desc, Integer.valueOf(index) }));
    }
    if (index >= size) {
      throw new IndexOutOfBoundsException(
        format("%s (%s) must be less than size (%s)", new Object[] { desc, Integer.valueOf(index), Integer.valueOf(size) }));
    }
  }
  
  public static void checkPositionIndex(int index, int size)
  {
    checkPositionIndex(index, size, "index");
  }
  
  public static void checkPositionIndex(int index, int size, String desc)
  {
    checkArgument(size >= 0, "negative size: %s", new Object[] { Integer.valueOf(size) });
    if (index < 0) {
      throw new IndexOutOfBoundsException(format(
        "%s (%s) must not be negative", new Object[] { desc, Integer.valueOf(index) }));
    }
    if (index > size) {
      throw new IndexOutOfBoundsException(format(
        "%s (%s) must not be greater than size (%s)", new Object[] { desc, Integer.valueOf(index), Integer.valueOf(size) }));
    }
  }
  
  public static void checkPositionIndexes(int start, int end, int size)
  {
    checkPositionIndex(start, size, "start index");
    checkPositionIndex(end, size, "end index");
    if (end < start) {
      throw new IndexOutOfBoundsException(format(
        "end index (%s) must not be less than start index (%s)", new Object[] { Integer.valueOf(end), Integer.valueOf(start) }));
    }
  }
  
  static String format(String template, Object... args)
  {
    StringBuilder builder = new StringBuilder(
      template.length() + 16 * args.length);
    int templateStart = 0;
    int i = 0;
    while (i < args.length)
    {
      int placeholderStart = template.indexOf("%s", templateStart);
      if (placeholderStart == -1) {
        break;
      }
      builder.append(template.substring(templateStart, placeholderStart));
      builder.append(args[(i++)]);
      templateStart = placeholderStart + 2;
    }
    builder.append(template.substring(templateStart));
    if (i < args.length)
    {
      builder.append(" [");
      builder.append(args[(i++)]);
      while (i < args.length)
      {
        builder.append(", ");
        builder.append(args[(i++)]);
      }
      builder.append("]");
    }
    return builder.toString();
  }
}
