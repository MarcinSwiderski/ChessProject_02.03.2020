package pwr.chessproject.api.models;

/**
 * Generic model of JSON response
 */
public class CreateNewGameResponse {

        private String game_id;
        private String status;
        private String _id;

        @SuppressWarnings("unused")
        public CreateNewGameResponse() {
        }

        public CreateNewGameResponse(String game_id, String status, String _id) {
            this.game_id = game_id;
            this.status = status;
            this._id = _id;
        }

        public String getGame_id() {
            return game_id;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
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
