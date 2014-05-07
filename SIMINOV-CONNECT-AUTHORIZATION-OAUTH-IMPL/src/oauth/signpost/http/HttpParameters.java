package oauth.signpost.http;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import oauth.signpost.OAuth;

public class HttpParameters
  implements Map<String, SortedSet<String>>, Serializable
{
  private TreeMap<String, SortedSet<String>> wrappedMap = new TreeMap();
  
  public SortedSet<String> put(String key, SortedSet<String> value)
  {
    return (SortedSet)this.wrappedMap.put(key, value);
  }
  
  public SortedSet<String> put(String key, SortedSet<String> values, boolean percentEncode)
  {
    if (percentEncode)
    {
      remove(key);
      for (String v : values) {
        put(key, v, true);
      }
      return get(key);
    }
    return (SortedSet)this.wrappedMap.put(key, values);
  }
  
  public String put(String key, String value)
  {
    return put(key, value, false);
  }
  
  public String put(String key, String value, boolean percentEncode)
  {
    key = percentEncode ? OAuth.percentEncode(key) : key;
    SortedSet<String> values = (SortedSet)this.wrappedMap.get(key);
    if (values == null)
    {
      values = new TreeSet();
      this.wrappedMap.put(key, values);
    }
    if (value != null)
    {
      value = percentEncode ? OAuth.percentEncode(value) : value;
      values.add(value);
    }
    return value;
  }
  
  public String putNull(String key, String nullString)
  {
    return put(key, nullString);
  }
  
  public void putAll(Map<? extends String, ? extends SortedSet<String>> m)
  {
    this.wrappedMap.putAll(m);
  }
  
  public void putAll(Map<? extends String, ? extends SortedSet<String>> m, boolean percentEncode)
  {
    if (percentEncode) {
      for (String key : m.keySet()) {
        put(key, (SortedSet)m.get(key), true);
      }
    } else {
      this.wrappedMap.putAll(m);
    }
  }
  
  public void putAll(String[] keyValuePairs, boolean percentEncode)
  {
    for (int i = 0; i < keyValuePairs.length - 1; i += 2) {
      put(keyValuePairs[i], keyValuePairs[(i + 1)], percentEncode);
    }
  }
  
  public void putMap(Map<String, List<String>> m)
  {
    for (String key : m.keySet())
    {
      SortedSet<String> vals = get(key);
      if (vals == null)
      {
        vals = new TreeSet();
        put(key, vals);
      }
      vals.addAll((Collection)m.get(key));
    }
  }
  
  public SortedSet<String> get(Object key)
  {
    return (SortedSet)this.wrappedMap.get(key);
  }
  
  public String getFirst(Object key)
  {
    return getFirst(key, false);
  }
  
  public String getFirst(Object key, boolean percentDecode)
  {
    SortedSet<String> values = (SortedSet)this.wrappedMap.get(key);
    if ((values == null) || (values.isEmpty())) {
      return null;
    }
    String value = (String)values.first();
    return percentDecode ? OAuth.percentDecode(value) : value;
  }
  
  public String getAsQueryString(Object key)
  {
    return getAsQueryString(key, true);
  }
  
  public String getAsQueryString(Object key, boolean percentEncode)
  {
    StringBuilder sb = new StringBuilder();
    if (percentEncode) {
      key = OAuth.percentEncode((String)key);
    }
    Set<String> values = (Set)this.wrappedMap.get(key);
    if (values == null) {
      return key + "=";
    }
    Iterator<String> iter = values.iterator();
    while (iter.hasNext())
    {
      sb.append(key + "=" + (String)iter.next());
      if (iter.hasNext()) {
        sb.append("&");
      }
    }
    return sb.toString();
  }
  
  public String getAsHeaderElement(String key)
  {
    String value = getFirst(key);
    if (value == null) {
      return null;
    }
    return key + "=\"" + value + "\"";
  }
  
  public boolean containsKey(Object key)
  {
    return this.wrappedMap.containsKey(key);
  }
  
  public boolean containsValue(Object value)
  {
    for (Set<String> values : this.wrappedMap.values()) {
      if (values.contains(value)) {
        return true;
      }
    }
    return false;
  }
  
  public int size()
  {
    int count = 0;
    for (String key : this.wrappedMap.keySet()) {
      count += ((SortedSet)this.wrappedMap.get(key)).size();
    }
    return count;
  }
  
  public boolean isEmpty()
  {
    return this.wrappedMap.isEmpty();
  }
  
  public void clear()
  {
    this.wrappedMap.clear();
  }
  
  public SortedSet<String> remove(Object key)
  {
    return (SortedSet)this.wrappedMap.remove(key);
  }
  
  public Set<String> keySet()
  {
    return this.wrappedMap.keySet();
  }
  
  public Collection<SortedSet<String>> values()
  {
    return this.wrappedMap.values();
  }
  
  public Set<Map.Entry<String, SortedSet<String>>> entrySet()
  {
    return this.wrappedMap.entrySet();
  }
}
