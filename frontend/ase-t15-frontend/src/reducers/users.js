import {
    GET_USERS,
    GET_CUSTOMERS,
    GET_DELIVERERS,
    GET_TOKENS,
    CREATE_TOKEN
} from "../actions/types";

const initialState = {
    users: [],
    customers: [],
    deliverers: [],
};

function userReducer(users = initialState, action) {
    const { type, payload } = action;

    switch (type) {
        case GET_CUSTOMERS:
            return { ...users, customers: payload };

        case GET_DELIVERERS:
            return { ...users, deliverers: payload };

        case GET_USERS:
            return { ...users, users: payload };

        case GET_TOKENS:
            return { ...users, tokens: payload };

        case CREATE_TOKEN:
            console.log(payload);
            return { ...users, tokens: [...users.tokens, payload] };

        default:
            return users;
    }
}

export default userReducer;