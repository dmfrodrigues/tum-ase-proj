import {
    GET_USERS,
    GET_CUSTOMERS,
    GET_DELIVERERS
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
            return { ...users, customers: [payload] };

        case GET_DELIVERERS:
            return { ...users, deliverers: [payload] };

        case GET_USERS:
            return { ...users, users: payload };

        default:
            return users;
    }
}

export default userReducer;