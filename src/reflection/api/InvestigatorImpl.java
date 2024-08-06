package reflection.api;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class InvestigatorImpl implements Investigator{

    private Class theClass;
    private Object theObject;
    @Override
    public void load(Object anInstanceOfSomething) {
        this.theClass = anInstanceOfSomething.getClass();
        this.theObject = anInstanceOfSomething;
    }

    @Override
    public int getTotalNumberOfMethods(){
        Method[] theMethods = theClass.getDeclaredMethods();
        return theMethods.length;
    }

    @Override
    public int getTotalNumberOfConstructors(){
        Constructor[] constructors = theClass.getConstructors();
        return constructors.length;
    }

    @Override
    public int getTotalNumberOfFields(){
        Field [] allFields = theClass.getDeclaredFields();
        return allFields.length;
    }

    @Override
    public Set<String> getAllImplementedInterfaces(){
        Set<String> implementedInterfaces = new HashSet<>();
        Class<?>[] interfaces = theClass.getInterfaces();
        for (Class<?> singleInterface : interfaces) {
            implementedInterfaces.add(singleInterface.getSimpleName());
        }
        return implementedInterfaces;
    }

    @Override
    public int getCountOfConstantFields(){
        int countOfConstantFields = 0;
        Field [] allFields = theClass.getDeclaredFields();
        for (Field field : allFields) {
            if (Modifier.isFinal(field.getModifiers())){
               countOfConstantFields++;
            }
        }
        return countOfConstantFields;
    }
    @Override
    public int getCountOfStaticMethods(){
        int countOfStaticMethods = 0;
        Method[] theMethods = theClass.getDeclaredMethods();
        for (Method method : theMethods) {
            if (Modifier.isStatic(method.getModifiers())){
                countOfStaticMethods++;
            }
        }
        return countOfStaticMethods;
    }
    @Override
    public boolean isExtending() {
        if (!theClass.getSuperclass().getSimpleName().equals("Object"))
            return true;
        else{
            return false;
        }
    }
    @Override
    public String getParentClassSimpleName(){
        if (theClass.getSuperclass().getSimpleName().equals("Object")){
            return null;
        }
        else{
            return (theClass.getSuperclass().getSimpleName());
        }
    }
    @Override
    public boolean isParentClassAbstract(){
        if(isExtending()) {
            try {
                Class parentClazz = Class.forName(theClass.getSuperclass().getName());
                return Modifier.isAbstract(parentClazz.getModifiers());

            } catch (ClassNotFoundException e) {
                return false;
            }
        }
        return false;
    }
    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain(){
        Set<String> allFields = new HashSet<>();
        Class clazz = theClass;

        while (clazz != null) {
            // Get all declared fields of the current class
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                allFields.add(field.getName());
            }
            clazz = clazz.getSuperclass();
        }
        return allFields;
    }
    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args){
        Object result = 0;
        Class[] parameterTypes = new Class[args.length];
        for(int i = 0; i<args.length; i++)
        {
            parameterTypes[i] = getPrimitiveClass(args[i].getClass());
        }
        try{
            Method func = theClass.getMethod(methodName, parameterTypes);
            result = (func.invoke(theObject,args));
        }
        catch (NoSuchMethodException e){
            System.out.println("NoSuchMethodException" + e.getMessage());
        }
        catch (SecurityException e){
            System.out.println("SecurityException" + e.getMessage());
        }
        catch (IllegalAccessException e){
            System.out.println("IllegalAccessException" + e.getMessage());
        }
        catch (InvocationTargetException e){
            System.out.println("InvocationTargetException" + e.getMessage());
        }

        return (int)result;
    }
    @Override
    public Object createInstance(int numberOfArgs, Object... args){
        Class[] parameterTypes = new Class[numberOfArgs];
        for(int i = 0; i < numberOfArgs; i++) {
            parameterTypes[i] = getPrimitiveClass(args[i].getClass());
        }
        try{
            Constructor constructor = theClass.getConstructor(parameterTypes);
            return constructor.newInstance(args);
        }
        catch (Exception e){
            System.out.println("Exception " + e.getMessage());
        }
        return null;
    }
    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args){
        try{
            Method func = theClass.getDeclaredMethod(name, parametersTypes);
            func.setAccessible(true);
            return func.invoke(theObject, args);
        }
        catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }
        return null;
    }
    @Override
    public String getInheritanceChain(String delimiter){
        return getInheritanceChainHelper(theClass, delimiter);
    }

    private String getInheritanceChainHelper(Class Clazz, String delimiter){
        if(Clazz.getSimpleName().equals("Object")){
            return new String("Object");
        }
        else{
          String inheritance = getInheritanceChainHelper(Clazz.getSuperclass(), delimiter);
          inheritance += delimiter;
          inheritance += Clazz.getSimpleName();
          return inheritance;
        }
    }
    private Class<?> getPrimitiveClass(Class<?> clazz) {
        if (clazz == Integer.class)
            return int.class;
        if (clazz == Double.class)
            return double.class;
        if (clazz == Float.class)
            return float.class;
        if (clazz == Long.class)
            return long.class;
        if (clazz == Character.class)
            return char.class;
        if (clazz == Byte.class)
            return byte.class;
        if (clazz == Short.class)
            return short.class;
        if (clazz == Boolean.class)
            return boolean.class;
        return clazz;
    }
}
