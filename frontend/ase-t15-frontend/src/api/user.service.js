import API_URL from "./common";
import axios from "axios";
import authHeader from './auth-header';

class UserDataService {
    getDeliverers() {
        return axios.get(API_URL + "/deliverer", { headers: authHeader() });
    }

    getCustomers(id) {
        return axios.get(API_URL + "/customer", { headers: authHeader() });
    }

    getUsers() {
        return axios.get(API_URL + "/person", { headers: authHeader() });
    }
}

export default new UserDataService();