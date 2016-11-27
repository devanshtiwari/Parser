package com.xpathgenerator;

/**
 * <h1>Attribute</h1>
 * <p>Attribute class manages the attributes of the XPath. All the conditions on attributes can be realized in this class.
 * Attributes with specific values, AND , OR conditions , multiple attributes, everything is managed in Attribute Class.</p>
 * @since 2016-11-14
 * @author Avinash and Devansh
 */
public class Attribute {
    private String name;
    private String value;
    private Condition condition;

    /**
     * Enum to keep conditions of AND and OR.
     */
    public enum Condition{
        OR("or"),AND("and");
        private String value;
        private Condition(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Attribute(){}
    /**
     * Attribute Constructor takes parameters name, its value and condition. (Its for internal use of Tag only. )
     * @param name Name of the attribute
     * @param value Value of the attribute
     * @param condition Condition (And or OR)
     */
    public Attribute(String name, String value, Condition condition) {
        if(name == "")
            try {
                throw new Exception("Attribute Name is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        this.name = name;
        this.value = value;
        this.condition = condition;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == "")
            try {
                throw new Exception("Attribute Name is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
