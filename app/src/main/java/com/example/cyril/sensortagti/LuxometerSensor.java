package com.example.cyril.sensortagti;

import static java.lang.Math.pow;
import java.util.UUID;

/**
 * Implements the Luxometer Light Sensor.
 */
public class LuxometerSensor extends Sensor
{

    private double luxvalue;

    public LuxometerSensor(UUID serviceUuid,BluetoothLeService mBluetoothLeService,String address)
    {
        super(serviceUuid,mBluetoothLeService,address);
        this.luxvalue=0.0;
    }

    @Override
    public Point3D convert(byte[] value)
    {
        // bits [1..0] are status bits and need to be cleared according
        // to the user guide, but the iOS code doesn't bother. It should
        // have minimal impact.
        int mantissa;
        int exponent;
        Integer sfloat= shortUnsignedAtOffset(value, 0);
        mantissa = sfloat & 0x0FFF;
        exponent = (sfloat >> 12) & 0xFF;
        double output;
        double magnitude = pow(2.0f, exponent);
        output = (mantissa * magnitude);
        this.luxvalue=output / 100.0f;
        return new Point3D(this.luxvalue,0,0);
    }

    @Override
    public String toString()
    {
        return this.luxvalue+" Lux";
    }

}
