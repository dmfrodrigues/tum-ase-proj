import {
    CREATE_ORDER,
    RETRIEVE_ORDERS,
    RETRIEVE_ORDER,
    UPDATE_ORDER,
    DELETE_ORDER
} from "./types";

import DeliveryDataService from "../api/delivery.service";

export const createOrder = (customerId, createdById, delivererId, pickupAddress, boxId) => async (dispatch) => {
    try {
        const res = await DeliveryDataService.create({ customerId, createdById, delivererId, pickupAddress, boxId });

        dispatch({
            type: CREATE_ORDER,
            payload: res.data,
        });

        return Promise.resolve(res.data);
    } catch (err) {
        return Promise.reject(err);
    }
}

export const getOrders = () => async (dispatch) => {
    try {
        const res = await DeliveryDataService.getAll();

        dispatch({
            type: RETRIEVE_ORDERS,
            payload: res.data,
        });
    } catch (err) {
        console.log(err);
    }
}

export const retrieveOrder = (id) => async (dispatch) => {
    try {
        const res = await DeliveryDataService.get(id);

        dispatch({
            type: RETRIEVE_ORDER,
            payload: res.data,
        });
    } catch (err) {
        console.log(err);
    }
}

export const editOrder = (id, data) => async (dispatch) => {
    try {
        const res = await DeliveryDataService.update(id, data);

        dispatch({
            type: UPDATE_ORDER,
            payload: data,
        });

        return Promise.resolve(res.data);
    } catch (err) {
        return Promise.reject(err);
    }
}

export const deleteOrder = (id) => async (dispatch) => {
    try {
        await DeliveryDataService.delete(id);

        dispatch({
            type: DELETE_ORDER,
            payload: { id },
        });
    } catch (err) {
        console.log(err);
    }
}