package org.specialtripsagency;

/**
 * This class was automatically generated by the data modeler tool.
 * $HASH(625bde1358deadbc9e9875f072c57243)
 */
@org.kie.api.definition.type.Label(value = "Rejection")
public class Rejection extends java.lang.Object implements java.io.Serializable {

    static final long serialVersionUID = 1L;
    
    @org.kie.api.definition.type.Label(value = "Reason")
    @org.kie.api.definition.type.Position(value = 0)
    private java.lang.String reason;

    public Rejection() {
    }

    public Rejection(java.lang.String reason) {
        this.reason = reason;
    }


    
    public java.lang.String getReason() {
        return this.reason;
    }

    public void setReason(java.lang.String reason) {
        this.reason = reason;
    }
}