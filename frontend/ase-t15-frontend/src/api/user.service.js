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

    create(data) {
        const form = new FormData();

        form.append("username", data.username);
        form.append("password", data.password);
        form.append("name", data.name);
        form.append("email", data.email);

        return axios.put(API_URL + "/" + data.role.toLowerCase(), form, { headers: authHeader() });
    }

    edit(data) {
        const form = new FormData();

        form.append("username", data.username);
        form.append("name", data.name);
        form.append("email", data.email);

        return axios.patch(API_URL + "/person/" + data.id, form, { headers: authHeader() });
    }

    delete(id) {
        return axios.delete(API_URL + "/agent/" + id, { headers: authHeader() });
    }

    getTokens() {
        return axios.get(API_URL + "/auth/token", { headers: authHeader() });
    }

    createToken(data) {
        const form = new FormData();

        form.append("id", data.id);

        return axios.put(API_URL + "/auth/token", form, { headers: authHeader() });
    }
}

export default new UserDataService();