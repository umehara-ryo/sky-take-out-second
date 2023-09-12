package com.sky.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocketサービス
 */
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    //セッションオブジェクトを保持する
    private static Map<String, Session> sessionMap = new HashMap();

    /*
      呼び出されたメソッドと接続する
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("クライアント：" + sid + "接続を確立する");
        sessionMap.put(sid, session);
    }

    /**
     * クライアントメッセージを受け取る後で呼び出されたメソッド
     *
     * @param message クライアントから送ってくるメッセージ
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("クライアントから：" + sid + "のメッセージ:" + message + "受け取る");
    }

    /**
     * 连接关闭调用的方法
     *呼び出しをクロスするメソッドと接続する
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("接続切断:" + sid);
        sessionMap.remove(sid);
    }

    /**
     * 一斉送信
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                //サーバーからクライアントに送信
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
