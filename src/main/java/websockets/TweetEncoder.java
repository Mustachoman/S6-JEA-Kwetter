/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websockets;

import com.google.gson.Gson;
import dto.TweetDTO;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class TweetEncoder implements Encoder.Text<TweetDTO> {

    @Override
    public String encode(TweetDTO tweet) throws EncodeException {
        return new Gson().toJson(tweet);
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }

}
