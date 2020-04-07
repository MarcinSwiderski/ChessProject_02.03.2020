package pwr.chessproject.api.models;

public class MoveVIResponse {

    private String from;
    private String to;
    private String status;
    private String _id;

    @SuppressWarnings("unused")
    public MoveVIResponse() {
    }

    public MoveVIResponse(String from, String to, String status, String _id) {
        this.from = from;
        this.to = to;
        this.status = status;
        this._id = _id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
