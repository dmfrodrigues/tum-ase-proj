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
        const form = new FormData();
        form.append("customerId", data.customerId);
        form.append("createdById", data.createdById);
        form.append("delivererId", data.delivererId);
        form.append("pickupAddress", data.pickupAddress);
        form.append("boxId", data.boxId);
        return axios.put(API_URL + "/delivery", form, { headers: authHeader() });
    }

    update(id, data) {
        const form = new FormData();
        form.append("customerId", data.customerId);
        if (data.createdById)
            form.append("createdById", data.createdById);
        if (data.delivererId)
            form.append("delivererId", data.delivererId);
        if (data.pickupAddress)
            form.append("pickupAddress", data.pickupAddress);
        if (data.boxId)
            form.append("boxId", data.boxId);
        if (data.state && (data.state != data.oldState))
            form.append("state", data.state);

        return axios.patch(API_URL + "/delivery/" + id, form, { headers: authHeader() });
    }

    delete(id) {
        return axios.delete(API_URL + "/delivery/" + id, { headers: authHeader() });
    }
}

export default new DeliveryDataService();