import API_URL from "./common";
import axios from "axios";
import authHeader from './auth-header';

class BoxDataService {
    getBoxes() {
        return axios.get(API_URL + "/box", { headers: authHeader() });
    }
}

export default new BoxDataService();