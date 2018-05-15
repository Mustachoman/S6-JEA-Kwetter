package websockets;

import dto.TweetDTO;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@javax.websocket.server.ServerEndpoint(value = "/socket", encoders = TweetEncoder.class, decoders = TweetDecoder.class)
public class Endpoint {

    public static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        peers.add(session);
    }

    @OnMessage
    public void onMessage(TweetDTO tweet, Session session) throws IOException, EncodeException {
        System.out.println("Socket message! " + tweet);
        for (Session peer : peers) {
            peer.getBasicRemote().sendObject(tweet);
        }
    }

    @OnClose
    public void onClose(Session session) {
        peers.remove(session);
    }
}