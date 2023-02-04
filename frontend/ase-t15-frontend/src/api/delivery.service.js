import API_URL from "./common";
import axios from "axios";
import authHeader from './auth-header';

class DeliveryDataService {
    getAll() {
        return axios.get(API_URL + "/delivery", { headers: authHeader() });
    }

    get(id) {
        return axios.get(API_URL + "/delivery/" + id, { headers: authHeader() });
    }

    create(data) {
        return axios.post(API_URL + "/delivery", data, { headers: authHeader() });
    }

    update(id, data) {
        return axios.put(API_URL + "/delivery/" + id, data, { headers: authHeader() });
    }

    delete(id) {
        return axios.delete(API_URL + "/delivery/" + id, { headers: authHeader() });
    }
}

export default new DeliveryDataService();