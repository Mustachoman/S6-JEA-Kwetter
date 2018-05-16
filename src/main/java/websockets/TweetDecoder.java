package websockets;

import com.google.gson.Gson;
import dto.TweetDTO;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class TweetDecoder implements Decoder.Text<TweetDTO> {

    @Override
    public TweetDTO decode(String tweetJson) throws DecodeException {
        return new Gson().fromJson(tweetJson, TweetDTO.class);
    }

    @Override
    public boolean willDecode(String arg0) {
        return true;
    }

    @Override
    public void init(EndpointConfig ec) {
        
    }

    @Override
    public void destroy() {
    }

}