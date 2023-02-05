import {
    GET_USERS,
    GET_CUSTOMERS,
    GET_DELIVERERS,
    GET_TOKENS,
    CREATE_TOKEN
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

export const createUser = (data) => async (dispatch) => {
    try {
        const res = await UserDataService.create(data);

        dispatch({
            type: GET_USERS,
            payload: res.data,
        });
    } catch (err) {
        console.log(err);
    }
}

export const editUser = (data) => async (dispatch) => {
    try {
        const res = await UserDataService.edit(data);

        dispatch({
            type: GET_USERS,
            payload: res.data,
        });
    } catch (err) {
        console.log(err);
    }
}

export const deleteUser = (id) => async (dispatch) => {
    try {
        const res = await UserDataService.delete(id);

        dispatch({
            type: GET_USERS,
            payload: res.data,
        });
    } catch (err) {
        console.log(err);
    }
}

export const getTokens = () => async (dispatch) => {
    try {
        const res = await UserDataService.getTokens();

        dispatch({
            type: GET_TOKENS,
            payload: res.data,
        });
    } catch (err) {
        console.log(err);
    }
}

export const createToken = (data) => async (dispatch) => {
    try {
        const res = await UserDataService.createToken(data);

        dispatch({
            type: CREATE_TOKEN,
            payload: data,
        });
    } catch (err) {
        console.log(err);
    }
}