package gui_simple.src;

/**
 * Created by Julian on 15.06.2015.
 */
public class CalculatePillarLength {
    //variables
    protected static double height = 4;
    protected static double radius = 3;
    double length;

    IF3DVector3 vector1;
    IF3DVector3 vector2;
    IF3DVector3 vector3;
    IF3DVector3 vector4;
    IF3DVector3 vector5;
    IF3DVector3 vector6;
    float pillar1;

    //Constructor
    CalculatePillarLength(){
        length = Math.sqrt(Math.pow(height, 2) + Math.pow(radius, 2));

        vector1 = new IF3DVector3( (float) (-Math.cos(0.5235987755982988) * radius), (float)radius/2, 0.0f);
        vector2 = new IF3DVector3( 0.0f, (float) -radius, 0.0f);
        vector3 = new IF3DVector3( (float) (Math.cos(0.5235987755982988) * radius), (float)  radius/2, 0.0f);
        vector4 = new IF3DVector3( (float) (-Math.cos(0.5235987755982988) * radius), (float) -radius/2, (float) -height);
        vector5 = new IF3DVector3( (float) (Math.cos(0.5235987755982988) * radius), (float) -radius/2, (float) -height);
        vector6 = new IF3DVector3( 0.0f, (float) radius, (float) -height);

        pillar1 = 0;
    }
}
