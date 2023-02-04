import {
    GET_USERS,
    GET_CUSTOMERS,
    GET_DELIVERERS
} from "./types";

import UserDataService from "../api/user.service";

export const getCustomers = () => async (dispatch) => {
    try {
        const res = await UserDataService.getCustomers();

        dispatch({
            type: GET_CUSTOMERS,
            payload: res.data,
        });
    } catch (err) {
        console.log(err);
    }
}

export const getDeliverers = () => async (dispatch) => {
    try {
        const res = await UserDataService.getDeliverers();

        dispatch({
            type: GET_DELIVERERS,
            payload: res.data,
        });
    } catch (err) {
        console.log(err);
    }
}

export const getUsers = () => async (dispatch) => {
    try {
        const res = await UserDataService.getUsers();

        dispatch({
            type: GET_USERS,
            payload: res.data,
        });
    } catch (err) {
        console.log(err);
    }
}