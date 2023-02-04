import {
    CREATE_ORDER,
    RETRIEVE_ORDERS,
    RETRIEVE_ORDER,
    UPDATE_ORDER,
    DELETE_ORDER
} from "../actions/types";

const initialState = [];

function orderReducer(orders = initialState, action) {
    const { type, payload } = action;
    console.log("orderReducer: type: " + type + ", payload: " + payload);

    switch (type) {
        case CREATE_ORDER:
            return [...orders, payload];

        case RETRIEVE_ORDERS:
            return payload;

        case RETRIEVE_ORDER:
            return payload;

        case UPDATE_ORDER:
            return orders.map((order) => {
                if (order.id === payload.id) {
                    return {
                        ...order,
                        ...payload,
                    };
                } else {
                    return order;
                }
            });

        case DELETE_ORDER:
            return orders.filter(({ id }) => id !== payload.id);

        default:
            return orders;
    }
}

export default orderReducer;