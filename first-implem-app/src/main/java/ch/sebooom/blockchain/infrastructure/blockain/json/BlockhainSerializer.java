package ch.sebooom.blockchain.infrastructure.blockain.json;

import ch.sebooom.blockchain.domain.BlockChain;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class BlockhainSerializer extends StdSerializer<BlockChain> {


    public BlockhainSerializer(){
        this(null);
    }

    public BlockhainSerializer(Class<BlockChain> blockChainClass){
        super(blockChainClass);
    }

    @Override
    public void serialize(BlockChain blockChain, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
       // jsonGenerator.writeStartObject();
       // jsonGenerator.writeStringField("car_brand", car.getType());
       // jsonGenerator.writeEndObject();
    }
}
