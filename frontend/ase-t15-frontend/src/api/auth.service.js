import API_URL from "./common";
import axios from "axios";

class AuthService {
    login(username, password) {
        return axios
            .post(API_URL + "/auth", {},
                { auth: { username, password } }
            )
            .then((response) => {
                if (response.data.accessToken) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }

                return username;
            });
    }

    logout() {
        localStorage.removeItem("user");

        return axios
            .post(API_URL + "/auth/logout", {},
                {}
            )
    }

    register(username, email, password) {
        return axios.post(API_URL + "signup", {
            username,
            email,
            password,
        });
    }
}

export default new AuthService();