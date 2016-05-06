package workshop.common;

import java.net.Socket;

public interface ISocketConnection {
    /**
     * Connects to server by ip and port.
     * @param ip is server ip.
     * @param port is server port.
     * @return true if connection was successfully completed.
     */
    public boolean connect(String ip, int port);
    
    /**
     * Connects to server by socket.
     * @param socket is socket to connect.
     * @return true if connection was successfully completed.
     */
    public boolean connect(Socket socket);

    /**
     * Disconnects from server.
     */
    public void disconnect();
    
    /**
     * Sends request.
     * @param request is request to send.
     * @return server response.
     */
    public Request sendAndGetResponse(Request request);
    
    /**
     * Sends request.
     * @param request is request to send.
     * @return true if sending was successfully completed.
     */
    public boolean send(Request request);
    
    /**
     * Gets response.
     * @return response.
     */
    public Request getResponse();
}
