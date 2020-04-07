package pwr.chessproject.api.models;

public class MovePlayerResponse {

    private String status;
    private String _id;

    @SuppressWarnings("unused")
    public MovePlayerResponse() {
    }

    public MovePlayerResponse(String status, String _id) {
        this.status = status;
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
