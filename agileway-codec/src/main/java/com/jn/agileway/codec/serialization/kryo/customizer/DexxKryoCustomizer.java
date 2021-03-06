package com.jn.agileway.codec.serialization.kryo.customizer;

import com.esotericsoftware.kryo.Kryo;
import com.jn.agileway.codec.serialization.kryo.KryoCustomizer;
import com.jn.langx.util.os.Platform;
import de.javakaffee.kryoserializers.dexx.ListSerializer;
import de.javakaffee.kryoserializers.dexx.MapSerializer;
import de.javakaffee.kryoserializers.dexx.SetSerializer;

public class DexxKryoCustomizer implements KryoCustomizer {
    @Override
    public String getName() {
        return "dexx";
    }

    @Override
    public void customize(Kryo kryo) {
        if (Platform.isAndroid) {
            ListSerializer.registerSerializers(kryo);
            MapSerializer.registerSerializers(kryo);
            SetSerializer.registerSerializers(kryo);
        }
    }
}
