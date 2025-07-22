package org.serverclient;

class ServerResponse {
    String status;
    String message;

    ServerResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
