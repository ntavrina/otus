package otus.hw8;


import java.lang.reflect.Field;
import java.util.List;

public class MyJsonWriter {

    public static StringBuilder toJson(Object o) throws Exception {
        if(o == null)
            throw new IllegalStateException();

        StringBuilder json = new StringBuilder();
        try {
            json.append("{");

            if(o.getClass().isPrimitive())
                json.append("\"value:\"").append(o);
            else {
                Field[] F = o.getClass().getDeclaredFields();
                for(int i=0; i<F.length; ++i) {
                    F[i].setAccessible(true);
                    Class cla = F[i].get(o).getClass();
                    if(cla.isPrimitive())
                        json.append("\"").append(F[i].getName()).append("\":").append(F[i].get(o));
                    else {
                        json.append("\"").append(F[i].getName()).append("\":");
                        if(!cla.isArray()) {
                            boolean isList = cla.getName().contains("List");
                            if(isList) {
                                List elements = (List) F[i].get(o);
                                json.append("[");
                                try {
                                    for(int j=0; j<elements.size(); ++j) {
                                        Class elCla = elements.get(j).getClass();
                                        boolean isStr = elCla.getName().equals("java.lang.String");
                                        if (isStr)
                                            json.append("\"");
                                        if(elements.get(j) != null)
                                            json.append(elements.get(j));
                                        if (isStr)
                                            json.append("\"");

                                        if(elements.size() > 1 && j < elements.size() - 1)
                                            json.append(",");
                                    }
                                } catch (Exception e) {}
                                json.append("]");
                            } else {
                                boolean isString = cla.getName().equals("java.lang.String");
                                if (isString)
                                    json.append("\"");
                                if (F[i].get(o) != null)
                                    json.append(F[i].get(o));
                                if (isString)
                                    json.append("\"");
                            }
                        } else {
                            Object[] elements = (Object[]) F[i].get(o);
                            json.append("[");
                            try {
                                for(int j=0; j<elements.length; ++j) {
                                    Class elCla = elements[j].getClass();
                                    boolean isStr = elCla.getName().equals("java.lang.String");
                                    if (isStr)
                                        json.append("\"");
                                    if(elements[j] != null)
                                        json.append(elements[j]);
                                    if (isStr)
                                        json.append("\"");

                                    if(elements.length > 1 && j < elements.length - 1)
                                        json.append(",");
                                }
                            } catch (Exception e) {}
                            json.append("]");
                        }
                    }
                    if(F.length > 1 && i < F.length - 1)
                        json.append(",");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } finally {
            json.append("}");
        }
        return json;
    }
}
