import {
    GET_CUSTOMERS,
    GET_DELIVERERS
} from "../actions/types";

const initialState = [];

function userReducer(users = initialState, action) {
    const { type, payload } = action;

    switch (type) {
        case GET_CUSTOMERS:
            return { ...users, customers: [payload] };

        case GET_DELIVERERS:
            return { ...users, deliverers: [payload] };

        default:
            return users;
    }
}

export default userReducer;