package reflection.api;

public class maintest {
    private static Object Polygon;

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(4,6);
        InvestigatorImpl investigator = new InvestigatorImpl();
        investigator.load(rectangle);
//        investigator.load(new Polygon());
        System.out.println( investigator.getTotalNumberOfMethods());
        System.out.println( investigator.getTotalNumberOfConstructors());
        System.out.println( investigator.getTotalNumberOfFields());
        System.out.println( investigator.getAllImplementedInterfaces());
        System.out.println( investigator.getCountOfConstantFields());
        System.out.println( investigator.getCountOfStaticMethods());
        System.out.println( investigator.isExtending());
        System.out.println( investigator.getParentClassSimpleName());
        System.out.println( investigator.isParentClassAbstract());
        System.out.println( investigator.invokeMethodThatReturnsInt("calcArea"));
        System.out.println( investigator.getNamesOfAllFieldsIncludingInheritanceChain());


    }
}
