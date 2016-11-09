package com.xpathgenerator;

public class Attribute {
    private String name;
    private String value;
    private Condition condition;
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
    public Attribute(){

    }
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
