package ch.sebooom.blockchain.infrastructure.blockain.json;

import ch.sebooom.blockchain.domain.BlockChain;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class BlockhainSerialzer implements JsonSerializer<BlockChain> {

    @Override
    public JsonElement serialize(BlockChain blockChain, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}
