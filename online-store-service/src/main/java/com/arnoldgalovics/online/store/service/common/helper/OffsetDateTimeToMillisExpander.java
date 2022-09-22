package com.arnoldgalovics.online.store.service.common.helper;

import feign.Param;

import java.time.OffsetDateTime;

//https://arnoldgalovics.com/spring-cloud-feign-request-parameter-conversion/
public class OffsetDateTimeToMillisExpander implements Param.Expander {
    @Override
    public String expand(Object value) {
        /**
         * Description
         * The java.lang.Class.isAssignableFrom() determines if the class or interface represented by this Class object is either the same as, or is a superclass or superinterface of, the class or interface represented by the specified Class parameter.
         *
         * Declaration
         * Following is the declaration for java.lang.Class.isAssignableFrom() method
         *
         * public boolean isAssignableFrom(Class<?> cls)
         * Parameters
         * cls − This is the Class object to be checked.
         *
         * Return Value
         * This method returns the boolean value indicating whether objects of the type cls can be assigned to objects of this class.
         *
         * Exception
         * NullPointerException − if the specified Class parameter is null.
         *
         * Example
         * The following example shows the usage of java.lang.Class.isAssignableFrom() method.
         *
         * import java.lang.*;
         *
         * public class ClassDemo {
         *
         *    public static void main(String[] args) {
         *
         *       try {
         *          ClassDemo cls = new ClassDemo();
         *          Class c = cls.getClass();
         *
         *          // class object associated with BaseClass
         *          Class subClass = SubClass.class;
         *
         *          // checks whether BaseClass is assignable from ClassDemo
         *          boolean retval = subClass.isAssignableFrom(c);
         *          System.out.println("Return Value = " + retval);
         *
         *          // checks whether ClassDemo is assignable from BaseClass
         *          retval = c.isAssignableFrom(subClass);
         *          System.out.println("Return Value  = " + retval);
         *       } catch(Exception e) {
         *          System.out.println(e.toString());
         *       }
         *    }
         * }
         *
         * // base class
         * class SubClass extends ClassDemo {
         *    public SubClass() {
         *       // no argument constructor
         *    }
         * }
         * Let us compile and run the above program, this will produce the following result −
         *
         * Return Value = false
         * Return Value = true
         */
        if (!OffsetDateTime.class.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("This expander is not supporting the type");
        }
        long millis = ((OffsetDateTime) value).toInstant().toEpochMilli();
        return "" + millis;
    }
}
