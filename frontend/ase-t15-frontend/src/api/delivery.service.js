import axios from "axios";
import authHeader from './auth-header';

class DeliveryDataService {
    getAll() {
        return axios.get(API_URL + "/deliveries", { headers: authHeader() });
    }

    get(id) {
        return axios.get(API_URL + "/deliveries/" + id, { headers: authHeader() });
    }

    create(data) {
        return axios.post(API_URL + "/deliveries", data, { headers: authHeader() });
    }

    update(id, data) {
        return axios.put(API_URL + "/deliveries/" + id, data, { headers: authHeader() });
    }

    delete(id) {
        return axios.delete(API_URL + "/deliveries/" + id, { headers: authHeader() });
    }
}

export default new DeliveryDataService();