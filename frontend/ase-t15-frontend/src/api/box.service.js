import API_URL from "./common";
import axios from "axios";
import authHeader from './auth-header';

class BoxDataService {
    getBoxes() {
        return axios.get(API_URL + "/box", { headers: authHeader() });
    }

    create(data) {
        const form = new FormData();
        console.log(data);

        form.append("username", data.username);
        form.append("address", data.address);
        form.append("password", data.password);

        return axios.put(API_URL + "/box", form, { headers: authHeader() });
    }

    edit(data) {
        const form = new FormData();
        console.log(data);

        if (data.password && data.password !== "")
            form.append("password", data.password);
        form.append("username", data.username);
        form.append("address", data.address);

        return axios.patch(API_URL + "/box/" + data.id, form, { headers: authHeader() });
    }

    delete(id) {
        return axios.delete(API_URL + "/agent/" + id, { headers: authHeader() });
    }
}

export default new BoxDataService();