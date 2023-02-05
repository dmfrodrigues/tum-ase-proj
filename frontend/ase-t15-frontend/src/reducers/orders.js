import {
    CREATE_ORDER,
    RETRIEVE_ORDERS,
    RETRIEVE_ORDER,
    UPDATE_ORDER,
    DELETE_ORDER
} from "../actions/types";

const initialState = {
    orders: [],
    order: {},
};

function orderReducer(orders = initialState, action) {
    const { type, payload } = action;

    switch (type) {
        case CREATE_ORDER:
            return [...orders, payload];

        case RETRIEVE_ORDERS:
            return { orders: payload, order: orders.order };

        case RETRIEVE_ORDER:
            console.log("orders.orders: ", payload);
            return { orders: orders.orders, order: payload };

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